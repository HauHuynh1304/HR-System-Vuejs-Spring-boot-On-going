import { getAccessToken } from "../utils/cookies";

export default function guest({ next, router }) {
  if (getAccessToken()) {
    return router.push({ path: "/dashboard" });
  }

  return next();
}
