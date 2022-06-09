<template>
  <div>
    <div class="container">
      <div class="col-lg-4 col-md-6 mt-auto ml-auto mr-auto">
        <form @submit.prevent="handleSubmit()">
          <card class="card-login card-white">
            <template slot="header">
              <img src="/img/card-primary.png" alt="" />
              <h1 class="card-title">HR <br />SYSTEM</h1>
            </template>

            <div>
              <base-input
                required
                v-model="formLogin.data.username"
                type="email"
                placeholder="Email"
                addon-left-icon="tim-icons icon-email-85"
              >
              </base-input>

              <base-input
                required
                v-model="formLogin.data.password"
                placeholder="Password"
                addon-left-icon="tim-icons icon-lock-circle"
                type="password"
              >
              </base-input>
              <div slot="footer">
                <base-button
                  native-type="submit"
                  type="primary"
                  class="mb-3 mt-4"
                  size="lg"
                  block
                >
                  Get Started
                </base-button>
              </div>
            </div>
          </card>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import formMixin from "@/mixins/form-mixin";
import { login } from "../api/authen";
import {
  setAccessToken,
  setRefreshToken,
  removeAccessToken,
  removeRefreshToken,
} from "../utils/cookies";
import { MESSAGE } from "../constant/message";
import { getLoginUserInfo } from "../api/user";
import { LOCAL_STORAGE } from "../constant/common";

export default {
  mixins: [formMixin],
  data() {
    return {
      formLogin: {
        data: {
          username: null,
          password: null,
        },
      },
    };
  },
  methods: {
    handleSubmit() {
      removeAccessToken();
      removeRefreshToken();
      login(this.formLogin)
        .then(async (res) => {
          let status = res.status;
          switch (status) {
            case 200:
              await setAccessToken(res.data.accessToken);
              await setRefreshToken(res.data.refreshToken);
              this.$store.dispatch("isLogin", true);
              await getLoginUserInfo().then((res) => {
                localStorage.setItem(
                  LOCAL_STORAGE.NAME,
                  JSON.stringify(res.data.personalInfo)
                );
                this.$router.push({ path: "/dashboard" });
              });
              break;
            case 404:
              this.$notify({
                type: "danger",
                message: MESSAGE.LOGIN.ERR,
                horizontalAlign: "center",
              });
            default:
              break;
          }
        })
        .catch(() => {
          this.$notify({
            type: "danger",
            message: MESSAGE.LOGIN.ERR,
            horizontalAlign: "center",
          });
        });
    },
  },
};
</script>
<style>
.navbar-nav .nav-item p {
  line-height: inherit;
  margin-left: 5px;
}
</style>
