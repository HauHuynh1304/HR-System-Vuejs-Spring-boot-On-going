package com.company.hrsystem.service;

import java.util.Arrays;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.ObjectUtils;

import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.config.SystemProperties;
import com.company.hrsystem.constants.CommonConstant;
import com.company.hrsystem.dto.AuthenAccountDto;
import com.company.hrsystem.dto.AuthenRoleDto;
import com.company.hrsystem.dto.JwtDto;
import com.company.hrsystem.dto.SystemAccountDto;
import com.company.hrsystem.dto.SystemAccountRoleDto;
import com.company.hrsystem.mapper.EmployeeMapper;
import com.company.hrsystem.mapper.SystemAccountMapper;
import com.company.hrsystem.mapper.SystemAccountRoleMapper;
import com.company.hrsystem.request.AuthenRequest;
import com.company.hrsystem.request.ChangePasswordRequest;
import com.company.hrsystem.request.IsEmailInDbRequest;
import com.company.hrsystem.request.SignUpRequest;
import com.company.hrsystem.request.UpdateAccountRequest;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.serviceInterface.AuthenticationServiceInterface;
import com.company.hrsystem.utils.AuthenUtil;
import com.company.hrsystem.utils.DateUtil;
import com.company.hrsystem.utils.MessageUtil;
import com.company.hrsystem.utils.ObjectUtil;

@Service
public class AuthenticationService implements AuthenticationServiceInterface {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RefreshTokenService jwtRefreshService;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SystemAccountMapper accountMapper;

	@Autowired
	private SystemAccountRoleMapper accountRoleMapper;

	@Autowired
	private HistoryActionService historyActionService;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private SystemAccountMapper systemAccountMapper;

	@Autowired
	private JWTService jwtService;

