package com.company.hrsystem.commons.exceptions;

import com.company.hrsystem.commons.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class TokenException extends RuntimeException {

	private static final long serialVersionUID = 7410075796052599309L;

	private String system;

	private String version;

	private int status;

	private String errorMessage;

	@JsonFormat(pattern = DateUtil.DAY_HOUR_SECOND, timezone = DateUtil.TIME_ZONE)
	private Date timestamp;

	private Object data;

	public TokenException(String system, String version, String errorMessage) {
		super(errorMessage);
		this.system = system;
		this.version = version;
		setTimestamp(Calendar.getInstance().getTime());
	}

}
