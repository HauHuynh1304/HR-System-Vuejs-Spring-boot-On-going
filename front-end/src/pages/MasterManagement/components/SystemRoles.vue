<template>
  <div>
    <card>
      <div slot="header">
        <div class="title">
          <div class="row">
            <b-button
              id="system-role-button"
              class="btn btn-link "
              v-b-toggle.systemRoles
              variant="primary"
              @click="isOpenArea = !isOpenArea"
            >
              SYSTEM ROLES
            </b-button>
          </div>
        </div>
        <b-collapse id="systemRoles" class="mt-2">
          <div class="col-md-6">
            <b-button
              size="sm"
              @click="openInsertRolesModal"
              class="btn btn-info tim-icons icon-simple-add"
            />
          </div>
          <b-table
            hover
            responsive
            :fields="fields"
            :items="systemRoles"
            id="systemRolesTable"
          >
            <template #cell(roleName)="row">
              <p :class="row.item.deletedFlag ? 'line-through' : ''">
                {{ row.item.roleName }}
              </p>
            </template>
            <template #cell(edit)="row">
              <b-button
                size="sm"
                type="link"
                @click="clickEditIcon(row.item, row.index, $event.target)"
                class="btn btn-link"
                v-if="!row.item.deletedFlag"
              >
                <i class="tim-icons icon-pencil" />
              </b-button>
              <b-button
                size="sm"
                type="link"
                @click="
                  row.item.deletedFlag
                    ? clickUnDelRolesIcon(row.item, row.index, $event.target)
                    : clickDelRolesIcon(row.item, row.index, $event.target)
                "
                class="btn btn-link"
              >
                <i
                  :class="
                    row.item.deletedFlag
                      ? 'tim-icons icon-refresh-01'
                      : 'tim-icons icon-trash-simple'
                  "
                ></i>
              </b-button>
            </template>
          </b-table>
        </b-collapse>
      </div>
    </card>
    <b-modal
      :id="modalRoles.delRoles.id"
      @ok="deleteAction($event)"
      @hidden="resetRolesObj"
    >
      <h5 class="text-center">
        Want to delete
        <strong>{{ modalRoles.roleName }}</strong>
      </h5>
      <template #modal-footer="{ ok, cancel}">
        <b-button variant="warning" size="sm" @click="cancel()">
          Cancel
        </b-button>
        <b-button variant="success" size="sm" @click="ok()">
          OK
        </b-button>
      </template>
    </b-modal>
    <b-modal
      :id="modalRoles.undelRoles.id"
      @ok="unDelAction($event)"
      @hidden="resetRolesObj"
    >
      <h5 class="text-center">
        Want to re-open
        <strong>{{ modalRoles.roleName }}</strong>
      </h5>
      <template #modal-footer="{ ok, cancel}">
        <b-button variant="warning" size="sm" @click="cancel()">
          Cancel
        </b-button>
        <b-button variant="success" size="sm" @click="ok()">
          OK
        </b-button>
      </template>
    </b-modal>
    <b-modal
      :id="modalRoles.editRole.id"
      @ok="updateAction($event)"
      @hidden="resetRolesObj"
    >
      <h5 class="text-center">
        Want to update
        <strong>{{ modalRoles.newObj.systemRole.roleName }}</strong>
      </h5>
      <div class="row">
        <div class="col-md-6">
          <base-input
            v-model="modalRoles.newObj.systemRole.roleDescription"
            id="roleDescription"
            label="Description"
          />
        </div>
        <div class="col-md-6">
          <base-input
            v-model="modalRoles.newObj.systemRole.applyScope"
            id="applyScope"
            label="Apply Scope"
          />
        </div>
      </div>
      <template #modal-footer="{ ok, cancel}">
        <b-button variant="warning" size="sm" @click="cancel()">
          Cancel
        </b-button>
        <b-button variant="success" size="sm" @click="ok()">
          OK
        </b-button>
      </template>
    </b-modal>
    <b-modal
      :id="modalRoles.addRole.id"
      :ref="modalRoles.addRole.id"
      @ok="AddAction($event)"
      @hidden="resetRolesObj"
    >
      <h5 class="text-center">
        Want to add new Role
      </h5>
      <div class="row">
        <div class="col-md-6">
          <base-input
            v-model="modalRoles.newObj.systemRole.roleName"
            id="roleName"
            label="Role"
          />
        </div>
        <div class="col-md-6">
          <base-input
            v-model="modalRoles.newObj.systemRole.roleDescription"
            id="roleDescription"
            label="Description"
          />
        </div>
        <div class="col-md-6">
          <base-input
            v-model="modalRoles.newObj.systemRole.applyScope"
            id="applyScope"
            label="Apply Scope"
            placeholder="default value is API"
          />
        </div>
      </div>
      <template #modal-footer="{ ok, cancel}">
        <b-button variant="warning" size="sm" @click="cancel()">
          Cancel
        </b-button>
        <b-button variant="success" size="sm" @click="ok()">
          OK
        </b-button>
      </template>
    </b-modal>
  </div>
</template>

