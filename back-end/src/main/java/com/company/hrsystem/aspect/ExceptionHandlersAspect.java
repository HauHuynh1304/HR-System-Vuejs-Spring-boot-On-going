package com.company.hrsystem.aspect;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.company.hrsystem.commons.exceptions.GlobalException;
import com.company.hrsystem.commons.exceptions.InternalAuthenticationServiceException;
import com.company.hrsystem.commons.exceptions.NullPointRequestException;
import com.company.hrsystem.commons.exceptions.TokenException;
import com.company.hrsystem.commons.exceptions.UsernameNotFoundException;
import com.company.hrsystem.error.ErrorDetails;
import com.company.hrsystem.response.ResponseData;

@RestControllerAdvice
public class ExceptionHandlersAspect {

	@ExceptionHandler(value = TokenException.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseData handleTokenRefreshException(TokenException ex, WebRequest request) {
		request.getDescription(false);
		return new ResponseData(ex.getSystem(), ex.getVersion(), HttpStatus.NOT_FOUND.value(), null, ex.getMessage(),
				null);
	}

	@ExceptionHandler(value = NullPointRequestException.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseData handleBadRequestException(NullPointRequestException ex, WebRequest request) {
		request.getDescription(false);
		return new ResponseData(ex.getSystem(), ex.getVersion(), HttpStatus.BAD_REQUEST.value(), null, ex.getMessage(),
				null);
	}

	@ExceptionHandler(value = GlobalException.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseData handleSqlException(GlobalException ex, WebRequest request) {
		request.getDescription(false);
		return new ResponseData(ex.getSystem(), ex.getVersion(), HttpStatus.NOT_ACCEPTABLE.value(), null,
				ex.getMessage(), null);
	}

	@ExceptionHandler(value = UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseData UsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
		request.getDescription(false);
		return new ResponseData(ex.getSystem(), ex.getVersion(), HttpStatus.NOT_FOUND.value(), null, ex.getMessage(),
				null);
	}

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseData handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex,
			WebRequest request) {
		request.getDescription(false);
		return new ResponseData(ex.getSystem(), ex.getVersion(), HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value(),
				null, ex.getMessage(), null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ResponseData handleConstraintException(MethodArgumentNotValidException ex) {
		List<ErrorDetails> errorDetails = new ArrayList<>();

		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			errorDetails.add(new ErrorDetails(fieldError.getField(), fieldError.getDefaultMessage()));
		}

		return new ResponseData(errorDetails);
	}

}
