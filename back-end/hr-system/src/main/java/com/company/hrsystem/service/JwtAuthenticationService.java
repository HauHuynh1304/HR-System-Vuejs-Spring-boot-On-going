package com.company.hrsystem.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.company.hrsystem.request.JwtAuthenRequest;
import com.company.hrsystem.response.JwtResponse;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.utils.JwtTokenUtil;
import com.company.hrsystem.utils.MessageUtil;

@Service
public class JwtAuthenticationService {

	@Value("${system.name}")
	private String sytem;

	@Value("${system.version}")
	private String verion;

	@Value("${token.store}")
	private String tokenStore;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtRefreshService jwtRefreshService;

	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	CacheService cacheService;

	public ResponseTemplate handleJwtAuthen(@RequestBody JwtAuthenRequest req) throws Exception {
		String email = req.getData().getUsername();
		String password = req.getData().getPassword();
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String accessToken = jwtTokenUtil.generateJWT(userDetails);
		String refreshToken = jwtRefreshService.generateRefreshTokenByEmail(email).getRefreshTokenName();
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("user.login.successful"), null,
				new JwtResponse(accessToken, refreshToken));
	}

	public ResponseTemplate handleLogOut(HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		SecurityContextHolder.getContext().setAuthentication(null);
		cacheService.deleteCache(tokenStore, token);
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("user.logout.successful"), null, null);
	}
}
