package com.company.hrsystem.mapper.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.RequestTypeDto;
import com.company.hrsystem.mapper.RequestTypeMapper;

@MapperImpl
public class RequestTypeMapperImpl {

	@Autowired
	private RequestTypeMapper requestTypeMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertRequestType(RequestTypeDto request) {
		return requestTypeMapper.insertRequestType(request);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateRequestType(RequestTypeDto request) {
		return requestTypeMapper.updateRequestType(request);
	}

	public List<RequestTypeDto> findAllRequestType() {
		return requestTypeMapper.findAllRequestType();
	}

	public List<RequestTypeDto> findRequestType() {
		return requestTypeMapper.findRequestType();
	}

	public String findRequestTypeById(Integer requestTypeId) {
		return requestTypeMapper.findRequestTypeById(requestTypeId);
	}

}
