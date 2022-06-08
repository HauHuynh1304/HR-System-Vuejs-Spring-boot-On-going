import { getAccessToken } from "../utils/cookies";

export default function auth({ next, router }) {
  if (!getAccessToken()) {
    return router.push({ name: "Login" });
  }

  return next();
}
