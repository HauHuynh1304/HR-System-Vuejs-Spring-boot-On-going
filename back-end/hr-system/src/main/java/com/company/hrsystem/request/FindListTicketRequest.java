package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.dto.RequestEmployeeDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindListTicketRequest implements Serializable {

	private static final long serialVersionUID = -6397134404167633343L;

	private Integer requestTypeId;

	private RequestEmployeeDto requestEmployee;
	
	private Integer[] systemAccountIds;

	private FindListTicketRequest data;

}
