<template>
  <div class="card">
    <card card-body-classes="table-full-width">
      <h4 slot="header" class="title">
        {{
          routerProps.HUMAN_MANAGEMENT.CHILDREN.ADD_EMPLOYEE.NAME.toUpperCase()
        }}
      </h4>
      <div class="row">
        <div class="col-md-3">
          <card type="user" align="center">
            <img
              class="avatar"
              :src="imageObj.imageUrl ? imageObj.imageUrl : defaultImg"
            />
            <input
              style="display: none"
              id="file"
              ref="fileInput"
              type="file"
              @change="fileSelected"
            />
            <br />
            <base-button
              class="btn btn-info"
              @click="$refs.fileInput.click()"
              size="sm"
              >Upload image
            </base-button>
            <br />
            <base-button
              @click="submitForm"
              class="btn btn-success"
              method="post"
              size="sm"
              >ADD
            </base-button>
            <base-button
              @click="resetForm"
              class="btn btn-warning"
              method="post"
              size="sm"
              >RESET
            </base-button>
          </card>
        </div>
        <div class="col-md-9">
          <div class="row">
            <div class="col-md-3 p-auto">
              <base-input
                v-model.trim="newEmployeeObj.employee.employeeProfileId"
                id="ProfileId"
                label="Profile ID"
                placeholder="Profile ID"
              />
            </div>
            <div class="col-md-3 p-auto">
              <base-input
                v-model.trim="newEmployeeObj.personalInfo.personalName"
                id="FullName"
                label="Full Name"
                placeholder="Full Name"
              />
            </div>
            <div class="col-md-3 p-auto">
              <base-input
                v-model.trim="newEmployeeObj.personalInfo.personalIdCard"
                id="IdCard"
                label="ID Card"
                placeholder="ID Card"
              />
            </div>
            <div class="col-md-3 p-auto">
              <base-input
                v-model.trim="newEmployeeObj.personalInfo.personalAddress"
                id="Address"
                label="Address"
                placeholder="Address"
              />
            </div>
          </div>
          <div class="row">
            <div class="col-md-3 p-auto">
              <base-input
                v-model.trim="newEmployeeObj.personalInfo.personalBirthday"
                id="Birthdate"
                label="Birth date"
                placeholder="Birth date"
                type="date"
              />
            </div>
            <div class="col-md-3 p-auto">
              <h5 class="mb-2">Sex</h5>
              <b-form-select
                v-model.trim="newEmployeeObj.personalInfo.personalSex"
                :options="sexSelect"
                class="form-control"
              />
            </div>
            <div class="col-md-3 p-auto">
              <base-input
                v-model.trim="newEmployeeObj.personalInfo.personalEmail"
                id="PersonalEmail"
                label="Personal Email"
                placeholder="Personal Email"
              />
            </div>

            <div class="col-md-3 p-auto">
              <base-input
                v-model.trim="newEmployeeObj.personalInfo.personalPhoneNumber"
                id="Phone"
                label="Phone"
                placeholder="Phone"
              />
            </div>
          </div>
          <div class="row">
            <div class="col-md-3 p-auto">
              <label class="control-label">Company email</label>
              <b-form-select
                v-model.trim="newEmployeeObj.employee.systemAccountId"
                :options="originAccounts"
                value-field="systemAccountId"
                text-field="systemEmail"
                class="form-control"
              />
            </div>
            <div class="col-md-3 p-auto">
              <label class="control-label">Room</label>
              <b-form-select
                v-model.trim="newEmployeeObj.employee.roomId"
                :options="originRooms"
                value-field="roomId"
                text-field="roomName"
                class="form-control"
              />
            </div>
            <div class="col-md-3 p-auto">
              <base-input
                v-model.trim="newEmployeeObj.employee.employeeStartDate"
                id="StartDate"
                label="Start Date"
                placeholder="Start Date"
                type="date"
              />
            </div>
          </div>
          <div class="row">
            <div class="col-md-6 p-auto">
              <b-form-group
                label="Submited Documents"
                id="tags-component-select"
                label-for="tags-component-select"
                ref="tags-component-select"
              >
                <!-- Prop `add-on-change` is needed to enable adding tags vie the `change` event -->
                <b-form-tags
                  id="tags-component-select"
                  v-model.trim="tagDocumentValue"
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
                      <li
                        v-for="tag in tags"
                        :key="tag"
                        class="list-inline-item"
                      >
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
                      :disabled="
                        disabled || availableDocumentOptions.length === 0
                      "
                      :options="availableDocumentOptions"
                    >
                      <template #first>
                        <!-- This is required to prevent bugs with Safari -->
                        <option disabled value="">Documents...</option>
                      </template>
                    </b-form-select>
                  </template>
                </b-form-tags>
              </b-form-group>
            </div>
            <div class="col-md-6 p-auto">
              <b-form-group
                label="Positions"
                id="tags-component-select"
                label-for="tags-component-select"
                ref="tags-component-select"
              >
                <!-- Prop `add-on-change` is needed to enable adding tags vie the `change` event -->
                <b-form-tags
                  id="tags-component-select"
                  v-model.trim="tagPositionValue"
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
                      <li
                        v-for="tag in tags"
                        :key="tag"
                        class="list-inline-item"
                      >
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
                      :disabled="
                        disabled || availablePositionOptions.length === 0
                      "
                      :options="availablePositionOptions"
                    >
                      <template #first>
                        <!-- This is required to prevent bugs with Safari -->
                        <option disabled value="">Positions...</option>
                      </template>
                    </b-form-select>
                  </template>
                </b-form-tags>
              </b-form-group>
            </div>
          </div>
        </div>
      </div>
    </card>
  </div>
