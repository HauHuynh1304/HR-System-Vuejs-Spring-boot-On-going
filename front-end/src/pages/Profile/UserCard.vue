<template>
  <card type="user">
    <p class="card-text"></p>
    <div class="author">
      <div class="block block-one"></div>
      <div class="block block-two"></div>
      <div class="block block-three"></div>
      <div class="block block-four"></div>
      <a href="#">
        <img class="avatar" :src="this.urlImg" />
        <h4 class="title">{{ user.personalInfo.personalName }}</h4>
        <h5 class="title">Department: {{ user.room.roomName }}</h5>
      </a>
    </div>
  </card>
</template>
<script>
import { URL_IMG } from "@/utils/request";

export default {
  props: {
    user: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      urlImg: URL_IMG + this.user.personalInfo.personalImage,
    };
  },
  methods: {
    getProfile() {
      console.log();
      return;
    },
  },
};
</script>
<style></style>
