package com.company.hrsystem.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenUtil {

	public Authentication authentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public Boolean isAuthen(String role) {
		return authentication().getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role));
	}

	public UserDetails userDetails() {
		return (UserDetails) authentication().getPrincipal();
	}

	public String getUsername() {
		return userDetails().getUsername();
	}

}
