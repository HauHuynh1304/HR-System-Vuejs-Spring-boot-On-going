package com.company.hrsystem.response;

import java.io.Serializable;

import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.dto.RequestEmployeeDto;
import com.company.hrsystem.dto.RequesterActionDto;
import com.company.hrsystem.dto.SupervisorActionDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindTicketRequestByIdResponse implements Serializable {

	private static final long serialVersionUID = -264778152296287817L;

	private RequestEmployeeDto requestEmployee;

	private SupervisorActionDto supervisorAction;

	private ApproverActionDto approverAction;
	
	private RequesterActionDto requesterAction;

}
