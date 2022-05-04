package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.EmployeePositionDto;
import com.company.hrsystem.model.EmployeeModel;

@Mapper
public interface EmployeePositionMapper {

	int insertEmployeePosition(EmployeeModel employee, List<EmployeePositionDto> positions);

}
