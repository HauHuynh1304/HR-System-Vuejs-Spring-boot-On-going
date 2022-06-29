package com.company.hrsystem.constants;

public class CommonConstant {

	public static final String ROOT_ROLE = "ROLE_ADMIN";

	public static final String ROLE_INSERT_NOT_NULL = "[roleName]";
	public static final String ROLE_UPDATE_NOT_NULL = "[roleId]";

	public static final String DOCUMENT_INSERT_NOT_NULL = "[documentName]";
	public static final String DOCUMENT_UPDATE_NOT_NULL = "[documentId]";

	public static final String POSITION_INSERT_NOT_NULL = "[positionName]";
	public static final String POSITION_UPDATE_NOT_NULL = "[positionId]";

	public static final String REASON_INSERT_NOT_NULL = "[reasonName]";
	public static final String REASON_UPDATE_NOT_NULL = "[reasonId]";

	public static final String REQUEST_TYPE_INSERT_NOT_NULL = "[requestTypeName]";
	public static final String REQUEST_TYPE_UPDATE_NOT_NULL = "[requestTypeId]";

	public static final String ROOM_INSERT_NOT_NULL = "[roomName]";
	public static final String ROOM_UPDATE_NOT_NULL = "[roomId]";

	public static final String EMPLOYEE_INSERT_NOT_NULL = "[roomId, systemAccountId, employeeStartDate]";
	public static final String EMPLOYEE_UPDATE_NOT_NULL = "[employeeId, roomId, systemAccountId, employeeStartDate]";
	public static final String EMPLOYEE_ID_NOT_NULL = "[employeeId]";

	public static final String INSERT_COMMENT = "[commentDetail]";

	// ACTION TYPE SAVE TO HISTORY TABLE
	public static final String INSERT_ACTION = "INSERT";
	public static final String UPDATE_ACTION = "UPDATE";
	public static final String LOGIN_ACTION = "LOG IN";
	public static final String LOGOUT_ACTION = "LOG OUT";
	public static final String CHANGE_PW_ACTION = "CHANGE PASSWORD";
	public static final String DELETE_ACTION = "SOFT DELETED";

	// DEFAULT VALUE
	public static final int ZERO_VALUE = 0;

	// TABLE NAME
	public static final String TABLE_SYSTEM_ROLE = "system_role";
	public static final String TABLE_DOCUMENT = "document";
	public static final String TABLE_POSITION = "position";
	public static final String TABLE_REASON = "reason";
	public static final String TABLE_REQUEST_TYPE = "request_type";
	public static final String TABLE_ROOM = "room";
	public static final String TABLE_SYSTEM_ACCOUNT = "system_account";
	public static final String TABLE_SYSTEM_ACCOUNT_ROLE = "system_account_role";
	public static final String TABLE_PERSONAL = "personal_info";
	public static final String TABLE_EMPLOYEE = "employee";
	public static final String TABLE_EMPLOYEE_DOCUMENT = "employee_document";
	public static final String TABLE_EMPLOYEE_POSITION = "employee_position";
	public static final String TABLE_REQUEST_TICKET = "request";
	public static final String TABLE_SUPERVISOR_ACTION = "supervisor_action";
	public static final String TABLE_APPROVER_ACTION = "approver_action";
	public static final String TABLE_REQUESTER_ACTION = "requester_action";
	public static final String TABLE_REQUEST_EMPLOYEE = "request_employee";
	public static final String TABLE_COMMENT = "comment";

}
