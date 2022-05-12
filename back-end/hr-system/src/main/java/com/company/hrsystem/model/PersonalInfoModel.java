package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class PersonalInfoModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer personalInfoId;

	private String personalName;

	private String personalBirthday;

	private String personalAddress;

	private String personalPhoneNumber;

	private String personalSex;

	private String personalIdCard;

	private String personalEmail;

	private String personalImage;

	private String createdAt;

	private String updatedAt;

}