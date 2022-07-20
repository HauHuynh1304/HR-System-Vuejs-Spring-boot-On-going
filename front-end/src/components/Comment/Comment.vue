<template>
  <card>
    <div class="row clearfix">
      <div class="col-lg-12">
        <div class="chat">
          <div class="chat-history" id="chat-box">
            <ul class="m-b-0">
              <li
                class="clearfix"
                v-for="(comment, index) in comments"
                :key="index"
              >
                <div
                  :class="
                    comment
                      ? userEmail === comment.requesterEmail
                        ? 'message-data text-right'
                        : userEmail === comment.supervisorEmail
                        ? 'message-data text-right'
                        : userEmail === comment.approverEmail
                        ? 'message-data text-right'
                        : 'message-data'
                      : ''
                  "
                >
                  <span class="message-data-time">
                    <strong>
                      {{
                        comment
                          ? comment.requesterEmail
                            ? comment.requesterEmail.substring(
                                0,
                                comment.requesterEmail.indexOf("@")
                              )
                            : comment.supervisorEmail
                            ? comment.supervisorEmail.substring(
                                0,
                                comment.supervisorEmail.indexOf("@")
                              )
                            : comment.approverEmail.substring(
                                0,
                                comment.approverEmail.indexOf("@")
                              )
                          : ""
                      }}
                    </strong>
                    {{
                      comment
                        ? "at " +
                          comment.createdAt.substring(
                            0,
                            comment.createdAt.indexOf(".")
                          )
                        : null
                    }}</span
                  >
                </div>
                <div
                  :class="
                    comment
                      ? userEmail === comment.requesterEmail
                        ? 'message other-message float-right'
                        : userEmail === comment.supervisorEmail
                        ? 'message other-message float-right'
                        : userEmail === comment.approverEmail
                        ? 'message other-message float-right'
                        : 'message my-message'
                      : ''
                  "
                >
                  {{ comment ? comment.commentDetail : null }}
                </div>
              </li>
            </ul>
          </div>
          <form id="sendMessage" @submit.prevent="sendMessage">
            <div class="chat-message clearfix">
              <div class="input-group mb-0">
                <div class="input-group-prepend">
                  <span class="input-group-text">
                    <i class="tim-icons icon-send" @click="sendMessage" />
                  </span>
                </div>
                <input
                  type="text"
                  class="form-control"
                  v-model.trim="commentRequest.comment.commentDetail"
                  placeholder="Enter text here..."
                />
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </card>
</template>

<script>
import jwt_decode from "jwt-decode";
import { getAccessToken } from "../../utils/cookies";
import { COMMENT_REQUEST } from "@/constant/comment";
import { EVENT_BUS } from "@/constant/common";
import { FE_ROUTER_PROP } from "@/constant/routerProps";
import { insertComment, findListComment } from "@/api/business";

export default {
  props: {
    requestTicket: {
      type: Object,
      default: {},
    },
    shouldPlay: Boolean,
  },
  data() {
    return {
      comments: null,
      employeeId: null,
      userEmail: null,
      commentRequest: COMMENT_REQUEST,
    };
  },
  async created() {
    this.userEmail = jwt_decode(getAccessToken()).sub;
    await this.findListComment();
  },
  methods: {
    async findListComment() {
      await findListComment(this.$route.params.id).then((res) => {
        this.comments = res.data;
        this.$bus.emit(EVENT_BUS.CLOSE_LOADING_MODAL);
      });
      // Scroll to the last comment
      let objDiv = document.getElementById("chat-box");
      objDiv.scrollTop = objDiv.scrollHeight;
    },
    setObjActionId() {
      let originRouteRequestedTicket =
        FE_ROUTER_PROP.REQUEST_TICKET.CHILDREN.REQUESTED_TICKET.PATH;
      if (
        this.$route.path.includes(
          originRouteRequestedTicket.substring(
            0,
            originRouteRequestedTicket.lastIndexOf("/")
          )
        )
      ) {
        this.commentRequest.comment.requesterActionId = this.requestTicket.requesterAction.requesterActionId;
      } else {
        if (
          this.userEmail === this.requestTicket.supervisorAction.supervisorEmail
        ) {
          this.commentRequest.comment.supervisorActionId = this.requestTicket.supervisorAction.supervisorActionId;
        }
        if (
          this.userEmail === this.requestTicket.approverAction.approverEmail
        ) {
          this.commentRequest.comment.approverActionId = this.requestTicket.approverAction.approverActionId;
        }
      }
    },
    sendMessage() {
      if (!this.commentRequest.comment.commentDetail.length) {
        return;
      }
      this.setObjActionId();
      this.$bus.emit(EVENT_BUS.OPEN_LOADING_MODAL);
      insertComment(this.commentRequest).then(() => {
        this.commentRequest.comment.commentDetail = null;
        this.findListComment();
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.chat .chat-history {
  padding: 0 1.5rem 0 1.5rem;
  border-bottom: 2px solid #fff;
}

.chat .chat-history ul {
  padding: 0;
}

.chat .chat-history ul li {
  list-style: none;
  margin-bottom: 0.5rem;
}

.chat .chat-history .message-data {
  margin-bottom: 0.5rem;
}

.chat .chat-history .message-data-time {
  font-size: 0.75rem;
  color: #434651;
  padding-left: 6px;
}

.chat .chat-history .message {
  padding: 0.3rem 0.5rem;
  font-size: 0.75rem;
  line-height: 26px;
  border-radius: 7px;
  display: inline-block;
}

.chat .chat-history .my-message {
  background: #efefef;
}

.chat .chat-history .other-message {
  background: #e8f1f3;
  text-align: right;
}

.chat .chat-message {
  padding: 20px;
}

.me {
  color: #1d8ecd;
}

.float-right {
  float: right;
}

.clearfix:after {
  visibility: hidden;
  display: block;
  font-size: 0;
  content: " ";
  clear: both;
  height: 0;
}

@media only screen and (max-height: 767px) {
  .chat .chat-history {
    height: 300px;
    overflow-x: auto;
  }
}

@media only screen and (min-height: 768px) and (max-height: 992px) {
  .chat .chat-history {
    height: 600px;
    overflow-x: auto;
  }
}

@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) and (orientation: landscape) and (-webkit-min-device-pixel-ratio: 1) {
  .chat .chat-history {
    height: calc(100vh - 350px);
    overflow-x: auto;
  }
}
</style>
