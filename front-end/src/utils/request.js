import axios from "axios";
import {
  getAccessToken,
  getRefreshToken,
  setAccessToken,
} from "@/utils/cookies";
import { API } from "../constant/api";
import store from "../store";

let windownProtocol = window.location.protocol.concat(
  "//",
  location.hostname,
  ":"
);
let appServerPort = null;
let appBaseApiUrl = null;
let baseURLParam = null;
export let URL_IMG = null;
// miliseconds
const timeOut = 60 * 1000;
if (windownProtocol.includes("localhost")) {
  appServerPort = process.env.VUE_APP_SERVER_PORT;
  appBaseApiUrl = process.env.VUE_APP_BASE_API;
  URL_IMG = windownProtocol.concat(appServerPort, "/");
  baseURLParam = windownProtocol.concat(appServerPort, appBaseApiUrl);
} else {
  baseURLParam = process.env.VUE_APP_PRODUCT.concat(process.env.VUE_APP_BASE_API);
  URL_IMG = process.env.VUE_APP_PRODUCT.concat("/");
}

// create an axios instance
const request = axios.create({
  timeout: timeOut,
  baseURL: baseURLParam,
});

export const get = (url, params) => {
  return new Promise((resolve, reject) => {
    request.get(url + params).then(
      (obj) => {
        resolve(obj?.data);
      },
      (err) => {
        reject(err);
      }
    );
  });
};

export const post = (url, params, config) => {
  return new Promise((resolve, reject) => {
    request.post(url, params, config).then(
      (obj) => {
        resolve(obj?.data);
      },
      (err) => {
        reject(err);
      }
    );
  });
};

request.interceptors.request.use(
  async (config) => {
    let accessToken = await getAccessToken();
    if (accessToken) {
      config.headers["Authorization"] = "Bearer " + accessToken;
    }
    return config;
  },
  (err) => {
    Promise.reject(err);
  }
);

request.interceptors.response.use((response) => {
  const originalRequest = response.config;
  let status = response?.data.status;
  switch (status) {
    case 511:
      if (!originalRequest._retry) {
        originalRequest._retry = true;
        return new Promise((resolve, reject) => {
          post(API.AUTHEN.REFRESH_TOKEN, {
            data: {
              refreshTokenName: getRefreshToken(),
            },
          }).then((res) => {
            if (res !== undefined && res.data) {
              originalRequest.headers["Authorization"] =
                "Bearer " + res.data.accessToken;
              setAccessToken(res.data.accessToken);
            }
            resolve(request(originalRequest));
          });
        });
      } else {
        // when restart project
        store.dispatch("logout", false);
      }
      break;
    case 404:
      if (originalRequest.url === API.AUTHEN.REFRESH_TOKEN) {
        store.dispatch("logout", false);
      }
      break;
    default:
      return response;
  }
});
