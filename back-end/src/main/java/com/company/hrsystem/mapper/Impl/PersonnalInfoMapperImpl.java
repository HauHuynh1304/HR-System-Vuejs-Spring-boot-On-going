package com.company.hrsystem.mapper.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.PersonalInfoDto;
import com.company.hrsystem.mapper.PersonnalInfoMapper;

@MapperImpl
public class PersonnalInfoMapperImpl {

	@Autowired
	private PersonnalInfoMapper personnalInfoMapper;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertPersonalInfo(PersonalInfoDto personalInfo) {
		return personnalInfoMapper.insertPersonalInfo(personalInfo);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updatePersonalInfo(PersonalInfoDto personalInfo) {
		return personnalInfoMapper.updatePersonalInfo(personalInfo);
	}
	
	public Boolean isExistPersonInfo(String personalEmail) {
		return personnalInfoMapper.isExistPersonInfo(personalEmail);
	}
	
}
