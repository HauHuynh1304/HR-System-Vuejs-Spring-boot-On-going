<template>
  <div class="card">
    <card card-body-classes="table-full-width">
      <h4 slot="header" class="title">
        {{ routerProps.HUMAN_MANAGEMENT.CHILDREN.REPORT.NAME.toUpperCase() }}
      </h4>
      <search-report-component @[EVENT_BUS.CLICK_DOWNLOAD]="onDownload" />
      <div id="report-table" v-if="reportData">
        <b-table-simple responsive hover small>
          <b-thead>
            <b-tr>
              <b-th>No.</b-th>
              <b-th>Requester</b-th>
              <b-th>Request Type</b-th>
              <b-th>Reason</b-th>
              <b-th>Approver</b-th>
              <b-th>Start Date</b-th>
              <b-th>End Date</b-th>
              <b-th>Duration</b-th>
              <b-th>Total</b-th>
            </b-tr>
          </b-thead>
          <b-tbody v-if="reportData">
            <b-tr v-for="(rootItem, rootIndex) in reportData" :key="rootIndex">
              <b-td>
                {{ rootIndex + 1 }}
              </b-td>
              <b-td>
                {{ rootItem.requester }}
              </b-td>
              <b-td>
                <b-tr v-for="(item, index) in rootItem.reportInfo" :key="index">
                  {{ item.requestType }}
                </b-tr>
              </b-td>
              <b-td>
                <b-tr v-for="(item, index) in rootItem.reportInfo" :key="index">
                  {{ item.reason }}
                </b-tr>
              </b-td>
              <b-td>
                <b-tr v-for="(item, index) in rootItem.reportInfo" :key="index">
                  {{ item.approver }}
                </b-tr>
              </b-td>
              <b-td>
                <b-tr v-for="(item, index) in rootItem.reportInfo" :key="index">
                  {{ item.startDate }}
                </b-tr>
              </b-td>
              <b-td>
                <b-tr v-for="(item, index) in rootItem.reportInfo" :key="index">
                  {{ item.endDate }}
                </b-tr>
              </b-td>
              <b-td>
                <div id="duration-report">
                  <b-tr
                    v-for="(item, index) in rootItem.reportInfo"
                    :key="index"
                    id="duration-report"
                  >
                    {{ item.duration }}
                  </b-tr>
                </div>
              </b-td>
              <b-td>
                <span class="font-weight-bold text-warning">
                  {{ totalDuration(rootItem.reportInfo) }}
                </span>
              </b-td>
            </b-tr>
          </b-tbody>
        </b-table-simple>
      </div>
    </card>
  </div>
</template>

<script>
import Card from "@/components/Cards/Card.vue";
import { FE_ROUTER_PROP } from "@/constant/routerProps";
import SearchReportComponent from "./components/SearchReportComponent.vue";
import { EVENT_BUS, REPORT_TYPE } from "@/constant/common";
import * as XLSX from "xlsx/xlsx.mjs";

export default {
  components: { Card, SearchReportComponent },
  data() {
    return {
      routerProps: FE_ROUTER_PROP,
      pageOptions: [5, 10, 15, 20],
      totalRows: null,
      currentPage: 1,
      perPage: 10,
      filter: null,
      reportData: null,
      EVENT_BUS: EVENT_BUS,
    };
  },
  created() {
    this.$bus.on(EVENT_BUS.FIND_REPORT_INFO, (data) => {
      this.reportData = data;
    });
  },
  methods: {
    totalDuration(array) {
      return array.reduce((acc, item) => {
        return acc + item.duration;
      }, 0);
    },
    onDownload() {
      const workbook = XLSX.utils.book_new();
      this.reportData.forEach((el) => {
        const worksheet = XLSX.utils.json_to_sheet(el.reportInfo);
        XLSX.utils.book_append_sheet(workbook, worksheet, el.requester);
      });
      XLSX.writeFile(workbook, REPORT_TYPE.GENERAL);
    },
  },
};
</script>

<style lang="scss" scoped>
#duration-report {
  width: 100%;
}
#report-table > .table-responsive {
  max-height: calc(100vh - 350px);
  thead th {
    background: white;
    position: -webkit-sticky; /* for Safari */
    position: sticky;
    top: 0;
    z-index: 1;
  }
}
</style>
