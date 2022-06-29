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
import jwt_decode from "jwt-decode";
import { ROLES } from "@/constant/common";
import { FE_ROUTER_PROP } from "@/constant/routerProps";

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
          let accessTocken = res.data.accessToken;
          let refreshToken = res.data.refreshToken;
          switch (status) {
            case 200:
              await setAccessToken(accessTocken);
              await setRefreshToken(refreshToken);
              this.$store.dispatch("isLogin", true);
              await getLoginUserInfo().then((res) => {
                localStorage.setItem(
                  LOCAL_STORAGE.NAME,
                  JSON.stringify(res.data.personalInfo)
                );
                let roles = jwt_decode(accessTocken).roles;
                roles.indexOf(ROLES.ADMIN) !== -1
                  ? this.$router.push({
                      path: FE_ROUTER_PROP.ADMIN.ROOT_PATH.concat(
                        "/",
                        FE_ROUTER_PROP.ADMIN.CHILDREN.ACCOUNT_MANAGEMENT.PATH
                      ),
                    })
                  : this.$router.push({
                      path: FE_ROUTER_PROP.REQUEST_TICKET.ROOT_PATH.concat(
                        "/",
                        FE_ROUTER_PROP.REQUEST_TICKET.CHILDREN
                          .CREATE_REQUEST_TICKET.PATH
                      ),
                    });
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
