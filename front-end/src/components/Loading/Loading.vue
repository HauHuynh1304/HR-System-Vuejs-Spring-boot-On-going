<template>
  <div>
    <b-modal
      ref="loading-modal"
      id="loading-modal"
      no-close-on-backdrop
      hide-footer
      hide-header
    >
      <div class="text-center">
        <div class="lds-hourglass" />
      </div>
    </b-modal>
  </div>
</template>

<script>
import { EVENT_BUS } from "@/constant/common";
export default {
  created() {
    this.$bus.on(EVENT_BUS.OPEN_LOADING_MODAL, () => {
      this.$refs["loading-modal"]?.show();
      this.$emit(EVENT_BUS.DISABLE_ELEMENT);
    });
    this.$bus.on(EVENT_BUS.CLOSE_LOADING_MODAL, () => {
      this.$refs["loading-modal"]?.hide();
      this.$emit(EVENT_BUS.DISABLE_ELEMENT);
    });
  },
};
</script>

<style lang="scss" scoped>
@import "~@/assets/sass/black-dashboard/custom/variables";
.lds-hourglass {
  display: inline-block;
  position: relative;
  width: 80px;
  height: 80px;
}
.lds-hourglass:after {
  content: " ";
  display: block;
  border-radius: 50%;
  width: 0;
  height: 0;
  margin: 8px;
  box-sizing: border-box;
  border: 20px solid $black;
  border-color: $white transparent $white transparent;
  animation: lds-hourglass 1.2s infinite;
}
@keyframes lds-hourglass {
  0% {
    transform: rotate(0);
    animation-timing-function: cubic-bezier(0.55, 0.055, 0.675, 0.19);
  }
  50% {
    transform: rotate(900deg);
    animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
  }
  100% {
    transform: rotate(1800deg);
  }
}
/deep/ #loading-modal___BV_modal_content_ {
  background: transparent !important;
  box-shadow: none;
}
</style>
