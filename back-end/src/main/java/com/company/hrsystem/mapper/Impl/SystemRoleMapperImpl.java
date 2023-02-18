package com.company.hrsystem.mapper.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.SystemRoleDto;
import com.company.hrsystem.mapper.SystemRoleMapper;

@MapperImpl
public class SystemRoleMapperImpl {

	@Autowired
	private SystemRoleMapper systemRoleMapper;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertSystemRoleSelected(SystemRoleDto request) {
		return systemRoleMapper.insertSystemRoleSelected(request);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertListSystemRole(List<SystemRoleDto> dtos) {
		return systemRoleMapper.insertListSystemRole(dtos);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateSystemRoleSelected(SystemRoleDto request) {
		return systemRoleMapper.updateSystemRoleSelected(request);
	}
	
	public List<SystemRoleDto> findRoles() {
		return systemRoleMapper.findRoles();
	}
	
	public List<SystemRoleDto> findAllRoles() {
		return systemRoleMapper.findAllRoles();
	}
	
}
