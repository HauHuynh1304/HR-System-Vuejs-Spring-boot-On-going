package com.company.hrsystem.service.interfaces;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.company.hrsystem.dto.RefreshTokenDto;
import com.company.hrsystem.request.ResfreshTokenRequest;
import com.company.hrsystem.response.ResponseTemplate;

import io.jsonwebtoken.impl.DefaultClaims;

public interface IRefreshTokenService {

	public RefreshTokenDto findRefreshTokenByEmail(String email);

	public RefreshTokenDto findRefreshTokenByToken(String token);

	public RefreshTokenDto generateRefreshTokenByEmail(String email);

	public ResponseTemplate handleRefreshToken(ResfreshTokenRequest resfreshTokenRequest,
			HttpServletRequest httpServletRequest);

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims);
}
