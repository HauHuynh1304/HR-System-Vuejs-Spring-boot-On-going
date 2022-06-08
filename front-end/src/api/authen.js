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
