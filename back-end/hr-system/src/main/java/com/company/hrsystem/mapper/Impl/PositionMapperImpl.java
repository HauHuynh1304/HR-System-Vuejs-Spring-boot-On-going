package com.company.hrsystem.mapper.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.PositionDto;
import com.company.hrsystem.mapper.PositionMapper;

@MapperImpl
public class PositionMapperImpl {

	@Autowired
	private PositionMapper positionMapper;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertPosition(PositionDto request) {
		return positionMapper.insertPosition(request);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updatePosition(PositionDto request) {
		return positionMapper.updatePosition(request);
	}
	
	public List<PositionDto> findPositions() {
		return positionMapper.findPositions();
	}
	
	public List<PositionDto> findAllPositions() {
		return positionMapper.findAllPositions();
	}
	
}
