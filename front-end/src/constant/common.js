import MESSAGE from "./message";
export const LOCAL_STORAGE = {
  NAME: "HR_SYSTEM_USER",
};

export const ROLES = {
  ADMIN: "ROLE_ADMIN",
  MANAGER: "ROLE_MANAGER",
  SUPERVISOR: "ROLE_SUPERVISOR",
  HUMAN_RESOURCES: "ROLE_HR",
  EMPLOYEE: "ROLE_EMPLOYEE",
};

export const EVENT_BUS = {
  EDIT_ACCOUNT: "TARGET_ACCOUNT",
  REFRESH_TABLE_LIST_USER: "REFRESH_TABLE_LIST_USER",
};

export const ACCOUNT_STATUS = {
  ACTIVE: {
    value: false,
    text: "YES",
  },
  NOT_ACTIVE: {
    value: true,
    text: "NO",
  },
};
