import { getAccessToken } from "../utils/cookies";
import jwt_decode from "jwt-decode";
import { ROLES } from "@/constant/common";

export default function masterManagement({ next, router }) {
  if (!getAccessToken()) {
    return router.push({ name: "Login" });
  }
  let roles = jwt_decode(getAccessToken()).roles;
  if (!roles.includes(ROLES.ROOT_ADMIN)) {
    return router.push({ path: "forbident" });
  }
  return next();
}
