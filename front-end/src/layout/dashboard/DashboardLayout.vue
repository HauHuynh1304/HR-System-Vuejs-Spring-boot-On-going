<template>
  <div class="wrapper">
    <side-bar :background-color="backgroundColor">
      <template slot="links">
        <sidebar-link
          v-if="
            roles.includes(ACCOUNT_ROLES.ADMIN) ||
              roles.includes(ACCOUNT_ROLES.ROOT_ADMIN)
          "
          :to="
            routerProps.ADMIN.ROOT_PATH.concat(
              '/',
              routerProps.ADMIN.CHILDREN.ACCOUNT_MANAGEMENT.PATH
            )
          "
          :name="routerProps.ADMIN.CHILDREN.ACCOUNT_MANAGEMENT.NAME"
          icon="tim-icons icon-notes"
        />
        <sidebar-link
          v-if="roles.includes(ACCOUNT_ROLES.ROOT_ADMIN)"
          :to="
            routerProps.ADMIN.ROOT_PATH.concat(
              '/',
              routerProps.ADMIN.CHILDREN.MASTER_MANAGEMENT.PATH
            )
          "
          :name="routerProps.ADMIN.CHILDREN.MASTER_MANAGEMENT.NAME"
          icon="tim-icons icon-trophy"
        />
        <li
          class="nav-item"
          v-if="
            roles.includes(ACCOUNT_ROLES.HUMAN_RESOURCES) ||
              roles.includes(ACCOUNT_ROLES.ROOT_ADMIN)
          "
        >
          <a class="nav-link">
            <i
              :class="
                hrVisible
                  ? 'tim-icons icon-minimal-up'
                  : 'tim-icons icon-minimal-down'
              "
            ></i>
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
              v-if="roles.includes(ACCOUNT_ROLES.HUMAN_RESOURCES)"
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
            <sidebar-link
              :to="
                routerProps.HUMAN_MANAGEMENT.ROOT_PATH.concat(
                  '/',
                  routerProps.HUMAN_MANAGEMENT.CHILDREN.REPORT.PATH
                )
              "
              :name="routerProps.HUMAN_MANAGEMENT.CHILDREN.REPORT.NAME"
              icon="tim-icons icon-paper"
              class="ml-3"
              v-if="roles.includes(ACCOUNT_ROLES.HUMAN_RESOURCES)"
            />
          </b-collapse>
        </li>
        <li class="nav-item" v-if="roles.includes(ACCOUNT_ROLES.EMPLOYEE)">
          <a class="nav-link">
            <i :class="
                rqVisible
                  ? 'tim-icons icon-minimal-up'
                  : 'tim-icons icon-minimal-down'
              "></i>
            <p
              aria-controls="request-ticket"
              @click="rqVisible = !rqVisible"
              :aria-expanded="rqVisible ? true : false"
              :class="rqVisible ? null : 'collapsed'"
            >
              {{ routerProps.REQUEST_TICKET.ROOT_NAME }}
            </p>
          </a>
          <b-collapse id="request-ticket" v-model="rqVisible">
            <sidebar-link
              :to="
                routerProps.REQUEST_TICKET.ROOT_PATH.concat(
                  '/',
                  routerProps.REQUEST_TICKET.CHILDREN.LIST_REQUESTED_TICKET.PATH
                )
              "
              :name="
                routerProps.REQUEST_TICKET.CHILDREN.LIST_REQUESTED_TICKET.NAME
              "
              icon="tim-icons icon-bullet-list-67"
              class="ml-3"
            />
            <sidebar-link
              :to="
                routerProps.REQUEST_TICKET.ROOT_PATH.concat(
                  '/',
                  routerProps.REQUEST_TICKET.CHILDREN
                    .LIST_RECEIVED_REQUEST_TICKET.PATH
                )
              "
              :name="
                routerProps.REQUEST_TICKET.CHILDREN.LIST_RECEIVED_REQUEST_TICKET
                  .NAME
              "
              icon="tim-icons icon-bus-front-12"
              class="ml-3"
            />
            <sidebar-link
              :to="
                routerProps.REQUEST_TICKET.ROOT_PATH.concat(
                  '/',
                  routerProps.REQUEST_TICKET.CHILDREN.CREATE_REQUEST_TICKET.PATH
                )
              "
              :name="
                routerProps.REQUEST_TICKET.CHILDREN.CREATE_REQUEST_TICKET.NAME
              "
              icon="tim-icons icon-simple-add"
              class="ml-3"
            />
          </b-collapse>
        </li>
      </template>
    </side-bar>
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
  },
  data() {
    return {
      rqVisible: true,
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
