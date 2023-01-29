package com.company.hrsystem.mapper.Impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.EmployeeDocumentDto;
import com.company.hrsystem.dto.EmployeeDto;
import com.company.hrsystem.mapper.EmployeeDocumentMapper;

@MapperImpl
public class EmployeeDocumentMapperImpl {

	@Autowired
	private EmployeeDocumentMapper employeeDocumentMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertEmployeeDocument(EmployeeDto employee, List<EmployeeDocumentDto> documents) {
		return employeeDocumentMapper.insertEmployeeDocument(employee, documents);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateEmployeeDocument(List<EmployeeDocumentDto> documents, Timestamp updatedAt) {
		return employeeDocumentMapper.updateEmployeeDocument(documents, updatedAt);
	}

	public Set<EmployeeDocumentDto> findLastInsertListEmployeeDocument(Integer employeeId) {
		return employeeDocumentMapper.findLastInsertListEmployeeDocument(employeeId);
	}

	public List<EmployeeDocumentDto> findEmployeeDocumentsByEmployeeId(Integer employeeId) {
		return employeeDocumentMapper.findEmployeeDocumentsByEmployeeId(employeeId);
	}

}
