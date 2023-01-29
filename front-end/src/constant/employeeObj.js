export const NEW_EMPLOYEE_OBJECT = {
  employee: {
    employeeProfileId: null,
    roomId: null,
    systemAccountId: null,
    employeeStartDate: null,
  },
  personalInfo: {
    personalName: null,
    personalBirthday: null,
    personalAddress: null,
    personalPhoneNumber: null,
    personalSex: null,
    personalIdCard: null,
    personalEmail: null,
  },
  documents: [],
  positions: [],
};

export const UPDATE_EMPLOYEE_OBJECT = {
  employee: {
    employeeId: null,
    employeeProfileId: null,
    roomId: null,
    deletedFlag: null,
    employeeStartDate: null,
    employeeEndDate: null,
  },
  personalInfo: {
    personalInfoId: null,
    personalName: null,
    personalBirthday: null,
    personalAddress: null,
    personalPhoneNumber: null,
    personalSex: null,
    personalIdCard: null,
    personalEmail: null,
  },
  room: {
    roomId: null,
    roomName: null,
  },
  systemEmail: null,
  documents: [],
  positions: [],
};
