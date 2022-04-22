package com.company.hrsystem.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
abstract class PersonalInfoModel {

	private Integer personalInfoId;

	private String personalName;

	private Date personalBirthday;

	private String personalAddress;

	private String personalPhoneNumber;

	private String personalSex;

	private String personalIdCard;

	private String personalEmail;

	private String personalImage;

	private Date createdAt;

	private Date updatedAt;

}