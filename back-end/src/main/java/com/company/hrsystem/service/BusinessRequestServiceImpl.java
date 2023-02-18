package com.company.hrsystem.service;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.company.hrsystem.annotations.SendEmail;
import com.company.hrsystem.annotations.WriteLogToDB;
import com.company.hrsystem.commons.configs.SystemProperties;
import com.company.hrsystem.commons.constants.CommonConstant;
import com.company.hrsystem.commons.enums.BusinessRequestStatusEnum;
import com.company.hrsystem.commons.enums.PartialDateEnum;
import com.company.hrsystem.commons.exceptions.GlobalException;
import com.company.hrsystem.commons.utils.AuthenUtil;
import com.company.hrsystem.commons.utils.DateUtil;
import com.company.hrsystem.commons.utils.FileUtil;
import com.company.hrsystem.commons.utils.LogUtil;
import com.company.hrsystem.commons.utils.MessageUtil;
import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.dto.CommentDto;
import com.company.hrsystem.dto.NotificationDto;
import com.company.hrsystem.dto.ReasonDto;
import com.company.hrsystem.dto.RequestDto;
import com.company.hrsystem.dto.RequestEmployeeDto;
import com.company.hrsystem.dto.RequestTypeDto;
import com.company.hrsystem.dto.RequesterActionDto;
import com.company.hrsystem.dto.SupervisorActionDto;
import com.company.hrsystem.dto.SystemAccountDto;
import com.company.hrsystem.mapper.HistoryActionMapper;
import com.company.hrsystem.mapper.Impl.ApproverActionMapperImpl;
import com.company.hrsystem.mapper.Impl.CommentMapperImpl;
import com.company.hrsystem.mapper.Impl.EmployeeDocumentMapperImpl;
import com.company.hrsystem.mapper.Impl.EmployeeMapperImpl;
import com.company.hrsystem.mapper.Impl.EmployeePositionMapperImpl;
import com.company.hrsystem.mapper.Impl.NotificationMapperImpl;
import com.company.hrsystem.mapper.Impl.ReasonMapperImpl;
import com.company.hrsystem.mapper.Impl.RequestEmployeeMapperImpl;
import com.company.hrsystem.mapper.Impl.RequestMapperImpl;
import com.company.hrsystem.mapper.Impl.RequestTypeMapperImpl;
import com.company.hrsystem.mapper.Impl.RequesterActionMapperImpl;
import com.company.hrsystem.mapper.Impl.SupervisorAcctionMapperImpl;
import com.company.hrsystem.mapper.Impl.SystemAccountMapperImpl;
import com.company.hrsystem.request.ApproverActionRequest;
import com.company.hrsystem.request.BusinessRequest;
import com.company.hrsystem.request.CommentRequest;
import com.company.hrsystem.request.FindListTicketRequest;
import com.company.hrsystem.request.MutipleUpdateRequestTicketStatusRequest;
import com.company.hrsystem.request.NotificationRequest;
import com.company.hrsystem.request.RequesterActionRequest;
import com.company.hrsystem.request.SupervisorActionRequest;
import com.company.hrsystem.response.FindEmployeeResponse;
import com.company.hrsystem.response.FindListTicketResponse;
import com.company.hrsystem.response.FindTicketRequestByIdResponse;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.service.interfaces.IBusinessRequestService;

@Service
public class BusinessRequestServiceImpl implements IBusinessRequestService {

	@Autowired
	private SupervisorAcctionMapperImpl supervisorAcctionMapperImpl;

	@Autowired
	private ApproverActionMapperImpl approverActionMapperImpl;

	@Autowired
	private RequestEmployeeMapperImpl requestEmployeeMapperImpl;

	@Autowired
	private EmployeeMapperImpl employeeMapperImpl;

	@Autowired
	private CommentMapperImpl commentMapperImpl;

	@Autowired
	private ReasonMapperImpl reasonMapperImpl;

	@Autowired
	private SystemAccountMapperImpl systemAccountMapperImpl;

	@Autowired
	private RequestTypeMapperImpl requestTypeMapperImpl;

	@Autowired
	private NotificationMapperImpl notificationMapperImpl;

	@Autowired
	private EmployeeDocumentMapperImpl employeeDocumentMapperImpl;

	@Autowired
	private EmployeePositionMapperImpl employeePositionMapperImpl;

	@Autowired
	private HistoryActionMapper historyActionMapper;

	@Autowired
	private RequesterActionMapperImpl requesterActionMapperImpl;

