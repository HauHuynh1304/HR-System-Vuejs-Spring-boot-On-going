<template>
  <div>
    <card>
      <h4 slot="header" class="title">
        {{
          routerProps.REQUEST_TICKET.CHILDREN.LIST_RECEIVED_REQUEST_TICKET.NAME.toUpperCase()
        }}
      </h4>
      <search-received-request-ticket-component />
    </card>
    <card v-if="items">
      <div class="row">
        <div class="col-md-2 p-auto">
          <b-form-group
            label="Per page"
            label-for="per-page-select"
            label-cols-sm="6"
            label-cols-md="4"
            label-cols-lg="6"
            label-align-sm="left"
            label-size="sm"
          >
            <b-form-select
              id="per-page-select"
              v-model="perPage"
              :options="pageOptions"
              size="sm"
            ></b-form-select>
          </b-form-group>
        </div>
        <div class="col-md-3 p-auto">
          <b-form-input
            id="filter-input"
            v-model="filter"
            type="search"
            size="sm"
            placeholder="Filter"
          />
        </div>
        <div
          id="mutiple-request-area"
          class="col-md-3 p-auto text-right d-flex justify-content-around align-items-start"
        >
          <base-button size="sm" type="info" @click="approveAll">
            APPROVE ALL
          </base-button>
          <base-button size="sm" type="warning" @click="rejectAll">
            REJECT ALL
          </base-button>
        </div>
        <div class="col-md-4 p-auto">
          <b-pagination
            id="pagination"
            v-model="currentPage"
            :total-rows="totalRows"
            :per-page="perPage"
            align="right"
            size="sm"
          />
        </div>
      </div>
      <div id="list-received-ticket-table">
        <b-table
          hover
          :items="items"
          :fields="fields"
          :filter="filter"
          :current-page="currentPage"
          :per-page="perPage"
          responsive
        >
          <template #cell(requestType)="row">
            <b-link @click="openReceivedTicketDetailModal(row.item.requestId)">
              {{ row.item.requestType }}
            </b-link>
          </template>
          <template #cell(requestEmployee.requestStatus)="row">
            <p
              :class="
                row.item.requestEmployee.requestStatus === ticketStatus.APPROVED
                  ? 'text-success'
                  : row.item.requestEmployee.requestStatus ===
                    ticketStatus.REJECT
                  ? 'text-danger'
                  : row.item.requestEmployee.requestStatus ===
                    ticketStatus.CANCEL
                  ? 'text-warning'
                  : 'text-primary'
              "
            >
              {{ row.item.requestEmployee.requestStatus }}
            </p>
          </template>
          <template #cell(requestEmployee.startDate)="row">
            <p>
              {{
                row.item.requestType === SPECIAL_VALUE.OVER_TIME
                  ? moment(row.item.requestEmployee.startDate).format(
                      DATE_TIME_FORMAT
                    )
                  : moment(row.item.requestEmployee.startDate).format(
                      DATE_FORMAT
                    )
              }}
            </p>
          </template>
          <template #cell(requestEmployee.endDate)="row">
            <p>
              {{
                row.item.requestType === SPECIAL_VALUE.OVER_TIME
                  ? moment(row.item.requestEmployee.endDate).format(
                      DATE_TIME_FORMAT
                    )
                  : moment(row.item.requestEmployee.endDate).format(DATE_FORMAT)
              }}
            </p>
          </template>
          <template #cell(requestEmployee.duration)="row">
            <p>
              {{
                row.item.requestEmployee.duration +
                  (row.item.requestType === SPECIAL_VALUE.OVER_TIME
                    ? " hours"
                    : " day(s)")
              }}
            </p>
          </template>
        </b-table>
      </div>
    </card>
    <b-modal
      size="xl"
      :ref="modal.receivedDetail.id"
      :id="modal.receivedDetail.id"
      hide-footer
      hide-header
    >
      <requested-ticket
        :requestId="modal.receivedDetail.requestId"
        :isRequesterArea="false"
      />
    </b-modal>
  </div>
</template>

