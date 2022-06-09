<template>
  <div class="card ">
    <card card-body-classes="table-full-width">
      <h4 slot="header" class="title">Users List</h4>
      <b-form-input
        id="filter-input"
        v-model="filter"
        type="search"
        placeholder="Type to Search"
      />
      <div class="row">
        <b-col sm="7" md="6" class="my-3">
          <b-form-group
            label="Per page"
            label-for="per-page-select"
            label-cols-sm="6"
            label-cols-md="4"
            label-cols-lg="3"
            label-align-sm="right"
            label-size="sm"
          >
            <b-form-select
              id="per-page-select"
              v-model="perPage"
              :options="pageOptions"
              size="sm"
            ></b-form-select>
          </b-form-group>
        </b-col>
        <b-col sm="7" md="6" class="my-3">
          <b-pagination
            id="pagination"
            v-model="currentPage"
            :total-rows="totalRows"
            :per-page="perPage"
            align="center"
            size="sm"
          />
        </b-col>
      </div>
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
    </card>
    <accountVue />
  </div>
</template>

<script>
import { findAccounts } from "../../../api/authen";
import accountVue from "../../../components/Account/account.vue";
import { EVENT_BUS } from "../../../constant/common";
export default {
  components: {
    accountVue,
  },
  data() {
    return {
      backgroundColor: "primary",
      pageOptions: [5, 10, 15, 20],
      totalRows: 1,
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
      findAccounts().then((res) => {
        this.items = res?.data;
        this.totalRows = this.items?.length;
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
