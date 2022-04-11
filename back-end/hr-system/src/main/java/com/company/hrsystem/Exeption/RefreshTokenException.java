package com.company.hrsystem.Exeption;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Getter
@Setter
public class RefreshTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String system;

	private String version;

	private int status;

	private String errorMessage;

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Date timestamp;

	private Object data;

	public RefreshTokenException(String system, String version, String errorMessage) {
		super(errorMessage);
		this.system = system;
		this.version = version;
		setTimestamp(Calendar.getInstance().getTime());
	}

}
