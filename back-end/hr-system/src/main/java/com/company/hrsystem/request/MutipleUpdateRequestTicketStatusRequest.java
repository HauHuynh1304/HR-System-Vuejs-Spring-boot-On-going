package com.company.hrsystem.request;

import java.io.Serializable;
import java.util.List;

import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.dto.SupervisorActionDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MutipleUpdateRequestTicketStatusRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<SupervisorActionDto> supervisorAction;

	private List<ApproverActionDto> approverAction;

	private MutipleUpdateRequestTicketStatusRequest data;

}
