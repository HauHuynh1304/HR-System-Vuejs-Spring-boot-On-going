<template>
  <div v-if="user" class="row">
    <div class="col-md-8">
      <edit-profile-form :model="model" />
      <edit-profile-form :model="model" />
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
export default {
  // name: "user-profile-example",

  components: {
    EditProfileForm,
    UserCard,
    EditPasswordCard,
  },

  data: () => ({
    formEditPassword: null,
    user: null,
    model: {
      personalInfo: {
        personalName: null,
        personalAddress: null,
        systemEmail: null,
        personalEmail: null,
        personalPhoneNumber: null,
        personalIdCard: null,
        personalSex: null,
        personalBirthday: null,
      },
      systemEmail: null,
    },
  }),

  created() {
    this.getProfile();
  },

  methods: {
    async getProfile() {
      await this.$store.dispatch("profile/me");
      this.user = await this.$store.getters["profile/me"];
      this.model = this.user;
    },
  },
};
</script>
