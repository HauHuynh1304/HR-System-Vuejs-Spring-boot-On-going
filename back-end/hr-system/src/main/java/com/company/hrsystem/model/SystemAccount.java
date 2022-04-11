package com.company.hrsystem.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SystemAccount {
   
    private Integer systemAccountId;

    private String systemEmail;

    private String systemPassword;

    private Boolean deletedFlag;

    private Date createdAt;

    private Date updatedAt;
    
}