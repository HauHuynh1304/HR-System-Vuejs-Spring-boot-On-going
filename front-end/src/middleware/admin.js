import { getAccessToken } from "../utils/cookies";
import jwt_decode from "jwt-decode";
import { ROLES } from "@/constant/common";

export default function admin({ next, router }) {
  if (!getAccessToken()) {
    return router.push({ name: "Login" });
  }
  let roles = jwt_decode(getAccessToken()).roles;
  if (!roles.includes(ROLES.ROOT_ADMIN) && !roles.includes(ROLES.ADMIN)) {
    return router.push({ path: "forbident" });
  }
  return next();
}
