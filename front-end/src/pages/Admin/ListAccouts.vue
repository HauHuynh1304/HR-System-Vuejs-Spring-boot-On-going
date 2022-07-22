<template>
  <div class="card ">
    <card card-body-classes="table-full-width">
      <h4 slot="header" class="title">
        {{ routerProps.ADMIN.CHILDREN.ACCOUNT_MANAGEMENT.NAME.toUpperCase() }}
      </h4>
      <card>
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
        <div class="row">
          <div class="table-responsive">
            <b-table
              hover
              :items="items"
              :fields="fields"
              :select-mode="selectMode"
              selectable
              @row-selected="onRowSelected"
              :filter="filter"
              :current-page="currentPage"
              :per-page="perPage"
            >
              <template #cell(roles)="data">
                <span v-html="data.value"></span>
              </template>
            </b-table>
          </div>
        </div>
      </card>
    </card>
    <accountVue />
  </div>
</template>

<script>
import { findAccounts } from "../../api/authen";
import accountVue from "./account.vue";
import { EVENT_BUS, DATE_FORMAT } from "../../constant/common";
import { FE_ROUTER_PROP } from "@/constant/routerProps";
import Card from "../../components/Cards/Card.vue";
import moment from "moment";

export default {
  components: {
    accountVue,
    Card,
  },
  data() {
    return {
      routerProps: FE_ROUTER_PROP,
      backgroundColor: "primary",
      pageOptions: [5, 10, 15, 20],
      totalRows: null,
      currentPage: 1,
      perPage: 10,
      selectMode: "single",
      filter: null,
      items: null,
      fields: [
        {
          key: "systemEmail",
          label: "EMAIL",
          thClass: "text-center text-info",
          tdClass: "text-center",
        },
        {
          key: "createdAt",
          label: "CREATED AT",
          thClass: "text-center text-info",
          tdClass: "text-center",
          formatter: (value) => {
            return moment(value).format(DATE_FORMAT);
          },
        },
        {
          key: "roles",
          label: "ROLES",
          thClass: "text-center text-info",
          formatter: (value) => {
            let role = value.map((item) => item.roleName + "<br/>").toString();
            return role.replaceAll(",", "");
          },
        },
        {
          key: "deletedFlag",
          label: "ACTIVE",
          thClass: "text-center text-danger",
          tdClass: "text-center",
          formatter: (value) => {
            return value ? "NO" : "YES";
          },
          filterByFormatted: true,
        },
      ],
    };
  },
  created() {
    this.getTabledata();
    this.$bus.on(EVENT_BUS.REFRESH_TABLE_LIST_USER, () => {
      this.getTabledata();
    });
  },
  methods: {
    onRowSelected(item) {
      this.$bus.emit(EVENT_BUS.EDIT_ACCOUNT, item);
    },
    getTabledata() {
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      findAccounts().then((res) => {
        this.items = res?.data;
        this.totalRows = this.items?.length;
        this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
      });
    },
  },
};
</script>

<style scoped>
#per-page-select {
  color: black;
}

#pagination /deep/ .page-link {
  color: black;
  font-size: 0.75rem;
}
</style>
