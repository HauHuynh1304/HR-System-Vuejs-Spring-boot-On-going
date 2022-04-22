package com.company.hrsystem.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CheckAuthenUtil {

	public static Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	public static Boolean checkAuthen(String role) {
		return authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role));
	}
}
