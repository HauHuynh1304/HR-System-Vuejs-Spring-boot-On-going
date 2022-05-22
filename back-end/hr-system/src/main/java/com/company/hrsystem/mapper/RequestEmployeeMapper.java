package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.dto.RequestDto;
import com.company.hrsystem.dto.RequestEmployeeDto;
import com.company.hrsystem.dto.SupervisorActionDto;

@Mapper
public interface RequestEmployeeMapper {

	int insertRequestEmployee(RequestDto requestDto, SupervisorActionDto supervisorActionDto,
			ApproverActionDto approverActionDto, RequestEmployeeDto requestEmployeeDto);

	int updateRequestEmployee(RequestEmployeeDto requestEmployeeDto);

	int updateRequestBySupervisor(RequestEmployeeDto requestEmployeeDto);
	
	int updateRequestByApprover(RequestEmployeeDto requestEmployeeDto);

}
