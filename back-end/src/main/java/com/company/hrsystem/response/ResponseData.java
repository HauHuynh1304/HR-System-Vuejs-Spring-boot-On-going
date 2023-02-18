package com.company.hrsystem.response;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.http.HttpStatus;

import com.company.hrsystem.commons.configs.SystemProperties;
import com.company.hrsystem.commons.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ResponseData implements Serializable {

	private static final long serialVersionUID = 6290782884653718958L;

	private String system;

	private String version;

	private int status;

	private String message;

	private String errorMessage;

	@JsonFormat(pattern = DateUtil.DAY_HOUR_SECOND, timezone = DateUtil.TIME_ZONE)
	private Timestamp timestamp;

	private Object data;

	public ResponseData(String system, String version, int status, String message, String errorMessage, Object data) {
		this.system = system;
		this.version = version;
		this.status = status;
		this.message = message;
		this.errorMessage = errorMessage;
		setTimestamp(DateUtil.getCurrentDayHourSecond());
		this.data = data;
	}

	public ResponseData(Object data) {
		this.system = SystemProperties.SYSTEM_NAME;
		this.version = SystemProperties.SYSTEM_VERSION;
		this.status = HttpStatus.OK.value();
		setTimestamp(DateUtil.getCurrentDayHourSecond());
		this.data = data;
	}

}
