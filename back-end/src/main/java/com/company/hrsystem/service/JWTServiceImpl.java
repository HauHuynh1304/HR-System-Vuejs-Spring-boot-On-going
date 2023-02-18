package com.company.hrsystem.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.company.hrsystem.commons.configs.SystemProperties;
import com.company.hrsystem.commons.utils.MathUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTServiceImpl implements Serializable {

	private static final long serialVersionUID = -3219569059042848051L;

	@Autowired
	private CacheServiceImpl cacheService;

	// Get user name from JWT token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// Get expiration time from JWT token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	// Check expired token
	public Boolean isTokenExprived(String token) {
		final Date expriration = getExpirationDateFromToken(token);
		return expriration.before(expriration);
	}

	// Validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExprived(token));
	}

	// Generate JWT
	public String generateJWT(UserDetailsImpl userDetails) {
		final String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		Map<String, Object> claims = new HashMap<>();
		claims.put(SystemProperties.JWT_PAYLOAD_ID, userDetails.getId());
		claims.put(SystemProperties.JWT_PAYLOAD_ROLES, authorities);
		claims.put(SystemProperties.JWT_PAYLOAD_MAX_VALID_TIME, 
				MathUtil.calculateFromString(SystemProperties.JWT_REFRESH_VALID_TIME));
		return doGenerateJWT(claims, userDetails.getUsername());
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	public String doGenerateJWT(Map<String, Object> claims, String subject) {
		String token = Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + MathUtil.calculateFromString(SystemProperties.JWT_ACCESS_VALID_TIME)))
				.signWith(SignatureAlgorithm.HS512, SystemProperties.JWT_SECRET).compact();
		cacheService.updateCache(SystemProperties.TOKEN_STORE, subject, token);
		return token;
	}

	public String getTokenFromHeader(HttpServletRequest request) {
		return request.getHeader(SystemProperties.TOKEN_AUTHORIZATION).substring(7);
	}

	public String getHeaderFromRequest(HttpServletRequest request) {
		return request.getHeader(SystemProperties.TOKEN_AUTHORIZATION);
	}

	// Decoded JWT
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SystemProperties.JWT_SECRET).parseClaimsJws(token).getBody();
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

}
