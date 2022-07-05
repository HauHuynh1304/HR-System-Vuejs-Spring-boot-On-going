<template>
  <div>
    <div class="row">
      <div class="col-md-6">
        <system-roles />
      </div>
      <div class="col-md-6">
        <request-types />
      </div>
    </div>
    <div class="row">
      <div class="col-md-6">
        <positions />
      </div>
      <div class="col-md-6">
        <document-types />
      </div>
    </div>
    <div class="row">
      <div class="col-md-6">
        <reason-types />
      </div>
      <div class="col-md-6">
        <room-types />
      </div>
    </div>
  </div>
</template>
<script>
import DocumentTypes from "./components/DocumentTypes.vue";
import Positions from "./components/Positions.vue";
import ReasonTypes from "./components/ReasonTypes.vue";
import RequestTypes from "./components/RequestTypes.vue";
import RoomTypes from "./components/RoomTypes.vue";
import SystemRoles from "./components/SystemRoles.vue";
export default {
  components: {
    SystemRoles,
    RequestTypes,
    Positions,
    DocumentTypes,
    ReasonTypes,
    RoomTypes,
  },
};
</script>
, ReasonTypes RequestType
<style></style>
