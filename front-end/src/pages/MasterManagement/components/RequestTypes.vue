<template>
  <div>
    <card>
      <div slot="header">
        <div class="title">
          <div class="row">
            <b-button
              class="btn btn-link "
              v-b-toggle.requestType
              variant="primary"
              @click="isOpenArea = !isOpenArea"
            >
              REQUEST TYPES
            </b-button>
          </div>
        </div>
        <b-collapse id="requestType" class="mt-2">
          <div class="col-md-6">
            <b-button
              size="sm"
              @click="openInsertRequestTypeModal"
              class="btn btn-info tim-icons icon-simple-add"
            />
          </div>
          <b-table
            hover
            responsive
            :fields="fields"
            :items="requestType"
            id="systemRolesTable"
          >
            <template #cell(requestTypeName)="row">
              <p :class="row.item.deletedFlag ? 'line-through' : ''">
                {{ row.item.requestTypeName }}
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
        <strong>{{ modal.requestTypeName }}</strong>
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
        <strong>{{ modal.requestTypeName }}</strong>
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
        <strong>{{ modal.newObj.requestType.requestTypeName }}</strong>
      </h5>
      <div class="row">
        <div class="col-md-6">
          <base-input
            v-model="modal.newObj.requestType.roleDescription"
            id="roleDescription"
            label="Description"
          />
        </div>
        <div class="col-md-6">
          <base-input
            v-model="modal.newObj.requestType.applyScope"
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
        Want to add new Request Type
      </h5>
      <div class="row">
        <div class="col-md-6">
          <base-input
            v-model.trim="modal.newObj.requestType.requestTypeName"
            id="requestTypeName"
            label="Request Type"
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
import { TABLE_COLUMN, OBJ } from "@/constant/requestTypeTable";
import {
  findAllRequestType,
  updateRequestType,
  insertRequestType,
} from "@/api/master";
import { ACTION } from "@/constant/common";
import { resetObject, diff, isAllNullValue } from "@/utils/objectUtil";
import { MESSAGE } from "@/constant/message";
import { fexibleMesage } from "@/utils/message";
export default {
  name: "system-roles",
  data() {
    return {
      isOpenArea: false,
      fields: TABLE_COLUMN.fields,
      requestType: null,
      modal: {
        add: {
          id: "add-modal-request-type",
        },
        // Will open Modal Edit latter
        // edit: {
        //   id: "edit-role-modal",
        // },
        undel: {
          id: "undel-modal-request-type",
        },
        del: {
          id: "del-modal-request-type",
        },
        requestTypeName: null,
        oldObj: Object.assign({}, OBJ),
        normalObj: Object.assign({}, OBJ),
        newObj: Object.assign({}, OBJ),
      },
    };
  },
  methods: {
    openInsertRequestTypeModal() {
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
    //   this.modal.oldObj.requestType = Object.assign({}, item);
    //   this.modal.newObj.requestType = Object.assign({}, item);
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
    //   diff(this.modal.newObj.requestType, this.modal.oldObj.requestType);
    //   if (isAllNullValue(this.modal.newObj.requestType)) {
    //     return;
    //   } else {
    //     this.modal.newObj.requestType.requestTypeId = this.modal.oldObj.requestType.requestTypeId;
    //     this.updateByAction(ACTION.UPDATE);
    //   }
    // },
    AddAction(e) {
      if (!this.modal.newObj.requestType.requestTypeName) {
        this.$notify({
          type: "warning",
          message: fexibleMesage(
            MESSAGE.FLEXIBLE_MESSAGE.ERR,
            TABLE_COLUMN.fields[0].label
          ),
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        e.preventDefault();
        return;
      }
      let isDuplicateRole = false;
      this.requestType.forEach((el) => {
        if (
          el.requestTypeName === this.modal.newObj.requestType.requestTypeName
        ) {
          isDuplicateRole = true;
        }
      });
      if (isDuplicateRole) {
        this.$notify({
          type: "warning",
          message: fexibleMesage(
            MESSAGE.FLEXIBLE_MESSAGE.DUPLICATE,
            this.modal.newObj.requestType.requestTypeName
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
      updateRequestType(data)
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
      insertRequestType(data)
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
      this.modal.requestTypeName = item.requestTypeName;
      this.modal.normalObj.requestType.requestTypeId = item.requestTypeId;
      if (action === ACTION.DELETE) {
        this.modal.normalObj.requestType.deletedFlag = true;
        this.$root.$emit("bv::show::modal", this.modal.del.id, button);
      }
      if (action === ACTION.UNDELETE) {
        this.modal.normalObj.requestType.deletedFlag = false;
        this.$root.$emit("bv::show::modal", this.modal.undel.id, button);
      }
    },
    resetObj() {
      resetObject(this.modal.normalObj.requestType);
      resetObject(this.modal.oldObj.requestType);
      resetObject(this.modal.newObj.requestType);
    },
    initData() {
      findAllRequestType().then((res) => {
        this.requestType = res?.data;
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
#requestType {
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
