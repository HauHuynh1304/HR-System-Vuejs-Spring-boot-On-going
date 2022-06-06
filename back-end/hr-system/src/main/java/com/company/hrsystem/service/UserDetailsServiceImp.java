package com.company.hrsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.company.hrsystem.Exeption.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.company.hrsystem.dto.AuthenAccountDto;
import com.company.hrsystem.mapper.SystemAccountMapper;
import com.company.hrsystem.utils.MessageUtil;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Value("${system.name}")
	private String system;

	@Value("${system.version}")
	private String version;

	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	SystemAccountMapper systemAccountMapper;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AuthenAccountDto accountModel = systemAccountMapper.findAuthenAccountByEmail(email);
		if (ObjectUtils.isEmpty(accountModel)) {
			throw new UsernameNotFoundException(system, version, messageUtil.getMessagelangUS("user.login.err"));
		}
		return UserDetailsImpl.build(accountModel);
	}

}
