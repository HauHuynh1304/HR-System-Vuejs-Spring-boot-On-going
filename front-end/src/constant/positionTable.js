import { DATE_FORMAT } from "@/constant/common";
import moment from "moment";

export const POSITION_TABLE_COLUMN = {
  fields: [
    {
      key: "positionName",
      label: "Position Name",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "startDate",
      label: "start",
      thClass: "text-center text-info",
      tdClass: "text-center",
      formatter: (value) => {
        return moment(value).format(DATE_FORMAT);
      },
    },
    {
      key: "endDate",
      label: "end",
      thClass: "text-center text-info",
      tdClass: "text-center",
      formatter: (value) => {
        return value ? moment(value).format(DATE_FORMAT) : null;
      },
    },
    {
      key: "edit",
      label: "",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
  ],
};

export const POSITION_MASTER_TABLE_COLUMN = {
  fields: [
    {
      key: "positionName",
      label: "Position Name",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "edit",
      label: "",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
  ],
};

export const OBJ = {
  position: {
    positionId: null,
    positionName: null,
    deletedFlag: null,
  },
};
