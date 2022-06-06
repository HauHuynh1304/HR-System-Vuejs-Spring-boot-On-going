import { LOCAL_STORAGE } from "../../constant/common";
import { removeAccessToken, removeRefreshToken } from "../../utils/cookies";
import router from "@/router";

export default {
  state: {
    isAuthenticated: false,
  },

  getters: {
    isAuthenticated(state) {
      return state.isAuthenticated;
    },
  },

  mutations: {
    isAuthenticated(state, payload) {
      state.isAuthenticated = payload;
    },
  },

  actions: {
    isLogin({ commit }, payload) {
      commit("isAuthenticated", payload);
    },

    register(context, payload) {},

    logout({ commit }, payload) {
      commit("isAuthenticated", payload);
      removeAccessToken();
      removeRefreshToken();
      localStorage.removeItem(LOCAL_STORAGE.NAME);
      return router.push({ name: "Login" }).catch(() => {});
    },
  },
};
