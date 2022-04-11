package com.company.hrsystem.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.hrsystem.constants.ApiUrlConstant;
import com.company.hrsystem.request.JwtAuthenRequest;
import com.company.hrsystem.request.JwtResfreshRequest;
import com.company.hrsystem.service.JwtAuthenticationService;
import com.company.hrsystem.service.JwtRefreshService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	JwtAuthenticationService jwtAuthenService;

	@Autowired
	JwtRefreshService jwtRefreshService;

	@PostMapping(ApiUrlConstant.LOG_IN)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenRequest authenticateRequest)
			throws Exception {
		return ResponseEntity.ok(jwtAuthenService.handleJwtAuthen(authenticateRequest));
	}

	@PostMapping(ApiUrlConstant.REFRESH_TOKEN)
	public ResponseEntity<?> refreshtoken(@RequestBody JwtResfreshRequest jwtResfreshRequest,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(jwtRefreshService.handleRefreshToken(jwtResfreshRequest, servletRequest));
	}

	@PostMapping(ApiUrlConstant.SIGN_UP)
	public ResponseEntity<?> signUp(@RequestBody Object req) {

		return null;
	}
	
	@PostMapping(ApiUrlConstant.LOG_OUT)
	public ResponseEntity<?> LogOut(HttpServletRequest request) {
		return ResponseEntity.ok(jwtAuthenService.handleLogOut(request));
	}
}
