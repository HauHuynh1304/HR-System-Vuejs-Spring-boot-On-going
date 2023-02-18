package com.company.hrsystem.aspect;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.company.hrsystem.commons.constants.CommonConstant;
import com.company.hrsystem.commons.enums.BusinessRequestStatusEnum;
import com.company.hrsystem.commons.enums.ObjectInstance;
import com.company.hrsystem.commons.utils.AuthenUtil;
import com.company.hrsystem.commons.utils.ClientUtil;
import com.company.hrsystem.commons.utils.DateUtil;
import com.company.hrsystem.commons.utils.ObjectUtil;
import com.company.hrsystem.dto.HistoryActionDto;
import com.company.hrsystem.dto.SupervisorActionDto;
import com.company.hrsystem.dto.SystemAccountDto;
import com.company.hrsystem.mapper.Impl.EmployeeMapperImpl;
import com.company.hrsystem.mapper.Impl.HistoryActionMapperImpl;
import com.company.hrsystem.request.ApproverActionRequest;
import com.company.hrsystem.request.BusinessRequest;
import com.company.hrsystem.request.ChangePasswordRequest;
import com.company.hrsystem.request.DocumentRequest;
import com.company.hrsystem.request.EmployeeRequest;
import com.company.hrsystem.request.MutipleUpdateRequestTicketStatusRequest;
import com.company.hrsystem.request.PositionRequest;
import com.company.hrsystem.request.ReasonRequest;
import com.company.hrsystem.request.RequestTypeRequest;
import com.company.hrsystem.request.RequesterActionRequest;
import com.company.hrsystem.request.RoomRequest;
import com.company.hrsystem.request.SignUpRequest;
import com.company.hrsystem.request.SupervisorActionRequest;
import com.company.hrsystem.request.SystemRoleRequest;
import com.company.hrsystem.request.UpdateAccountRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Aspect
@Component
public class WriteLogToDBAspect {
	
	public static final String NON_VALUE = "N/A";

	@Autowired
	private EmployeeMapperImpl employeeMapperImpl;

	@Autowired
	private HistoryActionMapperImpl historyActionMapperImpl;

	@Pointcut(value = "@annotation(com.company.hrsystem.annotations.WriteLogToDB)")
	public void annotationPointCutDefinition() {
	}
	
	@AfterReturning("annotationPointCutDefinition() && args(servletRequest)")
	public void doWrite(JoinPoint joinPoint, HttpServletRequest servletRequest) {
		HistoryActionDto history = null;
		String methodName = joinPoint.getSignature().getName();

		switch (methodName) {
			case "handleLogOut": // Scope: AuthenticationService.handleLogOut
				history = initConstantInfo(CommonConstant.ZERO_VALUE, CommonConstant.LOGOUT_ACTION, servletRequest);
				history.setTargetValue(NON_VALUE);
				break;
			default:
				break;
		}
		historyActionMapperImpl.insertHistoryActionNoTransaction(history);
	}
	
