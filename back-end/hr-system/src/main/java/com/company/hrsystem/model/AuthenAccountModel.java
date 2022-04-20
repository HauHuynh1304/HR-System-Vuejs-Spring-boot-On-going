package com.company.hrsystem.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthenAccountModel {
	
	private String systemEmail;

    private String systemPassword;
    
    private List<AuthenRoleModel> authenRoleModels;
    
}
