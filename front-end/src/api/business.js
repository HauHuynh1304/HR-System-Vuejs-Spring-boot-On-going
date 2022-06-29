import { post, get } from "../utils/request";
import { API } from "../constant/api";

export function findAccountByRole(data) {
  return get(API.BUSINESS.FIND_ACCOUNT_BY_ROLE, data).then((res) => res);
}

export function findAllReason() {
  return post(API.BUSINESS.FIND_ALL_REASON).then((res) => res);
}

export function findAllRequestType() {
  return post(API.BUSINESS.FIND_ALL_REQUEST_TYPE).then((res) => res);
}

export function insertRequestTicket(data) {
  return post(API.BUSINESS.INSERT_REQUEST, { data: data }).then((res) => res);
}

export function findListRequestedTicket(data) {
  return post(API.BUSINESS.SEARCH_LIST_CREATED_REQUEST, { data: data }).then(
    (res) => res
  );
}

export function findRequestedTicket(data) {
  return get(API.BUSINESS.SEARCH_REQUEST_BY_ID, data).then((res) => res);
}

export function findListComment(data) {
  return get(API.BUSINESS.FIND_LIST_COMMENT, data).then((res) => res);
}

export function findEmployeeId() {
  return post(API.BUSINESS.FIND_EMPLOYEE_ID).then((res) => res);
}

export function insertComment(data) {
  return post(API.BUSINESS.INSERT_COMMENT, { data: data }).then((res) => res);
}

export function updateRequesterAction(data) {
  return post(API.BUSINESS.UPDATE_REQUESTER_ACTION, { data: data }).then(
    (res) => res
  );
}

export function findReceivedTicket(data) {
  return post(API.BUSINESS.SEARCH_LIST_RECEIVED_REQUEST, { data: data }).then(
    (res) => res
  );
}

export function updateSupervisorAction(data) {
  return post(API.BUSINESS.UPDATE_SUPERVISOR_ACTION, { data: data }).then(
    (res) => res
  );
}

export function updateApproverAction(data) {
  return post(API.BUSINESS.UPDATE_APPROVER_ACTION, { data: data }).then(
    (res) => res
  );
}

export function mutipleUpdateRequestTicketStatus(data) {
  return post(API.BUSINESS.MUTIPLE_UPDATE_REQUEST_TICKET_STATUS, {
    data: data,
  }).then((res) => res);
}
