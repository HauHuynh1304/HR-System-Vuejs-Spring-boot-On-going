package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.AuthenAccountDto;

@Mapper
public interface AuthenAccountMapper {
	
	public AuthenAccountDto findAuthenAccountByEmail(String email);
	
}
