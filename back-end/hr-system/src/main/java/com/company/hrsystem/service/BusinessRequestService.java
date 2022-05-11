package com.company.hrsystem.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.dto.RequestEmployeeDto;
import com.company.hrsystem.enums.BusinessRequestStatusEnum;
import com.company.hrsystem.enums.PartialDateEnum;
import com.company.hrsystem.mapper.ApproverActionMapper;
import com.company.hrsystem.mapper.EmployeeMapper;
import com.company.hrsystem.mapper.RequestEmployeeMapper;
import com.company.hrsystem.mapper.RequestMapper;
import com.company.hrsystem.mapper.SupervisorAcctionMapper;
import com.company.hrsystem.request.BusinessRequest;
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
	private MessageUtil messageUtil;

	@Autowired
	private AuthenUtil authenUtil;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate insertBusinessRequest(BusinessRequest request) {
		String partialDate = request.getData().getRequestEmployee().getPartialDate();
		String startDate = request.getData().getRequestEmployee().getStartDate();
		String endDate = request.getData().getRequestEmployee().getEndDate();
		RequestEmployeeDto requestEmployee = request.getData().getRequestEmployee();
		if (!PartialDateEnum.isExists(partialDate)) {
			LogUtil.warn(messageUtil.getMessagelangUS("Partial.date.not.correct"));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("value.not.correct"));
		}
		try {
			requestEmployee.setDuration(DateUtil.caculateDuration(partialDate, startDate, endDate));
			requestEmployee.setEmployeeId(employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId()));
			requestMapper.insertRequest(request.getData());
			supervisorAcctionMapper.insertSupervisorAction(request.getData().getSupervisorAcction());
			approverActionMapper.insertApproverAction(request.getData().getApproverAction());
			requestEmployeeMapper.insertRequestEmployee(request.getData(), request.getData().getSupervisorAcction(),
					request.getData().getApproverAction(), requestEmployee);
			return new ResponseTemplate(system, version, HttpStatus.OK.value(),
					messageUtil.getMessagelangUS("insert.request.success"), null, null);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("value.not.correct"));
		}
	}

	public ResponseTemplate updateBusinessRequest(BusinessRequest request) {
		String requestStatus = request.getData().getRequestEmployee().getRequestStatus();
		if (requestStatus.isBlank() || BusinessRequestStatusEnum.isForbidentEmployee(requestStatus)
				|| !BusinessRequestStatusEnum.isExists(requestStatus)) {
			LogUtil.warn(messageUtil.getMessagelangUS("update.request.error"));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("update.request.error"));
		}
		RequestEmployeeDto obj = request.getData().getRequestEmployee();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		requestEmployeeMapper.updateRequestEmployee(request.getData().getRequestEmployee());
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("update.request.success"), null, null);
	}

}
