<template>
  <div>
    <h4 slot="header" class="title">
      SUBMITTED DOCUMENTS
    </h4>
    <div class="row">
      <div class="col-md-6">
        <b-button
          size="sm"
          @click="openInsertDocumentModal"
          class="btn btn-info tim-icons icon-simple-add"
        />
      </div>
      <div class="col-md-6">
        <b-pagination
          id="pagination"
          v-model="currentPage"
          :total-rows="totalRows"
          :current-page="currentPage"
          :per-page="perPage"
          align="right"
          size="sm"
        />
      </div>
    </div>
    <b-table
      hover
      sticky-header
      :fields="fields"
      :items="documentObj"
      :per-page="perPage"
      :current-page="currentPage"
    >
      <template #cell(documentName)="row">
        <p :class="row.item.deletedFlag ? 'line-through' : ''">
          {{ row.item.documentName }}
        </p>
      </template>
      <template #cell(edit)="row">
        <b-button
          size="sm"
          type="link"
          @click="
            row.item.deletedFlag
              ? clickUnDelDocument(row.item, row.index, $event.target)
              : clickDelDocument(row.item, row.index, $event.target)
          "
          class="btn btn-link"
        >
          <i
            :class="
              row.item.deletedFlag
                ? 'tim-icons icon-refresh-01'
                : 'tim-icons icon-trash-simple'
            "
          ></i>
        </b-button>
      </template>
    </b-table>
    <!-- Modal -->
    <b-modal
      :id="modalDocument.delInfo.id"
      @ok="deleteDocumentAction($event)"
      @hidden="resetDocumentModal"
    >
      <h5 class="text-center">
        Want to delete
        <strong>{{ modalDocument.documentName }}</strong>
      </h5>
      <template #modal-footer="{ ok, cancel}">
        <b-button variant="warning" size="sm" @click="cancel()">
          Cancel
        </b-button>
        <b-button variant="success" size="sm" @click="ok()">
          OK
        </b-button>
      </template>
    </b-modal>
    <b-modal
      :id="modalDocument.undelInfo.id"
      @ok="unDelDocumentAction($event)"
      @hidden="resetDocumentModal"
    >
      <h5 class="text-center">
        Want to re-open
        <strong>{{ modalDocument.documentName }}</strong>
      </h5>
      <template #modal-footer="{ ok, cancel}">
        <b-button variant="warning" size="sm" @click="cancel()">
          Cancel
        </b-button>
        <b-button variant="success" size="sm" @click="ok()">
          OK
        </b-button>
      </template>
    </b-modal>
    <b-modal
      id="insertDocumentModal"
      ref="insertDocumentModal"
      @ok="insertDocumentsAction($event)"
      title="New Documents"
      no-stacking
      @hidden="resetDocumentModal"
    >
      <b-form-group
        label="Submited Documents"
        id="tags-document-component-select"
        label-for="tags-component-select"
        ref="tags-component-select"
      >
        <!-- Prop `add-on-change` is needed to enable adding tags vie the `change` event -->
        <b-form-tags
          id="tags-document-component-select"
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
            <ul v-if="tags.length > 0" class="list-inline d-inline-block mb-2">
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
              :disabled="disabled || availableDocumentOptions.length === 0"
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
      <template #modal-footer="{ ok, cancel}">
        <b-button variant="warning" size="sm" @click="cancel()">
          Cancel
        </b-button>
        <b-button variant="success" size="sm" @click="ok()">
          OK
        </b-button>
      </template>
    </b-modal>
  </div>
</template>
<script>
import { DOCUMENT_TABLE_COLUMN } from "@/constant/documentTable";
import { UPDATE_EMPLOYEE_OBJECT } from "@/constant/employeeObj";
import { updateEmployee, findDocuments } from "@/api/humanResources";
import { ACTION, EVENT_BUS } from "@/constant/common";
import { MESSAGE } from "@/constant/message";

