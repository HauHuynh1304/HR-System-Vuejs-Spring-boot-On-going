package com.company.hrsystem.mapper.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.ReasonDto;
import com.company.hrsystem.mapper.ReasonMapper;

@MapperImpl
public class ReasonMapperImpl {

	@Autowired
	private ReasonMapper reasonMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertReason(ReasonDto request) {
		return reasonMapper.insertReason(request);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateReason(ReasonDto request) {
		return reasonMapper.updateReason(request);
	}

	public List<ReasonDto> findAllReason() {
		return reasonMapper.findAllReason();
	}
	
	public List<ReasonDto> findReason() {
		return reasonMapper.findReason();
	}

}
