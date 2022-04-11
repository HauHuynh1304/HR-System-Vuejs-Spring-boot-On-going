package com.company.hrsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.hrsystem.mapper.AuthenAccountMapper;
import com.company.hrsystem.model.AuthenAccountModel;


@Service
public class AuthenAccountService {
	
	@Autowired
	AuthenAccountMapper authenAccountMapper;
	
	public AuthenAccountModel findAuthenAccountByEmail(String email) {
		return authenAccountMapper.findAuthenAccountByEmail(email);
	}
}
