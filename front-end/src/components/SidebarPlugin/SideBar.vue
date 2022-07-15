<template>
  <div class="sidebar" :data="backgroundColor">
    <!--
            Tip 1: you can change the color of the sidebar's background using: data-background-color="white | black | darkblue"
            Tip 2: you can change the color of the active button using the data-active-color="primary | info | success | warning | danger"
        -->
    <!-- -->
    <div class="sidebar-wrapper" id="style-3">
      <div class="logo">
        <a
          href="https://github.com/HauHuynh1304"
          aria-label="sidebar mini logo"
          target="_blank"
          class="simple-text logo-mini"
        >
          <div class="logo-img" :class="{ 'logo-img-rtl': $rtl.isRTL }">
            <img src="/img/laravel-vue.svg" alt="" />
          </div>
        </a>
        <a
          href="https://github.com/HauHuynh1304"
          target="_blank"
          class="simple-text logo-normal"
        >
          {{ title }}
        </a>
      </div>
      <slot> </slot>
      <ul class="nav">
        <!--By default vue-router adds an active class to each route link. This way the links are colored when clicked-->
        <slot name="links">
          <sidebar-link
            v-for="(link, index) in sidebarLinks"
            :key="index"
            :to="link.path"
            :name="link.name"
            :icon="link.icon"
          >
          </sidebar-link>
        </slot>
      </ul>
    </div>
  </div>
</template>
<script>
import SidebarLink from "./SidebarLink";

export default {
  props: {
    title: {
      type: String,
      default: "HR SYSTEM",
    },
    backgroundColor: {
      type: String,
      default: "vue",
    },
    sidebarLinks: {
      type: Array,
      default: () => [],
    },
  },
  components: {
    SidebarLink,
  },
  data() {
    return {
      links: [],
    };
  },
};
</script>
<style scoped>
.nav-link{
  margin: 0;
}
</style>
