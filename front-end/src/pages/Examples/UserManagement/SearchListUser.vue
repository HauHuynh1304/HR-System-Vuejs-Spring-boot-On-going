<template>
  <card>
    <b-form ref="searchForm" id="searchForm" @submit.prevent="onSubmit">
      <div class="row">
        <div class="col-md-3 p-auto">
          <base-input
            id="fullName"
            label="Full Name"
            placeholder="Full Name"
            v-model="data.personalInfo.personalName"
          />
        </div>
        <div class="col-md-3 p-auto">
          <base-input
            id="phone"
            label="Phone"
            v-model="data.personalInfo.personalPhoneNumber"
            placeholder="Phone"
          />
        </div>
        <div class="col-md-2 p-auto">
          <base-input
            id="startDate"
            label="Start Date"
            v-model="data.employee.employeeStartDate"
            type="date"
          />
        </div>
        <div class="col-md-2 p-auto">
          <base-input
            id="endDate"
            label="End Date"
            type="date"
            v-model="data.employee.employeeEndDate"
          />
        </div>
        <div class="col-md-2 p-auto">
          <label class="control-label">On Working</label>
          <b-form-select
            v-model="data.employee.deletedFlag"
            :options="activedStatus"
            class="form-control"
          />
        </div>
      </div>
      <div class="row">
        <div class="col-md-3 p-auto">
          <label class="control-label">Email</label>
          <b-form-select
            v-model="data.employee.systemAccountId"
            :options="listAccounts"
            value-field="systemAccountId"
            text-field="systemEmail"
            class="form-control"
          />
        </div>
        <div class="col-md-3 p-auto">
          <label class="control-label">Room</label>
          <b-form-select
            v-model="data.room.roomId"
            :options="listRooms"
            value-field="roomId"
            text-field="roomName"
            class="form-control"
          />
        </div>
        <div class="col-md-3 p-auto">
          <label class="control-label">Position</label>
          <b-form-select
            v-model="data.position.positionId"
            :options="listPositions"
            value-field="positionId"
            text-field="positionName"
            class="form-control"
          />
        </div>
        <div class="col-md-3 p-auto">
          <label class="control-label">Full Document</label>
          <b-form-select
            v-model="data.isFullDocuments"
            :options="documentOptions"
            class="form-control"
          />
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="text-right pt-3">
            <base-button
              native-type="submit"
              @click="onSearch"
              type="info"
              size="sm"
            >
              SEARCH
            </base-button>
            <base-button
              id="onResetButton"
              @click="onResetSearch"
              type="warning"
              size="sm"
            >
              RESET
            </base-button>
          </div>
        </div>
      </div>
    </b-form>
  </card>
</template>
<script>
import { findAllAccounts } from "@/api/master";
import {
  findPositions,
  findRooms,
  findListEmployee,
} from "@/api/humanResources";
import {
  DOCUMENT_STATUS,
  OBJECT_SEARCH,
  ACTIVED_STATUS,
} from "@/constant/searchListUserForm";
import { resetObject } from "@/utils/objectUtil";
import { EVENT_BUS } from "@/constant/common";
import { MESSAGE } from "@/constant/message";
export default {
  name: "Search-list-user",
  data() {
    return {
      listAccounts: null,
      listRooms: null,
      listPositions: null,
      documentOptions: DOCUMENT_STATUS,
      activedStatus: ACTIVED_STATUS,
      data: OBJECT_SEARCH,
    };
  },
  methods: {
    onSubmit() {
      this.onSearch;
    },
    onSearch() {
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      findListEmployee(this.data)
        .then((res) => {
          this.$bus.emit(EVENT_BUS.FETCH_DATA_LIST_EMPLOYEE, res.data);
        })
        .catch((err) => {
          this.$notify({
            type: "warning",
            message: MESSAGE.CALL_API_ERR.ERR,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
          this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
        });
    },
    onResetSearch() {
      resetObject(this.data.personalInfo);
      resetObject(this.data.employee);
      resetObject(this.data.room);
      resetObject(this.data.position);
      this.data.isFullDocuments = null;
    },
  },
  async created() {
    this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
    await findAllAccounts().then((res) => (this.listAccounts = res.data));
    await findPositions().then((res) => (this.listPositions = res.data));
    await findRooms().then((res) => (this.listRooms = res.data));
    this.onResetSearch();
    this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
  },
};
</script>
<style></style>
