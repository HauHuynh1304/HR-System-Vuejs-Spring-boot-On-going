<template>
  <div>
    <div>
      <card card-body-classes="table-full-width">
        <h4 slot="header" class="title">
          {{
            routerProps.HUMAN_MANAGEMENT.CHILDREN.UPDATE_EMPLOYEE.NAME.toUpperCase()
          }}
        </h4>
        <div class="row">
          <div class="col-md-3">
            <card type="user" align="center">
              <img
                class="avatar"
                :src="imageObj.imageUrl ? imageObj.imageUrl : defaultImg"
              />
              <br />
              {{ employeeObj.systemEmail }}
              <br />
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
                >Update
              </base-button>
              <base-button
                @click="resetForm"
                class="btn btn-warning"
                method="post"
                size="sm"
                >Refresh
              </base-button>
            </card>
          </div>
          <div class="col-md-9">
            <div class="row">
              <div class="col-md-3 p-auto">
                <base-input
                  v-model.trim="employeeObj.employee.employeeProfileId"
                  id="ProfileId"
                  label="Profile ID"
                  placeholder="Profile ID"
                />
              </div>
              <div class="col-md-3 p-auto">
                <base-input
                  v-model.trim="employeeObj.personalInfo.personalName"
                  id="FullName"
                  label="Full Name"
                  placeholder="Full Name"
                />
              </div>
              <div class="col-md-3 p-auto">
                <base-input
                  v-model.trim="employeeObj.personalInfo.personalIdCard"
                  id="IdCard"
                  label="ID Card"
                  placeholder="ID Card"
                />
              </div>
              <div class="col-md-3 p-auto">
                <base-input
                  v-model.trim="employeeObj.personalInfo.personalAddress"
                  id="Address"
                  label="Address"
                  placeholder="Address"
                />
              </div>
            </div>
            <div class="row">
              <div class="col-md-3 p-auto">
                <base-input
                  v-model.trim="employeeObj.personalInfo.personalBirthday"
                  id="Birthdate"
                  label="Birth date"
                  placeholder="Birth date"
                  type="date"
                />
              </div>
              <div class="col-md-3 p-auto">
                <h5 class="mb-2">Sex</h5>
                <b-form-select
                  v-model.trim="employeeObj.personalInfo.personalSex"
                  :options="sexSelect"
                  class="form-control"
                />
              </div>
              <div class="col-md-3 p-auto">
                <base-input
                  v-model.trim="employeeObj.personalInfo.personalEmail"
                  id="PersonalEmail"
                  label="Personal Email"
                  placeholder="Personal Email"
                />
              </div>

              <div class="col-md-3 p-auto">
                <base-input
                  v-model.trim="employeeObj.personalInfo.personalPhoneNumber"
                  id="Phone"
                  label="Phone"
                  placeholder="Phone"
                />
              </div>
            </div>
            <div class="row">
              <div class="col-md-3 p-auto">
                <label class="control-label">Working Status</label>
                <b-form-select
                  v-model.trim="employeeObj.employee.deletedFlag"
                  :options="workingStatus"
                  class="form-control"
                />
              </div>
              <div class="col-md-3 p-auto">
                <label class="control-label">Room</label>
                <b-form-select
                  v-model.trim="employeeObj.room.roomId"
                  :options="initRooms"
                  value-field="roomId"
                  text-field="roomName"
                  class="form-control"
                />
              </div>
              <div class="col-md-3 p-auto">
                <base-input
                  v-model.trim="employeeObj.employee.employeeStartDate"
                  id="StartDate"
                  label="Start Date"
                  type="date"
                />
              </div>
              <div class="col-md-3 p-auto">
                <base-input
                  v-model.trim="employeeObj.employee.employeeEndDate"
                  id="EndDate"
                  label="End Date"
                  type="date"
                />
              </div>
            </div>
          </div>
        </div>
      </card>
    </div>
    <div>
      <div class="row" style="height: 100px;">
        <div class="col-md-4">
          <card>
            <document-component :documentObj="employeeObj.documents" />
          </card>
        </div>
        <div class="col-md-8">
          <card>
            <position-component :positionObj="employeeObj.positions" />
          </card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Card from "../../../components/Cards/Card.vue";
import { FE_ROUTER_PROP } from "@/constant/routerProps";
import { UPDATE_EMPLOYEE_OBJECT } from "@/constant/employeeObj";
import { findAllRooms } from "../../../api/master";
import { isAllNullValue, diff, isContainNullValue } from "@/utils/objectUtil";
import { findEmployeeById, updateEmployee } from "../../../api/humanResources";
import { MESSAGE } from "../../../constant/message";
import { EVENT_BUS, SEX_SELECT, LOCAL_STORAGE } from "../../../constant/common";
import { isValidEmail, isValidNumber } from "@/utils/validate";
import { URL_IMG } from "@/utils/request";
import { ACTIVED_STATUS } from "@/constant/searchListUserForm";
import DocumentComponent from "./components/DocumentComponent.vue";
import PositionComponent from "./components/PositionComponent.vue";

