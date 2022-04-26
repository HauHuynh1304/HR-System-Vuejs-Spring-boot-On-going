package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.request.PositionRequest;

@Mapper
public interface PositionMapper {

	int insertPosition(PositionRequest request);
	
	int updatePosition(PositionRequest request);
	
}