	@AfterReturning("annotationPointCutDefinition() && args(request, servletRequest)")
	public void doWrite(JoinPoint joinPoint, Object request, HttpServletRequest servletRequest) {
		String methodName = joinPoint.getSignature().getName();
		ObjectInstance objectInstance = ObjectInstance.get(request.getClass().getName());
		HistoryActionDto history = null;
		String targetTable = "";
		int doChangeDataByUserId = CommonConstant.ZERO_VALUE;
		switch (objectInstance) {
			case AUTHEN_REQUEST: // Scope: AuthenticationService.handleLogin
				history = initConstantInfo(doChangeDataByUserId, CommonConstant.LOGIN_ACTION, servletRequest);
				history.setTargetValue(NON_VALUE);
				break;
			case SIGNUP_REQUEST: // Scope: AuthenticationService.handleSignUp
				history = initConstantInfo(doChangeDataByUserId, CommonConstant.INSERT_ACTION, servletRequest);
				history.setTargetTable(
						CommonConstant.TABLE_SYSTEM_ACCOUNT + ", " + CommonConstant.TABLE_SYSTEM_ACCOUNT_ROLE);
				history.setTargetValue(ObjectUtil.dataToString(((SignUpRequest) request).getData()));
				break;
			case CHANGE_PASSWORD_REQUEST: // Scope: AuthenticationService.handleChangePassword
				history = initConstantInfo(doChangeDataByUserId, CommonConstant.CHANGE_PW_ACTION, servletRequest);
				history.setTargetTable(CommonConstant.TABLE_SYSTEM_ACCOUNT);
				history.setTargetValue(((ChangePasswordRequest) request).getData().getAccount().getSystemPassword());
				break;
			case UPDATE_ACCOUNT_REQUEST: // Scope: AuthenticationService.updateAccount
				UpdateAccountRequest updateAccountRequest = ((UpdateAccountRequest) request).getData();
				SystemAccountDto account = updateAccountRequest.getAccount();
				
				doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
				history = initConstantInfo(doChangeDataByUserId, CommonConstant.UPDATE_ACTION, servletRequest);
				
				if (StringUtils.isNotEmpty(account.getSystemPassword()) 
						|| ObjectUtils.isNotEmpty(account.getDeletedFlag())) {
					targetTable = targetTable.concat(CommonConstant.TABLE_SYSTEM_ACCOUNT);
				}
				
				if (updateAccountRequest.getDeleteRoleIds().length > 0
						|| updateAccountRequest.getAddNewRoleIds().length > 0) {
					targetTable = StringUtils.isNotEmpty(targetTable)
							? targetTable.concat(", " + CommonConstant.TABLE_SYSTEM_ACCOUNT_ROLE)
							: targetTable.concat(CommonConstant.TABLE_SYSTEM_ACCOUNT_ROLE);
				}
				
				history.setTargetTable(targetTable);
				history.setTargetValue(ObjectUtil.dataToString(updateAccountRequest));
				break;
			case BUSINESS_REQUEST: // Scope: BusinessRequestService.insertBusinessRequest
				BusinessRequest businessRequest =  ((BusinessRequest) request).getData();
				doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
				
				history = initConstantInfo(doChangeDataByUserId, CommonConstant.INSERT_ACTION, servletRequest);
				history.setTargetTable(CommonConstant.TABLE_REQUEST_TICKET + ", "
						+ CommonConstant.TABLE_REQUESTER_ACTION + ", " + CommonConstant.TABLE_SUPERVISOR_ACTION + ", "
						+ CommonConstant.TABLE_APPROVER_ACTION + ", " + CommonConstant.TABLE_REQUEST_EMPLOYEE);
				history.setTargetValue(ObjectUtil.dataToString(businessRequest));
				break;
			case REQUESTER_ACTION: // Scope: BusinessRequestService.updateRequesterAction
				RequesterActionRequest requesterAction =  ((RequesterActionRequest) request).getData();
				doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
				history = initConstantInfo(doChangeDataByUserId, CommonConstant.UPDATE_ACTION, servletRequest);
				history.setTargetTable(
						CommonConstant.TABLE_REQUESTER_ACTION + ", " + CommonConstant.TABLE_REQUEST_EMPLOYEE);
				history.setTargetValue(ObjectUtil.dataToString(requesterAction));
				break;
			case SUPERVISOR_ACTION: // Scope: BusinessRequestService.updateSupervisorAction
				SupervisorActionRequest supervisorAction =  ((SupervisorActionRequest) request).getData();
				doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
				history = initConstantInfo(doChangeDataByUserId, CommonConstant.UPDATE_ACTION, servletRequest);
				if (supervisorAction.getSupervisorAction().getActionType().toLowerCase()
						.equals(BusinessRequestStatusEnum.REJECT.toString().toLowerCase())) {
					history.setTargetTable(
							CommonConstant.TABLE_SUPERVISOR_ACTION + ", " + CommonConstant.TABLE_REQUEST_EMPLOYEE);
				} else {
					history.setTargetTable(CommonConstant.TABLE_SUPERVISOR_ACTION);
				}
				history.setTargetValue(ObjectUtil.dataToString(supervisorAction));
				break;
			case APPROVER_ACTION: // Scope: BusinessRequestService.updateApproverAction
				ApproverActionRequest approverAction =  ((ApproverActionRequest) request).getData();
				doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
				history = initConstantInfo(doChangeDataByUserId, CommonConstant.UPDATE_ACTION, servletRequest);
				history.setTargetTable(
						CommonConstant.TABLE_APPROVER_ACTION + ", " + CommonConstant.TABLE_REQUEST_EMPLOYEE);
				history.setTargetValue(ObjectUtil.dataToString(approverAction));
				break;
			case MULTIPLE_ACTION: // Scope: BusinessRequestService.mutipleUpdateRequestTicketStatus
				MutipleUpdateRequestTicketStatusRequest multipleAction =  ((MutipleUpdateRequestTicketStatusRequest) request).getData();
				doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
				history = initConstantInfo(doChangeDataByUserId, CommonConstant.UPDATE_ACTION, servletRequest);

				if (!CollectionUtils.isEmpty(multipleAction.getSupervisorAction())) {
					switch (isCancelStatus(multipleAction.getSupervisorAction()).toString()) {
						case "true":
							history.setTargetTable(
									CommonConstant.TABLE_SUPERVISOR_ACTION + ", " + CommonConstant.TABLE_REQUEST_EMPLOYEE);
							break;
						default:
							history.setTargetTable(CommonConstant.TABLE_SUPERVISOR_ACTION);
							break;
					}
				} else {
					history.setTargetTable(
							CommonConstant.TABLE_APPROVER_ACTION + ", " + CommonConstant.TABLE_REQUEST_EMPLOYEE);
				}
				history.setTargetValue(ObjectUtil.dataToString(multipleAction));
				break;
			case SYSTEM_ROLE: // Scope: MasterService
				SystemRoleRequest systemRoleRequest =  ((SystemRoleRequest) request).getData();
				doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
				switch (methodName) {
					case "insertSystemRole":
						history = initConstantInfo(doChangeDataByUserId, CommonConstant.INSERT_ACTION, servletRequest);
						break;
					case "updateSystemRole":
						history = initConstantInfo(doChangeDataByUserId, CommonConstant.UPDATE_ACTION, servletRequest);
						break;
					default:
						break;
					}
				history.setTargetTable(CommonConstant.TABLE_SYSTEM_ROLE);
				history.setTargetValue(ObjectUtil.dataToString(systemRoleRequest));
				break;
			case DOCUMENT: // Scope: MasterService
				DocumentRequest documentRequest =  ((DocumentRequest) request).getData();
				doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
				switch (methodName) {
					case "insertDocument":
						history = initConstantInfo(doChangeDataByUserId, CommonConstant.INSERT_ACTION, servletRequest);
						break;
					case "updateDocument":
						history = initConstantInfo(doChangeDataByUserId, CommonConstant.UPDATE_ACTION, servletRequest);
						break;
					default:
						break;
					}
				history.setTargetTable(CommonConstant.TABLE_DOCUMENT);
				history.setTargetValue(ObjectUtil.dataToString(documentRequest));
				break;
			case POSITION: // Scope: MasterService
				PositionRequest positionRequest =  ((PositionRequest) request).getData();
				doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
				switch (methodName) {
					case "insertPosition":
						history = initConstantInfo(doChangeDataByUserId, CommonConstant.INSERT_ACTION, servletRequest);
						break;
					case "updatePosition":
						history = initConstantInfo(doChangeDataByUserId, CommonConstant.UPDATE_ACTION, servletRequest);
						break;
					default:
						break;
					}
				history.setTargetTable(CommonConstant.TABLE_POSITION);
				history.setTargetValue(ObjectUtil.dataToString(positionRequest));
				break;
			case REASON: // Scope: MasterService
				ReasonRequest reasonRequest =  ((ReasonRequest) request).getData();
				doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
				switch (methodName) {
					case "insertReason":
						history = initConstantInfo(doChangeDataByUserId, CommonConstant.INSERT_ACTION, servletRequest);
						break;
					case "updateReason":
						history = initConstantInfo(doChangeDataByUserId, CommonConstant.UPDATE_ACTION, servletRequest);
						break;
					default:
						break;
					}
				history.setTargetTable(CommonConstant.TABLE_REASON);
				history.setTargetValue(ObjectUtil.dataToString(reasonRequest));
				break;
			case REQUEST_TYPE: // Scope: MasterService
				RequestTypeRequest requestType =  ((RequestTypeRequest) request).getData();
				doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
				switch (methodName) {
					case "insertRequestType":
						history = initConstantInfo(doChangeDataByUserId, CommonConstant.INSERT_ACTION, servletRequest);
						break;
					case "updateRequestType":
						history = initConstantInfo(doChangeDataByUserId, CommonConstant.UPDATE_ACTION, servletRequest);
						break;
					default:
						break;
					}
				history.setTargetTable(CommonConstant.TABLE_REQUEST_TYPE);
				history.setTargetValue(ObjectUtil.dataToString(requestType));
				break;
			case ROOM: // Scope: MasterService
				RoomRequest roomRequest  =  ((RoomRequest) request).getData();
				doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
				switch (methodName) {
					case "insertRequestType":
						history = initConstantInfo(doChangeDataByUserId, CommonConstant.INSERT_ACTION, servletRequest);
						break;
					case "updateRequestType":
						history = initConstantInfo(doChangeDataByUserId, CommonConstant.UPDATE_ACTION, servletRequest);
						break;
					default:
						break;
					}
				history.setTargetTable(CommonConstant.TABLE_ROOM);
				history.setTargetValue(ObjectUtil.dataToString(roomRequest));
				break;
			default:
				break;
		}
		historyActionMapperImpl.insertHistoryActionNoTransaction(history);
	}
	
