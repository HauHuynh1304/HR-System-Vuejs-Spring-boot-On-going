import { post, get } from "../utils/request";
import { API } from "../constant/api";

export function login(data) {
  return post(API.AUTHEN.LOG_IN, data).then((res) => {
    return res;
  });
}

export function logout(data) {
  return post(API.AUTHEN.LOG_OUT, data).then((res) => {
    return res;
  });
}

export function changePw(data) {
  return post(API.AUTHEN.CHANGE_PASSWORD, { data: { account: data } }).then(
    (res) => {
      return res;
    }
  );
}

export function findAccounts() {
  return post(API.AUTHEN.FIND_ACCOUNTS).then((res) => {
    return res;
  });
}

export function addNewAccount(data) {
  return post(API.AUTHEN.SIGN_UP, { data: data }).then((res) => {
    return res;
  });
}

export function updateAccount(data) {
  return post(API.AUTHEN.UPDATE_ACCOUNT, { data: data }).then((res) => {
    return res;
  });
}

export function isEmailInDb(data) {
  return post(API.AUTHEN.IS_EMAIL_IN_DB, { data: data }).then((res) => {
    return res;
  });
}
