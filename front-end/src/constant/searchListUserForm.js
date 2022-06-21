export const DOCUMENT_STATUS = [
  { value: true, text: "OK" },
  { value: false, text: "NOT YET" },
];

export const ACTIVED_STATUS = [
  { value: true, text: "Resign" },
  { value: false, text: "Working" },
];

export const OBJECT_SEARCH = {
  personalInfo: {
    personalName: null,
    personalPhoneNumber: null,
  },
  employee: {
    employeeStartDate: null,
    employeeEndDate: null,
    systemAccountId: null,
    deletedFlag: null,
  },
  room: {
    roomId: null,
  },
  position: {
    positionId: null,
  },
  isFullDocuments: null,
};
