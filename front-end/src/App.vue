<template>
  <div id="app">
    <router-view />
    <!-- you can add element-ui's tooltip -->
    <el-tooltip placement="top" content="tooltip">
      <back-to-top :custom-style="myBackToTopStyle" :visibility-height="300" :back-position="50" transition-name="fade" />
    </el-tooltip>
  </div>
  </div>
</template>

<script>
import BackToTop from "@/components/BackToTop";

export default {
  name: "App",
  components: { BackToTop },
  data() {
    return {
      // customizable button style, show/hide critical point, return position
      myBackToTopStyle: {
        right: "50px",
        bottom: "50px",
        width: "40px",
        height: "40px",
        "border-radius": "4px",
        "line-height": "45px",
        background: "#e7eaf1",
      },
    };
  },
};
</script>

<style scoped>
.placeholder-container div {
  margin: 10px;
}
</style>
