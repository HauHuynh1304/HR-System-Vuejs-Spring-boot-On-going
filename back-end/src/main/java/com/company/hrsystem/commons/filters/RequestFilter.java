package com.company.hrsystem.commons.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.company.hrsystem.commons.configs.SystemProperties;
import com.company.hrsystem.commons.constants.ApiUrlConstant;
import com.company.hrsystem.commons.constants.MessageCodeConstant;
import com.company.hrsystem.commons.enums.BooleanEnum;
import com.company.hrsystem.commons.utils.CacheUtils;
import com.company.hrsystem.commons.utils.HttpServletResponseUtil;
import com.company.hrsystem.commons.utils.JwtUtils;
import com.company.hrsystem.commons.utils.LogUtil;
import com.company.hrsystem.commons.utils.MessageUtil;
import com.company.hrsystem.commons.utils.StringUtil;
import com.company.hrsystem.response.ResponseData;
import com.company.hrsystem.service.UserDetailsServiceImp;

import io.jsonwebtoken.ExpiredJwtException;

public class RequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsServiceImp userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String uri = request.getRequestURI().toString();
		final String token = StringUtils.isEmpty(JwtUtils.getToken(request)) ? null
				: JwtUtils.getTokenInBearerForm(request);
		final Boolean isLoginURI = uri
				.equals(StringUtil.apiBuilder(ApiUrlConstant.ROOT_API, ApiUrlConstant.AUTHEN_LOG_IN));
		if (StringUtils.isNotEmpty(token) && !isLoginURI) {
			try {
				final String username = JwtUtils.getUsername(token);
				BooleanEnum isInCache = CacheUtils.isExistsStringInCache(SystemProperties.TOKEN_STORE,
						JwtUtils.getUsername(token), token) ? BooleanEnum.getValue(1) : BooleanEnum.getValue(0);
				switch (isInCache) {
				case TRUE:
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					filterChain.doFilter(request, response);
					break;
				default:
					LogUtil.warn("Access Token not in cache.");
					errAccessToken(response, MessageUtil.getMessagelangUS(MessageCodeConstant.JWT005));
					break;
				}
			} catch (ExpiredJwtException e) {
				BooleanEnum isRefreshURI = uri
						.equals(StringUtil.apiBuilder(ApiUrlConstant.ROOT_API, ApiUrlConstant.AUTHEN_REFRESH_TOKEN))
								? BooleanEnum.getValue(1)
								: BooleanEnum.getValue(0);
				switch (isRefreshURI) {
				case TRUE:
					request.setAttribute(SystemProperties.JWT_ATTRIBUTE, e.getClaims());
					filterChain.doFilter(request, response);
					break;
				default:
					LogUtil.warn("Access token was expired.");
					errAccessToken(response, e, MessageUtil.getMessagelangUS(MessageCodeConstant.JWT004));
					break;
				}
			} catch (Exception e) {
				LogUtil.error("Coundn't decode Token");
				LogUtil.error(ExceptionUtils.getStackTrace(e));
				errAccessToken(response, MessageUtil.getMessagelangUS(MessageCodeConstant.JWT003));
			}
		} else if (isLoginURI) {
			filterChain.doFilter(request, response);
		} else {
			LogUtil.warn("Access Token is required.");
			errAccessToken(response, MessageUtil.getMessagelangUS(MessageCodeConstant.JWT001));
		}
	}

	public void errAccessToken(HttpServletResponse response, Exception e, String message) throws IOException {
		HttpServletResponseUtil.ServletResponse(response,
				new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
						HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value(), null,
						message, null));
	}

	public void errAccessToken(HttpServletResponse response, String message) throws IOException {
		HttpServletResponseUtil.ServletResponse(response,
				new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
						com.company.hrsystem.commons.constants.HttpStatus.ACCESS_TOKEN_NOT_IN_CACHE, null,
						message, null));
	}

}
