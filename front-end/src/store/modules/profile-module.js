import { getLoginUserInfo } from "../../api/user";
import { LOCAL_STORAGE } from "../../constant/common";

const state = {
  me: null,
};

const mutations = {
  SET_RESOURCE: (state, me) => {
    state.me = me;
  },
};

const actions = {
  async me({ commit }) {
    return getLoginUserInfo()
      .then((res) => {
        commit("SET_RESOURCE", res.data);
        localStorage.setItem(
          LOCAL_STORAGE.NAME,
          JSON.stringify(res.data.personalInfo)
        );
      })
      .catch((err) => {});
  },

  update({ commit, dispatch }, params) {
    return service.update(params).then((profile) => {
      commit("SET_RESOURCE", profile);
    });
  },
};

const getters = {
  me: (state) => state.me,
};

const profile = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};

export default profile;
