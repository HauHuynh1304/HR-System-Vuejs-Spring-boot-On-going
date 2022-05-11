package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.request.ApproverActionRequest;

@Mapper
public interface ApproverActionMapper {

	int insertApproverAction(ApproverActionDto approverActionDto);

	/**
	 * This method related to update status CANCEL by employee
	 */
	int updateActionByEmployee(ApproverActionDto approverActionDto);

	/**
	 * This method related to update by Approver
	 */
	int updateActionByApprover(ApproverActionRequest request);

}
