package com.company.hrsystem.mapper.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.EmployeeDto;
import com.company.hrsystem.dto.PersonalInfoDto;
import com.company.hrsystem.mapper.EmployeeMapper;
import com.company.hrsystem.request.FindListEmployeesRequest;
import com.company.hrsystem.response.FindEmployeeResponse;
import com.company.hrsystem.response.FindListEmployeesResponse;

@MapperImpl
public class EmployeeMapperImpl {

	@Autowired
	private EmployeeMapper employeeMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Integer insertEmployee(PersonalInfoDto personalInfo, EmployeeDto employee) {
		return employeeMapper.insertEmployee(personalInfo, employee);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Integer updateEmployee(EmployeeDto employee) {
		return employeeMapper.updateEmployee(employee);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public Integer findEmployeeIdByAccountId(Integer accountId) {
		return employeeMapper.findEmployeeIdByAccountId(accountId);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public List<FindListEmployeesResponse> findListEmployees(FindListEmployeesRequest request) {
		return employeeMapper.findListEmployees(request);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public FindEmployeeResponse findEmployeeById(Integer id) {
		return employeeMapper.findEmployeeById(id);
	}

}
