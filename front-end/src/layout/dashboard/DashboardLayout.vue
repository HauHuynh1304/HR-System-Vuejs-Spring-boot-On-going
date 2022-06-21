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
          v-if="roles.includes(ACCOUNT_ROLES.ADMIN)"
          :to="
            routerProps.ADMIN.ROOT_PATH.concat(
              '/',
              routerProps.ADMIN.CHILDREN.ACCOUNT_MANAGEMENT.PATH
            )
          "
          :name="routerProps.ADMIN.CHILDREN.ACCOUNT_MANAGEMENT.NAME"
          icon="tim-icons icon-notes"
        />
        <li
          class="nav-item"
          v-if="roles.includes(ACCOUNT_ROLES.HUMAN_RESOURCES)"
        >
          <a class="nav-link">
            <i class="tim-icons icon-minimal-down"></i>
            <p
              aria-controls="hr"
              @click="hrVisible = !hrVisible"
              :aria-expanded="hrVisible ? true : false"
              :class="hrVisible ? null : 'collapsed'"
            >
              {{ routerProps.HUMAN_MANAGEMENT.ROOT_NAME }}
            </p>
          </a>
          <b-collapse id="hr" v-model="hrVisible">
            <sidebar-link
              :to="
                routerProps.HUMAN_MANAGEMENT.ROOT_PATH.concat(
                  '/',
                  routerProps.HUMAN_MANAGEMENT.CHILDREN.EMPLOYEES.PATH
                )
              "
              :name="routerProps.HUMAN_MANAGEMENT.CHILDREN.EMPLOYEES.NAME"
              icon="tim-icons icon-bullet-list-67"
              class="ml-3"
            />
            <sidebar-link
              :to="
                routerProps.HUMAN_MANAGEMENT.ROOT_PATH.concat(
                  '/',
                  routerProps.HUMAN_MANAGEMENT.CHILDREN.ADD_EMPLOYEE.PATH
                )
              "
              :name="routerProps.HUMAN_MANAGEMENT.CHILDREN.ADD_EMPLOYEE.NAME"
              icon="tim-icons icon-simple-add"
              class="ml-3"
            />
          </b-collapse>
        </li>
        <sidebar-link
          to="/icons"
          :name="$t('sidebar.icons')"
          icon="tim-icons icon-atom"
        />
        <sidebar-link
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
import { FE_ROUTER_PROP } from "@/constant/routerProps";

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
      hrVisible: true,
      routerProps: FE_ROUTER_PROP,
      backgroundColor: "primary",
      roles: null,
      ACCOUNT_ROLES: ROLES,
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
          type: "warning",
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
