<template>
  <div class="card ">
    <card card-body-classes="table-full-width">
      <h4 slot="header" class="title">
        {{ routerProps.HUMAN_MANAGEMENT.CHILDREN.EMPLOYEES.NAME.toUpperCase() }}
      </h4>
      <Search-list-user />
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
              :filter="filter"
              :current-page="currentPage"
              :per-page="perPage"
            >
              <template #cell(systemEmail)="row">
                <b-link
                  @click="openUpdateEmployeeModal(row.item.employee.employeeId)"
                >
                  {{ row.item.systemEmail }}
                </b-link>
              </template>
              <template #cell(employee.deletedFlag)="row">
                <p
                  :class="
                    row.item.employee.deletedFlag ? 'text-danger' : 'text-info'
                  "
                >
                  {{ row.item.employee.deletedFlag ? "resign" : "working" }}
                </p>
              </template>
            </b-table>
          </div>
        </div>
      </card>
    </card>
    <b-modal
      size="xl"
      :ref="modal.updateEmployee.id"
      :id="modal.updateEmployee.id"
      hide-footer
      hide-header
    >
      <update-user-page :employeeId="this.modal.updateEmployee.idValue" />
    </b-modal>
  </div>
</template>

<script>
import { EVENT_BUS } from "@/constant/common";
import { FE_ROUTER_PROP } from "@/constant/routerProps";
import { TABLE_LIST_EMPLOYEE } from "@/constant/tableListEmployee";
import SearchListUser from "./SearchListUser.vue";
import Card from "@/components/Cards/Card.vue";
import { URL_IMG } from "@/utils/request";
import { API } from "@/constant/api";
import UpdateUserPage from "./UpdateUserPage.vue";

export default {
  components: { SearchListUser, Card, UpdateUserPage },
  data() {
    return {
      URL_IMG: URL_IMG,
      API: API,
      routerProps: FE_ROUTER_PROP,
      pageOptions: [5, 10, 15, 20],
      totalRows: null,
      currentPage: 1,
      perPage: 10,
      filter: null,
      items: null,
      fields: TABLE_LIST_EMPLOYEE.fields,
      modal: {
        updateEmployee: {
          id: "update-employee",
          idValue: null,
        },
      },
    };
  },
  created() {
    this.$bus.on(EVENT_BUS.FETCH_DATA_LIST_EMPLOYEE, (data) => {
      this.items = data ? data : [];
      this.totalRows = this.items.length;
      this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
    });
  },
  methods: {
    openUpdateEmployeeModal(employeeId) {
      this.modal.updateEmployee.idValue = employeeId;
      this.$refs[this.modal.updateEmployee.id].show();
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
/deep/ #update-employee___BV_modal_body_ {
  position: fixed;
}
</style>
