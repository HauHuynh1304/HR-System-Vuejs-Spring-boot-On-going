package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.PositionDto;

@Mapper
public interface PositionMapper {

	int insertPosition(PositionDto request);

	int updatePosition(PositionDto request);
	
	List<PositionDto> findPositions();
	
	List<PositionDto> findAllPositions();

}
