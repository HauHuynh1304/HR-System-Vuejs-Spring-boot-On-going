import Cookies from "js-cookie";

const AccessTokenKey = "hr-access-token";
const RefreshTokenKey = "hr-refresh-token";
const maxValidTime = "hr-max-valid-time"

export function setAccessToken(token) {
  return Cookies.set(AccessTokenKey, token);
}
export function setRefreshToken(token) {
  return Cookies.set(RefreshTokenKey, token);
}
export function getAccessToken() {
  return Cookies.get(AccessTokenKey);
}

export function getRefreshToken() {
  return Cookies.get(RefreshTokenKey);
}
export function removeAccessToken() {
  return Cookies.remove(AccessTokenKey);
}
export function removeRefreshToken() {
  return Cookies.remove(RefreshTokenKey);
}

export function setMaxValidTime(time){
  return Cookies.set(maxValidTime, time);
}
export function getMaxValidTime() {
  return Cookies.get(maxValidTime);
}
export function removeMaxValidTime(){
  return Cookies.remove(maxValidTime);
}
