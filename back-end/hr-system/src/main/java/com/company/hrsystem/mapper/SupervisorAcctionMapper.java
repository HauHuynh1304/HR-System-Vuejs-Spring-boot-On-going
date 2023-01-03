package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.dto.SupervisorActionDto;

@Mapper
public interface SupervisorAcctionMapper {
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	int insertSupervisorAction(SupervisorActionDto supervisorActionDto);

	int updateActionBySupervisor(SupervisorActionDto supervisorActionDto);

}
