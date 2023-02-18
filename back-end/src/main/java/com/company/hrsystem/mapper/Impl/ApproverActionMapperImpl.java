package com.company.hrsystem.mapper.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.mapper.ApproverActionMapper;

@MapperImpl
public class ApproverActionMapperImpl implements ApproverActionMapper {

	@Autowired
	private ApproverActionMapper approverActionMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertApproverAction(ApproverActionDto approverActionDto) {
		return approverActionMapper.insertApproverAction(approverActionDto);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateActionByApprover(ApproverActionDto approverActionDto) {
		return approverActionMapper.updateActionByApprover(approverActionDto);
	}

}
