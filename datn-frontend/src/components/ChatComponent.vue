<script lang="ts">
import { defineComponent, inject } from "vue";
import { ChatService } from "@/base/service/chat.service";
import { UserService } from "@/base/service/user.service";
import { User } from "@/base/model/user.model";
import { io } from "socket.io-client";

export default defineComponent({
  name: "ChatComponent",
  setup() {
    return {
      chatService: inject("chatService") as ChatService,
      userService: inject("userService") as UserService,
    };
  },
  data() {
    return {
      messages: {} as Map<string, any>,
      admins: [] as User[],
      user: localStorage.getItem("user")
        ? JSON.parse(localStorage.getItem("user") as string)
        : new User(),
      message: "",
      adminSelected: new User(),
      messageList: [] as any[],
      socket: io("ws://localhost:9090").connect(),
    };
  },
  methods: {
    async sendMessage() {
      if (this.message) {
        await this.chatService.sendMessage({
          message: this.message,
          toUser: this.adminSelected?.id,
        });
        this.message = "";
        this.socket.emit("chat", this.message);
        this.getMessages();
      }
    },
    async getAdmins() {
      this.admins = await this.userService.findAdmin();
    },
    async getMessages() {
      this.messageList = await this.chatService.getMessages(
        this.adminSelected?.id
      );
      // xử lý bất đồng bộ
    },
    isMyMessage(message: any) {
      if (message) {
        return message?.user.id === this?.user.id;
      }
      return false;
    },
    formatDate(date: number) {
      return new Date(date).toLocaleTimeString();
    },
    setAdminSelected(admin: User) {
      this.adminSelected = admin;
      this.getMessages();
      console.log(this.messageList);
    },
  },
  created() {
    this.getAdmins();
    this.getMessages();
    this.socket.on("connect", () => {
      console.log("Connected to Socket.IO server");
    });
    this.socket.on("chat", (data: any) => {
      this.getMessages();
    });
    if (this.socket.connected) {
      console.log("Socket is already connected.");
    } else {
      console.log("Socket is not connected yet.");
    }
  },
  mounted() {},
});
</script>

<template>
  <div class="container mt-8">
    <div class="row clearfix">
      <div class="col-lg-12">
        <div class="card chat-app">
          <div id="plist" class="people-list">
            <div class="input-group">
              <div class="input-group-prepend d-flex">
                <span class="input-group-text"
                  ><i class="fa fa-search"></i
                ></span>
              </div>
              <input type="text" class="form-control" placeholder="Search..." />
            </div>
            <ul class="list-unstyled chat-list mt-2 mb-0">
              <li
                class="clearfix d-flex align-items-center"
                v-for="(item, index) in admins"
                :key="index"
                @click.prevent="setAdminSelected(item)"
                :class="{ active: adminSelected?.username === item?.username }"
              >
                <v-avatar
                  :image="'data:image/png;base64,' + item.avatar"
                  size="30"
                  class="border-dark avatar"
                ></v-avatar>
                <div class="about">
                  <div class="name">{{ item?.username }}</div>
                </div>
              </li>
            </ul>
          </div>
          <div class="chat d-flex flex-column justify-content-between">
            <div class="chat-header clearfix p-3">
              <div class="row">
                <div class="col-lg-6 d-flex align-items-center">
                  <a>
                    <v-avatar
                      :image="'data:image/png;base64,' + adminSelected.avatar"
                      size="30"
                      class="border-dark avatar"
                    ></v-avatar>
                  </a>
                  <div class="chat-about">
                    <h6 class="m-0">{{ adminSelected.username }}</h6>
                  </div>
                </div>
              </div>
            </div>
            <div class="chat-history">
              <ul class="m-b-0">
                <li
                  class="clearfix"
                  v-for="(item, index) in messageList"
                  :key="index"
                >
                  <div v-if="item?.from.username === user?.username">
                    <div class="message-data text-right">
                      <span class="message-data-time">{{
                        formatDate(item?.timestamp)
                      }}</span>
                      <v-avatar
                        :image="'data:image/png;base64,' + item?.user?.avatar"
                        size="30"
                        class="border-dark"
                      ></v-avatar>
                    </div>
                    <div class="message other-message float-right">
                      {{ item?.message }}
                    </div>
                  </div>
                  <div v-else>
                    <div class="message-data">
                      <v-avatar
                        :image="'data:image/png;base64,' + item?.user?.avatar"
                        size="30"
                        class="border-dark"
                      ></v-avatar>
                      <span class="message-data-name">{{
                        item?.user?.username
                      }}</span>
                      <span class="message-data-time">{{
                        formatDate(item?.timestamp)
                      }}</span>
                    </div>
                    <div class="message my-message">
                      {{ item?.message }}
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="chat-message clearfix">
              <div class="input-group">
                <input
                  type="text"
                  class="form-control"
                  placeholder="Enter text here..."
                  v-model="message"
                />
                <div class="input-group-prepend d-flex">
                  <span
                    class="input-group-text"
                    @click.enter.prevent="sendMessage"
                    ><i class="fa fa-send"></i
                  ></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card {
  background: #fff;
  transition: 0.5s;
  border: 0;
  margin-bottom: 30px;
  border-radius: 0.55rem;
  position: relative;
  width: 100%;
  box-shadow: 0 1px 2px 0 rgb(0 0 0 / 10%);
  height: 70vh;
}

