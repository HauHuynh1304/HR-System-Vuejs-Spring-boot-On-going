import { post, get } from "../utils/request";
import { API } from "../constant/api";

export function getLoginUserInfo() {
  return post(API.BUSINESS.FIND_CURRENT_USER).then((res) => {
    return res;
  });
}
