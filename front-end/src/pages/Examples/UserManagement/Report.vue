<template>
  <div>
    <card>
      <h4 slot="header" class="title">
        {{ routerProps.HUMAN_MANAGEMENT.CHILDREN.REPORT.NAME.toUpperCase() }}
      </h4>
      <search-report-component @[EVENT_BUS.CLICK_DOWNLOAD]="onDownload" />
    </card>
    <card v-if="isShowTable">
      <div class="d-flex justify-content-between">
        <div>
          <b-button variant="success" size="sm" @click="leaveReportArea">
            Leave Report
          </b-button>
          <b-button variant="success" size="sm" @click="otReportArea">
            OT Report
          </b-button>
        </div>
        <div>
          <b-button
            native-type="submit"
            variant="primary"
            size="sm"
            @click="onDownload"
          >
            Download
          </b-button>
        </div>
      </div>
      <br />
      <div id="report-table">
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
          <b-tbody>
            <b-tr
              v-for="(rootItem, rootIndex) in isLeaveReportArea
                ? leaveReportData
                : otReportData"
              :key="rootIndex"
            >
              <b-td>
                {{ rootIndex + 1 }}
              </b-td>
              <b-td>
                {{
                  rootItem.requester.substring(
                    0,
                    rootItem.requester.indexOf("@")
                  )
                }}
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
                  {{ item.approver.substring(0, item.approver.indexOf("@")) }}
                </b-tr>
              </b-td>
              <b-td>
                <b-tr v-for="(item, index) in rootItem.reportInfo" :key="index">
                  {{
                    isLeaveReportArea
                      ? item.startDate.substring(0, 10)
                      : item.startDate
                  }}
                </b-tr>
              </b-td>
              <b-td>
                <b-tr v-for="(item, index) in rootItem.reportInfo" :key="index">
                  {{
                    isLeaveReportArea
                      ? item.endDate.substring(0, 10)
                      : item.endDate
                  }}
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
import { EVENT_BUS, REPORT_TYPE, SPECIAL_VALUE } from "@/constant/common";
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
      isShowTable: false,
      EVENT_BUS: EVENT_BUS,
      isLeaveReportArea: true,
      isOTReportArea: false,
      leaveReportData: [],
      otReportData: [],
      countSearch: 0,
    };
  },
  created() {
    this.$bus.on(EVENT_BUS.FIND_REPORT_INFO, (data) => {
      this.isShowTable = true;
      this.leaveReportData = [];
      this.otReportData = [];
      data.forEach((el) => {
        let leaveObj = {
          requester: el.requester,
          reportInfo: el.reportInfo.filter(
            (item) => item.requestType !== SPECIAL_VALUE.OVER_TIME
          ),
        };
        if (leaveObj.reportInfo.length) {
          this.leaveReportData.push(leaveObj);
        }

        let otObj = {
          requester: el.requester,
          reportInfo: el.reportInfo.filter(
            (item) => item.requestType === SPECIAL_VALUE.OVER_TIME
          ),
        };
        if (otObj.reportInfo.length) {
          this.otReportData.push(otObj);
        }
      });
      this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
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
      let exportData = this.isLeaveReportArea
        ? this.leaveReportData
        : this.otReportData;
      if(!exportData.length) return;  
      exportData.forEach((el) => {
        const worksheet = XLSX.utils.json_to_sheet(el.reportInfo);
        XLSX.utils.book_append_sheet(workbook, worksheet, el.requester);
      });
      XLSX.writeFile(
        workbook,
        this.isLeaveReportArea
          ? REPORT_TYPE.LEAVE_REPORT
          : REPORT_TYPE.OT_REPORT
      );
    },
    leaveReportArea() {
      this.isLeaveReportArea = true;
      this.isOTReportArea = false;
      this.leaveReportData.length
        ? (this.isEnableDownload = true)
        : (this.isEnableDownload = false);
    },
    otReportArea() {
      this.isLeaveReportArea = false;
      this.isOTReportArea = true;
      this.otReportData.length
        ? (this.isEnableDownload = true)
        : (this.isEnableDownload = false);
    },
  },
};
</script>

<style lang="scss" scoped>
#duration-report {
  width: 100%;
}
#report-table > .table-responsive {
  max-height: 50vh;
  thead th {
    background: white;
    position: -webkit-sticky; /* for Safari */
    position: sticky;
    top: 0;
    z-index: 1;
  }
}
</style>
