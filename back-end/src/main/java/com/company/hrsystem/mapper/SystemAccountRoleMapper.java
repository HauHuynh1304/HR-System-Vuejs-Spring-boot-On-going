package com.company.hrsystem.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.SystemAccountDto;

@Mapper
public interface SystemAccountRoleMapper {

	int insertAccountRole(SystemAccountDto systemAccount, Integer[] roleIds);
	
	int delAccoutRoleById(SystemAccountDto systemAccount, Integer[] roleIds, Timestamp updatedAt);
	
	int enableAccoutRoleById(SystemAccountDto systemAccount, Integer[] roleIds, Timestamp updatedAt);

	List<Integer> findRolesBySystemAccountId(Integer systemAccountId);

}
