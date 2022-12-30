package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.dto.ApproverActionDto;

@Mapper
public interface ApproverActionMapper {

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	int insertApproverAction(ApproverActionDto approverActionDto);

	int updateActionByApprover(ApproverActionDto approverActionDto);

}
