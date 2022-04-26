package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.request.RequestTypeRequest;

@Mapper
public interface RequestTypeMapper {
	
	int insertRequestType(RequestTypeRequest request);
	
	int updateRequestType(RequestTypeRequest request);
	
}