<script>
import {
  SYSTEM_ROLES_TABLE_COLUMN,
  SYSTEM_ROLE_OBJ,
} from "@/constant/systemRolesTable";
import { findAllRoles, updateRole, insertRole } from "@/api/master";
import { ACTION } from "@/constant/common";
import { resetObject, diff, isAllNullValue } from "@/utils/objectUtil";
import { MESSAGE } from "@/constant/message";
import { fexibleMesage } from "@/utils/message";
export default {
  name: "system-roles",
  data() {
    return {
      isOpenArea: false,
      fields: SYSTEM_ROLES_TABLE_COLUMN.fields,
      systemRoles: null,
      modalRoles: {
        addRole: {
          id: "add-role-modal",
        },
        editRole: {
          id: "edit-role-modal",
        },
        undelRoles: {
          id: "undel-roles-modal",
        },
        delRoles: {
          id: "del-roles-modal",
        },
        roleName: null,
        oldObj: Object.assign({}, SYSTEM_ROLE_OBJ),
        normalObj: Object.assign({}, SYSTEM_ROLE_OBJ),
        newObj: Object.assign({}, SYSTEM_ROLE_OBJ),
      },
    };
  },
  methods: {
    openInsertRolesModal() {
      this.$refs[this.modalRoles.addRole.id].show();
    },
    clickUnDelRolesIcon(item, index, button) {
      this.setValueDeleteFlag(ACTION.UNDELETE, item, button);
    },
    clickDelRolesIcon(item, index, button) {
      this.setValueDeleteFlag(ACTION.DELETE, item, button);
    },
    clickEditIcon(item, index, button) {
      this.modalRoles.oldObj.systemRole = Object.assign({}, item);
      this.modalRoles.newObj.systemRole = Object.assign({}, item);
      this.$root.$emit("bv::show::modal", this.modalRoles.editRole.id, button);
    },
    deleteAction(e) {
      this.updateByAction(ACTION.DELETE);
    },
    unDelAction(e) {
      this.updateByAction(ACTION.UNDELETE);
    },
    updateAction(e) {
      diff(
        this.modalRoles.newObj.systemRole,
        this.modalRoles.oldObj.systemRole
      );
      if (isAllNullValue(this.modalRoles.newObj.systemRole)) {
        return;
      } else {
        this.modalRoles.newObj.systemRole.systemRoleId = this.modalRoles.oldObj.systemRole.systemRoleId;
        this.updateByAction(ACTION.UPDATE);
      }
    },
    AddAction(e) {
      if (!this.modalRoles.newObj.systemRole.roleName) {
        this.$notify({
          type: "warning",
          message: fexibleMesage(
            MESSAGE.FLEXIBLE_MESSAGE.ERR,
            SYSTEM_ROLES_TABLE_COLUMN.fields[0].label
          ),
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        e.preventDefault();
        return;
      }
      let isDuplicateRole = false;
      this.systemRoles.forEach((el) => {
        if (el.roleName === this.modalRoles.newObj.systemRole.roleName) {
          isDuplicateRole = true;
        }
      });
      if (isDuplicateRole) {
        this.$notify({
          type: "warning",
          message: fexibleMesage(
            MESSAGE.FLEXIBLE_MESSAGE.DUPLICATE,
            this.modalRoles.newObj.systemRole.roleName
          ),
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        e.preventDefault();
        return;
      }
      this.updateByAction(ACTION.INSERT);
    },
    updateByAction(action) {
      if (action === ACTION.DELETE || action === ACTION.UNDELETE) {
        this.callApiUpdate(this.modalRoles.normalObj);
      }
      if (action === ACTION.UPDATE) {
        this.callApiUpdate(this.modalRoles.newObj);
      }
      if (action === ACTION.INSERT) {
        this.callApiInsert(this.modalRoles.newObj);
      }
    },
    callApiUpdate(data) {
      updateRole(data)
        .then((res) => {
          if (res.status === 200) {
            this.initData();
          }
        })
        .catch((err) => {
          this.$notify({
            type: "warning",
            message: MESSAGE.CALL_API_ERR.ERR,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
        });
    },
    callApiInsert(data) {
      insertRole(data)
        .then((res) => {
          if (res.status === 200) {
            this.initData();
          }
        })
        .catch((err) => {
          this.$notify({
            type: "warning",
            message: MESSAGE.CALL_API_ERR.ERR,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
        });
    },
    setValueDeleteFlag(action, item, button) {
      this.modalRoles.roleName = item.roleName;
      this.modalRoles.normalObj.systemRole.systemRoleId = item.systemRoleId;
      if (action === ACTION.DELETE) {
        this.modalRoles.normalObj.systemRole.deletedFlag = true;
        this.$root.$emit(
          "bv::show::modal",
          this.modalRoles.delRoles.id,
          button
        );
      }
      if (action === ACTION.UNDELETE) {
        this.modalRoles.normalObj.systemRole.deletedFlag = false;
        this.$root.$emit(
          "bv::show::modal",
          this.modalRoles.undelRoles.id,
          button
        );
      }
    },
    resetRolesObj() {
      resetObject(this.modalRoles.normalObj.systemRole);
      resetObject(this.modalRoles.oldObj.systemRole);
      resetObject(this.modalRoles.newObj.systemRole);
    },
    initData() {
      findAllRoles().then((res) => {
        this.systemRoles = res?.data;
      });
    },
  },
  watch: {
    isOpenArea(newValue) {
      if (newValue) {
        this.initData();
      }
    },
  },
};
</script>

<style lang="scss" scoped>
#systemRoles {
  max-height: 55vh;
  overflow-x: auto;
}
.line-through {
  text-decoration: line-through;
}
/deep/ .modal-dialog > .modal-content > .modal-footer {
  display: flex;
  justify-content: right;
  button {
    margin-right: 1em;
  }
}
/deep/ .modal .modal-header .close {
  display: none;
}
#system-role-button {
  padding-top: 0;
}
</style>