	@Autowired
	private RequestMapperImpl requestMapperImp;

	@Autowired
	private RequesterActionMapperImpl requesterActionMapperImp;

	@Autowired
	private SupervisorAcctionMapperImpl supervisorAcctionMapperImp;

	@Autowired
	private ApproverActionMapperImpl approverActionMapperImp;

	@Transactional
	@WriteLogToDB
	@SendEmail(mailType = CommonConstant.EMAIL_NEW_REQUEST_TYPE, subject = CommonConstant.EMAIL_SUBJECT_NEW_REQUEST)
	public ResponseTemplate insertBusinessRequest(BusinessRequest request, HttpServletRequest httpServletRequest) {
		if (ObjectUtils.isEmpty(request.getData())) {
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("value.not.correct"));
		}
		String partialDate = request.getData().getRequestEmployee().getPartialDate();
		if (!PartialDateEnum.isExists(partialDate)) {
			LogUtil.warn(MessageUtil.getMessagelangUS("Partial.date.not.correct"));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("value.not.correct"));
		}
		int insertRows = 0;
		RequestEmployeeDto requestEmployee = request.getData().getRequestEmployee();
		Timestamp startDate = request.getData().getRequestEmployee().getStartDate();
		Timestamp endDate = request.getData().getRequestEmployee().getEndDate();
		if (DateUtil.isPreviousMonthYear(startDate) || DateUtil.isPreviousMonthYear(endDate)) {
			LogUtil.warn(MessageUtil.getMessagelangUS("Partial.date.not.correct"));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("previous.month"));
		}
		try {
			int employeeId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
			RequestDto requestDto = request.getData().getRequest();
			SupervisorActionDto supervisorActionDto = request.getData().getSupervisorAcction();
			ApproverActionDto approverActionDto = request.getData().getApproverAction();
			requestEmployee.setDuration(DateUtil.caculateDuration(partialDate, startDate, endDate));
			requestEmployee.setEmployeeId(employeeId);

			requestMapperImp.insertRequest(requestDto);

			RequesterActionDto requesterActionDto = new RequesterActionDto();
			requesterActionDto.setRequesterId(employeeId);
			requesterActionMapperImp.insertRequesterAction(requesterActionDto);
			supervisorAcctionMapperImp.insertSupervisorAction(supervisorActionDto);
			approverActionMapperImp.insertApproverAction(approverActionDto);
			
			request.getData().setRequesterActionDto(requesterActionDto); // just a trick for saving log
			
			insertRows = requestEmployeeMapperImpl.insertRequestEmployee(requestDto, supervisorActionDto, approverActionDto,
					requestEmployee, requesterActionDto);

			notificationMapperImpl.insertNotification(
					new NotificationDto(requestDto.getRequestId(), employeeId, supervisorActionDto.getSupervisorId()));
			notificationMapperImpl.insertNotification(
					new NotificationDto(requestDto.getRequestId(), employeeId, approverActionDto.getApproverId()));

			return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)),
					null, null);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("value.not.correct"));
		}
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate updateRequesterAction(RequesterActionRequest request,
			HttpServletRequest httpServletRequest) {
		RequesterActionDto requesterActionDto = request.getData().getRequesterAction();
		String requestStatus = requesterActionDto.getActionType();
		Timestamp currentDayHourSecond = DateUtil.getCurrentDayHourSecond();
		requesterActionDto.setUpdatedAt(currentDayHourSecond);

		// Check valid status from Client, ONLY CANCEL STATUS can be apply
		if (StringUtils.isBlank(requestStatus) || BusinessRequestStatusEnum.isForbidentEmployee(requestStatus)
				|| !BusinessRequestStatusEnum.isExists(requestStatus)) {
			LogUtil.warn(MessageUtil.getMessagelangUS("update.request.error"));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("update.request.error"));
		}

		// Check current request status
		RequestEmployeeDto paramRequestEmployee = new RequestEmployeeDto();
		paramRequestEmployee.setRequesterActionId(requesterActionDto.getRequesterActionId());
		RequestEmployeeDto resultRequestEmployee = requestEmployeeMapperImpl
				.findCurrentRequestEmployee(paramRequestEmployee);
		isForbidenByRequestStatus(resultRequestEmployee.getRequestStatus());

		// Update requester action
		requesterActionMapperImpl.updateRequesterAction(requesterActionDto);
	
		// Force update request status
		RequestEmployeeDto obj = new RequestEmployeeDto(requesterActionDto.getRequesterActionId(), null, null,
				requestStatus, currentDayHourSecond);
		requestEmployeeMapperImpl.updateRequestEmployee(obj);

		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("update.request.success"), null, null);
	}

	@Transactional
	@WriteLogToDB
	@SendEmail(mailType = CommonConstant.EMAIL_UPDATE_REQUEST_TYPE, subject = CommonConstant.EMAIL_SUBJECT_UPDATE_REQUEST)
	public ResponseTemplate updateSupervisorAction(SupervisorActionRequest request,
			HttpServletRequest httpServletRequest) {
		SupervisorActionDto supervisorActionDto = request.getData().getSupervisorAction();
		updateSupervisorAction(supervisorActionDto);
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("update.request.success"), null, null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate updateApproverAction(ApproverActionRequest request, HttpServletRequest httpServletRequest) {
		ApproverActionDto approverActionDto = request.getData().getApproverAction();
		updateApproverAction(approverActionDto);
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("update.request.success"), null, null);
	}

	@Transactional
	public ResponseTemplate insertComment(CommentRequest request, HttpServletRequest httpServletRequest) {
		if (StringUtils.isBlank(request.getData().getComment().getCommentDetail())) {
			LogUtil.warn(MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.INSERT_COMMENT));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.INSERT_COMMENT));
		}
		CommentDto obj = request.getData().getComment();
		commentMapperImpl.insertComment(obj);
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("comment.success"), null, null);
	}

	public ResponseTemplate findListCreatedRequestTicket(FindListTicketRequest request) {
		request.getData().getRequestEmployee()
				.setEmployeeId(employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId()));
		List<FindListTicketResponse> listObj = requestEmployeeMapperImpl.findListCreatedRequestTicket(request.getData());
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("get.data", String.valueOf(listObj.size())),
				null, listObj);
	}

	public ResponseTemplate findRequestTicketById(Integer id) {
		FindTicketRequestByIdResponse obj = requestEmployeeMapperImpl.findRequestTicketById(id,
				employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId()));
		if (ObjectUtils.isEmpty(obj)) {
			return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("get.data", String.valueOf(0)), null, null);
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null, obj);
	}

	public ResponseTemplate findListReceivedRequestTicket(FindListTicketRequest request) {
		request.getData().getRequestEmployee()
				.setEmployeeId(employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId()));
		List<FindListTicketResponse> listObj = requestEmployeeMapperImpl.findListReceivedRequestTicket(request.getData());
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("get.data", String.valueOf(listObj.size())),
				null, listObj);
	}

	public ResponseTemplate findCurrentUser() {
		int employeeId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
		FindEmployeeResponse obj = employeeMapperImpl.findEmployeeById(employeeId);
		obj.getPersonalInfo().setPersonalImage(FileUtil.getUrlImg(SystemProperties.PATH_SAVE_EMPLOYEE_IMAGE,
				obj.getPersonalInfo().getPersonalInfoId(), obj.getPersonalInfo().getPersonalImage()));
		obj.setDocuments(employeeDocumentMapperImpl.findEmployeeDocumentsByEmployeeId(employeeId));
		obj.setPositions(employeePositionMapperImpl.findEmployeePositionsByEmployeeId(employeeId));
		obj.setHistoryActions(historyActionMapper.findHistoriesByEmployeeId(employeeId));
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null, obj);
	}

	public ResponseTemplate findAccountByRole(String role) {
		List<SystemAccountDto> listObj = systemAccountMapperImpl.findAccountByRole(role);
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("get.data", String.valueOf(listObj.size())),
				null, listObj);
	}

	public ResponseTemplate findReason() {
		List<ReasonDto> listObj = reasonMapperImpl.findReason();
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("get.data", String.valueOf(listObj.size())),
				null, listObj);
	}

	public ResponseTemplate findRequestType() {
		List<RequestTypeDto> listObj = requestTypeMapperImpl.findRequestType();
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("get.data", String.valueOf(listObj.size())),
				null, listObj);
	}

	public ResponseTemplate findEmployeeId() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId()));
	}

	public ResponseTemplate findListComment(Integer id) {
		List<CommentDto> listComment = commentMapperImpl.findListComment(id,
				employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId()));
		if (ObjectUtils.isEmpty(listComment)) {
			return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("get.data", String.valueOf(0)), null, null);
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null, listComment);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate mutipleUpdateRequestTicketStatus(MutipleUpdateRequestTicketStatusRequest request,
			HttpServletRequest httpServletRequest) {
		if (!CollectionUtils.isEmpty(request.getData().getSupervisorAction())) {
			List<SupervisorActionDto> collection = request.getData().getSupervisorAction();
			collection.stream().forEach(el -> updateSupervisorAction(el));
		}

		if (!CollectionUtils.isEmpty(request.getData().getApproverAction())) {
			List<ApproverActionDto> collection = request.getData().getApproverAction();
			collection.stream().forEach(el -> updateApproverAction(el));
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("update.request.success"), null, null);
	}

	public ResponseTemplate findNotificationByReceiverId() {
		int employeeId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				notificationMapperImpl.findNotificationByReceiverId(employeeId));
	}

	public ResponseTemplate markNotificationAsRead(NotificationRequest request) {
		if (request.getData().getNotificationId().length > 0) {
			notificationMapperImpl.markNotificationAsRead(request.getData());
			return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					HttpStatus.OK.value(), MessageUtil.getMessagelangUS("update.success"), null, null);
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("value.not.correct"), null, null);

	}

	public ResponseTemplate deleteNotificationByReceiver(NotificationRequest request) {
		if (request.getData().getNotificationId().length > 0) {
			notificationMapperImpl.deleteNotificationByReceiver(request.getData());
			return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					HttpStatus.OK.value(), MessageUtil.getMessagelangUS("update.success"), null, null);
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("value.not.correct"), null, null);
	}

	private void updateSupervisorAction(SupervisorActionDto supervisorActionDto) {
		Timestamp currentDayHourSecond = DateUtil.getCurrentDayHourSecond();
		String requestStatus = supervisorActionDto.getActionType();
		supervisorActionDto.setUpdatedAt(currentDayHourSecond);

		// Check valid status from Client
		isErrorRequestManager(requestStatus);

		// Check current request status
		RequestEmployeeDto paramRequestEmployee = new RequestEmployeeDto();
		paramRequestEmployee.setSupervisorActionId(supervisorActionDto.getSupervisorActionId());
		RequestEmployeeDto resultRequestEmployee = requestEmployeeMapperImpl
				.findCurrentRequestEmployee(paramRequestEmployee);
		isForbidenByRequestStatus(resultRequestEmployee.getRequestStatus());

		// Update Supervisor Action
		supervisorAcctionMapperImpl.updateActionBySupervisor(supervisorActionDto);

		// update request status of employee to REJECT when
		// supervisor reject request from employee
		if (requestStatus.toLowerCase().equals(BusinessRequestStatusEnum.REJECT.toString().toLowerCase())) {
			RequestEmployeeDto obj = new RequestEmployeeDto(null, supervisorActionDto.getSupervisorActionId(), null,
					requestStatus, currentDayHourSecond);
			requestEmployeeMapperImpl.updateRequestBySupervisor(obj);
		}
	}

	private void updateApproverAction(ApproverActionDto approverActionDto) {
		String requestStatus = approverActionDto.getActionType();
		Timestamp currentDayHourSecond = DateUtil.getCurrentDayHourSecond();
		approverActionDto.setUpdatedAt(currentDayHourSecond);

		// Check valid status from Client
		isErrorRequestManager(requestStatus);

		// Check current request status
		RequestEmployeeDto paramRequestEmployee = new RequestEmployeeDto();
		paramRequestEmployee.setApproverActionId(approverActionDto.getApproverActionId());
		RequestEmployeeDto resultRequestEmployee = requestEmployeeMapperImpl
				.findCurrentRequestEmployee(paramRequestEmployee);
		isForbidenByRequestStatus(resultRequestEmployee.getRequestStatus());

		// Update Approver Action
		approverActionMapperImpl.updateActionByApprover(approverActionDto);

		// force update request status
		RequestEmployeeDto obj = new RequestEmployeeDto(null, null, approverActionDto.getApproverActionId(),
				requestStatus, currentDayHourSecond);
		requestEmployeeMapperImpl.updateRequestByApprover(obj);
		
	}

	private void isForbidenByRequestStatus(String status) {
		if (BusinessRequestStatusEnum.isForbidenByRequestStatus(status)) {
			LogUtil.warn(MessageUtil.getMessagelangUS("update.request.error"));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("update.request.error"));
		}
	}

	private void isErrorRequestManager(String status) {
		if (StringUtils.isBlank(status) || BusinessRequestStatusEnum.isForbidenManager(status)
				|| !BusinessRequestStatusEnum.isExists(status)) {
			LogUtil.warn(MessageUtil.getMessagelangUS("update.request.error"));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("update.request.error"));
		}
	}

}
