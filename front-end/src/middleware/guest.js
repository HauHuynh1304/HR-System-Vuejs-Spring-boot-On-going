import { getAccessToken } from "../utils/cookies";
import jwt_decode from "jwt-decode";
import { ROLES } from "@/constant/common";
import { FE_ROUTER_PROP } from "@/constant/routerProps";

export default function guest({ next, router }) {
  let accessToken = getAccessToken();
  if (accessToken) {
    let roles = jwt_decode(accessToken).roles;
    return roles.indexOf(ROLES.ADMIN) !== -1
      ? router.push({
          path: FE_ROUTER_PROP.ADMIN.ROOT_PATH.concat(
            "/",
            FE_ROUTER_PROP.ADMIN.CHILDREN.ACCOUNT_MANAGEMENT.PATH
          ),
        })
      : router.push({
          path: FE_ROUTER_PROP.REQUEST_TICKET.ROOT_PATH.concat(
            "/",
            FE_ROUTER_PROP.REQUEST_TICKET.CHILDREN.CREATE_REQUEST_TICKET.PATH
          ),
        });
  }
  return next();
}