export default {
  components: { Card, DocumentComponent, PositionComponent },
  data() {
    return {
      originEmployeeObj: null,
      originPersonalInfoObj: null,
      originRoom: null,
      sexSelect: SEX_SELECT,
      workingStatus: ACTIVED_STATUS,
      employeeObj: UPDATE_EMPLOYEE_OBJECT,
      // Value when call init API start
      initPositions: null,
      initRooms: null,
      // Props for selecting tags end
      routerProps: FE_ROUTER_PROP,
      defaultImg: null,
      imageObj: {
        imageUrl: null,
        file: null,
      },
    };
  },
  async beforeCreate() {
    await findAllRooms().then((res) => (this.initRooms = res?.data));
    await findEmployeeById(this.$route.params.id).then((res) => {
      this.employeeObj = res.data;
      this.originRoom = Object.assign({}, res.data.room);
      this.originEmployeeObj = Object.assign({}, res.data.employee);
      this.originPersonalInfoObj = Object.assign({}, res.data.personalInfo);
      this.defaultImg = URL_IMG + this.employeeObj.personalInfo.personalImage;
    });
  },
  created() {
    this.$bus.on(EVENT_BUS.REFRESH_EMPLOYEE, () => {
      findEmployeeById(this.$route.params.id).then((res) => {
        this.employeeObj = res.data;
        this.defaultImg = URL_IMG + this.employeeObj.personalInfo.personalImage;
      });
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
      findEmployeeById(this.$route.params.id).then((res) => {
        this.employeeObj = res.data;
        this.defaultImg = URL_IMG + this.employeeObj.personalInfo.personalImage;
        let user = JSON.parse(localStorage.getItem(LOCAL_STORAGE.NAME));
        if (res.data.personalInfo.personalInfoId == user.personalInfoId) {
          localStorage.setItem(
            LOCAL_STORAGE.NAME,
            JSON.stringify(res.data.personalInfo)
          );
          this.$bus.emit(EVENT_BUS.REFRESH_LOCAL_STORAGE);
        }
      });
    },
    submitForm() {
      let copyRoomObj = Object.assign({}, this.employeeObj.room);
      if (copyRoomObj.roomId === this.originRoom.roomId) {
        copyRoomObj.roomId = null;
      }
      if (
        isContainNullValue(this.employeeObj.employee) ||
        isContainNullValue(this.employeeObj.personalInfo)
      ) {
        this.$notify({
          type: "warning",
          message: MESSAGE.INSERT_EMPLOYEE.ERR_UPDATE_EMPLOYEE,
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        return;
      }
      if (
        !isValidEmail(this.employeeObj.personalInfo.personalEmail) ||
        !isValidNumber(this.employeeObj.personalInfo.personalPhoneNumber)
      ) {
        this.$notify({
          type: "warning",
          message: MESSAGE.INSERT_EMPLOYEE.ERR_FORMAT,
          icon: "tim-icons icon-bell-55",
          horizontalAlign: "center",
        });
        return;
      }

      let copyEmployeeObj = Object.assign({}, this.employeeObj.employee);
      diff(copyEmployeeObj, this.originEmployeeObj);
      if (!isAllNullValue(copyEmployeeObj)) {
        copyEmployeeObj.employeeId = this.originEmployeeObj.employeeId;
      }

      let copyPersonalInfoObj = Object.assign(
        {},
        this.employeeObj.personalInfo
      );
      diff(copyPersonalInfoObj, this.originPersonalInfoObj);
      if (!isAllNullValue(copyPersonalInfoObj) || this.imageObj.file) {
        copyPersonalInfoObj.personalInfoId = this.originPersonalInfoObj.personalInfoId;
      }

      if (
        !isAllNullValue(copyEmployeeObj) ||
        !isAllNullValue(copyPersonalInfoObj) ||
        copyRoomObj.roomId !== null ||
        this.imageObj.file !== null
      ) {
        let submitdata = Object.assign({}, UPDATE_EMPLOYEE_OBJECT);
        submitdata.employee = copyEmployeeObj;
        submitdata.personalInfo = copyPersonalInfoObj;
        let formData = new FormData();
        formData.append(
          "formEmployee",
          new Blob([JSON.stringify(submitdata)]),
          {
            type: "application/json",
          }
        );
        if (this.imageObj.file) {
          formData.append("image", this.imageObj.file);
        }
        updateEmployee(formData)
          .then((res) => {
            this.$notify({
              type: "success",
              message: res.message,
              icon: "tim-icons icon-bell-55",
              horizontalAlign: "center",
            });
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
      }
    },
  },
};
</script>

<style scoped>
#tags-component-select___input__ {
  color: black;
}
</style>
