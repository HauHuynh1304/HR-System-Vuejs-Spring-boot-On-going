package com.company.hrsystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.company.hrsystem.Exeption.RefreshTokenException;
import com.company.hrsystem.response.ResponseTemplate;

@RestControllerAdvice
public class TokenControllerAdvice {

	@ExceptionHandler(value = RefreshTokenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseTemplate handleTokenRefreshException(RefreshTokenException ex, WebRequest request) {
		request.getDescription(false);
		return new ResponseTemplate(ex.getSystem(), ex.getVersion(), HttpStatus.FORBIDDEN.value(), null,
				ex.getMessage(), null);
	}
	
}
