package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.RequestDto;

@Mapper
public interface RequestMapper {
	
	int insertRequest (RequestDto request);
	
}
