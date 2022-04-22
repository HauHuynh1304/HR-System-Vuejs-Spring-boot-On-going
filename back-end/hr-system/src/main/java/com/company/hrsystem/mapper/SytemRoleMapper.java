package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.request.SystemRoleRequest;

@Mapper
public interface SytemRoleMapper {
	
	int insertSystemRoleSelected(SystemRoleRequest request);
	
	int updateSystemRoleSelected(SystemRoleRequest request);
	
}
