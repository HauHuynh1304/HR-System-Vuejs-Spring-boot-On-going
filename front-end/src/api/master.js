import { post, get } from "../utils/request";
import { API } from "../constant/api";

export function getRoles() {
  return post(API.MASTER.FIND_ROLES).then((res) => {
    return res;
  });
}
