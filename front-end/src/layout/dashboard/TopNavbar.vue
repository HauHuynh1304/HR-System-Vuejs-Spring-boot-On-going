<template>
  <nav
    class="navbar navbar-expand-lg navbar-absolute"
    :class="{ 'bg-white': showMenu, 'navbar-transparent': !showMenu }"
  >
    <loading @[EVENT_BUS.DISABLE_ELEMENT]="disableElement" />
    <div class="container-fluid">
      <div class="navbar-wrapper">
        <div
          class="navbar-toggle d-inline"
          :class="{ toggled: $sidebar.showSidebar }"
        >
          <button
            type="button"
            class="navbar-toggler"
            aria-label="Navbar toggle button"
            @click="toggleSidebar"
          >
            <span class="navbar-toggler-bar bar1"></span>
            <span class="navbar-toggler-bar bar2"></span>
            <span class="navbar-toggler-bar bar3"></span>
          </button>
        </div>
        <span class="d-flex align-items-center">Hi, {{ title }}</span>
      </div>
      <button
        class="navbar-toggler navbar-nav ml-auto"
        type="button"
        @click="toggleMenu"
        data-toggle="collapse"
        data-target="#navigation"
        aria-controls="navigation-index"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-bar navbar-kebab"></span>
        <span class="navbar-toggler-bar navbar-kebab"></span>
        <span class="navbar-toggler-bar navbar-kebab"></span>
      </button>

      <collapse-transition>
        <div class="collapse navbar-collapse show" v-show="showMenu">
          <ul class="navbar-nav ml-auto">
            <base-dropdown
              tag="li"
              menu-on-right
              title-tag="a"
              class="nav-item"
              v-if="!isAdminArea"
            >
              <a
                slot="title"
                class="nav-link"
                data-toggle="dropdown"
                aria-expanded="true"
              >
                <div
                  :class="
                    isShowSysbolNewNotification
                      ? 'notification '
                      : 'd-none d-lg-block d-xl-block'
                  "
                />
                <i class="tim-icons icon-sound-wave"></i>
              </a>
              <div class="notification-box">
                <div v-if="!notificationData.length" class="text-center">
                  <span>Waiting for new Notification</span>
                </div>
                <div
                  v-for="(notification, index) in notificationData"
                  :key="index"
                >
                  <li class="nav-link">
                    <a
                      :href="
                        !isDisableElement
                          ? routerProps.REQUEST_TICKET.ROOT_PATH.concat(
                              '/',
                              routerProps.REQUEST_TICKET.CHILDREN.RECEIVED_REQUEST_TICKET.PATH.replace(
                                ':id',
                                notification.requestId
                              )
                            )
                          : null
                      "
                      class=" nav-item dropdown-item "
                      target="_blank"
                      @click="
                        markAsReadOneNotification(notification.notificationId)
                      "
                    >
                      <div class="d-flex justify-content-between">
                        <span
                          :class="
                            notification.readFlag
                              ? 'd-none d-lg-block d-xl-block'
                              : 'notification-symbol d-none d-lg-block d-xl-block'
                          "
                        >
                        </span>
                        <span> Ticket from {{ notification.sender }} </span>
                      </div>
                    </a>
                  </li>
                </div>
                <div v-if="!isDisableElement">
                  <div class="dropdown-divider" />
                  <div
                    class="d-flex justify-content-around"
                    id="notification-button"
                  >
                    <base-button
                      size="sm"
                      type="success"
                      @click="readAllNotification"
                      class="tim-icons icon-check-2"
                    />
                    <base-button
                      size="sm"
                      type="info"
                      @click="reloadNotification"
                      class="tim-icons icon-refresh-02"
                    />
                    <base-button
                      size="sm"
                      type="warning"
                      @click="cleanAllNotification"
                      class="btn tim-icons icon-trash-simple"
                    />
                  </div>
                </div>
              </div>
            </base-dropdown>
            <base-dropdown
              tag="li"
              menu-on-right
              title-tag="a"
              class="nav-item"
              menu-classes="dropdown-navbar"
            >
              <a
                slot="title"
                class="nav-link"
                data-toggle="dropdown"
                aria-expanded="true"
              >
                <div
                  v-b-popover.hover.bottom="MESSAGE.FREE_HOSTING.ERR"
                  class="photo avatar-image"
                  :style="{ 'background-image': `url('${avatar}')` }"
                />
              </a>
              <li class="nav-item">
                <div class="text-left">
                  <a
                    :href="!isDisableElement ? routerProps.USER.PATH : null"
                    aria-current="page"
                    class="nav-link"
                  >
                    <p>Profile</p>
                  </a>
                </div>
              </li>
              <div class="dropdown-divider"></div>
              <li class="nav-item">
                <div class="text-left">
                  <a class="nav-link">
                    <p @click="logout">Log out</p>
                  </a>
                </div>
              </li>
            </base-dropdown>
          </ul>
        </div>
      </collapse-transition>
      <div>
        <b-modal
          :ref="modal.id.maxValidTime"
          :id="modal.id.maxValidTime"
          no-close-on-backdrop
          hide-header
          @ok="forceLogout"
        >
          <div class="text-center">{{ MESSAGE.REFRESH_TOKEN_EXPIRED.ERR }}</div>
          <template #modal-footer="{ ok}">
            <b-button variant="success" size="sm" @click="ok()">
              OK
            </b-button>
          </template>
        </b-modal>
      </div>
    </div>
  </nav>
