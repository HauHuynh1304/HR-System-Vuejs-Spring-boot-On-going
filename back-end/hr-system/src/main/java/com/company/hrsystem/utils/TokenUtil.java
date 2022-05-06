package com.company.hrsystem.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.company.hrsystem.service.CacheService;
import com.company.hrsystem.service.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtil implements Serializable {

	public static final long serialVersionUID = 1L;

	@Value("${jwt.secret}")
	private String sercret;

	@Value("${jwt.accessValid}")
	private String accessTokenValid;

	@Value("${token.store}")
	private String tokenStore;

	@Value("${token.authorization}")
	private String tokenAuthorization;
	
	@Value("${jwt.payload.id}")
	private String id;
	
	@Value("${jwt.payload.roles}")
	private String roles;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private MathUtil mathUtil;

	// Decoded JWT
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(sercret).parseClaimsJws(token).getBody();
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

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
		claims.put(id, userDetails.getId());
		claims.put(roles, authorities);
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
				.setExpiration(new Date(System.currentTimeMillis() + mathUtil.calculateFromString(accessTokenValid)))
				.signWith(SignatureAlgorithm.HS512, sercret).compact();
		cacheService.updateCache(tokenStore, subject, token);
		return token;
	}

	public String getTokenFromHeader(HttpServletRequest request) {
		return request.getHeader(tokenAuthorization).substring(7);
	}

	public String getHeaderFromRequest(HttpServletRequest request) {
		return request.getHeader(tokenAuthorization);
	}

}
