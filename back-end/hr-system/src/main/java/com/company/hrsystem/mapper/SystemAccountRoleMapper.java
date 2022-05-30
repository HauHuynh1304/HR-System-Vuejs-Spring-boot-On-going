package com.company.hrsystem.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.SystemAccountRoleDto;
import com.company.hrsystem.model.SystemAccountModel;

@Mapper
public interface SystemAccountRoleMapper {

	int insertAccountRole(SystemAccountModel systemAccount, Integer[] roleIds);
	
	Set<SystemAccountRoleDto> findNewestRecordsByCurrentUser(Integer systemAccountId);

}
