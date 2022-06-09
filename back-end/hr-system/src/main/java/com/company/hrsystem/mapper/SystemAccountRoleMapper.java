package com.company.hrsystem.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.SystemAccountDto;
import com.company.hrsystem.dto.SystemAccountRoleDto;

@Mapper
public interface SystemAccountRoleMapper {

	int insertAccountRole(SystemAccountDto systemAccount, Integer[] roleIds);
	
	Set<SystemAccountRoleDto> findNewestRecordsByCurrentUser(Integer systemAccountId);
	
	int delAccoutRoleById(Integer[] systemRoleIds);

}
