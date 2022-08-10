<template>
  <div>
    <card>
      <h4 slot="header" class="title">
        {{
          routerProps.REQUEST_TICKET.CHILDREN.LIST_REQUESTED_TICKET.NAME.toUpperCase()
        }}
      </h4>
      <search-requested-ticket-component />
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
        <div class="col-md-4 p-auto">
          <b-form-input
            id="filter-input"
            v-model="filter"
            type="search"
            size="sm"
            placeholder="Filter"
          />
        </div>
        <div class="col-md-6 p-auto">
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
      <div id="list-my-ticket-table">
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
            <b-link @click="openTicketDetailModal(row.item.requestId)">
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
      :ref="modal.requestDetail.id"
      :id="modal.requestDetail.id"
      hide-footer
      hide-header
    >
      <requested-ticket
        :requestId="modal.requestDetail.requestId"
        :isRequesterArea="true"
      />
    </b-modal>
  </div>
</template>

<script>
import SearchRequestedTicketComponent from "./SearchRequestedTicketComponent.vue";
import Card from "../../../components/Cards/Card.vue";
import { FE_ROUTER_PROP } from "@/constant/routerProps";
import {
  EVENT_BUS,
  SPECIAL_VALUE,
  DATE_FORMAT,
  DATE_TIME_FORMAT,
} from "@/constant/common";
import { REQUESTED_TIKCET_TABLE } from "@/constant/requestedTicketTable";
import { TICKET_STATUS } from "@/constant/requestTicket";
import RequestedTicket from "./RequestedTicket.vue";
import moment from "moment";
export default {
  components: { SearchRequestedTicketComponent, Card, RequestedTicket },
  data() {
    return {
      ticketStatus: TICKET_STATUS,
      routerProps: FE_ROUTER_PROP,
      pageOptions: [5, 10, 15, 20],
      totalRows: null,
      currentPage: 1,
      perPage: 10,
      filter: null,
      items: null,
      fields: REQUESTED_TIKCET_TABLE.fields,
      modal: {
        requestDetail: {
          id: "request-detail-modal",
          requestId: null,
        },
      },
      SPECIAL_VALUE: SPECIAL_VALUE,
      DATE_FORMAT: DATE_FORMAT,
      DATE_TIME_FORMAT: DATE_TIME_FORMAT,
      moment: moment,
    };
  },
  created() {
    this.$bus.on(EVENT_BUS.FIND_REQUESTED_TICKET, (data) => {
      this.items = data ? data : [];
      this.totalRows = this.items.length;
      this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
    });
    this.$bus.on(EVENT_BUS.REFRESH_SPECIAL_REQUESTED_TICKET, (data) => {
      for (let i in this.items) {
        if (this.items[i].requestId == data.requestId) {
          this.items[i].requestEmployee.requestStatus = data.requestStatus;
          return;
        }
      }
    });
  },
  methods: {
    openTicketDetailModal(requestId) {
      this.modal.requestDetail.requestId = parseInt(requestId);
      this.$refs[this.modal.requestDetail.id].show();
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
/deep/ #request-detail-modal___BV_modal_body_ {
  position: fixed;
  width: 100%;
}
#list-my-ticket-table > .table-responsive {
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
