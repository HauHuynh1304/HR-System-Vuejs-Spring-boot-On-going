package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.SupervisorActionDto;

@Mapper
public interface SupervisorAcctionMapper {
	
	int insertSupervisorAction(SupervisorActionDto SupervisorActionDto);
	
}