</template>
<script>
import Loading from "@/components/Loading/Loading.vue";
import { CollapseTransition } from "vue2-transitions";
import Modal from "@/components/Modal";
import { LOCAL_STORAGE, EVENT_BUS, ROLES } from "@/constant/common";
import { NOTIFICATION_UPDATE_REQUEST } from "@/constant/notification";
import { logout } from "@/api/authen";
import { URL_IMG } from "@/utils/request";
import {
  findNotificationByReceiverId,
  markNotificationAsRead,
  deleteNotificationByReceiver,
} from "@/api/business";
import { FE_ROUTER_PROP } from "@/constant/routerProps";
import jwt_decode from "jwt-decode";
import { getAccessToken, getMaxValidTime } from "@/utils/cookies";
import { MESSAGE } from "@/constant/message";
import { get } from "@/utils/request";

export default {
  components: {
    CollapseTransition,
    Modal,
    Loading,
  },
  computed: {
    routeName() {
      const { name } = this.$route;
      return this.capitalizeFirstLetter(name);
    },
  },
  data() {
    return {
      isAdminArea: false,
      notificationUpdateRequest: NOTIFICATION_UPDATE_REQUEST,
      isShowSysbolNewNotification: false,
      routerProps: FE_ROUTER_PROP,
      notificationData: [],
      activeNotifications: false,
      showMenu: false,
      searchModalVisible: false,
      searchQuery: "",
      avatar: null,
      EVENT_BUS: EVENT_BUS,
      isDisableElement: false,
      modal: {
        id: {
          maxValidTime: "maxValidTime",
        },
      },
      MESSAGE: MESSAGE,
      currentTime: new Date().getTime(),
      title: null,
    };
  },
  async created() {
    await this.getProfile();
    this.$bus.on(EVENT_BUS.REFRESH_LOCAL_STORAGE, () => {
      this.getProfile();
    });
    await this.findNotificationByReceiverId();
    setTimeout(() => {
      this.$refs[this.modal.id.maxValidTime]?.show();
    }, parseInt(getMaxValidTime()) - this.currentTime);
  },
  methods: {
    forceLogout() {
      // If use login again by using another tag
      // Will not excute force logout
      if (parseInt(getMaxValidTime()) - this.currentTime < 1) {
        this.$store.dispatch("logout", false);
      }
    },
    disableElement() {
      this.isDisableElement = !this.isDisableElement;
    },
    async getProfile() {
      let user = await JSON.parse(localStorage.getItem(LOCAL_STORAGE.NAME));
      this.title = user.personalName;
      get(URL_IMG, user.personalImage)
        .then((res) => {
          this.avatar = URL_IMG + user.personalImage;
        })
        .catch((err) => {
          this.avatar = require("@/assets/image/1024px-User-avatar.png");
        });
    },
    findNotificationByReceiverId() {
      let roles = jwt_decode(getAccessToken()).roles;
      if (roles.includes(ROLES.ROOT_ADMIN) || roles.includes(ROLES.ADMIN)) {
        this.isAdminArea = true;
        return;
      }
      findNotificationByReceiverId().then((res) => {
        this.notificationData = res.data;
        this.notificationData.findIndex((item) => item.readFlag === false) ===
        -1
          ? (this.isShowSysbolNewNotification = false)
          : (this.isShowSysbolNewNotification = true);
      });
    },
    capitalizeFirstLetter(string) {
      return string.charAt(0).toUpperCase() + string.slice(1);
    },
    toggleNotificationDropDown() {
      this.activeNotifications = !this.activeNotifications;
    },
    closeDropDown() {
      this.activeNotifications = false;
    },
    toggleSidebar() {
      this.$sidebar.displaySidebar(!this.$sidebar.showSidebar);
    },
    hideSidebar() {
      this.$sidebar.displaySidebar(false);
    },
    toggleMenu() {
      if (this.isDisableElement) {
        return;
      }
      this.showMenu = !this.showMenu;
    },
    logout() {
      if (this.isDisableElement) {
        return;
      }
      logout(null).then((res) => {
        this.$store.dispatch("logout", false);
      });
    },
    readAllNotification(e) {
      e.stopPropagation();
      this.resetNotificationUpdateRequest();
      this.notificationData.forEach((el) =>
        el.readFlag === false
          ? this.notificationUpdateRequest.notificationId.push(
              el.notificationId
            )
          : ""
      );
      if (this.notificationUpdateRequest.notificationId.length) {
        this.markNotificationAsRead(this.notificationUpdateRequest);
      }
    },
    markNotificationAsRead(data) {
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      markNotificationAsRead(data).then((res) => {
        if (res.status === 200) {
          this.notificationData.forEach((el) => (el.readFlag = true));
        }
        this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
      });
    },
    cleanAllNotification(e) {
      e.stopPropagation();
      this.resetNotificationUpdateRequest();
      this.notificationData.forEach((el) =>
        this.notificationUpdateRequest.notificationId.push(el.notificationId)
      );
      if (this.notificationUpdateRequest.notificationId.length) {
        this.deleteNotificationByReceiver(this.notificationUpdateRequest);
      }
    },
    deleteNotificationByReceiver(data) {
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      deleteNotificationByReceiver(data).then((res) => {
        if (res.status === 200) {
          this.notificationData = [];
        }
        this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
      });
    },
    resetNotificationUpdateRequest() {
      this.notificationUpdateRequest.notificationId = [];
    },
    markAsReadOneNotification(notificationId) {
      this.resetNotificationUpdateRequest();
      this.notificationUpdateRequest.notificationId.push(notificationId);
      this.markNotificationAsRead(this.notificationUpdateRequest);
    },
    reloadNotification(e) {
      e.stopPropagation();
      this.findNotificationByReceiverId();
    },
  },
};
</script>
<style lang="scss" scoped>
@import "~@/assets/sass/black-dashboard/custom/variables";

.notification-box {
  max-height: 200px;
  max-width: 500px;
  overflow-x: auto;
}

.notification-symbol {
  background: $danger;
  color: $white;
  border-radius: $border-radius-xl;
  height: 6px;
  width: 6px;
  font-size: 12px;
  font-weight: 800;
  border: 1px solid $danger;
  margin: 0.4rem 1rem 0 0;
}
#notification-button {
  margin-bottom: 0.75rem;
}

#notification-button .tim-icons {
  font-size: 12px;
}

/deep/ .modal-dialog > .modal-content > .modal-footer {
  display: flex;
  justify-content: right;
  button {
    margin-right: 1em;
  }
}
</style>
