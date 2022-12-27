package com.company.hrsystem.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.company.hrsystem.service.UserDetailsImpl;

public class AuthenUtil {

	public static Authentication authentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static Boolean isAuthen(String role) {
		return authentication().getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role));
	}

	public static UserDetailsImpl userDetails() {
		return (UserDetailsImpl) authentication().getPrincipal();
	}

	public static String getUsername() {
		return userDetails().getUsername();
	}

	public static Integer getAccountId() {
		return userDetails().getId();
	}

}
