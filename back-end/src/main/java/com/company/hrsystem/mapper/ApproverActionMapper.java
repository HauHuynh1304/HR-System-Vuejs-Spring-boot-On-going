package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.ApproverActionDto;

@Mapper
public interface ApproverActionMapper {

	int insertApproverAction(ApproverActionDto approverActionDto);

	int updateActionByApprover(ApproverActionDto approverActionDto);

}