<script>
import SearchReceivedRequestTicketComponent from "./SearchReceivedRequestTicketComponent.vue";
import Card from "../../../components/Cards/Card.vue";
import { FE_ROUTER_PROP } from "@/constant/routerProps";
import {
  EVENT_BUS,
  SPECIAL_VALUE,
  DATE_FORMAT,
  DATE_TIME_FORMAT,
} from "@/constant/common";
import { RECEIVED_TIKCET_TABLE } from "@/constant/requestedTicketTable";
import {
  TICKET_STATUS,
  MUTIPLE_UPDATE_DATA,
  ACTION,
} from "@/constant/requestTicket";
import jwt_decode from "jwt-decode";
import { getAccessToken } from "@/utils/cookies";
import { mutipleUpdateRequestTicketStatus } from "@/api/business";
import { MESSAGE } from "@/constant/message";
import RequestedTicket from "../RequestedTicket/RequestedTicket.vue";
import moment from "moment";
export default {
  components: { SearchReceivedRequestTicketComponent, Card, RequestedTicket },
  data() {
    return {
      isApproverArea: false,
      isSupervisorArea: false,
      ticketStatus: TICKET_STATUS,
      routerProps: FE_ROUTER_PROP,
      pageOptions: [5, 10, 15, 20],
      totalRows: null,
      currentPage: 1,
      perPage: 10,
      filter: null,
      items: null,
      fields: RECEIVED_TIKCET_TABLE.fields,
      mutipleUpdateData: MUTIPLE_UPDATE_DATA,
      modal: {
        receivedDetail: {
          id: "received-detail-modal",
          requestId: null,
        },
      },
      moment: moment,
      SPECIAL_VALUE: SPECIAL_VALUE,
      DATE_FORMAT: DATE_FORMAT,
      DATE_TIME_FORMAT: DATE_TIME_FORMAT,
    };
  },
  created() {
    this.$bus.on(EVENT_BUS.FIND_RECEIVED_TICKET, (data) => {
      this.items = data ? data : [];
      this.totalRows = this.items.length;
      this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
    });
    this.$bus.on(EVENT_BUS.REFRESH_SPECIAL_RECEIVED_TICKET, (data) => {
      for (let i in this.items) {
        if (this.items[i].requestId == data.requestId) {
          this.items[i].requestEmployee.requestStatus = data.requestStatus;
          return;
        }
      }
    });
  },
  methods: {
    openReceivedTicketDetailModal(requestId) {
      this.modal.receivedDetail.requestId = parseInt(requestId);
      this.$refs[this.modal.receivedDetail.id].show();
    },
    approveAll() {
      if (!this.items) {
        return;
      }
      this.setData(this.ticketStatus.APPROVED);
      if (
        !this.mutipleUpdateData.supervisorAction.length &&
        !this.mutipleUpdateData.approverAction.length
      ) {
        return;
      } else {
        this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
        mutipleUpdateRequestTicketStatus(this.mutipleUpdateData)
          .then((res) => {
            if (res.status === 200) {
              this.$notify({
                type: "success",
                message: MESSAGE.MUTIPLE_UPDATE_TICKET_STATUS.UPDATE_SUCCESS,
                icon: "tim-icons icon-bell-55",
                horizontalAlign: "center",
              });
              this.$bus.emit(EVENT_BUS.REFRESH_RECEIVED_TICKET);
            } else {
              this.$notify({
                type: "warning",
                message: MESSAGE.MUTIPLE_UPDATE_TICKET_STATUS.APPROVE_ALL_ERR,
                icon: "tim-icons icon-bell-55",
                horizontalAlign: "center",
              });
            }
            this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
          })
          .catch((err) => {});
      }
    },
    rejectAll() {
      if (!this.items) {
        return;
      }
      this.setData(this.ticketStatus.REJECT);
      if (
        !this.mutipleUpdateData.supervisorAction.length &&
        !this.mutipleUpdateData.approverAction.length
      ) {
        return;
      } else {
        this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
        mutipleUpdateRequestTicketStatus(this.mutipleUpdateData)
          .then((res) => {
            if (res.status === 200) {
              this.$notify({
                type: "success",
                message: MESSAGE.MUTIPLE_UPDATE_TICKET_STATUS.UPDATE_SUCCESS,
                icon: "tim-icons icon-bell-55",
                horizontalAlign: "center",
              });
              this.$bus.emit(EVENT_BUS.REFRESH_RECEIVED_TICKET);
            } else {
              this.$notify({
                type: "warning",
                message: MESSAGE.MUTIPLE_UPDATE_TICKET_STATUS.REJECT_ALL_ERR,
                icon: "tim-icons icon-bell-55",
                horizontalAlign: "center",
              });
            }
            this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
          })
          .catch((err) => {});
      }
    },
    setData(status) {
      this.resetMutipleUpdateRequestTicketStatus();
      let userEmail = jwt_decode(getAccessToken()).sub;
      this.items.forEach((el) => {
        if (
          el.requestEmployee.requestStatus === TICKET_STATUS.WAITING &&
          userEmail === el.supervisor
        ) {
          let supervisorAction = Object.assign({}, ACTION.supervisorAction);
          supervisorAction.supervisorActionId =
            el.requestEmployee.supervisorActionId;
          supervisorAction.actionType = status;
          this.mutipleUpdateData.supervisorAction.push(supervisorAction);
        }

        if (
          el.requestEmployee.requestStatus === TICKET_STATUS.WAITING &&
          userEmail === el.approver
        ) {
          let approverAction = Object.assign({}, ACTION.approverAction);
          approverAction.approverActionId = el.requestEmployee.approverActionId;
          approverAction.actionType = status;
          this.mutipleUpdateData.approverAction.push(approverAction);
        }
      });
    },
    resetMutipleUpdateRequestTicketStatus() {
      this.mutipleUpdateData.supervisorAction = [];
      this.mutipleUpdateData.approverAction = [];
    },
  },
};
</script>

<style lang="scss" scoped>
#per-page-select {
  color: black;
}
#pagination /deep/ .page-link {
  color: black;
  font-size: 0.75rem;
}
#mutiple-request-area /deep/ button {
  margin: 0;
  padding: 0.25rem 0.5rem 0.25rem 0.5rem;
}
/deep/ #received-detail-modal___BV_modal_body_ {
  position: fixed;
  margin: 1rem 0 0 0;
  width: 100%;
}
#list-received-ticket-table > .table-responsive {
  max-height: 50vh;
  /deep/ thead tr {
    background: white;
    position: -webkit-sticky; /* for Safari */
    position: sticky;
    top: 0;
    z-index: 1;
  }
}
</style>
