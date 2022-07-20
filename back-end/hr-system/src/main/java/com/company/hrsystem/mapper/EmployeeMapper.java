package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.EmployeeDto;
import com.company.hrsystem.dto.PersonalInfoDto;
import com.company.hrsystem.request.FindListEmployeesRequest;
import com.company.hrsystem.response.FindEmployeeResponse;
import com.company.hrsystem.response.FindListEmployeesResponse;

@Mapper
public interface EmployeeMapper {

	Integer insertEmployee(PersonalInfoDto personalInfo, EmployeeDto employee);
	
	Integer updateEmployee(EmployeeDto employee);
	
	Integer findEmployeeIdByAccountId(Integer accountId);
	
	List<FindListEmployeesResponse> findListEmployees(FindListEmployeesRequest request);
	
	FindEmployeeResponse findEmployeeById(Integer id);

}
