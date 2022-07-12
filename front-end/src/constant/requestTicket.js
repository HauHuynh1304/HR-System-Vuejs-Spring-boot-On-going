export const TICKET_STATUS = {
  APPROVED: "APPROVED",
  WAITING: "WAITING",
  CANCEL: "CANCEL",
  REJECT: "REJECT",
};

export const PARTIAL_DAY = {
  ALL_DAY: "ALL DAY",
  HALF_DAY: "HALF DAY",
};

export const FIX_SELECT = {
  TIME_REFER: [
    { value: "AM", text: "AM" },
    { value: "PM", text: "PM" },
  ],
  PARTIAL_DAY: [
    { value: PARTIAL_DAY.ALL_DAY, text: PARTIAL_DAY.ALL_DAY },
    { value: PARTIAL_DAY.HALF_DAY, text: PARTIAL_DAY.HALF_DAY },
  ],
  TICKET_STATUS: [
    { value: TICKET_STATUS.APPROVED, text: TICKET_STATUS.APPROVED },
    { value: TICKET_STATUS.WAITING, text: TICKET_STATUS.WAITING },
    { value: TICKET_STATUS.CANCEL, text: TICKET_STATUS.CANCEL },
    { value: TICKET_STATUS.REJECT, text: TICKET_STATUS.REJECT },
  ],
};

export const INSERT_TICKET = {
  request: {
    requestTypeId: null,
    reasonId: null,
  },
  supervisorAcction: {
    supervisorId: null,
  },
  approverAction: {
    approverId: null,
  },
  requestEmployee: {
    startDate: null,
    endDate: null,
    partialDate: null,
    expectedApproveDate: null,
    requestDescription: null,
  },
};

export const SEARCH_REQUESTED_TICKET = {
  requestTypeId: null,
  requestEmployee: {
    startDate: null,
    endDate: null,
    requestStatus: null,
  },
  systemAccountIds: [],
};

export const REQUESTED_TICKET_RESPONSE = {
  approverAction: {
    approverEmail: null,
    actionType: null,
    approverActionId: null,
    updatedAt: null,
  },
  comments: [],
  requestEmployee: {
    requesterEmail: null,
    requestId: null,
    requestType: null,
    reason: null,
    startDate: null,
    endDate: null,
    duration: null,
    requestStatus: null,
    expectedApproveDate: null,
    requestDescription: null,
  },
  supervisorAction: {
    supervisorEmail: null,
    actionType: null,
    supervisorActionId: null,
    updatedAt: null,
  },
  requesterAction: {
    requesterEmail: null,
    actionType: null,
    requesterActionId: null,
    updatedAt: null,
  },
};

export const ACTION = {
  requesterAction: {
    requesterActionId: null,
    actionType: null,
  },
  supervisorAction: {
    supervisorActionId: null,
    actionType: null,
  },
  approverAction: {
    approverActionId: null,
    actionType: null,
  },
};

export const MUTIPLE_UPDATE_DATA = {
  supervisorAction: [],
  approverAction: [],
};
