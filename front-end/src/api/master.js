import { post, get } from "../utils/request";
import { API } from "../constant/api";

export function getRoles() {
  return post(API.MASTER.FIND_ROLES).then((res) => {
    return res;
  });
}

export function findAllRoles() {
  return post(API.MASTER.FIND_ALL_ROLES).then((res) => {
    return res;
  });
}

export function findAllAccounts() {
  return post(API.MASTER.FIND_ALL_ACCOUNT).then((res) => res);
}

export function findAllRooms() {
  return post(API.MASTER.FIND_ALL_ROOMS).then((res) => res);
}
export function updateRoom(data) {
  return post(API.MASTER.UPDATE_ROOM, { data: data }).then((res) => res);
}
export function insertRoom(data) {
  return post(API.MASTER.INSERT_ROOM, { data: data }).then((res) => res);
}

export function findAllDocuments() {
  return post(API.MASTER.FIND_ALL_DOCUMENTS).then((res) => res);
}
export function updateDocument(data) {
  return post(API.MASTER.UPDATE_DOCUMENT, { data: data }).then((res) => res);
}
export function insertDocument(data) {
  return post(API.MASTER.INSERT_DOCUMENT, { data: data }).then((res) => res);
}

export function findAvailbleAccounts() {
  return post(API.MASTER.FIND_AVAILABLE_ACCOUNTS).then((res) => res);
}

export function updateRole(data) {
  return post(API.MASTER.UPDATE_ROLE, { data: data }).then((res) => res);
}
export function insertRole(data) {
  return post(API.MASTER.INSERT_ROLE, { data: data }).then((res) => res);
}

export function findAllRequestType() {
  return post(API.MASTER.FIND_ALL_REQUEST_TYPE).then((res) => res);
}
export function insertRequestType(data) {
  return post(API.MASTER.INSERT_REQUEST_TYPE, { data: data }).then(
    (res) => res
  );
}
export function updateRequestType(data) {
  return post(API.MASTER.UPDATE_REQUEST_TYPE, { data: data }).then(
    (res) => res
  );
}

export function findAllPositions() {
  return post(API.MASTER.FIND_ALL_POSITIONS).then((res) => res);
}
export function insertPosition(data) {
  return post(API.MASTER.INSERT_POSITION, { data: data }).then((res) => res);
}
export function updatePosition(data) {
  return post(API.MASTER.UPDATE_POSITION, { data: data }).then((res) => res);
}

export function findAllReason() {
  return post(API.MASTER.FIND_ALL_REASON).then((res) => res);
}
export function insertReason(data) {
  return post(API.MASTER.INSERT_REASON, { data: data }).then((res) => res);
}
export function updateReason(data) {
  return post(API.MASTER.UPDATE_REASON, { data: data }).then((res) => res);
}
