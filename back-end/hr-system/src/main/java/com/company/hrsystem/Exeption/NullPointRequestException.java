package com.company.hrsystem.Exeption;

import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.company.hrsystem.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullPointRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String system;

	private String version;

	private int status;

	private String errorMessage;

	@JsonFormat(pattern = DateUtil.DAY_HOUR_SECOND, timezone = DateUtil.TIME_ZONE)
	private Date timestamp;

	private Object data;

	public NullPointRequestException(String system, String version, String errorMessage) {
		super(errorMessage);
		this.system = system;
		this.version = version;
		setTimestamp(Calendar.getInstance().getTime());
	}
	
}
