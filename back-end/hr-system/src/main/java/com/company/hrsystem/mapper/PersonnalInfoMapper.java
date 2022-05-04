package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.PersonalInfoDto;

@Mapper
public interface PersonnalInfoMapper {

	int insertPersonalInfo(PersonalInfoDto personalInfo);

}
