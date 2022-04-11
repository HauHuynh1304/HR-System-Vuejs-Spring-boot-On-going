package com.company.hrsystem.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.company.hrsystem.Exeption.RefreshTokenException;
import com.company.hrsystem.mapper.JwtResfreshMapper;
import com.company.hrsystem.mapper.SystemAccountMapper;
import com.company.hrsystem.model.JwtRefreshModel;
import com.company.hrsystem.request.JwtResfreshRequest;
import com.company.hrsystem.response.JwtResponse;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.utils.JwtTokenUtil;
import com.company.hrsystem.utils.MessageUtil;

import io.jsonwebtoken.impl.DefaultClaims;

@Service
public class JwtRefreshService {

	@Autowired
	UserDetailsServiceImp jwtUserDetailsService;

	@Autowired
	SystemAccountMapper systemAccountMapper;

	@Autowired
	JwtResfreshMapper jwtResfreshMapper;

	@Value("${jwt.refreshValid}")
	private Long refreshTokenValid;

	@Value("${system.name}")
	private String sytem;

	@Value("${system.version}")
	private String verion;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private MessageUtil messageUtil;

	public JwtRefreshModel findRefreshTokenByEmail(String email) {
		return jwtResfreshMapper.findRefreshTokenByEmail(email);
	}

	public JwtRefreshModel findRefreshTokenByToken(String token) {
		return jwtResfreshMapper.findRefreshTokenByToken(token);
	}

	public JwtRefreshModel generateRefreshTokenByEmail(String email) {
		JwtRefreshModel model = findRefreshTokenByEmail(email);
		JwtRefreshModel obj = new JwtRefreshModel();
		obj.setSystemAccountId(systemAccountMapper.findSystemAccountByEmail(email).getSystemAccountId());
		obj.setExpiryDate(Instant.now().plusMillis(refreshTokenValid).toString());
		obj.setRefreshTokenName(UUID.randomUUID().toString());
		if (ObjectUtils.isEmpty(model)) {
			// create new refresh token at the first time login
			jwtResfreshMapper.insertRefreshToken(obj);
		} else {
			// update new refresh token when login from second time
			jwtResfreshMapper.updateRefreshToken(obj);
		}
		return obj;
	}

	public ResponseTemplate verifyExpirationRefreshToken(JwtRefreshModel model, HttpServletRequest httpServletRequest) {
		Instant expiredRefreshToken = Instant.parse(model.getExpiryDate());
		Boolean isExpiredRefreshToken = expiredRefreshToken.compareTo(Instant.now()) < 0;
		if (!isExpiredRefreshToken) {
			DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) httpServletRequest.getAttribute("claims");
			Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
			String newAccessToken = jwtTokenUtil.doGenerateJWT(expectedMap, expectedMap.get("sub").toString());
			return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
					messageUtil.getMessagelangUS("refresh.success"), null,
					new JwtResponse(newAccessToken, model.getRefreshTokenName()));
		} else {
			throw new RefreshTokenException(sytem, verion, messageUtil.getMessagelangUS("expired.refresh.token"));
		}
	}

	public ResponseTemplate handleRefreshToken(JwtResfreshRequest jwtResfreshRequest,
			HttpServletRequest httpServletRequest) {
		JwtRefreshModel model = findRefreshTokenByToken(jwtResfreshRequest.getRefreshTokenName());
		if (ObjectUtils.isEmpty(model)) {
			throw new RefreshTokenException(sytem, verion, messageUtil.getMessagelangUS("not.valid.refresh.token"));
		} else {
			return verifyExpirationRefreshToken(model, httpServletRequest);
		}
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

}
