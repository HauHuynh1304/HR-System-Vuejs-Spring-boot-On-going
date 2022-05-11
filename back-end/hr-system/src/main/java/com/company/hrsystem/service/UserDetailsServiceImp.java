package com.company.hrsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.company.hrsystem.dto.AuthenAccountDto;
import com.company.hrsystem.mapper.SystemAccountMapper;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
	@Autowired
	SystemAccountMapper systemAccountMapper;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AuthenAccountDto accountModel = systemAccountMapper.findAuthenAccountByEmail(email);
		if (ObjectUtils.isEmpty(accountModel)) {
			throw new UsernameNotFoundException("User not found");
		}
		return UserDetailsImpl.build(accountModel);
	}

}
