package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.request.ReasonRequest;

@Mapper
public interface ReasonMapper {

	int insertReason(ReasonRequest request);
	
	int updateReason(ReasonRequest request);
	
}
