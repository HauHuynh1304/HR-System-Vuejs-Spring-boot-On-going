package com.company.hrsystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.company.hrsystem.Exeption.NullPointRequestException;
import com.company.hrsystem.Exeption.TokenException;
import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.response.ResponseTemplate;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(value = TokenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseTemplate handleTokenRefreshException(TokenException ex, WebRequest request) {
		request.getDescription(false);
		return new ResponseTemplate(ex.getSystem(), ex.getVersion(), HttpStatus.FORBIDDEN.value(), null,
				ex.getMessage(), null);
	}

	@ExceptionHandler(value = NullPointRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseTemplate handleBadRequestException(NullPointRequestException ex, WebRequest request) {
		request.getDescription(false);
		return new ResponseTemplate(ex.getSystem(), ex.getVersion(), HttpStatus.BAD_REQUEST.value(), null,
				ex.getMessage(), null);
	}

	@ExceptionHandler(value = GlobalException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ResponseTemplate handleSqlException(GlobalException ex, WebRequest request) {
		request.getDescription(false);
		return new ResponseTemplate(ex.getSystem(), ex.getVersion(), HttpStatus.NOT_ACCEPTABLE.value(), null,
				ex.getMessage(), null);
	}
}
