package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.model.AuthenAccountModel;

@Mapper
public interface AuthenAccountMapper {
	
	public AuthenAccountModel findAuthenAccountByEmail(String email);
	
}
