<template>
  <div class="card">
    <card card-body-classes="table-full-width">
      <h4 slot="header" class="title">
        {{ routerProps.HUMAN_MANAGEMENT.CHILDREN.REPORT.NAME.toUpperCase() }}
      </h4>
      <search-report-component />
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
      </card>
    </card>
  </div>
</template>

<script>
import Card from "@/components/Cards/Card.vue";
import { FE_ROUTER_PROP } from "@/constant/routerProps";
import SearchReportComponent from "./components/SearchReportComponent.vue";

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
    };
  },
};
</script>

<style></style>
