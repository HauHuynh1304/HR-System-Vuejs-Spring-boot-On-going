import store from "../store";
import { getAccessToken } from "../utils/cookies";
import Cookies from "js-cookie";

export default function auth({ next, router }) {
  if (!getAccessToken()) {
    return router.push({ name: "Login" });
  }

  return next();
}
