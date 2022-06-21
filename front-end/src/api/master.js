import { post, get } from "../utils/request";
import { API } from "../constant/api";

export function getRoles() {
  return post(API.MASTER.FIND_ROLES).then((res) => {
    return res;
  });
}

export function findAllAccounts() {
  return post(API.MASTER.FIND_ALL_ACCOUNT).then((res) => res);
}

export function findAllRooms() {
  return post(API.MASTER.FIND_ALL_ROOMS).then((res) => res);
}

export function findAllPositions() {
  return post(API.MASTER.FIND_ALL_POSITIONS).then((res) => res);
}

export function findAllDocuments() {
  return post(API.MASTER.FIND_ALL_DOCUMENTS).then((res) => res);
}

export function findAvailbleAccounts() {
  return post(API.MASTER.FIND_AVAILABLE_ACCOUNTS).then((res) => res);
}
