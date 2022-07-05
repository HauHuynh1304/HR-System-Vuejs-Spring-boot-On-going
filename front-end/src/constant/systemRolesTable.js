export const SYSTEM_ROLES_TABLE_COLUMN = {
  fields: [
    {
      key: "roleName",
      label: "role",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "roleDescription",
      label: "Description",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "applyScope",
      label: "scope",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "edit",
      label: "",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
  ],
};

export const SYSTEM_ROLE_OBJ = {
  systemRole: {
    systemRoleId: null,
    roleName: null,
    roleDescription: null,
    deletedFlag: null,
    applyScope: null,
  },
};
