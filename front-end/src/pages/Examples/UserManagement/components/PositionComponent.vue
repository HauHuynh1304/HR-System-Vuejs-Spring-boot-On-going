<template>
  <div>
    <h4 slot="header" class="title">
      TAKING POSITIONS
    </h4>
    <div class="row">
      <div class="col-md-6">
        <b-button
          size="sm"
          @click="openInsertPositionsModal"
          class="btn btn-info tim-icons icon-simple-add"
        />
        <b-modal
          id="insertPositionsModal"
          ref="insertPositionsModal"
          @ok="insertPositionsAction($event)"
          title="New Positions"
          @hidden="resetPositionsModal"
          no-stacking
        >
          <b-form-group
            id="tags-position-component-select"
            label-for="tags-component-select"
            ref="tags-component-select"
          >
            <!-- Prop `add-on-change` is needed to enable adding tags vie the `change` event -->
            <b-form-tags
              id="tags-position-component-select"
              v-model.trim="tagPositionValue"
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
                  :disabled="disabled || availablePositionOptions.length === 0"
                  :options="availablePositionOptions"
                >
                  <template #first>
                    <!-- This is required to prevent bugs with Safari -->
                    <option disabled value="">Positions...</option>
                  </template>
                </b-form-select>
              </template>
            </b-form-tags>
          </b-form-group>
          <base-input
            v-model="modalPosition.newPosition.startDate"
            id="PositionStartDate"
            label="Start Date"
            type="date"
          />
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
      <div class="col-md-6">
        <b-pagination
          id="pagination"
          v-model="currentPage"
          :total-rows="totalRows"
          :current-page="currentPage"
          :per-page="perPage"
          align="right"
          size="sm"
        />
      </div>
    </div>
    <b-table
      hover
      sticky-header
      :fields="fields"
      :items="positionObj"
      :per-page="perPage"
      :current-page="currentPage"
    >
      <template #cell(positionName)="row">
        <p :class="row.item.deletedFlag ? 'line-through' : ''">
          {{ row.item.positionName }}
        </p>
      </template>
      <template #cell(edit)="row">
        <b-button
          size="sm"
          type="link"
          @click="edit(row.item, row.index, $event.target)"
          class="btn btn-link"
        >
          <i class="tim-icons icon-pencil" />
        </b-button>
        <b-button
          size="sm"
          type="link"
          @click="
            row.item.deletedFlag
              ? undel(row.item, row.index, $event.target)
              : del(row.item, row.index, $event.target)
          "
          class="btn btn-link"
        >
          <i
            :class="
              row.item.deletedFlag
                ? 'tim-icons icon-refresh-01'
                : 'tim-icons icon-trash-simple'
            "
          />
        </b-button>
      </template>
    </b-table>
    <!-- Modal -->
    <b-modal
      :id="modalPosition.delInfo.id"
      @ok="deleteAction($event)"
      @hidden="resetPositionsModal"
    >
      <h5 class="text-center">
        Want to delete
        <strong>{{ modalPosition.positionName }}</strong>
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
      :id="modalPosition.editInfo.id"
      @ok="updateAction($event)"
      @hidden="resetPositionsModal"
    >
      <h5 class="text-center">
        Want to update
        <strong>{{ modalPosition.positionName }}</strong>
      </h5>
      <div class="row">
        <div class="col-md-6">
          <base-input
            v-model="modalPosition.normalInfo.startDate"
            id="PositionStartDate"
            label="Start Date"
            type="date"
          />
        </div>
        <div class="col-md-6">
          <base-input
            v-model="modalPosition.normalInfo.endDate"
            id="PositionEndtDate"
            label="End Date"
            type="date"
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
import { UPDATE_EMPLOYEE_OBJECT } from "@/constant/employeeObj";
import { POSITION_TABLE_COLUMN } from "@/constant/positionTable";
import { ACTION, EVENT_BUS } from "../../../../constant/common";
import { updateEmployee, findPositions } from "@/api/humanResources";
import { MESSAGE } from "@/constant/message";

