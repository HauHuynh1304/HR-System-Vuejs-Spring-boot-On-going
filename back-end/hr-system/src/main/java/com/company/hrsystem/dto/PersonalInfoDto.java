package com.company.hrsystem.dto;

import java.sql.Date;

import com.company.hrsystem.model.PersonalInfoModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PersonalInfoDto extends PersonalInfoModel {

	private static final long serialVersionUID = -6948419213324767844L;

	public PersonalInfoDto(Integer personalInfoId, String personalName, Date personalBirthday, String personalAddress,
			String personalPhoneNumber, String personalSex, String personalIdCard, String personalEmail) {
		super(personalInfoId, personalName, personalBirthday, personalAddress, personalPhoneNumber, personalSex,
				personalIdCard, personalEmail);
	}

}
