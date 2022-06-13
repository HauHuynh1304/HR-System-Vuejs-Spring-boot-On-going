<template>
  <card>
    <h4 slot="header" class="title">
      {{ routerProps.USER.NAME.toUpperCase() }}
    </h4>
    <div class="row">
      <div class="col-md-6 pr-md-1">
        <base-input
          label="Full Name"
          placeholder="Full Name"
          readonly
          v-model="model.personalInfo.personalName"
        />
      </div>
      <div class="col-md-6 pl-md-1">
        <base-input
          label="Address"
          v-model="model.personalInfo.personalAddress"
          readonly
          placeholder="Home Address"
        />
      </div>
    </div>
    <div class="row">
      <div class="col-md-6 pr-md-1">
        <base-input
          label="Company email"
          type="email"
          v-model="model.systemEmail"
          readonly
          placeholder="comapny@email.com"
        />
      </div>
      <div class="col-md-6 pl-md-1">
        <base-input
          label="Personal email"
          v-model="model.personalInfo.personalEmail"
          readonly
          placeholder="personal@email.com"
        />
      </div>
    </div>
    <div class="row">
      <div class="col-md-3 pr-md-1">
        <base-input
          label="Phone Number"
          v-model="model.personalInfo.personalPhoneNumber"
          readonly
          placeholder="City"
        />
      </div>
      <div class="col-md-3 px-md-1">
        <base-input
          label="ID Card"
          v-model="model.personalInfo.personalIdCard"
          readonly
          placeholder="Country"
        />
      </div>
      <div class="col-md-3 px-md-1">
        <base-input
          label="Sex"
          placeholder="ZIP Code"
          v-model="model.personalInfo.personalSex"
          readonly
        />
      </div>
      <div class="col-md-3 pl-md-1">
        <base-input
          label="Birth day"
          placeholder="ZIP Code"
          v-model="model.personalInfo.personalBirthday"
          readonly
        />
      </div>
    </div>
  </card>
</template>
<script>
import { FE_ROUTER_PROP } from "@/constant/routerProps";
export default {
  data() {
    return {
      routerProps: FE_ROUTER_PROP,
    };
  },
  props: {
    model: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
};
</script>
<style></style>
