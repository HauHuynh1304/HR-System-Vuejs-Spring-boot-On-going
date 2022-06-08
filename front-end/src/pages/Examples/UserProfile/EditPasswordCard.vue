<template>
  <card class="stacked-form">
    <div class="text-center">
      <b-button class="btn btn-link " v-b-toggle.collapse-1 variant="primary">
        Change Password
      </b-button>
    </div>
    <b-collapse id="collapse-1" class="mt-2">
      <form ref="password_form" @submit.prevent>
        <div>
          <base-input
            v-model="formEditPassword.password"
            label="Password"
            type="password"
            placeholder="Password"
          />
          <!-- <validation-error :errors="apiValidationErrors.password" /> -->
          <base-input
            v-model="formEditPassword.confirmPassword"
            label="Password Confirmation"
            type="password"
            placeholder="Password Confirmation"
          />
          <!-- <validation-error
            :errors="apiValidationErrors.password_confirmation"
          /> -->
          <div class="text-center">
            <base-button
              class="btn btn-primary"
              native-type="submit"
              type="primary"
              @click="changePassword()"
            >
              Submit
            </base-button>
          </div>
        </div>
      </form>
    </b-collapse>
  </card>
</template>
<script>
import ValidationError from "@/components/ValidationError.vue";
import formMixin from "@/mixins/form-mixin";
import { MESSAGE } from "../../../constant/message";
import { changePw } from "../../../api/authen";
import jwt_decode from "jwt-decode";
import { getAccessToken } from "@/utils/cookies.js";
import { isValidPassword } from "@/utils/validate.js";
export default {
  name: "edit-password-card",
  props: {
    formEditPassword: {
      type: Object,
      default: {},
    },
  },
  // components: { ValidationError },
  // mixins: [formMixin],
  data() {
    return {
      account: {
        systemEmail: null,
        systemPassword: null,
      },
    };
  },
  methods: {
    changePassword() {
      let decodeJwt = jwt_decode(getAccessToken());
      if (
        this.formEditPassword.password !== this.formEditPassword.confirmPassword
      ) {
        this.$notify({
          type: "warning",
          message: MESSAGE.PASSWORD.ERR,
          icon: "tim-icons icon-bell-55",
        });
      } else {
        if (!isValidPassword(this.formEditPassword.confirmPassword)) {
          this.$notify({
            type: "warning",
            message: MESSAGE.PASSWORD.FORMAT,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
          return;
        }
        this.account.systemEmail = decodeJwt.sub;
        this.account.systemPassword = this.formEditPassword.confirmPassword;
        changePw(this.account)
          .then((res) => {
            this.$notify({
              type: "success",
              message: res.message,
              icon: "tim-icons icon-bell-55",
              horizontalAlign: "center",
            });
            this.$store.dispatch("logout", false);
          })
          .catch(() => {
            this.$notify({
              type: "warning",
              message: MESSAGE.CALL_API_ERR.ERR,
              icon: "tim-icons icon-bell-55",
              horizontalAlign: "center",
            });
          });
      }
    },
  },
};
</script>
