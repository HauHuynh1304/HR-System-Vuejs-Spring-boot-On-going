package com.company.hrsystem.mapper.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.RequestDto;
import com.company.hrsystem.mapper.RequestMapper;

@MapperImpl
public class RequestMapperImpl {

	@Autowired
	private RequestMapper requestMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertRequest(RequestDto request) {
		return requestMapper.insertRequest(request);
	}

}
