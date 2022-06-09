package com.company.hrsystem.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.hrsystem.constants.ApiUrlConstant;
import com.company.hrsystem.request.AuthenRequest;
import com.company.hrsystem.request.ChangePasswordRequest;
import com.company.hrsystem.request.IsEmailInDbRequest;
import com.company.hrsystem.request.ResfreshTokenRequest;
import com.company.hrsystem.request.SignUpRequest;
import com.company.hrsystem.request.UpdateAccountRequest;
import com.company.hrsystem.service.AuthenticationService;
import com.company.hrsystem.service.RefreshTokenService;

@RestController
@CrossOrigin
@RequestMapping(ApiUrlConstant.ROOT_API)
public class AuthenticationController {

	@Autowired
	AuthenticationService authenService;

	@Autowired
	RefreshTokenService refreshTokenService;

	@PostMapping(ApiUrlConstant.AUTHEN_LOG_IN)
	public ResponseEntity<?> login(@RequestBody AuthenRequest request, HttpServletRequest servletRequest)
			throws Exception {
		return ResponseEntity.ok(authenService.handleLogin(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.AUTHEN_REFRESH_TOKEN)
	public ResponseEntity<?> refreshtoken(@RequestBody ResfreshTokenRequest resfreshTokenRequest,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(refreshTokenService.handleRefreshToken(resfreshTokenRequest, servletRequest));
	}

	@PostMapping(ApiUrlConstant.AUTHEN_SIGN_UP)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest request, HttpServletRequest servletRequest) {
		return ResponseEntity.ok(authenService.handleSignUp(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.AUTHEN_LOG_OUT)
	public ResponseEntity<?> logOut(HttpServletRequest request) {
		return ResponseEntity.ok(authenService.handleLogOut(request));
	}

	@PostMapping(ApiUrlConstant.AUTHEN_CHANGE_PASSWORD)
	public ResponseEntity<?> changePassword(HttpServletRequest servletRequest,
			@RequestBody ChangePasswordRequest ChangePwRequest) {
		return ResponseEntity.ok(authenService.handleChangePassword(servletRequest, ChangePwRequest));
	}

	@PostMapping(ApiUrlConstant.AUTHEN_FIND_ACCOUNTS)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> findAccounts(HttpServletRequest servletRequest) {
		return ResponseEntity.ok(authenService.findAccounts(servletRequest));
	}

	@PostMapping(ApiUrlConstant.AUTHEN_UPDATE_ACCOUNT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateAccount(@RequestBody UpdateAccountRequest accountRequest,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(authenService.updateAccount(accountRequest, servletRequest));
	}
	
	@PostMapping(ApiUrlConstant.AUTHEN_IS_EMAIL_IN_DB)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> isEmailInDb(@RequestBody IsEmailInDbRequest request) {
		return ResponseEntity.ok(authenService.isEmailInDb(request));
	}
}
