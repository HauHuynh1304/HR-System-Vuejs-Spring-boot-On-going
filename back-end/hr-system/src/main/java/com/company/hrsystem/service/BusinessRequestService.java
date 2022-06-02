package com.company.hrsystem.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.company.hrsystem.dto.CommentDto;
import com.company.hrsystem.dto.RequestDto;
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
import com.company.hrsystem.request.FindListTicketRequest;
import com.company.hrsystem.request.SupervisorActionRequest;
import com.company.hrsystem.response.FindEmployeeResponse;
import com.company.hrsystem.response.FindListTicketResponse;
import com.company.hrsystem.response.FindTicketRequestByIdResponse;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.utils.AuthenUtil;
import com.company.hrsystem.utils.DateUtil;
import com.company.hrsystem.utils.FileUtil;
import com.company.hrsystem.utils.LogUtil;
import com.company.hrsystem.utils.MessageUtil;

@Service
public class BusinessRequestService {

	@Value("${system.name}")
	private String system;

	@Value("${system.version}")
	private String version;

	@Value("${upload.employee.img.dir}")
	private String uploadEmployeeImgDir;

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

	@Autowired
	private FileUtil fileUtil;

	@Autowired
	private HistoryActionService historyActionService;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate insertBusinessRequest(BusinessRequest request, HttpServletRequest httpServletRequest) {
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
			int employeeId = employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId());
			RequestDto requestDto = request.getData().getRequest();
			SupervisorActionDto supervisorActionDto = request.getData().getSupervisorAcction();
			ApproverActionDto approverActionDto = request.getData().getApproverAction();
			requestEmployee.setDuration(DateUtil.caculateDuration(partialDate, startDate, endDate));
			requestEmployee.setEmployeeId(employeeId);

			requestMapper.insertRequest(requestDto);
			historyActionService.saveHistoryAction(requestDto, employeeId, CommonConstant.INSERT_ACTION,
					requestDto.getRequestId(), CommonConstant.TABLE_REQUEST_TICKET, httpServletRequest);

			supervisorAcctionMapper.insertSupervisorAction(supervisorActionDto);
			historyActionService.saveHistoryAction(supervisorActionDto, employeeId, CommonConstant.INSERT_ACTION,
					supervisorActionDto.getSupervisorActionId(), CommonConstant.TABLE_SUPERVISOR_ACTION,
					httpServletRequest);

			approverActionMapper.insertApproverAction(approverActionDto);
			historyActionService.saveHistoryAction(approverActionDto, employeeId, CommonConstant.INSERT_ACTION,
					approverActionDto.getApproverActionId(), CommonConstant.TABLE_APPROVER_ACTION, httpServletRequest);

			insertRows = requestEmployeeMapper.insertRequestEmployee(requestDto, supervisorActionDto, approverActionDto,
					requestEmployee);
			historyActionService.saveHistoryAction(requestEmployee, employeeId, CommonConstant.INSERT_ACTION,
					requestDto.getRequestId(), CommonConstant.TABLE_REQUEST_EMPLOYEE, httpServletRequest);

