package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.request.EmployeeRequest;

@Mapper
public interface EmployeeMapper {

	int insertEmployee(EmployeeRequest employee);

}
