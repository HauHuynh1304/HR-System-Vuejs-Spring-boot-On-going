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
                <span class="tim-icons icon-sound-wave text-info"></span>
              </a>
              <div class="notification-box">
                <div v-if="!notificationData.length" class="text-center">
                  <span>Waiting for new Notification</span>
                </div>
                <div id="notification-box-info">
                  <div
                    v-for="(notification, index) in notificationData"
                    :key="index"
                  >
                    <li class="nav-link">
                      <a
                        class=" nav-item dropdown-item "
                        target="_blank"
                        @click="
                          markAsReadOneNotification(
                            notification.notificationId,
                            notification.requestId
                          )
                        "
                      >
                        <div class="d-flex justify-content-start">
                          <span
                            :class="
                              notification.readFlag
                                ? 'd-none d-lg-block d-xl-block'
                                : 'notification-symbol d-none d-lg-block d-xl-block'
                            "
                          >
                          </span>
                          <span class="text-left">
                            <b> {{ notification.requestType }} </b> from
                            {{ notification.sender }}
                          </span>
                        </div>
                      </a>
                    </li>
                  </div>
                </div>
                <div id="group-button-notification">
                  <div class="dropdown-divider" />
                  <div
                    class="d-flex justify-content-around"
                    id="notification-button"
                  >
                    <base-button
                      size="sm"
                      @click="readAllNotification"
                      class="btn btn-link"
                      v-b-popover.hover.bottom="MESSAGE.INFO.READ_ALL"
                    >
                      <p class="tim-icons icon-check-2 text-success" />
                    </base-button>
                    <base-button
                      size="sm"
                      @click="reloadNotification"
                      class="btn btn-link"
                      v-b-popover.hover.bottom="MESSAGE.INFO.REFRESH"
                    >
                      <p class="tim-icons icon-refresh-02 text-info" />
                    </base-button>
                    <base-button
                      size="sm"
                      @click="cleanAllNotification"
                      class="btn btn-link"
                      v-b-popover.hover.bottom="MESSAGE.INFO.DELETE_ALL"
                    >
                      <p class="tim-icons icon-trash-simple text-warning" />
                    </base-button>
                  </div>
                </div>
              </div>
            </base-dropdown>
            <div
              v-b-popover.hover.bottom="MESSAGE.FREE_HOSTING.ERR"
              class="photo avatar-image"
              :style="{ 'background-image': `url('${avatar}')` }"
              @click="toProfilePage"
            >
              <a
                :href="!isDisableElement ? routerProps.USER.PATH : null"
                aria-current="page"
                class="nav-link"
                ref="toProfilePage"
              >
              </a>
            </div>
            <div>
              <b-button
                class="btn btn-link"
                @click="logout"
                v-b-popover.hover.bottom="MESSAGE.INFO.LOG_OUT"
              >
                <b-icon class="logout" icon="power" variant="warning"></b-icon>
              </b-button>
            </div>
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
      <b-modal
        size="xl"
        :ref="modal.receivedDetail.id"
        :id="modal.receivedDetail.id"
        hide-footer
        hide-header
      >
        <requested-ticket
          :requestId="modal.receivedDetail.requestId"
          :isRequesterArea="false"
        />
      </b-modal>
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
import RequestedTicket from "../../pages/RequestTicket/RequestedTicket/RequestedTicket.vue";
export default {
  components: {
    CollapseTransition,
    Modal,
    Loading,
    RequestedTicket,
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
        receivedDetail: {
          id: "received-detail-modal-on-topnav",
          requestId: null,
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
        this.isShowSysbolNewNotification = false;
      }
    },
    markNotificationAsRead(data) {
      markNotificationAsRead(data).then((res) => {
        if (res.status === 200) {
          this.notificationData.forEach((el) => (el.readFlag = true));
        }
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
      deleteNotificationByReceiver(data).then((res) => {
        if (res.status === 200) {
          this.notificationData = [];
          this.isShowSysbolNewNotification = false;
        }
      });
    },
    resetNotificationUpdateRequest() {
      this.notificationUpdateRequest.notificationId = [];
    },
    markAsReadOneNotification(notificationId, requestId) {
      this.resetNotificationUpdateRequest();
      this.notificationUpdateRequest.notificationId.push(notificationId);
      this.modal.receivedDetail.requestId = parseInt(requestId);
      this.$refs[this.modal.receivedDetail.id].show();
      setTimeout(() => {
        markNotificationAsRead(this.notificationUpdateRequest).then((res) => {
          if (res.status === 200) {
            this.notificationData.forEach((el) => {
              el.notificationId === notificationId
                ? (el.readFlag = true)
                : null;
            });
          }
        });
      }, 1);
    },
    reloadNotification(e) {
      e.stopPropagation();
      this.findNotificationByReceiverId();
    },
    toProfilePage() {
      this.$refs.toProfilePage.click();
    },
  },
};
</script>
<style lang="scss" scoped>
@import "~@/assets/sass/black-dashboard/custom/variables";

#notification-box-info {
  max-height: 200px;
  max-width: 500px;
  overflow-x: auto;
}
.icon-sound-wave {
  font-weight: bold;
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
  font-size: 15px;
  margin: auto;
  font-weight: bold;
}

/deep/ .modal-dialog > .modal-content > .modal-footer {
  display: flex;
  justify-content: right;
  button {
    margin-right: 1em;
  }
}
.photo {
  cursor: pointer;
  margin: 4px 3px 5px 10px;
}
.logout {
  font-size: 23px;
  font-weight: bold;
}
#group-button-notification {
  position: -webkit-sticky; /* for Safari */
  position: sticky;
  bottom: 0;
  background: white;
  height: auto;
}
/deep/ #received-detail-modal-on-topnav___BV_modal_body_ {
  position: fixed;
  margin: 1rem 0 0 0;
  width: 100%;
}
</style>