	public ResponseTemplate handleLogin(AuthenRequest req, HttpServletRequest servletRequest) {
		String email = req.getData().getUsername();
		String password = req.getData().getPassword();
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		String accessToken = jwtService.generateJWT(userDetailsImpl);
		String refreshToken = jwtRefreshService.generateRefreshTokenByEmail(email).getRefreshTokenName();
		historyActionService.saveHistoryAction(null, CommonConstant.ZERO_VALUE, CommonConstant.LOGIN_ACTION,
				CommonConstant.ZERO_VALUE, null, servletRequest);
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("user.login.successful"), null,
				new JwtDto(accessToken, refreshToken));
	}

	public ResponseTemplate handleLogOut(HttpServletRequest request) {
		cacheService.deleteCache(SystemProperties.TOKEN_STORE,
				jwtService.getUsernameFromToken(jwtService.getTokenFromHeader(request)));
		historyActionService.saveHistoryAction(null, CommonConstant.ZERO_VALUE, CommonConstant.LOGOUT_ACTION,
				CommonConstant.ZERO_VALUE, null, request);
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("user.logout.successful"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate handleSignUp(SignUpRequest request, HttpServletRequest servletRequest) {
		SystemAccountDto systemAccount = request.getData().getAccount();
		Integer[] roleIds = request.getData().getRoleIds();
		// Remove duplicate
		roleIds = Arrays.stream(roleIds).distinct().toArray(Integer[]::new);

		// Only root account can insert new root account
		Boolean isIncludesRoleRootAdmin = Arrays.stream(roleIds).anyMatch(roleId -> roleId == 1);
		if (isIncludesRoleRootAdmin && !AuthenUtil.isAuthen(SystemProperties.SYSTEM_ROLE_ROOT_ADMIN)) {
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("insert.root.account.err"));
		}

		systemAccount.setSystemPassword(passwordEncoder.encode(systemAccount.getSystemPassword()));
		int employeeId = employeeMapper.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
		accountMapper.insertSelective(systemAccount);
		historyActionService.saveHistoryAction(systemAccount, employeeId, CommonConstant.INSERT_ACTION,
				systemAccount.getSystemAccountId(), CommonConstant.TABLE_SYSTEM_ACCOUNT, servletRequest);

		accountRoleMapper.insertAccountRole(systemAccount, roleIds);
		Set<SystemAccountRoleDto> accountRoleDtos = accountRoleMapper
				.findNewestRecordsByCurrentUser(systemAccount.getSystemAccountId());
		for (SystemAccountRoleDto systemAccountRoleDto : accountRoleDtos) {
			historyActionService.saveHistoryAction(systemAccountRoleDto, employeeId, CommonConstant.INSERT_ACTION,
					systemAccountRoleDto.getSystemAccountRoleId(), CommonConstant.TABLE_SYSTEM_ACCOUNT_ROLE,
					servletRequest);
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("user.signup.successful"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate handleChangePassword(HttpServletRequest servletRequest,
			ChangePasswordRequest ChangePwRequest) {
		String emailFromToken = jwtService.getUsernameFromToken(jwtService.getTokenFromHeader(servletRequest));
		String emailFromRequest = ChangePwRequest.getData().getAccount().getSystemEmail();
		if (emailFromToken.equals(emailFromRequest) || AuthenUtil.isAuthen(SystemProperties.SYSTEM_ROLE_ADMIN)
				|| AuthenUtil.isAuthen(SystemProperties.SYSTEM_ROLE_ROOT_ADMIN)) {
			String password = passwordEncoder.encode(ChangePwRequest.getData().getAccount().getSystemPassword());
			SystemAccountDto account = new SystemAccountDto(null, emailFromRequest, password, null,
					DateUtil.getCurrentDayHourSecond());
			accountMapper.updateByEmailSelective(account);

			int targetRowId = 0;
			if (emailFromToken.equals(emailFromRequest)) {
				targetRowId = AuthenUtil.getAccountId();
			}
			if (AuthenUtil.isAuthen(SystemProperties.SYSTEM_ROLE_ADMIN) || AuthenUtil.isAuthen(SystemProperties.SYSTEM_ROLE_ROOT_ADMIN)) {
				targetRowId = systemAccountMapper.findSystemAccountIdByEmail(emailFromRequest);
			}
			historyActionService.saveHistoryAction(account, CommonConstant.ZERO_VALUE, CommonConstant.CHANGE_PW_ACTION,
					targetRowId, CommonConstant.TABLE_SYSTEM_ACCOUNT, servletRequest);
			return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					HttpStatus.OK.value(), MessageUtil.getMessagelangUS("change.password.success"), null, null);
		} else {
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("not.correct.email"));
		}
	}

	public ResponseTemplate findAccounts(HttpServletRequest servletRequest) {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("change.password.success"), null,
				systemAccountMapper.findAccounts());
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate updateAccount(UpdateAccountRequest accountRequest, HttpServletRequest servletRequest) {
		int updaterId = employeeMapper.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
		SystemAccountDto account = accountRequest.getData().getAccount();
		// Only root_admin account can update root_admin account
		AuthenAccountDto targetAccount = systemAccountMapper.findAuthenAccountById(account.getSystemAccountId());
		Set<AuthenRoleDto> roles = targetAccount.getRoles();
		Boolean isRootAdminTargetAccount = roles.stream()
				.anyMatch(r -> r.getRoleName().equals(SystemProperties.SYSTEM_ROLE_ROOT_ADMIN));
		if (isRootAdminTargetAccount && !AuthenUtil.isAuthen(SystemProperties.SYSTEM_ROLE_ROOT_ADMIN)) {
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("update.root.account.err"));
		}

		if (ObjectUtil.countNotNullParamater(account) > 1) {
			if (ObjectUtils.isNotEmpty(account.getSystemPassword())) {
				account.setSystemPassword(passwordEncoder.encode(account.getSystemPassword()));
			}
			systemAccountMapper.updateAccount(account);
			historyActionService.saveHistoryAction(account, updaterId, CommonConstant.UPDATE_ACTION,
					account.getSystemAccountId(), CommonConstant.TABLE_SYSTEM_ACCOUNT, servletRequest);
		}
		if (accountRequest.getData().getDeleteRoleIds().length > 0) {
			Integer[] deletedRoleIds = accountRequest.getData().getDeleteRoleIds();
			// Remove duplicate
			deletedRoleIds = Arrays.stream(deletedRoleIds).distinct().toArray(Integer[]::new);
			accountRoleMapper.delAccoutRoleById(deletedRoleIds);
			for (Integer i : deletedRoleIds) {
				SystemAccountRoleDto obj = new SystemAccountRoleDto();
				obj.setSystemAccountRoleId(i);
				obj.setDeletedFlag(true);
				historyActionService.saveHistoryAction(obj, updaterId, CommonConstant.DELETE_ACTION, i,
						CommonConstant.TABLE_SYSTEM_ACCOUNT_ROLE, servletRequest);
			}
		}
		if (accountRequest.getData().getAddNewRoleIds().length > 0) {
			Integer[] addNewRoleIds = accountRequest.getData().getAddNewRoleIds();
			// Remove duplicate
			addNewRoleIds = Arrays.stream(addNewRoleIds).distinct().toArray(Integer[]::new);
			accountRoleMapper.insertAccountRole(account, addNewRoleIds);
			Set<SystemAccountRoleDto> accountRoleDtos = accountRoleMapper
					.findNewestRecordsByCurrentUser(account.getSystemAccountId());
			for (SystemAccountRoleDto systemAccountRoleDto : accountRoleDtos) {
				historyActionService.saveHistoryAction(systemAccountRoleDto, updaterId, CommonConstant.INSERT_ACTION,
						systemAccountRoleDto.getSystemAccountRoleId(), CommonConstant.TABLE_SYSTEM_ACCOUNT_ROLE,
						servletRequest);
			}
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("update.success"), null, null);
	}

	public ResponseTemplate isEmailInDb(IsEmailInDbRequest request) {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				accountMapper.isEmailInDb(request.getData().getEmail()));
	}

}
