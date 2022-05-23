package com.company.hrsystem.response;

import java.io.Serializable;
import java.util.List;

import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.dto.CommentDto;
import com.company.hrsystem.dto.RequestEmployeeDto;
import com.company.hrsystem.dto.SupervisorActionDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindTicketRequestByIdResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String requestId;
	
	private String requester;
	
	private String requestType;

	private String reason;

	private RequestEmployeeDto requestEmployee;
	
	private String supervisor;

	private SupervisorActionDto supervisorAction;
	
	private String approver;

	private ApproverActionDto approverAction;

	private List<CommentDto> comments;

}