export default {
  name: "document-component",
  props: {
    documentObj: {
      type: Array,
      default: [],
    },
  },
  data() {
    return {
      originDocumentObj: null,
      documentOption: [],
      tagDocumentValue: [],
      updateEmployeeObj: UPDATE_EMPLOYEE_OBJECT,
      perPage: 5,
      totalRows: null,
      currentPage: 1,
      fields: DOCUMENT_TABLE_COLUMN.fields,
      modalDocument: {
        undelInfo: {
          id: "undel-document-modal",
        },
        delInfo: {
          id: "del-document-modal",
        },
        documentName: null,
        normalInfo: {
          documentId: null,
          employeeDocumentId: null,
          deletedFlag: null,
        },
      },
    };
  },
  watch: {
    documentObj: function() {
      this.totalRows = this.documentObj.length;
      this.updateEmployeeObj.employee.employeeId = this.$route.params.id;
    },
  },
  computed: {
    availableDocumentOptions() {
      return this.documentOption.filter(
        (opt) => this.tagDocumentValue.indexOf(opt) === -1
      );
    },
  },
  methods: {
    openInsertDocumentModal() {
      findDocuments().then((res) => {
        this.originDocumentObj = res.data;
        this.tagDocumentValue = this.documentObj.map((el) => el.documentName);
        this.documentOption = res.data.map((el) => el.documentName);
        this.$refs["insertDocumentModal"].show();
      });
    },
    setValueDeleteFlag(action, item, button) {
      if (action === ACTION.DELETE) {
        this.modalDocument.normalInfo.deletedFlag = true;
      }
      if (action === ACTION.UNDELETE) {
        this.modalDocument.normalInfo.deletedFlag = false;
      }
      this.modalDocument.documentName = item.documentName;
      this.modalDocument.normalInfo.employeeDocumentId =
        item.employeeDocumentId;
      this.$root.$emit(
        "bv::show::modal",
        this.modalDocument.delInfo.id,
        button
      );
    },
    clickDelDocument(item, index, button) {
      this.setValueDeleteFlag(ACTION.DELETE, item, button);
    },
    clickUnDelDocument(item, index, button) {
      this.setValueDeleteFlag(ACTION.UNDELETE, item, button);
    },
    updateDocument(action) {
      if (action === ACTION.UPDATE) {
        this.updateEmployeeObj.documents.push(this.modalDocument.normalInfo);
      }
      if (action === ACTION.INSERT) {
        let registeredDocumentName = this.documentObj.map(
          (el) => el.documentName
        );
        let newDocmentTag = this.tagDocumentValue.filter(
          (el) => registeredDocumentName.indexOf(el) === -1
        );
        if (!newDocmentTag.length || !this.tagDocumentValue.length) {
          this.$notify({
            type: "warning",
            message: MESSAGE.INSERT_EMPLOYEE.ERR_INSERT,
            icon: "tim-icons icon-bell-55",
            horizontalAlign: "center",
          });
          return;
        }
        this.originDocumentObj.forEach((el) => {
          return newDocmentTag.includes(el.documentName)
            ? this.updateEmployeeObj.documents.push(el)
            : null;
        });
      }
      let formData = new FormData();
      formData.append(
        "formEmployee",
        new Blob([JSON.stringify(this.updateEmployeeObj)]),
        {
          type: "application/json",
        }
      );
      updateEmployee(formData).then((res) => {
        this.$refs["insertDocumentModal"].hide();
        this.$bus.emit(EVENT_BUS.REFRESH_EMPLOYEE);
      });
    },
    deleteDocumentAction(e) {
      this.updateDocument(ACTION.UPDATE);
    },
    unDelDocumentAction(e) {
      this.updateDocument(ACTION.UPDATE);
    },
    insertDocumentsAction(e) {
      e.preventDefault();
      this.updateDocument(ACTION.INSERT);
    },
    resetDocumentModal() {
      this.updateEmployeeObj.documents = [];
      this.originDocumentObj = null;
      this.documentOption = [];
      this.tagDocumentValue = [];
    },
  },
};
</script>
<style lang="scss" scoped>
#pagination /deep/ .page-link {
  color: black;
  font-size: 0.75rem;
}
.line-through {
  text-decoration: line-through;
}
#tags-document-component-select___input__ {
  color: black;
}
/deep/ .modal-dialog > .modal-content > .modal-footer {
  display: flex;
  justify-content: right;
  button {
    margin-right: 1em;
  }
}
</style>
