<template>
  <card>
    <b-form ref="searchForm" id="searchForm" @submit.prevent="onSubmit">
      <div class="row">
        <div class="col-md-3 p-auto">
          <label class="control-label">Request Type</label>
          <b-form-select
            class="form-control"
            :options="requestOptions"
            v-model="submitObj.requestTypeId"
            value-field="requestTypeId"
            text-field="requestTypeName"
          />
        </div>
        <div class="col-md-3 p-auto">
          <base-input
            id="startDate"
            label="From"
            type="date"
            v-model="timeStampObj.startDate"
          />
        </div>
        <div class="col-md-3 p-auto">
          <base-input
            id="endDate"
            label="To"
            type="date"
            v-model="timeStampObj.endDate"
          />
        </div>
        <div class="col-md-3 p-auto">
          <label class="control-label">Request Status</label>
          <b-form-select
            class="form-control"
            :options="requestStatusOptions"
            v-model="submitObj.requestEmployee.requestStatus"
          />
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="text-right pt-3">
            <base-button
              native-type="submit"
              type="info"
              size="sm"
              @click="onSearch"
            >
              SEARCH
            </base-button>
            <base-button
              id="onResetButton"
              type="warning"
              size="sm"
              @click="onReset"
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
import Card from "../../../components/Cards/Card.vue";
import { findRequestType, findReceivedTicket } from "@/api/business";
import { FIX_SELECT, SEARCH_REQUESTED_TICKET } from "@/constant/requestTicket";
import { resetObject } from "@/utils/objectUtil";
import { EVENT_BUS, TIME_STAMP } from "@/constant/common";
import { MESSAGE } from "@/constant/message";
export default {
  components: { Card },
  name: "search-requested-ticket-component",
  data() {
    return {
      requestOptions: null,
      requestStatusOptions: FIX_SELECT.TICKET_STATUS,
      submitObj: SEARCH_REQUESTED_TICKET,
      timeStampObj: {
        startDate: null,
        endDate: null,
      },
    };
  },
  methods: {
    onSubmit() {
      this.onSearch;
    },
    onSearch() {
      let startDate = null;
      let endDate = null;
      if (this.timeStampObj.startDate) {
        startDate = this.timeStampObj.startDate.concat(TIME_STAMP.BEGIN);
      }
      if (this.timeStampObj.endDate) {
        endDate = this.timeStampObj.endDate.concat(TIME_STAMP.END);
      }
      this.submitObj.requestEmployee.startDate = startDate;
      this.submitObj.requestEmployee.endDate = endDate;
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      findReceivedTicket(this.submitObj)
        .then((res) => {
          this.$bus.emit(EVENT_BUS.FIND_RECEIVED_TICKET, res.data);
        })
        .catch((err) => {
          this.$notify({
            type: "warning",
            message: MESSAGE.CALL_API_ERR.ERR,
            horizontalAlign: "center",
          });
          this.onReset();
          this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
        });
    },
    onReset() {
      this.submitObj.requestTypeId = null;
      resetObject(this.timeStampObj);
      resetObject(this.submitObj.requestEmployee);
    },
  },
  async created() {
    this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
    await findRequestType()
      .then((res) => {
        this.requestOptions = res.data;
        this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
      })
      .catch((err) => {});
    this.$bus.on(EVENT_BUS.REFRESH_RECEIVED_TICKET, () => {
      this.onSearch();
    });
    this.onReset();
  },
};
</script>

<style></style>
