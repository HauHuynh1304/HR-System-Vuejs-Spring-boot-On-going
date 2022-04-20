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
import com.company.hrsystem.request.ResfreshTokenRequest;
import com.company.hrsystem.request.SignUpRequest;
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
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenRequest request) throws Exception {
		return ResponseEntity.ok(authenService.handleJwtAuthen(request));
	}

	@PostMapping(ApiUrlConstant.AUTHEN_REFRESH_TOKEN)
	public ResponseEntity<?> refreshtoken(@RequestBody ResfreshTokenRequest resfreshTokenRequest,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(refreshTokenService.handleRefreshToken(resfreshTokenRequest, servletRequest));
	}

	@PostMapping(ApiUrlConstant.AUTHEN_SIGN_UP)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
		return ResponseEntity.ok(authenService.handleSignUp(request));
	}

	@PostMapping(ApiUrlConstant.AUTHEN_LOG_OUT)
	public ResponseEntity<?> LogOut(HttpServletRequest request) {
		return ResponseEntity.ok(authenService.handleLogOut(request));
	}

	@PostMapping(ApiUrlConstant.AUTHEN_CHANGE_PASSWORD)
	public ResponseEntity<?> changePassword(HttpServletRequest servletRequest,
			@RequestBody ChangePasswordRequest ChangePwRequest) {
		return ResponseEntity.ok(authenService.handleChangePassword(servletRequest, ChangePwRequest));
	}
}
