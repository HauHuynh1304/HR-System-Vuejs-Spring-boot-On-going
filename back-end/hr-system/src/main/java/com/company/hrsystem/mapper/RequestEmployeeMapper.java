package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.dto.RequestDto;
import com.company.hrsystem.dto.RequestEmployeeDto;
import com.company.hrsystem.dto.SupervisorActionDto;
import com.company.hrsystem.response.FindListTicketResponse;
import com.company.hrsystem.response.FindTicketRequestByIdResponse;
import com.company.hrsystem.request.FindListTicketRequest;

@Mapper
public interface RequestEmployeeMapper {

	int insertRequestEmployee(RequestDto requestDto, SupervisorActionDto supervisorActionDto,
			ApproverActionDto approverActionDto, RequestEmployeeDto requestEmployeeDto);

	int updateRequestEmployee(RequestEmployeeDto requestEmployeeDto);

	int updateRequestBySupervisor(RequestEmployeeDto requestEmployeeDto);

	int updateRequestByApprover(RequestEmployeeDto requestEmployeeDto);
	
	int findRequestIdBySupervisorActionId(Integer id);
	
	int findRequestIdByApproverActionId(Integer id);

	List<FindListTicketResponse> findListCreatedRequestTicket(FindListTicketRequest request);
	
	List<FindListTicketResponse> findListReceivedRequestTicket(FindListTicketRequest request);

	FindTicketRequestByIdResponse findRequestTicketById(String id);
	
	

}