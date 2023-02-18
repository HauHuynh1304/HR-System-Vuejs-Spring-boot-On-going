package com.company.hrsystem.mapper.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.SupervisorActionDto;
import com.company.hrsystem.mapper.SupervisorAcctionMapper;

@MapperImpl
public class SupervisorAcctionMapperImpl {

	@Autowired
	private SupervisorAcctionMapper supervisorAcctionMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertSupervisorAction(SupervisorActionDto supervisorActionDto) {
		return supervisorAcctionMapper.insertSupervisorAction(supervisorActionDto);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateActionBySupervisor(SupervisorActionDto supervisorActionDto) {
		return supervisorAcctionMapper.updateActionBySupervisor(supervisorActionDto);
	}

}
