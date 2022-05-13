package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.PositionDto;

@Mapper
public interface PositionMapper {

	int insertPosition(PositionDto request);

	int updatePosition(PositionDto request);

}
