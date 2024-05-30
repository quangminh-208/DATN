<script lang="tsx">
import { defineComponent } from "vue";
import { Tour } from "@/base/model/tour.model";
import formatMoney from "@/plugins/utils";

export default defineComponent({
  name: "TourCardComponent",
  props: {
    tour: {
      type: Tour,
      default: () => new Tour(),
      required: true,
    },
  },
  methods: {
    detail() {
      this.$router.push("/tour/" + this.tour?.id);
    },
    formatMoney,
  },
});
</script>

<template>
  <div class="col-3">
    <div class="tour">
      <div class="tour__img-wrapper">
        <img
          role="button"
          title="{tour.value?.name}"
          :src="'data:image/jpg;base64,' + tour?.image"
          class="tour__img"
        />
      </div>
      <!-- <span class="tour__category text-dark">{{ tour?.category }}</span> -->
      <div class="tour__content text-dark">
        <h5 class="tour__content__heading">
          {{ tour?.name }}
        </h5>
        <span class="tour__content__date"
          >{{ tour?.startDate }} - {{ tour?.duration }}</span
        >
        <span class="tour__content__place"
          >Điểm khởi hành: {{ tour?.departure }}</span
        >
        <div class="tour__content__buying">
          <p class="tour__content__price">
            Giá: {{ formatMoney(tour?.priceAdult) }}
          </p>
          <button @click="detail" class="tour__content__btn">Chi tiết</button>
        </div>
        <span class="tour__content__quantity"
          >Còn {{ tour?.quantityForAdult }} tour</span
        >
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.tour {
  display: flex;
  flex-direction: column;
  justify-content: center;
  box-shadow: 0 0 3px $divider-dark-2;
  border: 1px solid $gray-dove;
  border-radius: 10px;
  position: relative;
  .tour__img-wrapper {
    width: 100%;
    background-color: $white;
    aspect-ratio: 3/2;
    border-top-right-radius: 10px;
    border-top-left-radius: 10px;
    overflow: hidden;
    .tour__img {
    }
    &:hover .tour__img {
      transform: scale(1.1);
    }
  }

  .tour__link {
    width: 100%;
    height: 100%;
    text-decoration: none;
    color: #000;
    cursor: pointer;
  }

  .tour__img {
    width: 100%;
    transition: all 0.5s ease;
  }

  .tour__category {
    padding: 10px;
    background-color: $white;
  }

  .tour__content {
    background-color: $white;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0 18px 18px;
    border-bottom-right-radius: 10px;
    border-bottom-left-radius: 10px;
    .tour__content__heading {
      min-height: 4.5rem;
      padding-bottom: 4px;
      margin-top: 1rem;
      align-self: center;
      font-size: 1.1rem;
      font-weight: 500;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
    }

    .tour__content__buying {
      width: 100%;
      display: flex;
      justify-content: space-between;
      align-items: center;
      .tour__content__price {
        margin: 0;
        font-size: 1.2rem;
        font-weight: bold;
        color: $primary;
      }

      .tour__content__btn {
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
}
</style>
