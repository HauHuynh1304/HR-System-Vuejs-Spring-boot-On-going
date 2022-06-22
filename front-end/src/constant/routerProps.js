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
  REQUEST_TICKET: {
    ROOT_PATH: "/business",
    ROOT_NAME: "Request Ticket",
    CHILDREN: {
      CREATE_REQUEST_TICKET: {
        PATH: "new-ticket",
        NAME: "New ticket",
      },
      LIST_REQUESTED_TICKET: {
        PATH: "list-requested-ticket",
        NAME: "Requested Ticket",
      },
      REQUESTED_TICKET: {
        PATH: "requested-ticket/:id",
        NAME: "Requested Ticket",
      },
      LIST_RECEIVED_REQUEST_TICKET: {
        PATH: "list-received-ticket",
        NAME: "Received Ticket",
      },
      RECEIVED_REQUEST_TICKET: {
        PATH: "received-ticket/:id",
        NAME: "received Ticket",
      },
    },
  },
};
