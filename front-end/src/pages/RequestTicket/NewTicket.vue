<template>
  <card>
    <div class="card">
      <h4 slot="header" class="title">
        {{
          routerProps.REQUEST_TICKET.CHILDREN.CREATE_REQUEST_TICKET.NAME.toUpperCase()
        }}
      </h4>
    </div>
    <div class="row">
      <div class="col-md-3 p-auto">
        <label class="control-label">Request Type</label>
        <b-form-select
          class="form-control"
          v-model="insertTicketObj.request.requestTypeId"
          :options="requestOptions"
          value-field="requestTypeId"
          text-field="requestTypeName"
          @change="isOpitonOT(insertTicketObj.request.requestTypeId)"
        />
      </div>
      <div :class="isOT ? 'col-md-3 p-auto' : 'col-md-2 p-auto'">
        <base-input
          id="startDate"
          label="Start Date"
          :type="isOT ? 'datetime-local' : 'date'"
          v-model="insertTicketObj.requestEmployee.startDate"
          @change="calDuration"
        />
      </div>
      <div :class="isOT ? 'col-md-3 p-auto' : 'col-md-2 p-auto'">
        <base-input
          id="endDate"
          label="End Date"
          :type="isOT ? 'datetime-local' : 'date'"
          v-model="insertTicketObj.requestEmployee.endDate"
          @change="calDuration"
        />
      </div>
      <div class="col-md-2 p-auto" v-if="!isOT">
        <label class="control-label">Partial Day</label>
        <b-form-select
          class="form-control"
          v-model="insertTicketObj.requestEmployee.partialDate"
          :options="partialDateOptions"
          @change="calDuration"
        />
      </div>
      <div class="col-md-3 p-auto">
        <div class="form-control" id="duration">
          duration:
          <strong> {{ duration }} </strong>
          {{ isOT ? "hours" : "days" }}
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-3 p-auto">
        <label class="control-label">Reason</label>
        <b-form-select
          class="form-control"
          v-model="insertTicketObj.request.reasonId"
          :options="reasonOptions"
          value-field="reasonId"
          text-field="reasonName"
        />
      </div>
      <div class="col-md-3 p-auto">
        <label class="control-label">Detail</label><br />
        <textarea
          id="Detail"
          rows="1"
          max-rows="3"
          size="sm"
          class="textarea"
          v-model="insertTicketObj.requestEmployee.requestDescription"
        />
      </div>
      <div class="col-md-3 p-auto">
        <label class="control-label">Approver</label>
        <b-form-select
          class="form-control"
          v-model="insertTicketObj.approverAction.approverId"
          :options="approverOptions"
          value-field="systemAccountId"
          text-field="systemEmail"
        />
      </div>
      <div class="col-md-3 p-auto">
        <label class="control-label">Supervisor</label>
        <b-form-select
          class="form-control"
          v-model="insertTicketObj.supervisorAcction.supervisorId"
          :options="supervisorOptions"
          value-field="systemAccountId"
          text-field="systemEmail"
        />
      </div>
    </div>
    <div class="row">
      <div class="col-md-3">
        <base-input
          id="endDate"
          label="Expected Approve"
          type="date"
          v-model="timeObj.date"
        />
      </div>
      <div class="col-md-3 p-auto">
        <label for="">hh:mm AM/PM</label>
        <div class="row">
          <div class="col-md-4">
            <base-input
              id="hour"
              placeholder="hh"
              v-model="timeObj.hour"
              @change="formatHour"
            />
          </div>
          <div class="col-md-4">
            <base-input
              id="hour"
              placeholder="mm"
              v-model="timeObj.minutes"
              @change="formatMinutes"
            />
          </div>
          <div class="col-md-4">
            <b-form-select
              class="form-control"
              :options="timeReferOptions"
              v-model="timeObj.timeRefer"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div class="text-right pt-3">
          <base-button
            native-type="submit"
            @click="onSubmit"
            type="info"
            size="sm"
          >
            Submit
          </base-button>
          <base-button
            id="onResetButton"
            @click="onReset"
            type="warning"
            size="sm"
          >
            RESET
          </base-button>
        </div>
      </div>
    </div>
  </card>