	@AfterReturning("annotationPointCutDefinition() && args(jsonString, multipartFile, servletRequest)")
	public void doWrite(JoinPoint joinPoint, String jsonString, MultipartFile multipartFile, HttpServletRequest servletRequest) {
		Gson gson = new GsonBuilder().setDateFormat(DateUtil.DAY).create();
		EmployeeRequest request = gson.fromJson(jsonString, EmployeeRequest.class);
		HistoryActionDto history = null;
		String methodName = joinPoint.getSignature().getName();
		int doChangeDataByUserId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
		switch (methodName) {
			case "insertEmployee": // Scope: HumanResourceService.insertEmployee
				history = initConstantInfo(doChangeDataByUserId, CommonConstant.INSERT_ACTION, servletRequest);
				if (ObjectUtils.isNotEmpty(request.getDocuments())) {
					history.setTargetTable(CommonConstant.TABLE_PERSONAL + ", " + CommonConstant.TABLE_EMPLOYEE + ", "
							+ CommonConstant.TABLE_EMPLOYEE_DOCUMENT + ", " + CommonConstant.TABLE_EMPLOYEE_POSITION);
				} else {
					history.setTargetTable(CommonConstant.TABLE_PERSONAL + ", " + CommonConstant.TABLE_EMPLOYEE + ", "
							+ CommonConstant.TABLE_EMPLOYEE_POSITION);
				}
				history.setTargetValue(ObjectUtil.dataToString(request));
				break;
			case "updateEmployee": // Scope: HumanResourceService.updateEmployee
				history = initConstantInfo(doChangeDataByUserId, CommonConstant.UPDATE_ACTION, servletRequest);
				
				String targetTable = "";
				if (ObjectUtil.countNotNullParamater(request.getEmployee()) > 1) {
					targetTable = targetTable.concat(CommonConstant.TABLE_EMPLOYEE);
				}
				
				if (ObjectUtil.countNotNullParamater(request.getPersonalInfo()) > 1 || multipartFile != null) {
					targetTable = StringUtils.isNotEmpty(targetTable)
							? targetTable.concat(", " + CommonConstant.TABLE_PERSONAL)
							: targetTable.concat(CommonConstant.TABLE_PERSONAL);
				}
				
				if (ObjectUtils.isNotEmpty(request.getDocuments())) {
					targetTable = StringUtils.isNotEmpty(targetTable)
							? targetTable.concat(", " + CommonConstant.TABLE_EMPLOYEE_DOCUMENT)
							: targetTable.concat(CommonConstant.TABLE_EMPLOYEE_DOCUMENT);
				}
				
				if (ObjectUtils.isNotEmpty(request.getPositions())) {
					targetTable = StringUtils.isNotEmpty(targetTable)
							? targetTable.concat(", " + CommonConstant.TABLE_EMPLOYEE_POSITION)
							: targetTable.concat(CommonConstant.TABLE_EMPLOYEE_POSITION);
				}
				history.setTargetTable(targetTable);
				
				history.setTargetValue(ObjectUtil.dataToString(request));
				break;
			default:
				break;
		}
		historyActionMapperImpl.insertHistoryActionNoTransaction(history);
	}
	
	private Boolean isCancelStatus(List<SupervisorActionDto> ListObject) {
		if (!CollectionUtils.isEmpty(ListObject)) {
			for (SupervisorActionDto el : ListObject) {
				if (el.getActionType().toLowerCase()
						.equals(BusinessRequestStatusEnum.REJECT.toString().toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private HistoryActionDto initConstantInfo(Integer employeeId, String action, HttpServletRequest request) {
		HistoryActionDto history = new HistoryActionDto();
		history.setComputerIp(ClientUtil.getClientIpAddress(request));
		history.setActionType(action);
		if (employeeId == CommonConstant.ZERO_VALUE) {
			employeeId = employeeMapperImpl.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
		}
		history.setEmployeeId(employeeId);
		
		return history;
	}

}
