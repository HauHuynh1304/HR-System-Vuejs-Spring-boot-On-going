package com.company.hrsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.hrsystem.dto.AuthenAccountDto;
import com.company.hrsystem.mapper.AuthenAccountMapper;


@Service
public class AuthenAccountService {
	
	@Autowired
	AuthenAccountMapper authenAccountMapper;
	
	public AuthenAccountDto findAuthenAccountByEmail(String email) {
		return authenAccountMapper.findAuthenAccountByEmail(email);
	}
}