</template>

<script>
import { FE_ROUTER_PROP } from "@/constant/routerProps";
import {
  FIX_SELECT,
  INSERT_TICKET,
  PARTIAL_DAY,
} from "@/constant/requestTicket";
import {
  findAccountByRole,
  findReason,
  findRequestType,
  insertRequestTicket,
} from "@/api/business";
import {
  ROLES,
  DATE_FORMAT,
  EVENT_BUS,
  SPECIAL_VALUE,
} from "@/constant/common";
import {
  resetObject,
  isContainNullValue,
  isContainNullExceptProps,
} from "@/utils/objectUtil";
import { MESSAGE } from "@/constant/message";
import moment from "moment";
export default {
  name: "new-ticket",
  data() {
    return {
      isOT: false,
      duration: 0,
      insertTicketObj: INSERT_TICKET,
      routerProps: FE_ROUTER_PROP,
      requestOptions: null,
      partialDateOptions: FIX_SELECT.PARTIAL_DAY,
      reasonOptions: null,
      approverOptions: null,
      supervisorOptions: null,
      timeReferOptions: FIX_SELECT.TIME_REFER,
      timeObj: {
        date: null,
        hour: null,
        minutes: null,
        timeRefer: null,
      },
    };
  },
  async created() {
    this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
    await findAccountByRole(ROLES.SUPERVISOR).then(
      (res) => (this.supervisorOptions = res.data)
    );
    await findAccountByRole(ROLES.MANAGER).then(
      (res) => (this.approverOptions = res.data)
    );
    await findReason().then((res) => (this.reasonOptions = res.data));
    await findRequestType().then((res) => (this.requestOptions = res.data));
    this.initTimeObj();
    this.insertTicketObj.requestEmployee.partialDate = PARTIAL_DAY.ALL_DAY;
    this.onReset();
    this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
  },
  methods: {
    isOpitonOT(optionID) {
      this.requestOptions.forEach((el) => {
        if (
          el.requestTypeId === optionID &&
          el.requestTypeName === SPECIAL_VALUE.OVER_TIME
        ) {
          this.isOT = true;
          this.duration = 0;
          this.insertTicketObj.requestEmployee.partialDate =
            SPECIAL_VALUE.HOURS;
        } else {
          this.isOT = false;
          // reset to init value
          this.insertTicketObj.requestEmployee.partialDate =
            PARTIAL_DAY.ALL_DAY;
          this.calDuration();
        }
      });
    },
    calDuration() {
      if (
        this.insertTicketObj.requestEmployee.startDate &&
        this.insertTicketObj.requestEmployee.endDate &&
        this.insertTicketObj.requestEmployee.partialDate
      ) {
        let startDate = moment(this.insertTicketObj.requestEmployee.startDate);
        let endDate = moment(this.insertTicketObj.requestEmployee.endDate);
        let duration = moment.duration(endDate.diff(startDate));
        let currentMonth = moment().month();
        if (
          startDate.month() < currentMonth ||
          endDate.month() < currentMonth
        ) {
          this.$notify({
            type: "warning",
            message: MESSAGE.DATE_TIME.ERR_MONTH_LESS,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
          this.insertTicketObj.requestEmployee.startDate = null;
          this.insertTicketObj.requestEmployee.endDate = null;
          return;
        }
        let ratio = 0;
        switch (this.insertTicketObj.requestEmployee.partialDate) {
          case PARTIAL_DAY.ALL_DAY:
          case SPECIAL_VALUE.HOURS:
            ratio = 1;
            break;
          case PARTIAL_DAY.HALF_DAY:
            ratio = 0.5;
            break;
          default:
            break;
        }
        if (!this.isOT) {
          this.duration = (duration.asDays() + 1) * ratio;
        } else {
          this.duration = duration.hours();
        }
        if (
          (this.duration < 0.5 && !this.isOT) ||
          (this.duration < 0 && this.isOT)
        ) {
          this.$notify({
            type: "warning",
            message: MESSAGE.INSERT_REQUEST_TICKET.ERR_DURATION,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
          this.insertTicketObj.requestEmployee.startDate = null;
          this.insertTicketObj.requestEmployee.endDate = null;
          this.duration = 0;
        }
      }
    },
    initTimeObj() {
      this.timeObj.date = moment(new Date()).format(DATE_FORMAT);
      this.timeObj.hour = 11;
      this.timeObj.minutes = 59;
      this.timeObj.timeRefer = "PM";
    },
    onSubmit() {
      if (
        !this.correctTimeObj() ||
        isContainNullValue(this.insertTicketObj.request) ||
        isContainNullValue(this.insertTicketObj.supervisorAcction) ||
        isContainNullValue(this.insertTicketObj.approverAction) ||
        isContainNullExceptProps(this.insertTicketObj.requestEmployee, [
          "requestDescription",
        ])
      ) {
        this.$notify({
          type: "warning",
          message: MESSAGE.INSERT_REQUEST_TICKET.ERR_EMPTY,
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        return;
      } else {
        this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
        insertRequestTicket(this.insertTicketObj)
          .then((res) => {
            this.$notify({
              type: "success",
              message: res.errorMessage
                ? res.errorMessage
                : MESSAGE.INSERT_REQUEST_TICKET.SUCCESS,
              icon: "tim-icons icon-bell-55",
              horizontalAlign: "center",
            });
            this.onReset();
            this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
          })
          .catch(() => {
            this.$notify({
              type: "warning",
              message: MESSAGE.CALL_API_ERR.ERR,
              icon: "tim-icons icon-bell-55",
              horizontalAlign: "center",
            });
            this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
          });
      }
    },
    correctTimeObj() {
      let correctTimeObj = false;
      if (
        !isContainNullValue(this.timeObj) &&
        parseInt(this.timeObj.hour) < 13 &&
        parseInt(this.timeObj.hour) >= 0 &&
        parseInt(this.timeObj.minutes) < 60 &&
        parseInt(this.timeObj.minutes) >= 0
      ) {
        correctTimeObj = true;
      }
      if (correctTimeObj) {
        let copyTimeObj = Object.assign({}, this.timeObj);
        if (copyTimeObj.timeRefer === "PM" && parseInt(copyTimeObj.hour) > 11) {
          this.$notify({
            type: "warning",
            message: MESSAGE.DATE_TIME.ERR,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
          return false;
        } else {
          if (
            copyTimeObj.timeRefer === "PM" &&
            parseInt(copyTimeObj.hour) <= 11
          ) {
            copyTimeObj.hour = parseInt(copyTimeObj.hour) + 12;
          }
          this.insertTicketObj.requestEmployee.expectedApproveDate = copyTimeObj.date.concat(
            " ",
            copyTimeObj.hour,
            ":",
            copyTimeObj.minutes,
            ":00"
          );
          return true;
        }
      } else {
        this.$notify({
          type: "warning",
          message: MESSAGE.DATE_TIME.ERR,
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        return false;
      }
    },
    onReset() {
      resetObject(this.insertTicketObj.request);
      resetObject(this.insertTicketObj.supervisorAcction);
      resetObject(this.insertTicketObj.approverAction);
      resetObject(this.insertTicketObj.requestEmployee);
      this.initTimeObj();
      this.duration = 0;
      this.insertTicketObj.requestEmployee.partialDate = PARTIAL_DAY.ALL_DAY;
    },
    formatHour() {
      this.timeObj.hour = this.timeObj.hour.toString().padStart(2, "0");
    },
    formatMinutes() {
      this.timeObj.minutes = this.timeObj.minutes.toString().padStart(2, "0");
    },
  },
};
</script>

<style lang="scss" scope>
@import "~@/assets/sass/black-dashboard/custom/variables";
.textarea {
  border-radius: 0.4285rem;
  font-size: 0.75rem;
  width: 100%;
  height: calc(2.25rem + 2px);
  padding: 0.5rem 0.7rem;
}

.textarea:focus {
  outline: none !important;
  border-color: $primary;
}

#duration {
  margin-top: 1.5rem;
  border-color: transparent;
  text-align: center;
}
</style>
