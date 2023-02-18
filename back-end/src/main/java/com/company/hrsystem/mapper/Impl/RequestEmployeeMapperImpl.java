package com.company.hrsystem.mapper.Impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.dto.RequestDto;
import com.company.hrsystem.dto.RequestEmployeeDto;
import com.company.hrsystem.dto.RequesterActionDto;
import com.company.hrsystem.dto.SupervisorActionDto;
import com.company.hrsystem.mapper.RequestEmployeeMapper;
import com.company.hrsystem.request.FindListTicketRequest;
import com.company.hrsystem.response.FindListTicketResponse;
import com.company.hrsystem.response.FindReportCaseSelectedResponse;
import com.company.hrsystem.response.FindTicketRequestByIdResponse;

@MapperImpl
public class RequestEmployeeMapperImpl {
	
	@Autowired 
	private RequestEmployeeMapper requestEmployeeMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertRequestEmployee(RequestDto requestDto, SupervisorActionDto supervisorActionDto,
			ApproverActionDto approverActionDto, RequestEmployeeDto requestEmployeeDto,
			RequesterActionDto requesterActionDto) {
		return requestEmployeeMapper.insertRequestEmployee(requestDto, supervisorActionDto, approverActionDto, requestEmployeeDto, requesterActionDto);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateRequestEmployee(RequestEmployeeDto requestEmployeeDto) {
		return requestEmployeeMapper.updateRequestEmployee(requestEmployeeDto);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateRequestBySupervisor(RequestEmployeeDto requestEmployeeDto) {
		return requestEmployeeMapper.updateRequestBySupervisor(requestEmployeeDto);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateRequestByApprover(RequestEmployeeDto requestEmployeeDto) {
		return requestEmployeeMapper.updateRequestByApprover(requestEmployeeDto);
	}
	
	public List<FindListTicketResponse> findListCreatedRequestTicket(FindListTicketRequest request){
		return requestEmployeeMapper.findListCreatedRequestTicket(request);
	}
	
	public List<FindListTicketResponse> findListReceivedRequestTicket(FindListTicketRequest request){
		return requestEmployeeMapper.findListReceivedRequestTicket(request);
	}
	
	public FindTicketRequestByIdResponse findRequestTicketById(Integer id, Integer employeeId) {
		return requestEmployeeMapper.findRequestTicketById(id, employeeId);
	}
	
	public RequestEmployeeDto findCurrentRequestEmployee(RequestEmployeeDto requestEmployeeDto) {
		return requestEmployeeMapper.findCurrentRequestEmployee(requestEmployeeDto);
	}
	
	public List<FindReportCaseSelectedResponse> findReportCaseSelected(FindListTicketRequest request){
		return requestEmployeeMapper.findReportCaseSelected(request);
	}
	
}