			return new ResponseTemplate(system, version, HttpStatus.OK.value(),
					messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null, null);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("value.not.correct"));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate updateBusinessRequest(BusinessRequest request, HttpServletRequest httpServletRequest) {
		String requestStatus = request.getData().getRequestEmployee().getRequestStatus();
		if (requestStatus.isBlank() || BusinessRequestStatusEnum.isForbidentEmployee(requestStatus)
				|| !BusinessRequestStatusEnum.isExists(requestStatus)) {
			LogUtil.warn(messageUtil.getMessagelangUS("update.request.error"));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("update.request.error"));
		}
		int employeeId = employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId());
		String currentDayHourSecond = DateUtil.getCurrentDayHourSecond();
		RequestEmployeeDto obj = request.getData().getRequestEmployee();
		obj.setUpdatedAt(currentDayHourSecond);

		requestEmployeeMapper.updateRequestEmployee(obj);
		historyActionService.saveHistoryAction(obj, employeeId, CommonConstant.UPDATE_ACTION, obj.getRequestId(),
				CommonConstant.TABLE_REQUEST_EMPLOYEE, httpServletRequest);

		// update action type of supervisor and approver to CANCEL when
		// employee cancel request
		if (requestStatus.toLowerCase().equals(BusinessRequestStatusEnum.CANCEL.toString().toLowerCase())) {
			SupervisorActionDto supervisorActionDto = new SupervisorActionDto(
					request.getData().getSupervisorAcction().getSupervisorActionId(),
					BusinessRequestStatusEnum.CANCEL.toString(), currentDayHourSecond);
			supervisorAcctionMapper.updateActionByEmployee(supervisorActionDto);
			historyActionService.saveHistoryAction(supervisorActionDto, employeeId, CommonConstant.UPDATE_ACTION,
					supervisorActionDto.getSupervisorActionId(), CommonConstant.TABLE_SUPERVISOR_ACTION,
					httpServletRequest);

			ApproverActionDto approverActionDto = new ApproverActionDto(
					request.getData().getApproverAction().getApproverActionId(),
					BusinessRequestStatusEnum.CANCEL.toString(), currentDayHourSecond);
			approverActionMapper.updateActionByEmployee(approverActionDto);
			historyActionService.saveHistoryAction(approverActionDto, employeeId, CommonConstant.UPDATE_ACTION,
					approverActionDto.getApproverActionId(), CommonConstant.TABLE_APPROVER_ACTION, httpServletRequest);
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("update.request.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate updateSupervisorAction(SupervisorActionRequest request,
			HttpServletRequest httpServletRequest) {
		SupervisorActionDto supervisorActionDto = request.getData().getSupervisorAction();
		String requestStatus = supervisorActionDto.getActionType();
		isErrorRequestManager(requestStatus);
		int employeeId = employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId());
		String currentDayHourSecond = DateUtil.getCurrentDayHourSecond();
		supervisorActionDto.setUpdatedAt(currentDayHourSecond);

		int numberRecord = supervisorAcctionMapper.updateActionBySupervisor(supervisorActionDto);
		historyActionService.saveHistoryAction(supervisorActionDto, employeeId, CommonConstant.UPDATE_ACTION,
				supervisorActionDto.getSupervisorActionId(), CommonConstant.TABLE_SUPERVISOR_ACTION,
				httpServletRequest);

		isInsertUpdateSucess(numberRecord);
		// update request status of employee to REJECT when
		// supervisor reject request from employee
		if (requestStatus.toLowerCase().equals(BusinessRequestStatusEnum.REJECT.toString().toLowerCase())) {
			RequestEmployeeDto obj = new RequestEmployeeDto(supervisorActionDto.getSupervisorActionId(), null,
					requestStatus, currentDayHourSecond);
			numberRecord = requestEmployeeMapper.updateRequestBySupervisor(obj);
			isInsertUpdateSucess(numberRecord);
			historyActionService.saveHistoryAction(obj, employeeId, CommonConstant.UPDATE_ACTION,
					requestEmployeeMapper.findRequestIdBySupervisorActionId(obj.getSupervisorActionId()),
					CommonConstant.TABLE_REQUEST_EMPLOYEE, httpServletRequest);
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("update.request.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate updateApproverAction(ApproverActionRequest request, HttpServletRequest httpServletRequest) {
		ApproverActionDto approverActionDto = request.getData().getApproverAction();
		String requestStatus = approverActionDto.getActionType();
		isErrorRequestManager(requestStatus);
		int employeeId = employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId());
		String currentDayHourSecond = DateUtil.getCurrentDayHourSecond();
		approverActionDto.setUpdatedAt(currentDayHourSecond);

		int numberRecord = approverActionMapper.updateActionByApprover(approverActionDto);
		historyActionService.saveHistoryAction(approverActionDto, employeeId, CommonConstant.UPDATE_ACTION,
				approverActionDto.getApproverActionId(), CommonConstant.TABLE_APPROVER_ACTION, httpServletRequest);

		isInsertUpdateSucess(numberRecord);
		// force update request status of employee to REJECT when
		// approver reject request from employee
		RequestEmployeeDto obj = new RequestEmployeeDto(null, approverActionDto.getApproverActionId(), requestStatus,
				currentDayHourSecond);
		numberRecord = requestEmployeeMapper.updateRequestByApprover(obj);
		isInsertUpdateSucess(numberRecord);
		historyActionService.saveHistoryAction(obj, employeeId, CommonConstant.UPDATE_ACTION,
				requestEmployeeMapper.findRequestIdBySupervisorActionId(obj.getApproverActionId()),
				CommonConstant.TABLE_REQUEST_EMPLOYEE, httpServletRequest);
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("update.request.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate insertComment(CommentRequest request, HttpServletRequest httpServletRequest) {
		if (StringUtils.isBlank(request.getData().getComment().getCommentDetail())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.INSERT_COMMENT));
			throw new GlobalException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.INSERT_COMMENT));
		}
		CommentDto obj = request.getData().getComment();
		int numberRecord = commentMapper.insertComment(obj);
		isInsertUpdateSucess(numberRecord);
		historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.INSERT_ACTION,
				obj.getCommentId(), CommonConstant.TABLE_COMMENT, httpServletRequest);
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("comment.success"), null, null);
	}

	public ResponseTemplate findListCreatedRequestTicket(FindListTicketRequest request) {
		request.getData().getRequestEmployee()
				.setEmployeeId(employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId()));
		List<FindListTicketResponse> listObj = requestEmployeeMapper.findListCreatedRequestTicket(request.getData());
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("get.data", String.valueOf(listObj.size())), null, listObj);
	}

	public ResponseTemplate findRequestTicketById(String id) {
		FindTicketRequestByIdResponse obj = requestEmployeeMapper.findRequestTicketById(id);
		if (ObjectUtils.isEmpty(obj)) {
			return new ResponseTemplate(system, version, HttpStatus.OK.value(),
					messageUtil.getFlexMessageLangUS("get.data", String.valueOf(0)), null, null);
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, obj);
	}

	public ResponseTemplate findListReceivedRequestTicket(FindListTicketRequest request) {
		request.getData().getRequestEmployee()
				.setEmployeeId(employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId()));
		List<FindListTicketResponse> listObj = requestEmployeeMapper.findListReceivedRequestTicket(request.getData());
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("get.data", String.valueOf(listObj.size())), null, listObj);
	}

	public ResponseTemplate findCurrentUser() {
		int employeeId = employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId());
		FindEmployeeResponse obj = employeeMapper.findEmployeeById(employeeId);
		try {
			String image = fileUtil.encodeImg(uploadEmployeeImgDir, obj.getPersonalInfo().getPersonalInfoId(),
					obj.getPersonalInfo().getPersonalImage());
			obj.getPersonalInfo().setPersonalImage(image);
		} catch (IOException e) {
			LogUtil.error(messageUtil.getFlexMessageLangUS("get.image.error", String.valueOf(employeeId)));
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, obj);
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
