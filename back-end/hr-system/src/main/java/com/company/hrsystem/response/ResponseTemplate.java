package com.company.hrsystem.response;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ResponseTemplate {

	private String system;

	private String verion;

	private int status;

	private String message;

	private String errorMessage;

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Date timestamp;

	private Object data;

	public ResponseTemplate(String system, String version, int status, String message, String errorMessage, Object data) {
		this.system = system;
		this.verion = version;
		this.status = status;
		this.message = message;
		this.errorMessage = errorMessage;
		setTimestamp(Calendar.getInstance().getTime());
		this.data = data;
	}

}
