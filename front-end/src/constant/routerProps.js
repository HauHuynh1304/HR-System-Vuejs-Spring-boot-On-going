export const FE_ROUTER_PROP = {
  LOGIN: {
    ROOT_PATH: "/",
    REDIRECT: "/login",
    CHILDREN: {
      LOGIN: {
        PATH: "/login",
        NAME: "Login",
      },
    },
  },
  DASHBOARD: {
    ROOT_PATH: "/",
    REDIRECT: "/dashboard",
    CHILDREN: {
      DASHBOARD: {
        PATH: "dashboard",
        NAME: "dashboard",
      },
    },
  },
  ADMIN: {
    ROOT_PATH: "/admin",
    ROOT_NAME: "Admin",
    CHILDREN: {
      ACCOUNT_MANAGEMENT: {
        PATH: "account-management",
        NAME: "account management",
      },
    },
  },
  USER: {
    PATH: "/user/profile",
    NAME: "profile",
  },
  HUMAN_MANAGEMENT: {
    ROOT_PATH: "/human-management",
    ROOT_NAME: "Human Mangement",
    CHILDREN: {
      EMPLOYEES: {
        PATH: "list-employee",
        NAME: "List employee",
      },
      ADD_EMPLOYEE: {
        PATH: "new-employee",
        NAME: "New employee",
      },
      UPDATE_EMPLOYEE: {
        PATH: "employee/:id",
        NAME: "UPDATE EMPLOYEE",
      },
    },
  },
};
