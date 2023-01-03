package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.dto.RequestDto;
import com.company.hrsystem.dto.RequestEmployeeDto;
import com.company.hrsystem.dto.RequesterActionDto;
import com.company.hrsystem.dto.SupervisorActionDto;
import com.company.hrsystem.response.FindListTicketResponse;
import com.company.hrsystem.response.FindReportCaseSelectedResponse;
import com.company.hrsystem.response.FindTicketRequestByIdResponse;
import com.company.hrsystem.request.FindListTicketRequest;

@Mapper
public interface RequestEmployeeMapper {
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	int insertRequestEmployee(RequestDto requestDto, SupervisorActionDto supervisorActionDto,
			ApproverActionDto approverActionDto, RequestEmployeeDto requestEmployeeDto,
			RequesterActionDto requesterActionDto);

	int updateRequestEmployee(RequestEmployeeDto requestEmployeeDto);

	int updateRequestBySupervisor(RequestEmployeeDto requestEmployeeDto);

	int updateRequestByApprover(RequestEmployeeDto requestEmployeeDto);

	List<FindListTicketResponse> findListCreatedRequestTicket(FindListTicketRequest request);

	List<FindListTicketResponse> findListReceivedRequestTicket(FindListTicketRequest request);

	FindTicketRequestByIdResponse findRequestTicketById(Integer id, Integer employeeId);

	RequestEmployeeDto findCurrentRequestEmployee(RequestEmployeeDto requestEmployeeDto);

	List<FindReportCaseSelectedResponse> findReportCaseSelected(FindListTicketRequest request);

}
