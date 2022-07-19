package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Date;

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

	private Date personalBirthday;

	private String personalAddress;

	private String personalPhoneNumber;

	private String personalSex;

	private String personalIdCard;

	private String personalEmail;

	private String personalImage;

	private String createdAt;

	private String updatedAt;

	public PersonalInfoModel(Integer personalInfoId, String personalName, Date personalBirthday,
			String personalAddress, String personalPhoneNumber, String personalSex, String personalIdCard,
			String personalEmail) {
		this.personalInfoId = personalInfoId;
		this.personalName = personalName;
		this.personalBirthday = personalBirthday;
		this.personalAddress = personalAddress;
		this.personalPhoneNumber = personalPhoneNumber;
		this.personalSex = personalSex;
		this.personalIdCard = personalIdCard;
		this.personalEmail = personalEmail;
	}

}