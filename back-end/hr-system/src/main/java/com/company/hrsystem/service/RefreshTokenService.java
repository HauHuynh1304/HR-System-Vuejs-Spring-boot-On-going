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

import com.company.hrsystem.Exeption.TokenException;
import com.company.hrsystem.dto.JwtDto;
import com.company.hrsystem.dto.RefreshTokenDto;
import com.company.hrsystem.mapper.ResfreshTokenMapper;
import com.company.hrsystem.mapper.SystemAccountMapper;
import com.company.hrsystem.request.ResfreshTokenRequest;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.utils.TokenUtil;
import com.company.hrsystem.utils.MessageUtil;

import io.jsonwebtoken.impl.DefaultClaims;

@Service
public class RefreshTokenService {

	@Autowired
	SystemAccountMapper systemAccountMapper;

	@Autowired
	ResfreshTokenMapper refreshTokenMapper;

	@Value("${jwt.refreshValid}")
	private Long refreshTokenValid;

	@Value("${system.name}")
	private String sytem;

	@Value("${system.name}")
	private String system;

	@Value("${system.version}")
	private String verion;

	@Value("${token.store}")
	private String tokenStore;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	CacheService cacheService;

	public RefreshTokenDto findRefreshTokenByEmail(String email) {
		return refreshTokenMapper.findRefreshTokenByEmail(email);
	}

	public RefreshTokenDto findRefreshTokenByToken(String token) {
		return refreshTokenMapper.findRefreshTokenByToken(token);
	}

	public RefreshTokenDto generateRefreshTokenByEmail(String email) {
		RefreshTokenDto model = findRefreshTokenByEmail(email);
		RefreshTokenDto obj = new RefreshTokenDto();
		obj.setSystemAccountId(systemAccountMapper.findSystemAccountIdByEmail(email).getSystemAccountId());
		obj.setExpiryDate(Instant.now().plusMillis(refreshTokenValid).toString());
		obj.setRefreshTokenName(UUID.randomUUID().toString());
		if (ObjectUtils.isEmpty(model)) {
			// create new refresh token at the first time login
			refreshTokenMapper.insertRefreshToken(obj);
		} else {
			// update new refresh token when login from second time
			refreshTokenMapper.updateRefreshToken(obj);
		}
		return obj;
	}

	public ResponseTemplate verifyExpirationRefreshToken(RefreshTokenDto model, HttpServletRequest httpServletRequest) {
		Instant expiredRefreshToken = Instant.parse(model.getExpiryDate());
		Boolean isExpiredRefreshToken = expiredRefreshToken.compareTo(Instant.now()) < 0;
		if (!isExpiredRefreshToken) {
			DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) httpServletRequest.getAttribute("claims");
			if (!ObjectUtils.isEmpty(claims)) {
				// Delete old token from cache
				cacheService.deleteCache(tokenStore, tokenUtil.getTokenFromHeader(httpServletRequest));

				// Create new token
				Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
				String newAccessToken = tokenUtil.doGenerateJWT(expectedMap, expectedMap.get("sub").toString());
				return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
						messageUtil.getMessagelangUS("refresh.success"), null,
						new JwtDto(newAccessToken, model.getRefreshTokenName()));
			} else {
				return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
						messageUtil.getMessagelangUS("valid.tokens"), null, null);
			}
		} else {
			throw new TokenException(system, verion, messageUtil.getMessagelangUS("expired.refresh.token"));
		}
	}

	public ResponseTemplate handleRefreshToken(ResfreshTokenRequest resfreshTokenRequest,
			HttpServletRequest httpServletRequest) {
		RefreshTokenDto model = findRefreshTokenByToken(resfreshTokenRequest.getData().getRefreshTokenName());
		if (ObjectUtils.isEmpty(model)) {
			throw new TokenException(system, verion, messageUtil.getMessagelangUS("not.valid.refresh.token"));
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
