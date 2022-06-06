package com.company.hrsystem.service;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.constants.CommonConstant;
import com.company.hrsystem.dto.JwtDto;
import com.company.hrsystem.dto.SystemAccountDto;
import com.company.hrsystem.dto.SystemAccountRoleDto;
import com.company.hrsystem.mapper.EmployeeMapper;
import com.company.hrsystem.mapper.SystemAccountMapper;
import com.company.hrsystem.mapper.SystemAccountRoleMapper;
import com.company.hrsystem.request.AuthenRequest;
import com.company.hrsystem.request.ChangePasswordRequest;
import com.company.hrsystem.request.SignUpRequest;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.utils.TokenUtil;
import com.company.hrsystem.utils.AuthenUtil;
import com.company.hrsystem.utils.DateUtil;
import com.company.hrsystem.utils.MessageUtil;

@Service
public class AuthenticationService {

	@Value("${system.name}")
	private String system;

	@Value("${system.version}")
	private String version;

	@Value("${token.store}")
	private String tokenStore;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private RefreshTokenService jwtRefreshService;

	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private AuthenUtil authenUtil;

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

	public ResponseTemplate handleLogin(AuthenRequest req, HttpServletRequest servletRequest) throws Exception {
		String email = req.getData().getUsername();
		String password = req.getData().getPassword();
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		String accessToken = tokenUtil.generateJWT(userDetailsImpl);
		String refreshToken = jwtRefreshService.generateRefreshTokenByEmail(email).getRefreshTokenName();
		historyActionService.saveHistoryAction(null, CommonConstant.ZERO_VALUE, CommonConstant.LOGIN_ACTION,
				CommonConstant.ZERO_VALUE, null, servletRequest);
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("user.login.successful"), null, new JwtDto(accessToken, refreshToken));
	}

	public ResponseTemplate handleLogOut(HttpServletRequest request) {
		cacheService.deleteCache(tokenStore, tokenUtil.getUsernameFromToken(tokenUtil.getTokenFromHeader(request)));
		historyActionService.saveHistoryAction(null, CommonConstant.ZERO_VALUE, CommonConstant.LOGOUT_ACTION,
				CommonConstant.ZERO_VALUE, null, request);
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("user.logout.successful"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate handleSignUp(SignUpRequest request, HttpServletRequest servletRequest) {
		SystemAccountDto systemAccount = request.getData().getAccount();
		Integer[] roleIds = request.getData().getRoleIds();
		systemAccount.setSystemPassword(passwordEncoder.encode(systemAccount.getSystemPassword()));
		int employeeId = employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId());
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
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("user.signup.successful"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate handleChangePassword(HttpServletRequest servletRequest,
			ChangePasswordRequest ChangePwRequest) {
		String emailFromToken = tokenUtil.getUsernameFromToken(tokenUtil.getTokenFromHeader(servletRequest));
		String emailFromRequest = ChangePwRequest.getData().getAccount().getSystemEmail();
		if (emailFromToken.equals(emailFromRequest) || authenUtil.isAuthen(CommonConstant.ROOT_ROLE)) {
			String password = passwordEncoder.encode(ChangePwRequest.getData().getAccount().getSystemPassword());
			SystemAccountDto account = new SystemAccountDto(null, emailFromRequest, password, null, null,
					DateUtil.getCurrentDayHourSecond());
			accountMapper.updateByEmailSelective(account);

			int targetRowId = 0;
			if (emailFromToken.equals(emailFromRequest)) {
				targetRowId = authenUtil.getAccountId();
			}
			if (authenUtil.isAuthen(CommonConstant.ROOT_ROLE)) {
				targetRowId = systemAccountMapper.findSystemAccountIdByEmail(emailFromRequest);
			}
			historyActionService.saveHistoryAction(account, CommonConstant.ZERO_VALUE, CommonConstant.CHANGE_PW_ACTION,
					targetRowId, CommonConstant.TABLE_SYSTEM_ACCOUNT, servletRequest);
			return new ResponseTemplate(system, version, HttpStatus.OK.value(),
					messageUtil.getMessagelangUS("change.password.success"), null, null);
		} else {
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("not.correct.email"));
		}
	}
}
