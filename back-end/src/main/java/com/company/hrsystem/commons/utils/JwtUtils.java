package com.company.hrsystem.commons.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;

import com.company.hrsystem.commons.configs.SystemProperties;
import com.company.hrsystem.service.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {

	public static String getUsername(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public static String setTokenBody(UserDetailsImpl userDetails) {
		final String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", userDetails.getId());
		claims.put("roles", authorities);
		claims.put("maxValidTime", MathUtil.calculateFromString(SystemProperties.JWT_REFRESH_VALID_TIME));
		return generateToken(claims, userDetails.getUsername());
	}

	public static String generateToken(Map<String, Object> claims, String subject) {
		String token = Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()
						+ MathUtil.calculateFromString(SystemProperties.JWT_ACCESS_VALID_TIME)))
				.signWith(SignatureAlgorithm.HS512, SystemProperties.JWT_SECRET).compact();
		CacheUtils.updateCache(SystemProperties.TOKEN_STORE, subject, token);
		return token;
	}

	// Token is in the form "Bearer token"
	public static String getTokenInBearerForm(HttpServletRequest request) {
		return getToken(request).substring(7);
	}

	public static String getToken(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}

	// Decoded JWT
	public static Claims getTokenBody(String token) {
		return Jwts.parser().setSigningKey(SystemProperties.JWT_SECRET).parseClaimsJws(token).getBody();
	}

	private static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getTokenBody(token);
		return claimsResolver.apply(claims);
	}

}
