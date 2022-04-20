package com.company.hrsystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.company.hrsystem.model.AuthenAccountModel;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
	@Autowired
	AuthenAccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AuthenAccountModel accountModel = accountService.findAuthenAccountByEmail(email);
		if(ObjectUtils.isEmpty(accountModel)) {
			throw new UsernameNotFoundException("User not found");
		}
		
		return new org.springframework.security.core.userdetails.User(
				email,
				accountModel.getSystemPassword(), 
				getAuthority(accountModel));	
		
	}
	
	private List<GrantedAuthority> getAuthority(AuthenAccountModel accountModel) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		accountModel.getAuthenRoleModels().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return authorities;
    }
}
