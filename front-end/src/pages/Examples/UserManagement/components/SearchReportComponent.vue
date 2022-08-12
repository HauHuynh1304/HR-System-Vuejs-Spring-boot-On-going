<template>
  <div>
    <b-form ref="searchForm" id="searchForm" @submit.prevent="onSubmit">
      <div class="row">
        <div class="col-md-6 p-auto">
          <div class="row">
            <div class="col-md-6 p-auto">
              <label class="control-label">Request Type</label>
              <b-form-select
                class="form-control"
                value-field="requestTypeId"
                text-field="requestTypeName"
                :options="requestTypes"
                v-model="submitObj.requestTypeId"
              />
            </div>
          </div>
          <div class="row">
            <div class="col-md-6 p-auto">
              <base-input
                id="startDate"
                label="From"
                type="date"
                v-model="timeStampObj.startDate"
              />
            </div>
            <div class="col-md-6 p-auto">
              <base-input
                id="endDate"
                label="To"
                type="date"
                v-model="timeStampObj.endDate"
              />
            </div>
          </div>
        </div>
        <div class="col-md-6 p-auto">
          <b-form-group
            label="Requester"
            id="tags-component-select"
            label-for="tags-component-select"
            ref="tags-component-select"
          >
            <!-- Prop `add-on-change` is needed to enable adding tags vie the `change` event -->
            <b-form-tags
              id="tags-component-select"
              v-model.trim="tag.tagEmail"
              add-on-change
              no-outer-focus
            >
              <template
                v-slot="{
                  tags,
                  inputAttrs,
                  inputHandlers,
                  disabled,
                  removeTag,
                }"
              >
                <ul
                  v-if="tags.length > 0"
                  class="list-inline d-inline-block mb-2"
                >
                  <li v-for="tag in tags" :key="tag" class="list-inline-item">
                    <b-form-tag
                      @remove="removeTag(tag)"
                      :title="tag"
                      :disabled="disabled"
                      variant="info"
                      >{{ tag }}</b-form-tag
                    >
                  </li>
                </ul>
                <b-form-select
                  v-bind="inputAttrs"
                  v-on="inputHandlers"
                  :disabled="disabled || availableEmailOptions.length === 0"
                  :options="availableEmailOptions"
                >
                  <template #first>
                    <!-- This is required to prevent bugs with Safari -->
                    <option disabled value="">Emails...</option>
                  </template>
                </b-form-select>
              </template>
            </b-form-tags>
          </b-form-group>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="text-right pt-3">
            <base-button
              native-type="submit"
              type="info"
              size="sm"
              @click="onSearch"
            >
              SEARCH
            </base-button>
            <base-button
              id="onResetButton"
              type="warning"
              size="sm"
              @click="onReset"
            >
              RESET
            </base-button>
          </div>
        </div>
      </div>
    </b-form>
  </div>
</template>

<script>
import Card from "@/components/Cards/Card.vue";
import { findRequestType } from "@/api/business";
import { findReportCaseSelected } from "@/api/humanResources";
import { findAllAccounts } from "@/api/master";
import { SEARCH_REQUESTED_TICKET } from "@/constant/requestTicket";
import { resetObject } from "@/utils/objectUtil";
import { EVENT_BUS, TIME_STAMP } from "@/constant/common";
import { MESSAGE } from "@/constant/message";

export default {
  components: { Card },
  data() {
    return {
      tag: {
        orginEmailOptions: [],
        tagEmail: [],
      },
      initListAccounts: null,
      requestTypes: null,
      submitObj: SEARCH_REQUESTED_TICKET,
      timeStampObj: {
        startDate: null,
        endDate: null,
      },
    };
  },
  async created() {
    this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
    await findAllAccounts().then((res) => {
      if (res.status === 200) {
        this.initListAccounts = res.data;
        this.tag.orginEmailOptions = res.data.map((el) => el.systemEmail);
      }
    });
    await findRequestType().then((res) => (this.requestTypes = res.data));
    this.onReset();
    this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
  },
  methods: {
    onSubmit() {
      this.onSearch;
    },
    onSearch() {
      let startDate = null;
      let endDate = null;
      if (this.timeStampObj.startDate) {
        startDate = this.timeStampObj.startDate.concat(TIME_STAMP.BEGIN);
      }
      if (this.timeStampObj.endDate) {
        endDate = this.timeStampObj.endDate.concat(TIME_STAMP.END);
      }
      this.submitObj.requestEmployee.startDate = startDate;
      this.submitObj.requestEmployee.endDate = endDate;
      this.getSystemAccountIds();
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      findReportCaseSelected(this.submitObj)
        .then((res) => {
          this.$bus.emit(EVENT_BUS.FIND_REPORT_INFO, res.data);
        })
        .catch((err) => {
          this.$notify({
            type: "warning",
            message: MESSAGE.CALL_API_ERR.ERR,
            horizontalAlign: "center",
          });
          this.onReset();
          this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
        });
    },
    onReset() {
      this.submitObj.systemAccountIds = [];
      this.tag.tagEmail = [];
      this.submitObj.requestTypeId = null;
      resetObject(this.timeStampObj);
      resetObject(this.submitObj.requestEmployee);
    },
    getSystemAccountIds() {
      this.submitObj.systemAccountIds = [];
      this.initListAccounts.forEach((el) => {
        this.tag.tagEmail.indexOf(el.systemEmail) !== -1
          ? this.submitObj.systemAccountIds.push(el.systemAccountId)
          : null;
      });
    },
    onDownload() {
      this.$emit(EVENT_BUS.CLICK_DOWNLOAD);
    },
  },
  computed: {
    availableEmailOptions() {
      return this.tag.orginEmailOptions.filter(
        (opt) => this.tag.tagEmail.indexOf(opt) === -1
      );
    },
  },
};
</script>

<style lang="scss" scoped>
#tags-component-select___input__ {
  color: black;
}
.list-inline-item {
  margin-bottom: 5px;
}
</style>
