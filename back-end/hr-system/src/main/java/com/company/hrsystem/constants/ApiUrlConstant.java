package com.company.hrsystem.constants;

public class ApiUrlConstant {

	public static final String ROOT_API = "/hr-system/api";

	public static final String AUTHEN_LOG_IN = "/authen/log-in";
	public static final String AUTHEN_LOG_OUT = "/authen/log-out";
	public static final String AUTHEN_REFRESH_TOKEN = "/authen/refresh-token";
	public static final String AUTHEN_SIGN_UP = "/authen/sign-up";
	public static final String AUTHEN_CHANGE_PASSWORD = "/authen/change-password";
	public static final String AUTHEN_FIND_ACCOUNTS = "/authen/find-accounts";
	public static final String AUTHEN_UPDATE_ACCOUNT = "/authen/update-account";
	public static final String AUTHEN_IS_EMAIL_IN_DB = "/authen/is-email-in-db";

	public static final String MASTER_INSERT_ROLE = "/master/insert-role";
	public static final String MASTER_UPDATE_ROLE = "/master/update-role";
	public static final String MASTER_INSERT_DOCUMENT = "/master/insert-document";
	public static final String MASTER_UPDATE_DOCUMENT = "/master/update-document";
	public static final String MASTER_INSERT_POSITION = "/master/insert-position";
	public static final String MASTER_UPDATE_POSITION = "/master/update-position";
	public static final String MASTER_INSERT_REASON = "/master/insert-reason";
	public static final String MASTER_UPDATE_REASON = "/master/update-reason";
	public static final String MASTER_INSERT_REQUEST_TYPE = "/master/insert-request-type";
	public static final String MASTER_UPDATE_REQUEST_TYPE = "/master/update-request-type";
	public static final String MASTER_INSERT_ROOM = "/master/insert-room";
	public static final String MASTER_UPDATE_ROOM = "/master/update-room";
	public static final String MASTER_FIND_ROLES = "/master/find-roles";
	public static final String MASTER_FIND_ALL_ACCOUNTS = "/master/find-all-accounts";
	public static final String MASTER_FIND_AVAILABLE_ACCOUNTS = "/master/find-available-accounts";
	public static final String MASTER_FIND_ALL_ROOM = "/master/find-all-rooms";
	public static final String MASTER_FIND_ALL_POSITIONS = "/master/find-all-positions";
	public static final String MASTER_FIND_ALL_DOCUMENTS = "/master/find-all-documents";

	public static final String HUMAN_RESOURCE_INSERT_EMPLOYEE = "/human-resources/insert-employee";
	public static final String HUMAN_RESOURCE_UPDATE_EMPLOYEE = "/human-resources/update-employee";
	public static final String HUMAN_RESOURCE_SEARCH_ALL_EMPLOYEES = "/human-resources/search-all-employees";
	public static final String HUMAN_RESOURCE_SEARCH_EMPLOYEE = "/human-resources/search-employee/{id}";

	public static final String BUSINESS_INSERT_REQUEST = "/business/insert-request";
	public static final String BUSINESS_SEARCH_LIST_CREATED_REQUEST = "/business/search-list-created-request-ticket";
	public static final String BUSINESS_SEARCH_LIST_RECEIVED_REQUEST = "/business/search-list-received-request-ticket";
	public static final String BUSINESS_SEARCH_REQUEST_BY_ID = "/business/find-request/{id}";
	public static final String BUSINESS_UPDATE_REQUESTER_ACTION = "/business/requester-action";
	public static final String BUSINESS_UPDATE_SUPERVISOR_ACTION = "/business/supervisor-action";
	public static final String BUSINESS_UPDATE_APPROVER_ACTION = "/business/approver-action";
	public static final String BUSINESS_INSERT_COMMENT = "/business/insert-comment";
	public static final String BUSINESS_FIND_CURRENT_USER = "/business/find-current-user";
	public static final String BUSINSES_FIND_ACCOUNT_BY_ROLE = "/business/find-account-by-role/{role}";
	public static final String BUSINESS_FIND_ALL_REASON = "/business/find-all-reason";
	public static final String BUSINESS_FIND_ALL_REQUEST_TYPE = "/business/find-all-request-type";
	public static final String BUSINESS_FIND_EMPLOYEE_ID = "/business/find-employee-id";
	public static final String BUSINESS_FIND_LIST_COMMENT = "/business/find-list-comment/{id}";

}
