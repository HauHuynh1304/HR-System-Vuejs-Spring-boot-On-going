<template>
  <div class="row">
    <div class="col-md-8">
      <card>
        <div slot="header">
          <div class="row">
            <div class="col-md-6 title" />
            <div class="col-md-6 title">
              <div
                :class="
                  requestTicket.requestEmployee.requestStatus ===
                  ticketStatus.APPROVED
                    ? 'text-right text-success'
                    : requestTicket.requestEmployee.requestStatus ===
                      ticketStatus.REJECT
                    ? 'text-right text-danger'
                    : requestTicket.requestEmployee.requestStatus ===
                      ticketStatus.CANCEL
                    ? 'text-right text-warning'
                    : 'text-right text-primary'
                "
              >
                {{ requestTicket.requestEmployee.requestStatus }}
              </div>
            </div>
          </div>
        </div>

        <!-- Request info start -->
        <div class="request-info">
          <div class="row">
            <div class="col-md-4">
              <label class="control-label">Request Type</label><br />
              <textarea
                id="Detail"
                rows="1"
                max-rows="3"
                size="sm"
                class="textarea"
                v-model="requestTicket.requestEmployee.requestType"
                readonly
              />
            </div>
            <div class="col-md-4">
              <base-input
                v-model.trim="requestTicket.requestEmployee.startDate"
                id="startDate"
                label="Start Date"
                readonly
              />
            </div>
            <div class="col-md-4">
              <base-input
                v-model.trim="requestTicket.requestEmployee.endDate"
                id="endDate"
                label="End Date"
                readonly
              />
            </div>
          </div>
          <div class="row">
            <div class="col-md-4">
              <label class="control-label">Detail</label><br />
              <textarea
                id="Detail"
                rows="1"
                max-rows="3"
                size="sm"
                class="textarea"
                v-model="requestTicket.requestEmployee.requestDescription"
                readonly
              />
            </div>
            <div class="col-md-4">
              <base-input
                v-model.trim="requestTicket.requestEmployee.expectedApproveDate"
                id="expectedApproveDate"
                label="Expected ApproveDate"
                readonly
              />
            </div>
            <div class="col-md-4">
              <base-input
                v-model.trim="requestTicket.requestEmployee.duration"
                id="status"
                :label="isOTArea ? 'duration (hours)' : 'duration (days)'"
                readonly
              />
            </div>
          </div>
        </div>
        <!-- Request info end -->
      </card>
      <comment
        :requestTicket="requestTicket"
        :requestId="requestId"
        :isRequesterArea="isRequesterArea"
      />
    </div>
    <div class="col-md-4">
      <card>
        <div slot="header" class="title">
          Request From
          {{
            requestTicket.requesterAction.requesterEmail
              ? requestTicket.requesterAction.requesterEmail.substring(
                  0,
                  requestTicket.requesterAction.requesterEmail.indexOf("@")
                )
              : ""
          }}
        </div>
        <div class="text-left">
          <h5>Last status: {{ requestTicket.requesterAction.actionType }}</h5>
          <h5>Last update at: {{ requestTicket.requesterAction.updatedAt }}</h5>
        </div>
        <div
          class="text-center"
          v-if="
            isRequesterArea &&
              requestTicket.requestEmployee.requestStatus ===
                ticketStatus.WAITING
          "
        >
          <base-button size="sm" type="warning" @click="cancelRequest">
            CANCEL REQUEST
          </base-button>
        </div>
      </card>
      <card>
        <div slot="header" class="title">
          To Supervisor
          {{
            requestTicket.supervisorAction.supervisorEmail
              ? requestTicket.supervisorAction.supervisorEmail.substring(
                  0,
                  requestTicket.supervisorAction.supervisorEmail.indexOf("@")
                )
              : ""
          }}
        </div>
        <div class="text-left">
          <h5>Last status: {{ requestTicket.supervisorAction.actionType }}</h5>
          <h5>
            Last update at: {{ requestTicket.supervisorAction.updatedAt }}
          </h5>
        </div>
        <div
          class="text-center"
          v-if="
            !isRequesterArea &&
              requestTicket.requestEmployee.requestStatus ===
                ticketStatus.WAITING &&
              requestTicket.supervisorAction.actionType ===
                ticketStatus.WAITING &&
              userEmail === requestTicket.supervisorAction.supervisorEmail
          "
        >
          <base-button size="sm" type="success" @click="approveRequest">
            APPROVE
          </base-button>
          <base-button size="sm" type="warning" @click="rejectRequest">
            REJECT
          </base-button>
        </div>
      </card>
      <card>
        <div slot="header" class="title">
          To Approver
          {{
            requestTicket.approverAction.approverEmail
              ? requestTicket.approverAction.approverEmail.substring(
                  0,
                  requestTicket.approverAction.approverEmail.indexOf("@")
                )
              : ""
          }}
        </div>
        <div class="text-left">
          <h5>Last status: {{ requestTicket.approverAction.actionType }}</h5>
          <h5>Last update at: {{ requestTicket.approverAction.updatedAt }}</h5>
        </div>
        <div
          class="text-center"
          v-if="
            !isRequesterArea &&
              requestTicket.requestEmployee.requestStatus ===
                ticketStatus.WAITING &&
              requestTicket.approverAction.actionType ===
                ticketStatus.WAITING &&
              userEmail === requestTicket.approverAction.approverEmail
          "
        >
          <base-button size="sm" type="success" @click="approveRequest">
            APPROVE
          </base-button>
          <base-button size="sm" type="warning" @click="rejectRequest">
            REJECT
          </base-button>
        </div>
      </card>
    </div>
  </div>
