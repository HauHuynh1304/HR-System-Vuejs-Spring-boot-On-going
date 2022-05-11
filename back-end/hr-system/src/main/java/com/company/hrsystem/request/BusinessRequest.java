package com.company.hrsystem.request;

import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.dto.RequestEmployeeDto;
import com.company.hrsystem.dto.SupervisorActionDto;
import com.company.hrsystem.model.RequestModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusinessRequest extends RequestModel {

	private static final long serialVersionUID = 1L;

	private RequestEmployeeDto requestEmployee;

	private SupervisorActionDto supervisorAcction;

	private ApproverActionDto approverAction;

	private BusinessRequest data;

}
