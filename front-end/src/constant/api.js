export const API = {
  AUTHEN: {
    LOG_IN: "/authen/log-in",
    LOG_OUT: "/authen/log-out",
    REFRESH_TOKEN: "/authen/refresh-token",
    SIGN_UP: "/authen/sign-up",
    CHANGE_PASSWORD: "/authen/change-password",
  },
  MASTER: {
    INSERT_ROLE: "/master/insert-role",
    UPDATE_ROLE: "/master/update-role",
    INSERT_DOCUMENT: "/master/insert-document",
    UPDATE_DOCUMENT: "/master/update-document",
    INSERT_POSITION: "/master/insert-position",
    UPDATE_POSITION: "/master/update-position",
    INSERT_REASON: "/master/insert-reason",
    UPDATE_REASON: "/master/update-reason",
    INSERT_REQUEST_TYPE: "/master/insert-request-type",
    UPDATE_REQUEST_TYPE: "/master/update-request-type",
    INSERT_ROOM: "/master/insert-room",
    UPDATE_ROOM: "/master/update-room",
  },
  HUMAN_RESOURCE: {
    INSERT_EMPLOYEE: "/human-resources/insert-employee",
    UPDATE_EMPLOYEE: "/human-resources/update-employee",
    SEARCH_ALL_EMPLOYEES: "/human-resources/search-all-employees",
    SEARCH_EMPLOYEE_BY_ACCOUNT_ID: "/human-resources/search-employee/{id}",
  },
  BUSINESS: {
    INSERT_REQUEST: "/business/insert-request",
    SEARCH_LIST_CREATED_REQUEST: "/business/search-list-created-request-ticket",
    SEARCH_LIST_RECEIVED_REQUEST:
      "/business/search-list-received-request-ticket",
    SEARCH_REQUEST_BY_ID: "/business/find-request/{id}",
    UPDATE_REQUEST: "/business/update-request",
    UPDATE_SUPERVISOR_ACTION: "/business/supervisor",
    UPDATE_APPROVER_ACTION: "/business/approver",
    INSERT_COMMENT: "/business/insert-comment",
    FIND_CURRENT_USER: "/business/find-current-user",
  },
};
