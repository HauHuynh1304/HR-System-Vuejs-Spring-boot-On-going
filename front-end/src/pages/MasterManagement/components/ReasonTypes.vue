<template>
  <div>
    <card>
      <div slot="header">
        <div class="title">
          <div class="row">
            <b-button
              class="btn btn-link "
              v-b-toggle.reason
              variant="primary"
              @click="isOpenArea = !isOpenArea"
            >
              REASON TYPES
            </b-button>
          </div>
        </div>
        <b-collapse id="reason" class="mt-2">
          <div class="col-md-6">
            <b-button
              size="sm"
              @click="openInsertModal"
              class="btn btn-info tim-icons icon-simple-add"
            />
          </div>
          <b-table hover responsive :fields="fields" :items="reason">
            <template #cell(reasonName)="row">
              <p :class="row.item.deletedFlag ? 'line-through' : ''">
                {{ row.item.reasonName }}
              </p>
            </template>
            <template #cell(edit)="row">
              <!-- Will open Modal Edit latter -->
              <!-- <b-button
                size="sm"
                type="link"
                @click="clickEditIcon(row.item, row.index, $event.target)"
                class="btn btn-link"
                v-if="!row.item.deletedFlag"
              >
                <i class="tim-icons icon-pencil" />
              </b-button> -->
              <b-button
                size="sm"
                type="link"
                @click="
                  row.item.deletedFlag
                    ? clickUnDelIcon(row.item, row.index, $event.target)
                    : clickDelIcon(row.item, row.index, $event.target)
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
    <b-modal :id="modal.del.id" @ok="deleteAction($event)" @hidden="resetObj">
      <h5 class="text-center">
        Want to delete
        <strong>{{ modal.reasonName }}</strong>
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
    <b-modal :id="modal.undel.id" @ok="unDelAction($event)" @hidden="resetObj">
      <h5 class="text-center">
        Want to re-open
        <strong>{{ modal.reasonName }}</strong>
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
    <!-- Will open Modal Edit latter -->
    <!-- <b-modal :id="modal.edit.id" @ok="updateAction($event)" @hidden="resetObj">
      <h5 class="text-center">
        Want to update
        <strong>{{ modal.newObj.reason.reasonName }}</strong>
      </h5>
      <div class="row">
        <div class="col-md-6">
          <base-input
            v-model="modal.newObj.reason.roleDescription"
            id="roleDescription"
            label="Description"
          />
        </div>
        <div class="col-md-6">
          <base-input
            v-model="modal.newObj.reason.applyScope"
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
    </b-modal> -->
    <b-modal
      :id="modal.add.id"
      :ref="modal.add.id"
      @ok="AddAction($event)"
      @hidden="resetObj"
    >
      <h5 class="text-center">
        Want to add new reason
      </h5>
      <div class="row">
        <div class="col-md-6">
          <base-input
            v-model.trim="modal.newObj.reason.reasonName"
            id="reasonName"
            label="reason"
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
import { REASON_TABLE_COLUMN, OBJ } from "@/constant/reasonTable";
import { findAllReason, updateReason, insertReason } from "@/api/master";
import { ACTION } from "@/constant/common";
import { resetObject, diff, isAllNullValue } from "@/utils/objectUtil";
import { MESSAGE } from "@/constant/message";
export default {
  name: "positon",
  data() {
    return {
      isOpenArea: false,
      fields: REASON_TABLE_COLUMN.fields,
      reason: null,
      modal: {
        add: {
          id: "add-reason-modal",
        },
        // Will open Modal Edit latter
        // edit: {
        //   id: "edit-role-modal",
        // },
        undel: {
          id: "undel-reason-modal",
        },
        del: {
          id: "del-reason-modal",
        },
        reasonName: null,
        oldObj: Object.assign({}, OBJ),
        normalObj: Object.assign({}, OBJ),
        newObj: Object.assign({}, OBJ),
      },
    };
  },
  methods: {
    openInsertModal() {
      this.$refs[this.modal.add.id].show();
    },
    clickUnDelIcon(item, index, button) {
      this.setValueDeleteFlag(ACTION.UNDELETE, item, button);
    },
    clickDelIcon(item, index, button) {
      this.setValueDeleteFlag(ACTION.DELETE, item, button);
    },
    // Will open Modal Edit latter
    // clickEditIcon(item, index, button) {
    //   this.modal.oldObj.reason = Object.assign({}, item);
    //   this.modal.newObj.reason = Object.assign({}, item);
    //   this.$root.$emit("bv::show::modal", this.modal.edit.id, button);
    // },
    deleteAction(e) {
      this.updateByAction(ACTION.DELETE);
    },
    unDelAction(e) {
      this.updateByAction(ACTION.UNDELETE);
    },
    // Will open Modal Edit latter
    // updateAction(e) {
    //   diff(this.modal.newObj.reason, this.modal.oldObj.reason);
    //   if (isAllNullValue(this.modal.newObj.reason)) {
    //     return;
    //   } else {
    //     this.modal.newObj.reason.reasonId = this.modal.oldObj.reason.reasonId;
    //     this.updateByAction(ACTION.UPDATE);
    //   }
    // },
    AddAction(e) {
      if (!this.modal.newObj.reason.reasonName) {
        this.$notify({
          type: "warning",
          message: MESSAGE.REQUEST_TYPE.ERR,
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        e.preventDefault();
        return;
      }
      let isDuplicateRole = false;
      this.reason.forEach((el) => {
        if (el.reasonName === this.modal.newObj.reason.reasonName) {
          isDuplicateRole = true;
        }
      });
      if (isDuplicateRole) {
        this.$notify({
          type: "warning",
          message: MESSAGE.ROLE.DUPLICATE,
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
        this.callApiUpdate(this.modal.normalObj);
      }
      // Will open Modal Edit latter
      // if (action === ACTION.UPDATE) {
      //   this.callApiUpdate(this.modal.newObj);
      // }
      if (action === ACTION.INSERT) {
        this.callApiInsert(this.modal.newObj);
      }
    },
    callApiUpdate(data) {
      updateReason(data)
        .then((res) => {
          if (res.status === 200) {
            this.initData();
          }
        })
        .catch((err) => {
          this.$notify({
            type: "warning",
            message: MESSAGE.CALL_API_ERR.DUPLICATE,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
        });
    },
    callApiInsert(data) {
      insertReason(data)
        .then((res) => {
          if (res.status === 200) {
            this.initData();
          }
        })
        .catch((err) => {
          this.$notify({
            type: "warning",
            message: MESSAGE.CALL_API_ERR.DUPLICATE,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
        });
    },
    setValueDeleteFlag(action, item, button) {
      this.modal.reasonName = item.reasonName;
      this.modal.normalObj.reason.reasonId = item.reasonId;
      if (action === ACTION.DELETE) {
        this.modal.normalObj.reason.deletedFlag = true;
        this.$root.$emit("bv::show::modal", this.modal.del.id, button);
      }
      if (action === ACTION.UNDELETE) {
        this.modal.normalObj.reason.deletedFlag = false;
        this.$root.$emit("bv::show::modal", this.modal.undel.id, button);
      }
    },
    resetObj() {
      resetObject(this.modal.normalObj.reason);
      resetObject(this.modal.oldObj.reason);
      resetObject(this.modal.newObj.reason);
    },
    initData() {
      findAllReason().then((res) => {
        this.reason = res?.data;
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
#reason {
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
</style>
