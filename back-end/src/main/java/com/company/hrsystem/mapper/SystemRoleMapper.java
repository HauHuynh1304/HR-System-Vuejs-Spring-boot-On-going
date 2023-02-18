package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.SystemRoleDto;

@Mapper
public interface SystemRoleMapper {

	int insertSystemRoleSelected(SystemRoleDto request);
	
	int insertListSystemRole(List<SystemRoleDto> dtos);

	int updateSystemRoleSelected(SystemRoleDto request);

	List<SystemRoleDto> findRoles();

	List<SystemRoleDto> findAllRoles();

}