</template>

<script>
import {
  findRequestedTicket,
  updateSupervisorAction,
  updateApproverAction,
} from "@/api/business";
import { FE_ROUTER_PROP } from "@/constant/routerProps";
import {
  REQUESTED_TICKET_RESPONSE,
  TICKET_STATUS,
  ACTION,
} from "@/constant/requestTicket";
import Comment from "@/components/Comment/Comment.vue";
import { updateRequesterAction } from "@/api/business";
import { resetObject } from "@/utils/objectUtil";
import jwt_decode from "jwt-decode";
import { getAccessToken } from "@/utils/cookies";
import {
  DATE_FORMAT,
  DATE_TIME_FORMAT,
  EVENT_BUS,
  SPECIAL_VALUE,
} from "@/constant/common";
import moment from "moment";
export default {
  components: { Comment },
  props: {
    requestId: {
      type: Number,
      default: null,
    },
    isRequesterArea: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      ticketStatus: TICKET_STATUS,
      requestTicket: REQUESTED_TICKET_RESPONSE,
      routerProps: FE_ROUTER_PROP,
      action: ACTION,
      userEmail: null,
      DATE_FORMAT: DATE_FORMAT,
      isOTArea: false,
    };
  },
  async created() {
    await this.getRequestedTicketData();
    this.userEmail = jwt_decode(getAccessToken()).sub;
  },
  methods: {
    getRequestedTicketData() {
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      findRequestedTicket(this.requestId)
        .then((res) => {
          res.data.requestEmployee.requestType === SPECIAL_VALUE.OVER_TIME
            ? (this.isOTArea = true)
            : (this.isOTArea = false);
          res.data.requestEmployee.startDate = moment(
            res.data.requestEmployee.startDate
          ).format(this.isOTArea ? DATE_TIME_FORMAT : DATE_FORMAT);
          res.data.requestEmployee.endDate = moment(
            res.data.requestEmployee.endDate
          ).format(this.isOTArea ? DATE_TIME_FORMAT : DATE_FORMAT);

          res.data.requesterAction.updatedAt = moment(
            res.data.requesterAction.updatedAt
          ).format(DATE_FORMAT);
          res.data.supervisorAction.updatedAt = moment(
            res.data.supervisorAction.updatedAt
          ).format(DATE_FORMAT);
          res.data.approverAction.updatedAt = moment(
            res.data.approverAction.updatedAt
          ).format(DATE_FORMAT);
          this.requestTicket = res.data;
          this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
        })
        .catch((err) => {});
    },
    cancelRequest() {
      if (
        this.requestTicket.requestEmployee.requestStatus !==
        this.ticketStatus.WAITING
      ) {
        return;
      }
      this.action.requesterAction.requesterActionId = this.requestTicket.requesterAction.requesterActionId;
      this.action.requesterAction.actionType = this.ticketStatus.CANCEL;
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      updateRequesterAction(this.action)
        .then((res) => {
          this.getRequestedTicketData();
          resetObject(this.action.requesterAction);
          // force update special request in list ticket
          this.emitEventToListTicket(
            FE_ROUTER_PROP.REQUEST_TICKET.CHILDREN.LIST_REQUESTED_TICKET.PATH,
            EVENT_BUS.REFRESH_SPECIAL_REQUESTED_TICKET,
            this.requestId,
            this.ticketStatus.CANCEL
          );
          this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
        })
        .catch((err) => {});
    },
    approveRequest() {
      if (
        this.requestTicket.requestEmployee.requestStatus !==
        this.ticketStatus.WAITING
      ) {
        return;
      }
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      if (
        this.userEmail === this.requestTicket.supervisorAction.supervisorEmail
      ) {
        this.action.supervisorAction.actionType = this.ticketStatus.APPROVED;
        this.action.supervisorAction.supervisorActionId = this.requestTicket.supervisorAction.supervisorActionId;
        updateSupervisorAction(this.action)
          .then((res) => {
            this.getRequestedTicketData();
            resetObject(this.action.supervisorAction);
            this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
          })
          .catch((err) => {});
      } else {
        this.action.approverAction.actionType = this.ticketStatus.APPROVED;
        this.action.approverAction.approverActionId = this.requestTicket.approverAction.approverActionId;
        updateApproverAction(this.action)
          .then((res) => {
            this.getRequestedTicketData();
            resetObject(this.action.approverAction);
            // force update special request in list ticket
            this.emitEventToListTicket(
              FE_ROUTER_PROP.REQUEST_TICKET.CHILDREN
                .LIST_RECEIVED_REQUEST_TICKET.PATH,
              EVENT_BUS.REFRESH_SPECIAL_RECEIVED_TICKET,
              this.requestId,
              this.ticketStatus.APPROVED
            );
            this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
          })
          .catch((err) => {});
      }
    },
    rejectRequest() {
      if (
        this.requestTicket.requestEmployee.requestStatus !==
        this.ticketStatus.WAITING
      ) {
        return;
      }
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      if (
        this.userEmail === this.requestTicket.supervisorAction.supervisorEmail
      ) {
        this.action.supervisorAction.actionType = this.ticketStatus.REJECT;
        this.action.supervisorAction.supervisorActionId = this.requestTicket.supervisorAction.supervisorActionId;
        updateSupervisorAction(this.action)
          .then((res) => {
            this.getRequestedTicketData();
            resetObject(this.action.supervisorAction);
            this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
          })
          .catch((err) => {});
      } else {
        this.action.approverAction.actionType = this.ticketStatus.REJECT;
        this.action.approverAction.approverActionId = this.requestTicket.approverAction.approverActionId;
        updateApproverAction(this.action)
          .then((res) => {
            this.getRequestedTicketData();
            resetObject(this.action.approverAction);
            // force update special request in list ticket
            this.emitEventToListTicket(
              FE_ROUTER_PROP.REQUEST_TICKET.CHILDREN
                .LIST_RECEIVED_REQUEST_TICKET.PATH,
              EVENT_BUS.REFRESH_SPECIAL_RECEIVED_TICKET,
              this.requestId,
              this.ticketStatus.REJECT
            );
            this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
          })
          .catch((err) => {});
      }
    },
    emitEventToListTicket(path, event, requestId, ticketStatus) {
      if (this.$route.path.includes(path)) {
        this.$bus.emit(event, {
          requestId: requestId,
          requestStatus: ticketStatus,
        });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@import "~@/assets/sass/black-dashboard/custom/variables";
.textarea {
  border-radius: 0.4285rem;
  font-size: 0.75rem;
  width: 100%;
  height: calc(2.25rem + 2px);
  padding: 0.5rem 0.7rem;
  background-color: $medium-gray;
  border-color: rgba(29, 37, 59, 0.3);
}
.textarea:focus {
  outline: none !important;
  border-color: $primary;
}
.wrapper {
  height: 100%;
}
.request-info {
  margin: auto;
}
</style>
