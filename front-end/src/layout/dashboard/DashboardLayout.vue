<template>
  <div class="wrapper">
    <side-bar :background-color="backgroundColor">
      <template slot="links">
        <sidebar-link
          to="/dashboard"
          :name="$t('sidebar.dashboard')"
          icon="tim-icons icon-chart-pie-36"
        />
        <sidebar-link
          to="/examples/user-management/list-users"
          :name="$t('sidebar.userManagement')"
          icon="tim-icons icon-notes"
        />
        <sidebar-link
          to="/icons"
          :name="$t('sidebar.icons')"
          icon="tim-icons icon-atom"
        />
        <sidebar-link
          v-if="roles.includes(ROLES.ADMIN)"
          to="/maps"
          :name="$t('sidebar.maps')"
          icon="tim-icons icon-pin"
        />
        <sidebar-link
          to="/notifications"
          :name="$t('sidebar.notifications')"
          icon="tim-icons icon-bell-55"
        />
        <sidebar-link
          to="/profile"
          :name="$t('sidebar.userProfile')"
          icon="tim-icons icon-single-02"
        />
        <sidebar-link
          to="/table-list"
          :name="$t('sidebar.tableList')"
          icon="tim-icons icon-puzzle-10"
        />
        <sidebar-link
          to="/typography"
          :name="$t('sidebar.typography')"
          icon="tim-icons icon-align-center"
        />
        >
      </template>
    </side-bar>
    <!-- <sidebar-share :background-color.sync="backgroundColor"> </sidebar-share> -->
    <div class="main-panel" :data="backgroundColor">
      <top-navbar></top-navbar>

      <dashboard-content @click.native="toggleSidebar"> </dashboard-content>
    </div>
    <content-footer></content-footer>
  </div>
</template>
<style lang="scss"></style>
<script>
import TopNavbar from "./TopNavbar.vue";
import DashboardContent from "./Content.vue";
import MobileMenu from "./MobileMenu";
import SidebarShare from "./SidebarSharePlugin";
import ContentFooter from "./ContentFooter";
import jwt_decode from "jwt-decode";
import { getAccessToken } from "../../utils/cookies";
import { ROLES } from "../../constant/common";
import { MESSAGE } from "../../constant/message";

export default {
  components: {
    TopNavbar,
    DashboardContent,
    ContentFooter,
    MobileMenu,
    SidebarShare,
  },
  data() {
    return {
      backgroundColor: "primary",
      roles: null,
      role_admin: "a",
      ROLES: ROLES,
    };
  },
  methods: {
    toggleSidebar() {
      if (this.$sidebar.showSidebar) {
        this.$sidebar.displaySidebar(false);
      }
    },
    getRoleFromToken() {
      this.roles = jwt_decode(getAccessToken()).roles;
      if (!this.roles) {
        this.$notify({
          type: "success",
          message: MESSAGE.REFRESH_TOKEN_EXPIRED.ERR,
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        this.$store.dispatch("logout", false);
      }
    },
  },
  watch: {
    accessToken(newToken) {
      this.getRoleFromToken();
    },
  },
  mounted() {
    if (getAccessToken()) {
      this.getRoleFromToken();
    }
  },
  created() {
    this.getRoleFromToken();
  },
};
</script>
