package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.EmployeeDto;
import com.company.hrsystem.dto.EmployeePositionDto;

@Mapper
public interface EmployeePositionMapper {

	int insertEmployeePosition(EmployeeDto employee, List<EmployeePositionDto> positions);

	int updateEmployeePosition(List<EmployeePositionDto> positions, String updatedAt);

}
