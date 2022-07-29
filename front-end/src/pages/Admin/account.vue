<template>
  <div class="plugin">
    <div class="dropdown show-dropdown" :class="{ show: isOpen }">
      <a data-toggle="dropdown" class="settings-icon">
        <i class="fa fa-cog fa-2x" @click="toggleDropDown"> </i>
      </a>
      <div class="dropdown-menu" :class="{ show: isOpen }">
        <div class="text-right mt-2">
          <a class="settings-icon">
            <p class="tim-icons icon-simple-remove" @click="closeDropDown" />
          </a>
        </div>
        <b-card no-body>
          <b-tabs pills card>
            <b-tab title="NEW ACCOUNT" active>
              <form
                @submit="onSubmit"
                id="addNewAccountForm"
                ref="addNewAccountForm"
              >
                <base-input
                  label="New Email"
                  v-model.trim="data.account.systemEmail"
                  placeholder="xxx@hrsystem.com"
                />
                <base-input
                  label="Password"
                  v-model.trim="data.account.systemPassword"
                  type="password"
                  placeholder="aB123@"
                />
              </form>
              <div>
                <b-form-group
                  label="Roles"
                  id="tags-component-select"
                  label-for="tags-component-select"
                  ref="tags-component-select"
                >
                  <!-- Prop `add-on-change` is needed to enable adding tags vie the `change` event -->
                  <b-form-tags
                    id="tags-component-select"
                    v-model="tagValue"
                    add-on-change
                    no-outer-focus
                  >
                    <template
                      v-slot="{
                        tags,
                        inputAttrs,
                        inputHandlers,
                        disabled,
                        removeTag,
                      }"
                    >
                      <ul
                        v-if="tags.length > 0"
                        class="list-inline d-inline-block mb-2"
                      >
                        <li
                          v-for="tag in tags"
                          :key="tag"
                          class="list-inline-item"
                        >
                          <b-form-tag
                            @remove="removeTag(tag)"
                            :title="tag"
                            :disabled="disabled"
                            variant="info"
                            >{{ tag }}</b-form-tag
                          >
                        </li>
                      </ul>
                      <b-form-select
                        v-bind="inputAttrs"
                        v-on="inputHandlers"
                        :disabled="disabled || availableOptions.length === 0"
                        :options="availableOptions"
                      >
                        <template #first>
                          <!-- This is required to prevent bugs with Safari -->
                          <option disabled value="">Roles...</option>
                        </template>
                      </b-form-select>
                    </template>
                  </b-form-tags>
                </b-form-group>
              </div>
              <div class="text-center pt-3">
                <base-button
                  native-type="submit"
                  @click="onSubmit"
                  type="info"
                  size="sm"
                >
                  ADD
                </base-button>
                <base-button @click="reset" type="warning" size="sm">
                  RESET
                </base-button>
              </div>
            </b-tab>
            <b-tab title="UPDATE">
              <form
                @submit="onUpdate"
                id="updateAccountForm"
                ref="updateAccountForm"
              >
                <base-input
                  label="Email"
                  v-model.trim="updateLogicProperties.targetEmail"
                  placeholder="xxx@hrsystem.com"
                  readonly
                />
                <base-input
                  label="New Password"
                  v-model.trim="dataUpdateAccount.account.systemPassword"
                  type="password"
                  placeholder="aB123@"
                />
              </form>
              <div>
                <form>
                  <h5 class="mb-2">Actived status</h5>
                  <b-form-select
                    v-model="updateLogicProperties.selected"
                    :options="statusOptions"
                    class="form-control"
                  >
                  </b-form-select>
                </form>
              </div>
              <b-form-tags
                id="tags-component-select"
                v-model="updateLogicProperties.registeredTags"
                add-on-change
                no-outer-focus
                class="mt-4"
              >
                <template
                  v-slot="{
                    tags,
                    inputAttrs,
                    inputHandlers,
                    disabled,
                    removeTag,
                  }"
                >
                  <ul
                    v-if="tags.length > 0"
                    class="list-inline d-inline-block mb-2"
                  >
                    <li v-for="tag in tags" :key="tag" class="list-inline-item">
                      <b-form-tag
                        @remove="removeTag(tag)"
                        :title="tag"
                        :disabled="disabled"
                        variant="info"
                        >{{ tag }}</b-form-tag
                      >
                    </li>
                  </ul>
                  <b-form-select
                    v-bind="inputAttrs"
                    v-on="inputHandlers"
                    :disabled="disabled || availableOptions.length === 0"
                    :options="availableOptionsOnUpdateAccount"
                  >
                    <template #first>
                      <!-- This is required to prevent bugs with Safari -->
                      <option disabled value="">Roles...</option>
                    </template>
                  </b-form-select>
                </template>
              </b-form-tags>
              <div class="text-center pt-3">
                <base-button
                  native-type="submit"
                  @click="onUpdate"
                  type="info"
                  size="sm"
                  :disabled="isDisableUpdateButton"
                >
                  UPDATE
                </base-button>
              </div>
            </b-tab>
          </b-tabs>
        </b-card>
      </div>
    </div>
  </div>
