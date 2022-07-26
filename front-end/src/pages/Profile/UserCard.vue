<template>
  <card type="user">
    <p class="card-text"></p>
    <div class="author">
      <div class="block block-one"></div>
      <div class="block block-two"></div>
      <div class="block block-three"></div>
      <div class="block block-four"></div>
      <div v-b-popover.hover.bottom="MESSAGE.FREE_HOSTING.ERR">
        <img class="avatar" :src="this.urlImg" />
        <h4 class="title">{{ user.personalInfo.personalName }}</h4>
        <h5 class="title">Department: {{ user.room.roomName }}</h5>
      </div>
    </div>
  </card>
</template>
<script>
import { URL_IMG } from "@/utils/request";
import { get } from "@/utils/request";
import { MESSAGE } from "@/constant/message";

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
      urlImg: null,
      MESSAGE: MESSAGE
    };
  },
  created() {
    get(URL_IMG, this.user.personalInfo.personalImage)
      .then((res) => {
        this.urlImg = URL_IMG + this.user.personalInfo.personalImage;
      })
      .catch((err) => {
        this.urlImg = require("@/assets/image/1024px-User-avatar.png");
      });
  },
  methods: {},
};
</script>
<style></style>
