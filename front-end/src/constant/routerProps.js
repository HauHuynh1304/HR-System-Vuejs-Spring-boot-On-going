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
  },
  ADMIN: {
    ROOT_PATH: "/admin",
    ROOT_NAME: "Admin",
    CHILDREN: {
      ACCOUNT_MANAGEMENT: {
        PATH: "account-management",
        NAME: "account management",
      },
      MASTER_MANAGEMENT: {
        PATH: "master-management",
        NAME: "Master data",
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
      REPORT: {
        PATH: "report",
        NAME: "report",
      },
    },
  },
  REQUEST_TICKET: {
    ROOT_PATH: "/business",
    ROOT_NAME: "Tickets",
    CHILDREN: {
      CREATE_REQUEST_TICKET: {
        PATH: "new-ticket",
        NAME: "New ticket",
      },
      LIST_REQUESTED_TICKET: {
        PATH: "list-leave-ticket",
        NAME: "My ticket",
      },
      LIST_RECEIVED_REQUEST_TICKET: {
        PATH: "list-received-ticket",
        NAME: "Received ticket",
      },
    },
  },
};