export default {
  name: "position-component",
  props: {
    positionObj: {
      type: Array,
      default: [],
    },
  },
  data() {
    return {
      originPositionObj: null,
      positionOption: [],
      tagPositionValue: [],
      updateEmployeeObj: UPDATE_EMPLOYEE_OBJECT,
      currentPage: 1,
      totalRows: null,
      perPage: 5,
      fields: POSITION_TABLE_COLUMN.fields,
      modalPosition: {
        editInfo: {
          id: "edit-position-modal",
        },
        undelInfo: {
          id: "undel-position-modal",
        },
        delInfo: {
          id: "del-position-modal",
        },
        positionName: null,
        oldObj: {
          startDate: null,
          endDate: null,
        },
        normalInfo: {
          positionId: null,
          employeePositionId: null,
          deletedFlag: null,
          startDate: null,
          endDate: null,
        },
        newPosition: {
          startDate: null,
        },
      },
    };
  },
  watch: {
    positionObj: function() {
      this.totalRows = this.positionObj.length;
      this.updateEmployeeObj.employee.employeeId = this.$route.params.id;
    },
  },
  computed: {
    availablePositionOptions() {
      return this.positionOption.filter(
        (opt) => this.tagPositionValue.indexOf(opt) === -1
      );
    },
  },
  methods: {
    openInsertPositionsModal() {
      findPositions().then((res) => {
        this.originPositionObj = res.data;
        this.tagPositionValue = this.positionObj.map((el) => el.positionName);
        this.positionOption = res.data.map((el) => el.positionName);
        this.$refs["insertPositionsModal"].show();
      });
    },
    updatePosition(action) {
      if (action === ACTION.UPDATE) {
        this.updateEmployeeObj.positions.push(this.modalPosition.normalInfo);
      }
      if (action === ACTION.INSERT) {
        if (!this.modalPosition.newPosition.startDate) {
          this.$notify({
            type: "warning",
            message: MESSAGE.INSERT_EMPLOYEE.ERR_COMMON,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
          return;
        }
        let registeredPostionName = this.positionObj.map(
          (el) => el.positionName
        );
        let newPositionTag = this.tagPositionValue.filter(
          (el) => registeredPostionName.indexOf(el) === -1
        );
        if (!newPositionTag.length || !this.tagPositionValue.length) {
          this.$notify({
            type: "warning",
            message: MESSAGE.INSERT_EMPLOYEE.ERR_INSERT,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
          return;
        }
        this.originPositionObj.forEach((el) => {
          return newPositionTag.includes(el.positionName)
            ? ((el.startDate = this.modalPosition.newPosition.startDate),
              this.updateEmployeeObj.positions.push(el))
            : null;
        });
      }
      this.submitForm(this.updateEmployeeObj);
    },
    submitForm(obj) {
      let formData = new FormData();
      formData.append("formEmployee", new Blob([JSON.stringify(obj)]), {
        type: "application/json",
      });
      updateEmployee(formData).then((res) => {
        this.$bus.emit(EVENT_BUS.REFRESH_EMPLOYEE);
        this.$refs["insertPositionsModal"].hide();
      });
    },
    setValueDeleteFlag(action, item, button) {
      if (action === ACTION.DELETE) {
        this.modalPosition.normalInfo.deletedFlag = true;
      }
      if (action === ACTION.UNDELETE) {
        this.modalPosition.normalInfo.deletedFlag = false;
      }
      this.modalPosition.positionName = item.positionName;
      this.modalPosition.normalInfo.employeePositionId =
        item.employeePositionId;
      this.$root.$emit(
        "bv::show::modal",
        this.modalPosition.delInfo.id,
        button
      );
    },
    del(item, index, button) {
      this.setValueDeleteFlag(ACTION.DELETE, item, button);
    },
    undel(item, index, button) {
      this.setValueDeleteFlag(ACTION.UNDELETE, item, button);
    },
    edit(item, index, button) {
      this.modalPosition.oldObj.startDate = item.startDate;
      this.modalPosition.oldObj.endDate = item.endDate;
      this.modalPosition.normalInfo.startDate = item.startDate;
      this.modalPosition.normalInfo.endDate = item.endDate;
      this.modalPosition.positionName = item.positionName;
      this.modalPosition.normalInfo.employeePositionId =
        item.employeePositionId;
      this.$root.$emit(
        "bv::show::modal",
        this.modalPosition.editInfo.id,
        button
      );
    },
    deleteAction(e) {
      this.updatePosition(ACTION.UPDATE);
    },
    updateAction(e) {
      if (
        this.modalPosition.normalInfo.startDate ===
        this.modalPosition.oldObj.startDate
      ) {
        this.modalPosition.normalInfo.startDate = null;
      }
      if (
        this.modalPosition.normalInfo.endDate ===
        this.modalPosition.oldObj.endDate
      ) {
        this.modalPosition.normalInfo.endDate = null;
      }
      this.updatePosition(ACTION.UPDATE);
    },
    insertPositionsAction(e) {
      e.preventDefault();
      this.updatePosition(ACTION.INSERT);
    },
    resetPositionsModal() {
      this.updateEmployeeObj.positions = [];
      this.originPositionObj = null;
      this.positionOption = [];
      this.tagPositionValue = [];
      this.modalPosition.newPosition.startDate = null;
    },
  },
};
</script>
<style lang="scss" scoped>
#pagination /deep/ .page-link {
  color: black;
  font-size: 0.75rem;
}
.line-through {
  text-decoration: line-through;
}
#tags-position-component-select___input__ {
  color: black;
}
/deep/ .modal-dialog > .modal-content > .modal-footer {
  display: flex;
  justify-content: right;
  button {
    margin-right: 1em;
  }
}
</style>
