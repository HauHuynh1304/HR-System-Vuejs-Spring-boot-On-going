<template>
  <div>
    <card>
      <div slot="header">
        <div class="title">
          <div class="row">
            <b-button
              class="btn btn-link "
              v-b-toggle.position
              variant="primary"
              @click="isOpenArea = !isOpenArea"
            >
              POSITION
            </b-button>
          </div>
        </div>
        <b-collapse id="position" class="mt-2">
          <div class="col-md-6">
            <b-button
              size="sm"
              @click="openInsertModal"
              class="btn btn-info tim-icons icon-simple-add"
            />
          </div>
          <b-table
            hover
            responsive
            :fields="fields"
            :items="position"
            id="systemRolesTable"
          >
            <template #cell(positionName)="row">
              <p :class="row.item.deletedFlag ? 'line-through' : ''">
                {{ row.item.positionName }}
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
        <strong>{{ modal.positionName }}</strong>
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
        <strong>{{ modal.positionName }}</strong>
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
        <strong>{{ modal.newObj.position.positionName }}</strong>
      </h5>
      <div class="row">
        <div class="col-md-6">
          <base-input
            v-model="modal.newObj.position.roleDescription"
            id="roleDescription"
            label="Description"
          />
        </div>
        <div class="col-md-6">
          <base-input
            v-model="modal.newObj.position.applyScope"
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
        Want to add new Position
      </h5>
      <div class="row">
        <div class="col-md-6">
          <base-input
            v-model.trim="modal.newObj.position.positionName"
            id="positionName"
            label="Position Name"
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
import { POSITION_MASTER_TABLE_COLUMN, OBJ } from "@/constant/positionTable";
import { findAllPositions, updatePosition, insertPosition } from "@/api/master";
import { ACTION } from "@/constant/common";
import { resetObject, diff, isAllNullValue } from "@/utils/objectUtil";
import { MESSAGE } from "@/constant/message";
import { fexibleMesage } from "@/utils/message";
export default {
  name: "positon",
  data() {
    return {
      isOpenArea: false,
      fields: POSITION_MASTER_TABLE_COLUMN.fields,
      position: null,
      modal: {
        add: {
          id: "add-position-modal",
        },
        // Will open Modal Edit latter
        // edit: {
        //   id: "edit-role-modal",
        // },
        undel: {
          id: "undel-position-modal",
        },
        del: {
          id: "del-position-modal",
        },
        positionName: null,
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
    //   this.modal.oldObj.position = Object.assign({}, item);
    //   this.modal.newObj.position = Object.assign({}, item);
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
    //   diff(this.modal.newObj.position, this.modal.oldObj.position);
    //   if (isAllNullValue(this.modal.newObj.position)) {
    //     return;
    //   } else {
    //     this.modal.newObj.position.positionId = this.modal.oldObj.position.positionId;
    //     this.updateByAction(ACTION.UPDATE);
    //   }
    // },
    AddAction(e) {
      if (!this.modal.newObj.position.positionName) {
        this.$notify({
          type: "warning",
          message: fexibleMesage(
            MESSAGE.FLEXIBLE_MESSAGE.ERR,
            POSITION_MASTER_TABLE_COLUMN.fields[0].label
          ),
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        e.preventDefault();
        return;
      }
      let isDuplicateRole = false;
      this.position.forEach((el) => {
        if (el.positionName === this.modal.newObj.position.positionName) {
          isDuplicateRole = true;
        }
      });
      if (isDuplicateRole) {
        this.$notify({
          type: "warning",
          message: fexibleMesage(
            MESSAGE.FLEXIBLE_MESSAGE.DUPLICATE,
            this.modal.newObj.position.positionName
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
      updatePosition(data)
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
      insertPosition(data)
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
      this.modal.positionName = item.positionName;
      this.modal.normalObj.position.positionId = item.positionId;
      if (action === ACTION.DELETE) {
        this.modal.normalObj.position.deletedFlag = true;
        this.$root.$emit("bv::show::modal", this.modal.del.id, button);
      }
      if (action === ACTION.UNDELETE) {
        this.modal.normalObj.position.deletedFlag = false;
        this.$root.$emit("bv::show::modal", this.modal.undel.id, button);
      }
    },
    resetObj() {
      resetObject(this.modal.normalObj.position);
      resetObject(this.modal.oldObj.position);
      resetObject(this.modal.newObj.position);
    },
    initData() {
      findAllPositions().then((res) => {
        this.position = res?.data;
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
#position {
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
