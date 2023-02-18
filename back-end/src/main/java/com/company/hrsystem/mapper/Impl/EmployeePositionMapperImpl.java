package com.company.hrsystem.mapper.Impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.EmployeeDto;
import com.company.hrsystem.dto.EmployeePositionDto;
import com.company.hrsystem.mapper.EmployeePositionMapper;

@MapperImpl
public class EmployeePositionMapperImpl {

	@Autowired
	private EmployeePositionMapper employeePositionMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertEmployeePosition(EmployeeDto employee, List<EmployeePositionDto> positions) {
		return employeePositionMapper.insertEmployeePosition(employee, positions);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateEmployeePosition(List<EmployeePositionDto> positions, Timestamp updatedAt) {
		return employeePositionMapper.updateEmployeePosition(positions, updatedAt);
	}

	public Set<EmployeePositionDto> findLastInsertListEmployeePosition(Integer employeeId) {
		return employeePositionMapper.findLastInsertListEmployeePosition(employeeId);
	}

	public List<EmployeePositionDto> findEmployeePositionsByEmployeeId(Integer employeeId) {
		return employeePositionMapper.findEmployeePositionsByEmployeeId(employeeId);
	}

}
