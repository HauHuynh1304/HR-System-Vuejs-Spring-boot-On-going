<template>
  <div v-if="user" class="row">
    <div class="col-md-8">
      <edit-profile-form :model="user" />
      <card type="tasks">
        <h4 slot="header">
          <Strong>MY ACTIVITIES</Strong>
        </h4>
        <div class="table-full-width table-responsive">
          <base-table :data="user.historyActions" thead-classes="text-primary">
            <template slot-scope="{ row }">
              <td>
                <p class="title">
                  {{ row.createdAt.substring(0, row.createdAt.indexOf(".")) }}
                </p>
                <p class="text-info">
                  <Strong>{{ row.actionType }}</Strong> from
                  <Strong>{{ row.computerIp }}</Strong>
                </p>
              </td>
            </template>
          </base-table>
        </div>
      </card>
    </div>
    <div class="col-md-4">
      <user-card :user="user" />
      <edit-password-card :formEditPassword="formEditPassword" />
    </div>
  </div>
</template>

<script>
import UserCard from "../Profile/UserCard";
import EditProfileForm from "../Profile/EditProfileForm";
import EditPasswordCard from "./UserProfile/EditPasswordCard";
import { BaseTable } from "@/components";
import { getLoginUserInfo } from "@/api/user";
import {
  LOCAL_STORAGE,
  DATE_FORMAT,
  DATE_TIME_FORMAT,
} from "@/constant/common";
import moment from "moment";

export default {
  // name: "user-profile-example",

  components: {
    EditProfileForm,
    UserCard,
    EditPasswordCard,
    BaseTable,
  },
  data() {
    return {
      formEditPassword: {
        password: null,
        confirmPassword: null,
      },
      user: null,
    };
  },
  created() {
    this.getProfile();
  },
  methods: {
    async getProfile() {
      getLoginUserInfo()
        .then((res) => {
          res.data.personalInfo.personalBirthday = moment(
            res.data.personalInfo.personalBirthday
          ).format(DATE_FORMAT);
          this.user = res.data;
          localStorage.setItem(
            LOCAL_STORAGE.NAME,
            JSON.stringify(res.data.personalInfo)
          );
        })
        .catch((err) => {});
    },
  },
};
</script>
