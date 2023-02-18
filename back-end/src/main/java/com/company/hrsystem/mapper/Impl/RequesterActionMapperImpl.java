package com.company.hrsystem.mapper.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.RequesterActionDto;
import com.company.hrsystem.mapper.RequesterActionMapper;

@MapperImpl
public class RequesterActionMapperImpl {

	@Autowired
	private RequesterActionMapper requesterActionMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertRequesterAction(RequesterActionDto requestAction) {
		return requesterActionMapper.insertRequesterAction(requestAction);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateRequesterAction(RequesterActionDto requestAction) {
		return requesterActionMapper.updateRequesterAction(requestAction);
	}

}
