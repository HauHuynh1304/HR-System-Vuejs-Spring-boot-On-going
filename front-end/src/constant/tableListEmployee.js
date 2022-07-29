import moment from "moment";
import { DATE_FORMAT } from "./common";

export const TABLE_LIST_EMPLOYEE = {
  fields: [
    {
      key: "personalInfo.personalName",
      label: "NAME",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "personalInfo.personalBirthday",
      label: "Birthday",
      thClass: "text-center text-info",
      tdClass: "text-center",
      formatter: (value) => {
        return moment(value).format(DATE_FORMAT);
      },
    },
    {
      key: "personalInfo.personalPhoneNumber",
      label: "Phone",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "systemEmail",
      label: "EMAIL",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "employee.employeeStartDate",
      label: "Start date",
      thClass: "text-center text-info",
      tdClass: "text-center",
      formatter: (value) => {
        return moment(value).format(DATE_FORMAT);
      },
    },
    {
      key: "employee.employeeEndDate",
      label: "End date",
      thClass: "text-center text-info",
      tdClass: "text-center",
      formatter: (value) => {
        if (value) {
          return moment(value).format(DATE_FORMAT);
        }
        return null;
      },
    },
    {
      key: "position",
      label: "Positions",
      thClass: "text-center text-info",
      tdClass: "text-center",
      formatter: (value) => {
        let position = value.map((item) => item.positionName).toString();
        return position.replaceAll(",", ", ");
      },
    },
    {
      key: "room.roomName",
      label: "room",
      thClass: "text-center text-info",
      tdClass: "text-center",
    },
    {
      key: "isFullDocuments",
      label: "Full documents",
      thClass: "text-center text-info",
      tdClass: "text-center",
      formatter: (value) => {
        return value ? "OK" : "NOT YET";
      },
    },
    {
      key: "employee.deletedFlag",
      label: "On working",
      thClass: "text-center text-danger",
      tdClass: "text-center",
    },
  ],
};
