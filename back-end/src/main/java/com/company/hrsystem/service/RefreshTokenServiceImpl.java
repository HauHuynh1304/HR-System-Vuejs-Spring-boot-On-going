package com.company.hrsystem.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.company.hrsystem.commons.configs.SystemProperties;
import com.company.hrsystem.commons.exceptions.TokenException;
import com.company.hrsystem.commons.utils.AuthenUtil;
import com.company.hrsystem.commons.utils.MathUtil;
import com.company.hrsystem.commons.utils.MessageUtil;
import com.company.hrsystem.dto.JwtDto;
import com.company.hrsystem.dto.RefreshTokenDto;
import com.company.hrsystem.mapper.Impl.RefreshTokkenMapperImpl;
import com.company.hrsystem.request.ResfreshTokenRequest;
import com.company.hrsystem.response.ResponseData;
import com.company.hrsystem.service.interfaces.IRefreshTokenService;

import io.jsonwebtoken.impl.DefaultClaims;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

	@Autowired
	private RefreshTokkenMapperImpl refreshTokenMapperImpl;

	@Autowired
	JWTServiceImpl jwtService;

	public RefreshTokenDto findRefreshTokenByEmail(String email) {
		return refreshTokenMapperImpl.findRefreshTokenByEmail(email);
	}

	public RefreshTokenDto findRefreshTokenByToken(String token) {
		return refreshTokenMapperImpl.findRefreshTokenByToken(token);
	}

	public RefreshTokenDto generateRefreshTokenByEmail(String email) {
		RefreshTokenDto model = findRefreshTokenByEmail(email);
		RefreshTokenDto obj = new RefreshTokenDto();
		obj.setSystemAccountId(AuthenUtil.getAccountId());
		obj.setExpiryDate(Instant.now()
				.plusMillis(MathUtil.calculateFromString(SystemProperties.JWT_REFRESH_VALID_TIME)).toString());
		obj.setRefreshTokenName(UUID.randomUUID().toString());
		if (ObjectUtils.isEmpty(model)) {
			// create new refresh token at the first time login
			refreshTokenMapperImpl.insertRefreshToken(obj);
		} else {
			// update new refresh token when login from second time
			refreshTokenMapperImpl.updateRefreshToken(obj);
		}
		return obj;
	}

	public ResponseData handleRefreshToken(ResfreshTokenRequest resfreshTokenRequest,
			HttpServletRequest httpServletRequest) {
		RefreshTokenDto model = findRefreshTokenByToken(resfreshTokenRequest.getData().getRefreshTokenName());
		if (ObjectUtils.isEmpty(model)) {
			throw new TokenException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("not.valid.refresh.token"));
		} else {
			DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) httpServletRequest
					.getAttribute(SystemProperties.JWT_ATTRIBUTE);
			if (ObjectUtils.isEmpty(claims)) {
				return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
						HttpStatus.OK.value(), MessageUtil.getMessagelangUS("valid.tokens"), null, null);
			} else {
				Instant expiredRefreshToken = Instant.parse(model.getExpiryDate());
				Boolean isExpiredRefreshToken = expiredRefreshToken.compareTo(Instant.now()) < 0;
				if (isExpiredRefreshToken) {
					throw new TokenException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
							MessageUtil.getMessagelangUS("expired.refresh.token"));
				} else {
					Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
					String newAccessToken = jwtService.doGenerateJWT(expectedMap, expectedMap.get("sub").toString());
					return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
							HttpStatus.OK.value(), MessageUtil.getMessagelangUS("refresh.success"), null,
							new JwtDto(newAccessToken, model.getRefreshTokenName()));
				}
			}
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