.chat-app .people-list {
  width: 280px;
  position: absolute;
  left: 0;
  top: 0;
  padding: 20px;
  z-index: 7;
}

.chat-app .chat {
  margin-left: 280px;
  border-left: 1px solid #eaeaea;
}

.people-list {
  -moz-transition: 0.5s;
  -o-transition: 0.5s;
  -webkit-transition: 0.5s;
  transition: 0.5s;
}

.people-list .chat-list {
  height: 62vh;
  overflow: auto;
}

.people-list .chat-list li {
  padding: 10px 15px;
  list-style: none;
  border-radius: 3px;
}

.people-list .chat-list li:hover {
  background: #efefef;
  cursor: pointer;
}

.people-list .chat-list li.active {
  background: #efefef;
}

.people-list .chat-list li .name {
  font-size: 15px;
}

.people-list .chat-list img {
  width: 45px;
  border-radius: 50%;
}

.people-list img {
  float: left;
  border-radius: 50%;
}

.avatar {
  float: left;
}

.people-list .about {
  padding-left: 8px;
}

.people-list .status {
  color: #999;
  font-size: 13px;
}

.chat {
  height: 100%;
}

.chat .chat-header {
  border-bottom: 2px solid #f4f7f6;
}

.chat .chat-header img {
  float: left;
  border-radius: 40px;
  width: 40px;
}

.chat .chat-header .chat-about {
  padding-left: 10px;
}

.chat .chat-history {
  padding: 20px;
  border-bottom: 2px solid #fff;
  height: 100%;
  overflow: auto;
  display: flex;
  flex-direction: column-reverse;
}

.chat .chat-history ul {
  padding: 0;
}

.chat .chat-history ul li {
  list-style: none;
  margin-bottom: 30px;
}

.chat .chat-history ul li:last-child {
  margin-bottom: 0px;
}

.chat .chat-history .message-data {
  margin-bottom: 15px;
}

.chat .chat-history .message-data img {
  border-radius: 40px;
  width: 40px;
}

.chat .chat-history .message-data-time {
  color: #434651;
  padding-left: 6px;
}

.chat .chat-history .message {
  color: #444;
  padding: 18px 20px;
  line-height: 26px;
  font-size: 16px;
  border-radius: 7px;
  display: inline-block;
  position: relative;
}

.chat .chat-history .message:after {
  bottom: 100%;
  left: 7%;
  border: solid transparent;
  content: " ";
  height: 0;
  width: 0;
  position: absolute;
  pointer-events: none;
  border-bottom-color: #fff;
  border-width: 10px;
  margin-left: -10px;
}

.chat .chat-history .my-message {
  background: #efefef;
}

.chat .chat-history .my-message:after {
  bottom: 100%;
  left: 30px;
  border: solid transparent;
  content: " ";
  height: 0;
  width: 0;
  position: absolute;
  pointer-events: none;
  border-bottom-color: #efefef;
  border-width: 10px;
  margin-left: -10px;
}

.chat .chat-history .other-message {
  background: #e8f1f3;
  text-align: right;
}

.chat .chat-history .other-message:after {
  border-bottom-color: #e8f1f3;
  left: 93%;
}

.chat .chat-message {
  padding: 20px;
}

.online,
.offline,
.me {
  margin-right: 2px;
  font-size: 8px;
  vertical-align: middle;
}

.online {
  color: #86c541;
}

.offline {
  color: #e47297;
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

@media only screen and (max-width: 767px) {
  .chat-app .people-list {
    height: 465px;
    width: 100%;
    overflow-x: auto;
    background: #fff;
    left: -400px;
    display: none;
  }

  .chat-app .people-list.open {
    left: 0;
  }

  .chat-app .chat {
    margin: 0;
  }

  .chat-app .chat .chat-header {
    border-radius: 0.55rem 0.55rem 0 0;
  }

  .chat-app .chat-history {
    height: 300px;
    overflow-x: auto;
  }
}

@media only screen and (min-width: 768px) and (max-width: 992px) {
  .chat-app .chat-list {
    height: 650px;
    overflow-x: auto;
  }

  .chat-app .chat-history {
    height: 600px;
    overflow-x: auto;
  }
}

@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) and (orientation: landscape) and (-webkit-min-device-pixel-ratio: 1) {
  .chat-app .chat-list {
    height: 480px;
    overflow-x: auto;
  }

  .chat-app .chat-history {
    height: calc(100vh - 350px);
    overflow-x: auto;
  }
}
</style>
