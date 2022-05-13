package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.SystemRoleDto;

@Mapper
public interface SytemRoleMapper {
	
	int insertSystemRoleSelected(SystemRoleDto request);
	
	int updateSystemRoleSelected(SystemRoleDto request);
	
}