</template>

<script>
import Card from "@/components/Cards/Card.vue";
import { FE_ROUTER_PROP } from "@/constant/routerProps";
import { NEW_EMPLOYEE_OBJECT } from "@/constant/employeeObj";
import { findAvailbleAccounts } from "@/api/master";
import {
  findDocuments,
  findPositions,
  findRooms,
  insertEmployee,
} from "@/api/humanResources";
import { resetObject, isContainNullValue } from "@/utils/objectUtil";
import { MESSAGE } from "@/constant/message";
import { SEX_SELECT } from "@/constant/common";
import { isValidEmail, isValidNumber } from "@/utils/validate";
import { createImageFromUrl } from "@/utils/fileUtil";
export default {
  components: { Card },
  data() {
    return {
      sexSelect: SEX_SELECT,
      newEmployeeObj: NEW_EMPLOYEE_OBJECT,
      // Value when call init API start
      originDocuments: null,
      originPositions: null,
      originAccounts: null,
      originRooms: null,
      // Value when call init API end
      // Props for selecting tags start
      originDocumentOptions: [],
      tagDocumentValue: [],
      originPositionOptions: [],
      tagPositionValue: [],
      // Props for selecting tags end
      routerProps: FE_ROUTER_PROP,
      defaultImg: require("@/assets/image/1024px-User-avatar.png"),
      imageObj: {
        imageUrl: null,
        file: null,
      },
    };
  },
  async created() {
    await findAvailbleAccounts().then(
      (res) => (this.originAccounts = res?.data)
    );
    await findRooms().then((res) => (this.originRooms = res?.data));
    await findPositions().then((res) => {
      this.originPositions = res.data;
      this.originPositionOptions = res.data.map((el) => el.positionName);
    });
    await findDocuments().then((res) => {
      this.originDocuments = res.data;
      this.originDocumentOptions = res.data.map((el) => el.documentName);
    });
  },
  methods: {
    fileSelected(e) {
      const file = e.target.files[0];
      if (file) {
        this.imageObj.file = file;
        this.imageObj.imageUrl = URL.createObjectURL(file);
      }
    },
    resetForm() {
      resetObject(this.newEmployeeObj.employee);
      resetObject(this.newEmployeeObj.personalInfo);
      resetObject(this.imageObj);
      this.newEmployeeObj.documents = [];
      this.newEmployeeObj.positions = [];
      this.tagDocumentValue = [];
      this.tagPositionValue = [];
      document.getElementById("file").value = null;
    },
    async submitForm() {
      this.getDocumentIds();
      this.getPositionIds();
      if (
        isContainNullValue(this.newEmployeeObj.employee) ||
        isContainNullValue(this.newEmployeeObj.personalInfo) ||
        !this.newEmployeeObj.positions.length
      ) {
        this.$notify({
          type: "warning",
          message: MESSAGE.INSERT_EMPLOYEE.ERR_EMPTY,
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        return;
      }
      if (
        !isValidEmail(this.newEmployeeObj.personalInfo.personalEmail) ||
        !isValidNumber(this.newEmployeeObj.personalInfo.personalPhoneNumber)
      ) {
        this.$notify({
          type: "warning",
          message: MESSAGE.INSERT_EMPLOYEE.ERR_FORMAT,
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        return;
      }
      if (this.imageObj.file === null) {
        this.imageObj.file = await createImageFromUrl(this.defaultImg);
      }
      let formData = new FormData();
      formData.append(
        "formEmployee",
        new Blob([JSON.stringify(this.newEmployeeObj)]),
        {
          type: "application/json",
        }
      );
      formData.append("image", this.imageObj.file);
      insertEmployee(formData)
        .then((res) => {
          this.$notify({
            type: "success",
            message: res.message,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
          findAvailbleAccounts().then(
            (res) => (this.originAccounts = res?.data)
          );
          this.resetForm();
        })
        .catch(() => {
          this.$notify({
            type: "warning",
            message: MESSAGE.CALL_API_ERR.ERR,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
        });
    },
    getDocumentIds() {
      // refresh before submit form
      this.newEmployeeObj.documents = [];
      this.originDocuments.forEach((el) => {
        this.tagDocumentValue.indexOf(el.documentName) !== -1
          ? this.newEmployeeObj.documents.push({
              documentId: el.documentId,
            })
          : null;
      });
    },
    getPositionIds() {
      // refresh before submit form
      this.newEmployeeObj.positions = [];
      this.originPositions.forEach((el) => {
        this.tagPositionValue.indexOf(el.positionName) !== -1
          ? this.newEmployeeObj.positions.push({
              positionId: el.positionId,
              startDate: this.newEmployeeObj.employee.employeeStartDate,
            })
          : null;
      });
    },
  },
  computed: {
    availableDocumentOptions() {
      return this.originDocumentOptions.filter(
        (opt) => this.tagDocumentValue.indexOf(opt) === -1
      );
    },
    availablePositionOptions() {
      return this.originPositionOptions.filter(
        (opt) => this.tagPositionValue.indexOf(opt) === -1
      );
    },
  },
};
</script>

<style scoped>
#tags-component-select___input__ {
  color: black;
}
.list-inline-item {
  margin-bottom: 5px;
}
</style>
