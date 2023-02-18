package com.company.hrsystem.commons.configs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.company.hrsystem.commons.constants.ApiUrlConstant;
import com.company.hrsystem.commons.utils.HttpServletResponseUtil;
import com.company.hrsystem.commons.utils.LogUtil;
import com.company.hrsystem.commons.utils.MessageUtil;
import com.company.hrsystem.commons.utils.StringUtil;
import com.company.hrsystem.response.ResponseData;
import com.company.hrsystem.service.CacheServiceImpl;
import com.company.hrsystem.service.JWTServiceImpl;
import com.company.hrsystem.service.UserDetailsServiceImp;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@Component
public class RequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsServiceImp userDetailsService;

	@Autowired
	private CacheServiceImpl cacheService;

	@Autowired
	private JWTServiceImpl jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = jwtService.getHeaderFromRequest(request);

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith(SystemProperties.JWT_START)) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtService.getUsernameFromToken(jwtToken);
				if (!cacheService.isExistsStringInCache(SystemProperties.TOKEN_STORE, username, jwtToken)) {
					responseErrAccessToken(response);
					return;
				}
			} catch (MalformedJwtException | SignatureException e) {
				responseErrAccessToken(response, e);
				return;
			} catch (ExpiredJwtException e) {
				String requestURL = request.getRequestURI().toString();
				if (requestURL
						.equals(StringUtil.apiBuilder(ApiUrlConstant.ROOT_API, ApiUrlConstant.AUTHEN_REFRESH_TOKEN))) {
					if (cacheService.isExistsStringInCache(SystemProperties.TOKEN_STORE, e.getClaims().getSubject(),
							jwtToken)) {
						request.setAttribute(SystemProperties.JWT_ATTRIBUTE, e.getClaims());
					}
				} else {
					responseErrAccessToken(response, e);
					return;
				}
			}
		}

		// Once we get the token validate it.
		if (username != null) {

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtService.validateToken(jwtToken, userDetails)) {

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

	public void responseErrAccessToken(HttpServletResponse response, Exception e) throws IOException {
		LogUtil.warn(MessageUtil.getMessagelangUS("not.valid.access.token"));
		LogUtil.error(ExceptionUtils.getStackTrace(e));
		HttpServletResponseUtil.ServletResponse(response,
				new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
						HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value(), null,
						MessageUtil.getMessagelangUS("not.valid.access.token"), null));
	}

	public void responseErrAccessToken(HttpServletResponse response) throws IOException {
		LogUtil.warn(MessageUtil.getMessagelangUS("not.in.cache.access.token"));
		HttpServletResponseUtil.ServletResponse(response,
				new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
						com.company.hrsystem.commons.constants.HttpStatus.ACCESS_TOKEN_NOT_IN_CACHE, null,
						MessageUtil.getMessagelangUS("not.in.cache.access.token"), null));
	}

}
