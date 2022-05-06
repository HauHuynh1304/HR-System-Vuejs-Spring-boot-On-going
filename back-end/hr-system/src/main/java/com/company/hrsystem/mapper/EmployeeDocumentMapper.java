package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.DocumentDto;
import com.company.hrsystem.model.EmployeeModel;

@Mapper
public interface EmployeeDocumentMapper {

	int insertEmployeeDocument(EmployeeModel employee, List<DocumentDto> documents);

}
