package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.dto.RequestDto;
import com.company.hrsystem.dto.RequestEmployeeDto;
import com.company.hrsystem.dto.RequesterActionDto;
import com.company.hrsystem.dto.SupervisorActionDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusinessRequest implements Serializable {

	private static final long serialVersionUID = 4713163192724499747L;

	private RequestDto request;

	private RequestEmployeeDto requestEmployee;
	
	private RequesterActionDto requesterActionDto;

	private SupervisorActionDto supervisorAcction;

	private ApproverActionDto approverAction;

	private BusinessRequest data;

}
