package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.model.EmployeeModel;

@Mapper
public interface EmployeeDocumentMapper {

	int insertEmployeeDocument(EmployeeModel employee, Integer[] documentIds);

}
