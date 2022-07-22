<template>
  <div>
    <card>
      <div slot="header">
        <div class="title">
          <div class="row">
            <b-button
              id="room-button"
              class="btn btn-link "
              v-b-toggle.room
              variant="primary"
              @click="isOpenArea = !isOpenArea"
            >
              ROOM TYPES
            </b-button>
          </div>
        </div>
        <b-collapse id="room" class="mt-2">
          <div class="col-md-6">
            <b-button
              size="sm"
              @click="openInsertModal"
              class="btn btn-info tim-icons icon-simple-add"
            />
          </div>
          <b-table hover responsive :fields="fields" :items="room">
            <template #cell(roomName)="row">
              <p :class="row.item.deletedFlag ? 'line-through' : ''">
                {{ row.item.roomName }}
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
        <strong>{{ modal.roomName }}</strong>
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
        <strong>{{ modal.roomName }}</strong>
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
        <strong>{{ modal.newObj.room.roomName }}</strong>
      </h5>
      <div class="row">
        <div class="col-md-6">
          <base-input
            v-model="modal.newObj.room.roleDescription"
            id="roleDescription"
            label="Description"
          />
        </div>
        <div class="col-md-6">
          <base-input
            v-model="modal.newObj.room.applyScope"
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
        Want to add new room
      </h5>
      <div class="row">
        <div class="col-md-6">
          <base-input
            v-model.trim="modal.newObj.room.roomName"
            id="roomName"
            label="room"
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
import { ROOM_TABLE_COLUMN, OBJ } from "@/constant/roomTable";
import { findAllRooms, updateRoom, insertRoom } from "@/api/master";
import { ACTION, EVENT_BUS } from "@/constant/common";
import { resetObject, diff, isAllNullValue } from "@/utils/objectUtil";
import { MESSAGE } from "@/constant/message";
import { fexibleMesage } from "@/utils/message";
export default {
  name: "positon",
  data() {
    return {
      isOpenArea: false,
      fields: ROOM_TABLE_COLUMN.fields,
      room: null,
      modal: {
        add: {
          id: "add-room-modal",
        },
        // Will open Modal Edit latter
        // edit: {
        //   id: "edit-role-modal",
        // },
        undel: {
          id: "undel-room-modal",
        },
        del: {
          id: "del-room-modal",
        },
        roomName: null,
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
    //   this.modal.oldObj.room = Object.assign({}, item);
    //   this.modal.newObj.room = Object.assign({}, item);
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
    //   diff(this.modal.newObj.room, this.modal.oldObj.room);
    //   if (isAllNullValue(this.modal.newObj.room)) {
    //     return;
    //   } else {
    //     this.modal.newObj.room.roomId = this.modal.oldObj.room.roomId;
    //     this.updateByAction(ACTION.UPDATE);
    //   }
    // },
    AddAction(e) {
      if (!this.modal.newObj.room.roomName) {
        this.$notify({
          type: "warning",
          message: fexibleMesage(
            MESSAGE.FLEXIBLE_MESSAGE.ERR,
            ROOM_TABLE_COLUMN.fields[0].label
          ),
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        e.preventDefault();
        return;
      }
      let isDuplicateRole = false;
      this.room.forEach((el) => {
        if (el.roomName === this.modal.newObj.room.roomName) {
          isDuplicateRole = true;
        }
      });
      if (isDuplicateRole) {
        this.$notify({
          type: "warning",
          message: fexibleMesage(
            MESSAGE.FLEXIBLE_MESSAGE.DUPLICATE,
            this.modal.newObj.room.roomName
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
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      updateRoom(data)
        .then((res) => {
          if (res.status === 200) {
            this.initData();
          }
          this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
        })
        .catch((err) => {
          this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
          this.$notify({
            type: "warning",
            message: MESSAGE.CALL_API_ERR.ERR,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
        });
    },
    callApiInsert(data) {
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      insertRoom(data)
        .then((res) => {
          if (res.status === 200) {
            this.initData();
          }
          this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
        })
        .catch((err) => {
          this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
          this.$notify({
            type: "warning",
            message: MESSAGE.CALL_API_ERR.ERR,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
        });
    },
    setValueDeleteFlag(action, item, button) {
      this.modal.roomName = item.roomName;
      this.modal.normalObj.room.roomId = item.roomId;
      if (action === ACTION.DELETE) {
        this.modal.normalObj.room.deletedFlag = true;
        this.$root.$emit("bv::show::modal", this.modal.del.id, button);
      }
      if (action === ACTION.UNDELETE) {
        this.modal.normalObj.room.deletedFlag = false;
        this.$root.$emit("bv::show::modal", this.modal.undel.id, button);
      }
    },
    resetObj() {
      resetObject(this.modal.normalObj.room);
      resetObject(this.modal.oldObj.room);
      resetObject(this.modal.newObj.room);
    },
    initData() {
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      findAllRooms().then((res) => {
        this.room = res.data;
        this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
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
/deep/ #room > .table-responsive {
  max-height: 55vh;
  thead th {
    background: white;
    position: -webkit-sticky; /* for Safari */
    position: sticky;
    top: 0;
    z-index: 1;
  }
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
#room-button {
  padding-top: 0;
}
</style>
