import { post, get } from "../utils/request";
import { API } from "../constant/api";

export function findListEmployee(data) {
  return post(API.HUMAN_RESOURCE.SEARCH_ALL_EMPLOYEES, { data: data }).then(
    (res) => {
      return res;
    }
  );
}

export function insertEmployee(data) {
  return post(API.HUMAN_RESOURCE.INSERT_EMPLOYEE, data).then((res) => res);
}

export function findEmployeeById(data) {
  return get(API.HUMAN_RESOURCE.SEARCH_EMPLOYEE_BY_ACCOUNT_ID, data).then(
    (res) => res
  );
}

export function updateEmployee(data) {
  return post(API.HUMAN_RESOURCE.UPDATE_EMPLOYEE, data).then((res) => res);
}

export function findPositions() {
  return post(API.HUMAN_RESOURCE.FIND_POSITIONS).then((res) => res);
}

export function findDocuments() {
  return post(API.HUMAN_RESOURCE.FIND_DOCUMENTS).then((res) => res);
}

export function findRooms() {
  return post(API.HUMAN_RESOURCE.FIND_ROOMS).then((res) => res);
}

export function findReportCaseSelected(data) {
  return post(API.HUMAN_RESOURCE.FIND_REPORT_CASE_SELECTED, {
    data: data,
  }).then((res) => res);
}
