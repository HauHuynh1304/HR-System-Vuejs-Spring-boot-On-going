package com.company.hrsystem.response;

import java.io.Serializable;

import com.company.hrsystem.dto.RequestEmployeeDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class FindListTicketResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String requestId;
	
	private String requester;
	
	private String requestType;

	private String reason;

	private String approver;
	
	private String supervisor;

	private RequestEmployeeDto requestEmployee;

}
