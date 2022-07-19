<template>
  <div class="row">
    <div class="col-md-8">
      <card>
        <div slot="header">
          <div class="row">
            <div class="col-md-6 title">
              {{
                isRequesterArea
                  ? routerProps.REQUEST_TICKET.CHILDREN.REQUESTED_TICKET.NAME.toUpperCase()
                  : routerProps.REQUEST_TICKET.CHILDREN.RECEIVED_REQUEST_TICKET.NAME.toUpperCase()
              }}
            </div>
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
        <h4 slot="header" class="title"></h4>

        <!-- Request info start -->
        <div class="container">
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
                label="duration (days)"
                readonly
              />
            </div>
          </div>
        </div>
        <!-- Request info end -->
      </card>
      <comment :requestTicket="requestTicket" />
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
import { DATE_FORMAT } from "@/constant/common";
import moment from "moment";

export default {
  components: { Comment },
  data() {
    return {
      isRequesterArea: false,
      ticketStatus: TICKET_STATUS,
      requestTicket: REQUESTED_TICKET_RESPONSE,
      routerProps: FE_ROUTER_PROP,
      action: ACTION,
      userEmail: null,
      DATE_FORMAT: DATE_FORMAT,
    };
  },
  async created() {
    await this.getRequestedTicketData();
    if (this.isRequesterPath()) {
      this.isRequesterArea = true;
    }
    this.userEmail = jwt_decode(getAccessToken()).sub;
  },
  methods: {
    getRequestedTicketData() {
      findRequestedTicket(this.$route.params.id).then((res) => {
        res.data.requestEmployee.startDate = moment(
          res.data.requestEmployee.startDate
        ).format(DATE_FORMAT);
        res.data.requestEmployee.endDate = moment(
          res.data.requestEmployee.endDate
        ).format(DATE_FORMAT);
        this.requestTicket = res.data;
      });
    },
    cancelRequest() {
      if (
        this.requestTicket.requestEmployee.requestStatus !==
        this.ticketStatus.WAITING
      ) {
        return;
      }
      this.setObjAction();
      updateRequesterAction(this.action).then((res) => {
        this.getRequestedTicketData();
        resetObject(this.action.requesterAction);
      });
    },
    setObjAction() {
      if (this.isRequesterPath()) {
        this.action.requesterAction.requesterActionId = this.requestTicket.requesterAction.requesterActionId;
        this.action.requesterAction.actionType = this.ticketStatus.CANCEL;
      }
    },
    isRequesterPath() {
      let originRouteRequestedTicket =
        FE_ROUTER_PROP.REQUEST_TICKET.CHILDREN.REQUESTED_TICKET.PATH;
      if (
        this.$route.path.includes(
          originRouteRequestedTicket.substring(
            0,
            originRouteRequestedTicket.lastIndexOf("/")
          )
        )
      ) {
        return true;
      }
      return false;
    },
    approveRequest() {
      if (
        this.requestTicket.requestEmployee.requestStatus !==
        this.ticketStatus.WAITING
      ) {
        return;
      }

      if (
        this.userEmail === this.requestTicket.supervisorAction.supervisorEmail
      ) {
        this.action.supervisorAction.actionType = this.ticketStatus.APPROVED;
        this.action.supervisorAction.supervisorActionId = this.requestTicket.supervisorAction.supervisorActionId;
        updateSupervisorAction(this.action).then((res) => {
          this.getRequestedTicketData();
          resetObject(this.action.supervisorAction);
        });
      } else {
        this.action.approverAction.actionType = this.ticketStatus.APPROVED;
        this.action.approverAction.approverActionId = this.requestTicket.approverAction.approverActionId;
        updateApproverAction(this.action).then((res) => {
          this.getRequestedTicketData();
          resetObject(this.action.approverAction);
        });
      }
    },
    rejectRequest() {
      if (
        this.requestTicket.requestEmployee.requestStatus !==
        this.ticketStatus.WAITING
      ) {
        return;
      }

      if (
        this.userEmail === this.requestTicket.supervisorAction.supervisorEmail
      ) {
        this.action.supervisorAction.actionType = this.ticketStatus.REJECT;
        this.action.supervisorAction.supervisorActionId = this.requestTicket.supervisorAction.supervisorActionId;
        updateSupervisorAction(this.action).then((res) => {
          this.getRequestedTicketData();
          resetObject(this.action.supervisorAction);
        });
      } else {
        this.action.approverAction.actionType = this.ticketStatus.REJECT;
        this.action.approverAction.approverActionId = this.requestTicket.approverAction.approverActionId;
        updateApproverAction(this.action).then((res) => {
          this.getRequestedTicketData();
          resetObject(this.action.approverAction);
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
</style>
