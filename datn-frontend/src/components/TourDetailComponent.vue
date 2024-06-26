<script lang="ts">
import { defineComponent } from "vue";
import { Tour } from "@/base/model/tour.model";
import { formatDate } from "date-fns";
import formatMoney, { hexToBase64 } from "@/plugins/utils";

export default defineComponent({
  methods: {
    formatDate(date: Date | undefined, format: string, options: any) {
      if (date) {
        return formatDate(date, format, options);
      }
      return "";
    },
    formatMoney(value: number | undefined) {
      if (value) {
        return formatMoney(value);
      }
      return "";
    },
    hexToBase64,
    redirectBooking() {
      this.$router.push(`/booking/${this.tour.id}`);
    },
  },
  data() {
    return { infoStatus: "default" };
  },
  props: {
    tour: {
      type: Tour,
      default: new Tour(),
    },
  },
});
</script>

<template>
  <div class="tour-detail_wrapper text-dark" v-if="tour">
    <div class="tour-detail__main-pic">
      <img
        class="tour-detail__main-pic__img"
        :src="'data:image/png;base64,' + tour?.image"
      />
      <div class="container-xxl tour-detail__main-pic__title">
        <h3>{{ tour.name }}</h3>
      </div>
    </div>
    <div class="container-xxl tour-detail">
      <div class="row mt-3">
        <div class="col-6">
          <p>
            Thời gian:
            <span>{{ formatDate(tour?.startDate, "dd/MM/yyyy", {}) }}</span>
          </p>
        </div>
        <div class="col-6">
          <p>
            Phương tiện: <span>{{ tour.vehicle }}</span>
          </p>
        </div>
      </div>
      <div class="row mb-3">
        <div class="col-6">
          <p>
            Điểm xuất phát: <span>{{ tour.departure }}</span>
          </p>
        </div>
        <div class="col-6">
          <p>
            Điểm đến: <span>{{ tour.location }}</span>
          </p>
        </div>
      </div>
      <div v-if="tour.description">
        <h5>Mô tả:</h5>
        <div v-html="tour?.description"></div>
      </div>
      <table class="table tour-detail__table align-middle my-4">
        <thead>
          <tr>
            <th scope="col">Khởi hành</th>
            <th scope="col">Mã tour</th>
            <th scope="col">Giá</th>
            <th scope="col">Giá trẻ em</th>
            <th scope="col">Giá em bé</th>
            <th></th>
          </tr>
        </thead>
        <tbody class="table-group-divider">
          <tr>
            <th scope="row">1</th>
            <th>{{ tour.id }}</th>
            <td>{{ formatMoney(tour?.priceAdult) }}</td>
            <td>{{ formatMoney(tour?.priceChild) }}</td>
            <td>{{ formatMoney(tour?.priceBaby) }}</td>
            <td>
              <button @click="redirectBooking" class="booking-btn">
                Đặt tour
              </button>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="tour-detail__attraction">
        <h5>Tour này có gì hay</h5>
        <div v-html="tour.attraction"></div>
      </div>
      <!-- <v-btn class="m-1">
        In chương trình tour
        <span class="mdi mdi-printer"></span>
      </v-btn> -->
      <div
        class="row row-gap-3 my-3"
        v-if="tour?.tourGalleryList && tour.tourGalleryList.length > 0"
      >
        <div
          class="col-3"
          v-for="(item, index) in tour?.tourGalleryList"
          v-bind:key="index"
        >
          <v-img
            :src="item.image"
            class="tour-detail__gallery"
            :cover="true"
            :alt="item.altText"
          />
          <small>{{ item.title }}</small>
        </div>
      </div>
      <v-btn class="my-4 mx-2" @click="infoStatus = 'default'"
        >Chương trình Tour</v-btn
      >
      <v-btn class="my-4 mx-2" @click="infoStatus = 'policy'">Chính sách Tour</v-btn>
      <div v-if="infoStatus == 'default'">
        <div
          v-for="(tourSchedule, index) in tour?.tourScheduleList"
          v-bind:key="index"
        >
          <p>{{ tourSchedule.name }}</p>
          <p v-html="tourSchedule.description"></p>
          <v-img
            :src="'data:image/png;base64,' + tourSchedule.image"
            v-if="tourSchedule.image"
            :cover="true"
            max-height="300px"
          />
          <span>{{ tourSchedule?.imageAltText }}</span>
        </div>
      </div>
      <div v-if="infoStatus == 'policy'">
        {{ tour.policy }}
      </div>
    </div>
  </div>
</template>
<style lang="scss" scoped>
.tour-detail_wrapper {
  display: flex;
  flex-direction: column;

  .tour-detail__main-pic {
    height: 45vh;
    overflow: hidden;
    position: relative;
    display: flex;

    .tour-detail__main-pic__img {
      align-self: center;
      width: 100%;
    }

    .tour-detail__main-pic__title {
      justify-self: center;
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      padding: 10px 6px;
      text-align: center;
      color: $text-light-1;
      background-color: $black-transparent;
      border-radius: 6px;
    }
  }

  .tour-detail {
    align-self: center;
    .booking-btn {
      font-size: 1rem;
      font-weight: bold;
      padding: 4px 12px;
      border: none;
      border-radius: 4px;
      background-color: $marigold;
      color: $text-light-1;
      cursor: pointer;
      &:hover {
        background-color: $marigold-dark;
      }
    }
  }
}
</style>
