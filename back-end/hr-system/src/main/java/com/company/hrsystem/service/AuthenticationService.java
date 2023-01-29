package com.company.hrsystem.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.annotations.WriteLogToDB;
import com.company.hrsystem.config.SystemProperties;
import com.company.hrsystem.dto.AuthenAccountDto;
import com.company.hrsystem.dto.AuthenRoleDto;
import com.company.hrsystem.dto.JwtDto;
import com.company.hrsystem.dto.SystemAccountDto;
import com.company.hrsystem.mapper.Impl.SystemAccountMapperImpl;
import com.company.hrsystem.mapper.Impl.SystemAccountRoleMapperImpl;
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
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RefreshTokenService jwtRefreshService;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private SystemAccountMapperImpl systemAccountMapperImpl;

	@Autowired
	private SystemAccountRoleMapperImpl systemAccountRoleMapperImpl;

	@Autowired
	private JWTService jwtService;

	@WriteLogToDB
	public ResponseTemplate handleLogin(AuthenRequest request, HttpServletRequest servletRequest) {
		String email = request.getData().getUsername();
		String password = request.getData().getPassword();
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		String accessToken = jwtService.generateJWT(userDetailsImpl);
		String refreshToken = jwtRefreshService.generateRefreshTokenByEmail(email).getRefreshTokenName();
		
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("user.login.successful"), null,
				new JwtDto(accessToken, refreshToken));
	}

	@WriteLogToDB
	public ResponseTemplate handleLogOut(HttpServletRequest servletRequest) {
		cacheService.deleteCache(SystemProperties.TOKEN_STORE,
				jwtService.getUsernameFromToken(jwtService.getTokenFromHeader(servletRequest)));
		
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("user.logout.successful"), null, null);
	}

	@Transactional
	@WriteLogToDB
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
		
		systemAccountMapperImpl.insertSelective(systemAccount);

		systemAccountRoleMapperImpl.insertAccountRole(systemAccount, roleIds);

		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("user.signup.successful"), null, null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate handleChangePassword(ChangePasswordRequest changePwRequest,
			HttpServletRequest servletRequest) {
		SystemAccountDto systemAccountDto = changePwRequest.getData().getAccount();
		String emailFromToken = jwtService.getUsernameFromToken(jwtService.getTokenFromHeader(servletRequest));
		String emailFromRequest = systemAccountDto.getSystemEmail();
		if (emailFromToken.equals(emailFromRequest) || AuthenUtil.isAuthen(SystemProperties.SYSTEM_ROLE_ADMIN)
				|| AuthenUtil.isAuthen(SystemProperties.SYSTEM_ROLE_ROOT_ADMIN)) {
			systemAccountDto.setSystemPassword(passwordEncoder.encode(systemAccountDto.getSystemPassword()));

			systemAccountMapperImpl.updateByEmailSelective(systemAccountDto);

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
				systemAccountMapperImpl.findAccounts());
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate updateAccount(UpdateAccountRequest accountRequest, HttpServletRequest servletRequest) {
		Timestamp updatedAt = DateUtil.getCurrentDayHourSecond();
		SystemAccountDto account = accountRequest.getData().getAccount();
		AuthenAccountDto targetAccount = systemAccountMapperImpl.findAuthenAccountById(account.getSystemAccountId());
		Set<AuthenRoleDto> roles = targetAccount.getRoles();
		Boolean isRootAdminTargetAccount = roles.stream()
				.anyMatch(r -> r.getRoleName().equals(SystemProperties.SYSTEM_ROLE_ROOT_ADMIN));
		if (isRootAdminTargetAccount && !AuthenUtil.isAuthen(SystemProperties.SYSTEM_ROLE_ROOT_ADMIN)) {
			// Only root_admin account can update root_admin account
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("update.root.account.err"));
		}
		
		// update password and account's status
		if (ObjectUtil.countNotNullParamater(account) > 1) {
			if (StringUtils.isNotEmpty(account.getSystemPassword())) {
				account.setSystemPassword(passwordEncoder.encode(account.getSystemPassword()));
			}
			systemAccountMapperImpl.updateAccount(account);
		}
		
		// update roles start
		if (accountRequest.getData().getDeleteRoleIds().length > 0) {
			Integer[] roleIds = accountRequest.getData().getDeleteRoleIds();
			// Remove duplicate
			roleIds = Arrays.stream(roleIds).distinct().toArray(Integer[]::new);
			systemAccountRoleMapperImpl.delAccoutRoleById(account, roleIds, updatedAt);
		}
		
		if (accountRequest.getData().getAddNewRoleIds().length > 0) {
			Integer[] addNewRoleIds = accountRequest.getData().getAddNewRoleIds();
			// Remove duplicate
			addNewRoleIds = Arrays.stream(addNewRoleIds).distinct().toArray(Integer[]::new);
			List<Integer> existRoles = systemAccountRoleMapperImpl.findRolesBySystemAccountId(account.getSystemAccountId());
			
			Integer[] enableRoles = Arrays.stream(addNewRoleIds).filter(roleID -> ArrayUtils.contains(existRoles.toArray(), roleID)).toArray(Integer[]::new);
			Integer[] newRoles = Arrays.stream(addNewRoleIds).filter(roleID -> !ArrayUtils.contains(existRoles.toArray(), roleID)).toArray(Integer[]::new);
			
			if (enableRoles.length > 0) {
				systemAccountRoleMapperImpl.enableAccoutRoleById(account, enableRoles, updatedAt);
			}
			
			if (newRoles.length > 0) {
				systemAccountRoleMapperImpl.insertAccountRole(account, newRoles);
			} 
		}
		// update roles end
		
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("update.success"), null, null);
	}

	public ResponseTemplate isEmailInDb(IsEmailInDbRequest request) {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				systemAccountMapperImpl.isEmailInDb(request.getData().getEmail()));
	}

}
