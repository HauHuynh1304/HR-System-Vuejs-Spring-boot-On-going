const state = {
  tokenErr: false,
};

const mutations = {
  SET_TOKEN_ERR: (state, value) => {
    state.tokenErr = value;
  },
};

const actions = {
  //   success({ commit, dispatch }, message) {
  //     this.$app.$notify({
  //       timeout: 2500,
  //       message: message,
  //       horizontalAlign: "right",
  //       verticalAlign: "top",
  //       icon: "add_alert",
  //       type: "success",
  //     });
  //   },

  setTokenErr({ commit }, payload) {
    commit("SET_TOKEN_ERR", payload);
  },
};

const getters = {
  tokenErr: (state) => state.tokenErr,
};

const alerts = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};

export default alerts;