</template>
<script>
import BaseButton from "@/components/BaseButton.vue";
import { getRoles } from "../../api/master";
import { addNewAccount, updateAccount, isEmailInDb } from "../../api/authen";
import { EVENT_BUS, ACCOUNT_STATUS } from "../../constant/common";
import { MESSAGE } from "../../constant/message";
import { isValidEmail, isValidPassword } from "@/utils/validate";
import { resetObject, isAllNullValue } from "@/utils/objectUtil";
export default {
  components: { BaseButton },
  name: "account-plugin",
  props: {},
  data() {
    return {
      // Properties for update account start
      isDisableUpdateButton: true,
      updateLogicProperties: {
        originStatus: null,
        originRoles: [],
        // originRegisterTags: [],
        registeredTags: [],
        targetEmail: null,
        selected: null,
      },
      dataUpdateAccount: {
        account: {
          systemAccountId: null,
          systemPassword: null,
          deletedFlag: null,
        },
        addNewRoleIds: [],
        deleteRoleIds: [],
      },
      // Properties for update account end

      // Properties for add new account start
      tagValue: [],
      data: {
        account: {
          systemEmail: null,
          systemPassword: null,
        },
        roleIds: [],
      },
      // Properties for add new account end
      isOpen: false,
      options: [],
      originRoleObj: [],
      statusOptions: [
        {
          value: ACCOUNT_STATUS.ACTIVE.value,
          text: ACCOUNT_STATUS.ACTIVE.text,
        },
        {
          value: ACCOUNT_STATUS.NOT_ACTIVE.value,
          text: ACCOUNT_STATUS.NOT_ACTIVE.text,
        },
      ],
    };
  },
  methods: {
    toggleDropDown() {
      this.isOpen = !this.isOpen;
      if (this.isOpen) {
        this.reset();
        this.options = [];
        getRoles()
          .then((res) => {
            this.originRoleObj = res.data;
            this.originRoleObj.forEach((el) => this.options.push(el.roleName));
          })
          .catch(() => {});
      }
    },
    closeDropDown() {
      this.isOpen = false;
      this.reset();
    },
    toggleList(list, itemToActivate) {
      list.forEach((listItem) => {
        listItem.active = false;
      });
      itemToActivate.active = true;
    },
    toggleMode(type) {
      let docClasses = document.body.classList;
      if (type === "white") {
        docClasses.add("white-content");
      } else {
        docClasses.remove("white-content");
      }
    },
    onSubmit() {
      // refresh before submit form
      this.data.roleIds = [];
      let finalRolesObj = this.originRoleObj.filter((el) => {
        return this.tagValue.includes(el.roleName);
      });
      finalRolesObj.forEach((el) => this.data.roleIds.push(el.systemRoleId));

      if (
        !isValidEmail(this.data.account.systemEmail) ||
        !isValidPassword(this.data.account.systemPassword) ||
        !this.data.roleIds.length
      ) {
        this.$notify({
          type: "warning",
          message:
            MESSAGE.EMAIL.ERR +
            "<br/>" +
            MESSAGE.PASSWORD.FORMAT +
            "<br/>" +
            MESSAGE.ROLE.ERR,
          horizontalAlign: "center",
        });
        return;
      }
      isEmailInDb({ email: this.data.account.systemEmail }).then((res) => {
        if (res.data) {
          this.$notify({
            type: "warning",
            message: MESSAGE.EMAIL.DUPLICATE,
            horizontalAlign: "center",
          });
        } else {
          addNewAccount(this.data)
            .then((res) => {
              if (res.status === 200) {
                this.$notify({
                  type: "success",
                  message: res.message,
                  horizontalAlign: "center",
                });
                this.reset();
                this.$bus.emit(EVENT_BUS.REFRESH_TABLE_LIST_USER);
              } else {
                this.$notify({
                  type: "warning",
                  message: res.errorMessage,
                  horizontalAlign: "center",
                });
              }
            })
            .catch(() => {
              this.$notify({
                type: "warning",
                message: MESSAGE.CALL_API_ERR.ERR,
                horizontalAlign: "center",
              });
              this.reset();
            });
        }
      });
    },
    reset() {
      resetObject(this.data.account);
      this.data.roleIds = [];
      this.tagValue = [];
    },
    resetDataUpdateAccount() {
      resetObject(this.updateLogicProperties);
      resetObject(this.dataUpdateAccount.account);
      this.dataUpdateAccount.addNewRoleIds = [];
      this.dataUpdateAccount.deleteRoleIds = [];
      this.isDisableUpdateButton = true;
    },
    onUpdate() {
      if (
        this.dataUpdateAccount.account.systemPassword &&
        !isValidPassword(this.dataUpdateAccount.account.systemPassword)
      ) {
        this.$notify({
          type: "warning",
          message: MESSAGE.PASSWORD.FORMAT,
          horizontalAlign: "center",
        });
        return;
      }
      let originRegisterTags = this.updateLogicProperties.originRoles.map(
        (el) => el.roleName
      );
      // Get new role Ids
      let addNewTags = this.updateLogicProperties.registeredTags.filter(
        (el) => originRegisterTags.indexOf(el) === -1
      );
      this.originRoleObj.forEach((el) => {
        return addNewTags.includes(el.roleName)
          ? this.dataUpdateAccount.addNewRoleIds.includes(el.systemRoleId)
            ? null
            : this.dataUpdateAccount.addNewRoleIds.push(el.systemRoleId)
          : null;
      });
      // Get deleted role Id
      let deletedTags = originRegisterTags.filter(
        (el) => this.updateLogicProperties.registeredTags.indexOf(el) === -1
      );
      this.updateLogicProperties.originRoles.forEach((el) => {
        return deletedTags.includes(el.roleName)
          ? this.dataUpdateAccount.deleteRoleIds.includes(
              el.systemAccountRoleId
            )
            ? null
            : this.dataUpdateAccount.deleteRoleIds.push(el.systemAccountRoleId)
          : null;
      });
      // Get account status
      if (
        this.updateLogicProperties.selected !==
        this.updateLogicProperties.originStatus
      ) {
        this.dataUpdateAccount.account.deletedFlag = this.updateLogicProperties.selected;
      } else {
        this.dataUpdateAccount.account.deletedFlag = null;
      }
      if (
        !this.dataUpdateAccount.account.systemPassword &&
        this.dataUpdateAccount.account.deletedFlag === null &&
        !this.dataUpdateAccount.addNewRoleIds.length &&
        !this.dataUpdateAccount.deleteRoleIds.length
      ) {
        return;
      }
      updateAccount(this.dataUpdateAccount)
        .then((res) => {
          this.$notify({
            type: res.status === 200 ? "success" : "warning",
            message: res.status === 200 ? res.message : res.errorMessage,
            horizontalAlign: "center",
          });
          res.status === 200
            ? this.$bus.emit(EVENT_BUS.REFRESH_TABLE_LIST_USER)
            : null;
        })
        .catch(() => {
          this.$notify({
            type: "warning",
            message: MESSAGE.CALL_API_ERR.ERR,
            horizontalAlign: "center",
          });
        });
    },
  },
  computed: {
    availableOptions() {
      return this.options.filter((opt) => this.tagValue.indexOf(opt) === -1);
    },
    availableOptionsOnUpdateAccount() {
      return this.options.filter(
        (opt) => this.updateLogicProperties.registeredTags.indexOf(opt) === -1
      );
    },
  },
  created() {
    this.$bus.on(EVENT_BUS.EDIT_ACCOUNT, (item) => {
      if (item.length) {
        this.isDisableUpdateButton = false;
        this.updateLogicProperties.originRoles = item[0].roles;
        this.updateLogicProperties.registeredTags = item[0].roles.map(
          (el) => el.roleName
        );
        this.updateLogicProperties.originStatus = item[0].deletedFlag;
        this.updateLogicProperties.selected = item[0].deletedFlag;
        this.dataUpdateAccount.account.systemAccountId =
          item[0].systemAccountId;
        this.updateLogicProperties.targetEmail = item[0].systemEmail;
      } else {
        this.resetDataUpdateAccount();
      }
    });
  },
};
</script>
<style scoped lang="scss">
@import "~@/assets/sass/black-dashboard/custom/variables";
.settings-icon {
  cursor: pointer;
}
.badge-vue {
  background-color: $vue;
}
.plugin {
  position: fixed;
  right: 0;
  width: 64px;
  background: rgba(0, 0, 0, 0.3);
  z-index: 1031;
  border-radius: 8px 0 0 8px;
  text-align: center;
  top: 130px;

  .fa-cog {
    color: $white;
    padding: 10px;
    border-radius: 0 0 6px 6px;
    width: auto;
  }

  .dropdown-menu {
    right: 80px;
    left: auto !important;
    top: -40px !important;
    width: 350px;
    border-radius: 0.1875rem;
    padding: 0 10px 0 10px;
  }
  .dropdown-menu:after,
  .dropdown-menu:before {
    color: transparent;
  }
}

#tags-component-select___input__ {
  color: black;
}

.custom-select {
  color: black;
}

.list-inline-item {
  margin-bottom: 5px;
}
</style>
