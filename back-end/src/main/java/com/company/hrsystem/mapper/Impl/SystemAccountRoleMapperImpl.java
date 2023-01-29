package com.company.hrsystem.mapper.Impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.SystemAccountDto;
import com.company.hrsystem.mapper.SystemAccountRoleMapper;

@MapperImpl
public class SystemAccountRoleMapperImpl {

	@Autowired
	private SystemAccountRoleMapper systemAccountRoleMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertAccountRole(SystemAccountDto systemAccount, Integer[] roleIds) {
		return systemAccountRoleMapper.insertAccountRole(systemAccount, roleIds);
	}

	/**
	 * 
	 * @param dto
	 * @param roleIds
	 * @param employeeId
	 * @param action
	 * @param rowId
	 * @param tableName
	 * @param request
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int delAccoutRoleById(SystemAccountDto systemAccount, Integer[] roleIds, Timestamp updatedAt) {
		return systemAccountRoleMapper.delAccoutRoleById(systemAccount, roleIds, updatedAt);
	}
	
	public List<Integer> findRolesBySystemAccountId(Integer systemAccountId) {
		return systemAccountRoleMapper.findRolesBySystemAccountId(systemAccountId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int enableAccoutRoleById(SystemAccountDto systemAccount, Integer[] roleIds, Timestamp updatedAt) {
		return systemAccountRoleMapper.enableAccoutRoleById(systemAccount, roleIds, updatedAt);
	}

}
