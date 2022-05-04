package com.company.hrsystem.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.company.hrsystem.constants.ApiUrlConstant;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.service.CacheService;
import com.company.hrsystem.service.UserDetailsServiceImp;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class RequestFilter extends OncePerRequestFilter {

	@Value("${token.store}")
	private String tokenStore;

	@Value("${system.name}")
	private String system;

	@Value("${system.version}")
	private String version;

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	private UserDetailsServiceImp userDetailsService;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private MessageUtil messageUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = tokenUtil.getHeaderFromRequest(request);

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			if (!cacheService.isExistsInCache(tokenStore, jwtToken)) {
				// cancel request
				LogUtil.warn(messageUtil.getMessagelangUS("not.valid.access.token"));
				HttpServletResponseUtil.ServletResponse(response,
						new ResponseTemplate(system, version, HttpStatus.FORBIDDEN.value(), null,
								messageUtil.getMessagelangUS("not.valid.access.token"), null));
				return;
			} else {
				try {
					username = tokenUtil.getUsernameFromToken(jwtToken);
				} catch (IllegalArgumentException e) {
					LogUtil.error(ExceptionUtils.getStackTrace(e));
				} catch (ExpiredJwtException e) {
					String requestURL = request.getRequestURI().toString();
					if (requestURL.equals(
							StringUtil.apiBuilder(ApiUrlConstant.ROOT_API, ApiUrlConstant.AUTHEN_REFRESH_TOKEN))) {
						allowForGenerateRefreshToken(e, request);
					}
				}
			}
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (tokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

	private void allowForGenerateRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

		// create a UsernamePasswordAuthenticationToken with null values.
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				null, null, null);
		// After setting the Authentication in the context, we specify
		// that the current user is authenticated. So it passes the
		// Spring Security Configurations successfully.
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		// Set the claims so that in controller we will be using it to create
		// new JWT
		request.setAttribute("claims", ex.getClaims());

	}
}
