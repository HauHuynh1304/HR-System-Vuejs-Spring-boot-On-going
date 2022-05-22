package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.RequestTypeDto;

@Mapper
public interface RequestTypeMapper {

	int insertRequestType(RequestTypeDto request);

	int updateRequestType(RequestTypeDto request);

}
