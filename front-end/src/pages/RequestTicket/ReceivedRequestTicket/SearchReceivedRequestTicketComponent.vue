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
            label="Start Date"
            type="date"
            v-model="submitObj.requestEmployee.startDate"
          />
        </div>
        <div class="col-md-3 p-auto">
          <base-input
            id="endDate"
            label="End Date"
            type="date"
            v-model="submitObj.requestEmployee.endDate"
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
import { EVENT_BUS } from "@/constant/common";
export default {
  components: { Card },
  name: "search-requested-ticket-component",
  data() {
    return {
      requestOptions: null,
      requestStatusOptions: FIX_SELECT.TICKET_STATUS,
      submitObj: SEARCH_REQUESTED_TICKET,
    };
  },
  async beforeCreate() {
    await findRequestType().then((res) => (this.requestOptions = res.data));
  },
  methods: {
    onSubmit() {
      this.onSearch;
    },
    onSearch() {
      findReceivedTicket(this.submitObj).then((res) => {
        this.$bus.emit(EVENT_BUS.FIND_RECEIVED_TICKET, res.data);
      });
    },
    onReset() {
      this.submitObj.requestTypeId = null;
      resetObject(this.submitObj.requestEmployee);
    },
  },
  created() {
    this.$bus.on(EVENT_BUS.REFRESH_RECEIVED_TICKET, () => {
      this.onSearch();
    });
  },
};
</script>

<style></style>
