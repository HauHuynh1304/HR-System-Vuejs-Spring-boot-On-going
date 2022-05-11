package com.company.hrsystem.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.hrsystem.service.UserDetailsImpl;

@Service
public class AuthenUtil {

	public Authentication authentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public Boolean isAuthen(String role) {
		return authentication().getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role));
	}

	public UserDetailsImpl userDetails() {
		return (UserDetailsImpl) authentication().getPrincipal();
	}

	public String getUsername() {
		return userDetails().getUsername();
	}

	public Integer getAccountId() {
		return userDetails().getId();
	}

}
