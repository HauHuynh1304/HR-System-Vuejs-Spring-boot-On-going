package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.model.SystemAccountModel;

@Mapper
public interface SystemAccountRoleMapper {

	int insertAccountRole(SystemAccountModel systemAccount, Integer[] roleIds);

}
