package com.company.hrsystem.response;

import java.sql.Timestamp;

import com.company.hrsystem.commons.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ResponseTemplate {

	private String system;

	private String version;

	private int status;

	private String message;

	private String errorMessage;
	
	@JsonFormat(pattern = DateUtil.DAY_HOUR_SECOND, timezone = DateUtil.TIME_ZONE)
	private Timestamp timestamp;

	private Object data;

	public ResponseTemplate(String system, String version, int status, String message, String errorMessage,
			Object data) {
		this.system = system;
		this.version = version;
		this.status = status;
		this.message = message;
		this.errorMessage = errorMessage;
		setTimestamp(DateUtil.getCurrentDayHourSecond());
		this.data = data;
	}

}
