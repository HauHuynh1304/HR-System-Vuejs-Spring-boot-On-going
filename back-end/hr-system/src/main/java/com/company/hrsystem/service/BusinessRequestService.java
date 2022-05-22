package com.company.hrsystem.service;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.constants.CommonConstant;
import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.dto.RequestEmployeeDto;
import com.company.hrsystem.dto.SupervisorActionDto;
import com.company.hrsystem.enums.BusinessRequestStatusEnum;
import com.company.hrsystem.enums.PartialDateEnum;
import com.company.hrsystem.mapper.ApproverActionMapper;
import com.company.hrsystem.mapper.CommentMapper;
import com.company.hrsystem.mapper.EmployeeMapper;
import com.company.hrsystem.mapper.RequestEmployeeMapper;
import com.company.hrsystem.mapper.RequestMapper;
import com.company.hrsystem.mapper.SupervisorAcctionMapper;
import com.company.hrsystem.request.ApproverActionRequest;
import com.company.hrsystem.request.BusinessRequest;
import com.company.hrsystem.request.CommentRequest;
import com.company.hrsystem.request.SupervisorActionRequest;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.utils.AuthenUtil;
import com.company.hrsystem.utils.DateUtil;
import com.company.hrsystem.utils.LogUtil;
import com.company.hrsystem.utils.MessageUtil;

@Service
public class BusinessRequestService {

	@Value("${system.name}")
	private String system;

	@Value("${system.version}")
	private String version;

	@Autowired
	private RequestMapper requestMapper;

	@Autowired
	private SupervisorAcctionMapper supervisorAcctionMapper;

	@Autowired
	private ApproverActionMapper approverActionMapper;

	@Autowired
	private RequestEmployeeMapper requestEmployeeMapper;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private AuthenUtil authenUtil;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate insertBusinessRequest(BusinessRequest request) {
		if (ObjectUtils.isEmpty(request.getData())) {
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("value.not.correct"));
		}
		String partialDate = request.getData().getRequestEmployee().getPartialDate();
		if (!PartialDateEnum.isExists(partialDate)) {
			LogUtil.warn(messageUtil.getMessagelangUS("Partial.date.not.correct"));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("value.not.correct"));
		}
		int insertRows = 0;
		RequestEmployeeDto requestEmployee = request.getData().getRequestEmployee();
		String startDate = request.getData().getRequestEmployee().getStartDate();
		String endDate = request.getData().getRequestEmployee().getEndDate();
		try {
			requestEmployee.setDuration(DateUtil.caculateDuration(partialDate, startDate, endDate));
			requestEmployee.setEmployeeId(employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId()));
			requestMapper.insertRequest(request.getData().getRequest());
			supervisorAcctionMapper.insertSupervisorAction(request.getData().getSupervisorAcction());
			approverActionMapper.insertApproverAction(request.getData().getApproverAction());
			insertRows = requestEmployeeMapper.insertRequestEmployee(request.getData().getRequest(),
					request.getData().getSupervisorAcction(), request.getData().getApproverAction(), requestEmployee);
			return new ResponseTemplate(system, version, HttpStatus.OK.value(),
					messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null, null);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("value.not.correct"));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate updateBusinessRequest(BusinessRequest request) {
		String requestStatus = request.getData().getRequestEmployee().getRequestStatus();
		if (requestStatus.isBlank() || BusinessRequestStatusEnum.isForbidentEmployee(requestStatus)
				|| !BusinessRequestStatusEnum.isExists(requestStatus)) {
			LogUtil.warn(messageUtil.getMessagelangUS("update.request.error"));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("update.request.error"));
		}
		String currentDayHourSecond = DateUtil.getCurrentDayHourSecond();
		RequestEmployeeDto obj = request.getData().getRequestEmployee();
		obj.setUpdatedAt(currentDayHourSecond);
		requestEmployeeMapper.updateRequestEmployee(obj);
		// update action type of supervisor and approver to CANCEL when
		// employee cancel request
		if (requestStatus.toLowerCase().equals(BusinessRequestStatusEnum.CANCEL.toString().toLowerCase())) {
			supervisorAcctionMapper.updateActionByEmployee(
					new SupervisorActionDto(request.getData().getSupervisorAcction().getSupervisorActionId(),
							BusinessRequestStatusEnum.CANCEL.toString(), currentDayHourSecond));
			approverActionMapper.updateActionByEmployee(
					new ApproverActionDto(request.getData().getApproverAction().getApproverActionId(),
							BusinessRequestStatusEnum.CANCEL.toString(), currentDayHourSecond));
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("update.request.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate updateSupervisorAction(SupervisorActionRequest request) {
		SupervisorActionDto supervisorActionDto = request.getData().getSupervisorAction();
		String requestStatus = supervisorActionDto.getActionType();
		isErrorRequestManager(requestStatus);
		String currentDayHourSecond = DateUtil.getCurrentDayHourSecond();
		supervisorActionDto.setUpdatedAt(currentDayHourSecond);
		int numberRecord = supervisorAcctionMapper.updateActionBySupervisor(supervisorActionDto);
		isInsertUpdateSucess(numberRecord);
		// update request status of employee to REJECT when
		// supervisor reject request from employee
		if (requestStatus.toLowerCase().equals(BusinessRequestStatusEnum.REJECT.toString().toLowerCase())) {
			numberRecord = requestEmployeeMapper.updateRequestBySupervisor(new RequestEmployeeDto(
					supervisorActionDto.getSupervisorActionId(), null, requestStatus, currentDayHourSecond));
			isInsertUpdateSucess(numberRecord);
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("update.request.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate updateApproverAction(ApproverActionRequest request) {
		ApproverActionDto approverActionDto = request.getData().getApproverAction();
		String requestStatus = approverActionDto.getActionType();
		isErrorRequestManager(requestStatus);
		String currentDayHourSecond = DateUtil.getCurrentDayHourSecond();
		approverActionDto.setUpdatedAt(currentDayHourSecond);
		int numberRecord = approverActionMapper.updateActionByApprover(approverActionDto);
		isInsertUpdateSucess(numberRecord);
		// force update request status of employee to REJECT when
		// approver reject request from employee
		numberRecord = requestEmployeeMapper.updateRequestByApprover(new RequestEmployeeDto(null,
				approverActionDto.getApproverActionId(), requestStatus, currentDayHourSecond));
		isInsertUpdateSucess(numberRecord);
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("update.request.success"), null, null);
	}

	public ResponseTemplate insertComment(CommentRequest request) {
		if (StringUtils.isBlank(request.getData().getComment().getCommentDetail())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.INSERT_COMMENT));
			throw new GlobalException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.INSERT_COMMENT));
		}
		int numberRecord = commentMapper.insertComment(request.getData().getComment());
		isInsertUpdateSucess(numberRecord);
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("comment.success"), null, null);
	}

	public void isErrorRequestManager(String status) {
		if (status.isBlank() || BusinessRequestStatusEnum.isForbidenManager(status)
				|| !BusinessRequestStatusEnum.isExists(status)) {
			LogUtil.warn(messageUtil.getMessagelangUS("update.request.error"));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("update.request.error"));
		}
	}

	public void isInsertUpdateSucess(Integer number) {
		if (number == 0) {
			LogUtil.warn(messageUtil.getMessagelangUS("update.request.error"));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("update.request.error"));
		}
	}

}
