import moment from "moment";
import { DATE_FORMAT } from "./common";

export const REQUESTED_TIKCET_TABLE = {
  fields: [
    {
      key: "requestType",
      label: "request type",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "requestEmployee.startDate",
      label: "start date",
      thClass: "text-center text-info",
      tdClass: "text-center",
      formatter: (value) => {
        return moment(value).format(DATE_FORMAT);
      },
    },
    {
      key: "requestEmployee.endDate",
      label: "end date",
      thClass: "text-center text-info",
      tdClass: "text-center",
      formatter: (value) => {
        return moment(value).format(DATE_FORMAT);
      },
    },
    {
      key: "requestEmployee.duration",
      label: "duration",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "reason",
      label: "reason",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "approver",
      label: "approver",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "requestEmployee.requestStatus",
      label: "status",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
  ],
};

export const RECEIVED_TIKCET_TABLE = {
  fields: [
    {
      key: "requestType",
      label: "request type",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "requestEmployee.startDate",
      label: "start date",
      thClass: "text-center text-info",
      tdClass: "text-center",
      formatter: (value) => {
        return moment(value).format(DATE_FORMAT);
      },
    },
    {
      key: "requestEmployee.endDate",
      label: "end date",
      thClass: "text-center text-info",
      tdClass: "text-center",
      formatter: (value) => {
        return moment(value).format(DATE_FORMAT);
      },
    },
    {
      key: "requestEmployee.duration",
      label: "duration",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "reason",
      label: "reason",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "requester",
      label: "requester",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "requestEmployee.requestStatus",
      label: "status",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
  ],
};
