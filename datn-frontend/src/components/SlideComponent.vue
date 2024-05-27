<script lang="tsx">
import { defineComponent, ref, onMounted } from "vue";
export default defineComponent({
  name: "SlideComponent",
  setup() {
    const carousel = ref([
      {
        index: 1,
        heading: "Hạ Long",
        subheading: "Nổi bật hè 2024",
        content: "Khởi hành 15/06/2024",
        img_url:
          "https://firebasestorage.googleapis.com/v0/b/monatourist-d447f.appspot.com/o/image%2Fslide-1.jpg?alt=media&token=ef98f6ef-bc12-4135-954e-617d5e3bfd33",
      },
      {
        index: 2,
        heading: "Đền Angkor Thom",
        subheading: "Ưu dãi giá sốc",
        content: "Khởi hành 10/06/2024",
        img_url:
          "https://firebasestorage.googleapis.com/v0/b/monatourist-d447f.appspot.com/o/image%2Fslide-2.jpg?alt=media&token=54a50cf0-6cdb-455e-93b3-3ad3c1287070",
      },
      {
        index: 3,
        heading: "Cao Bằng",
        subheading: "Khám phá, thám hiểm",
        content: "Khởi hành 27/07/2024",
        img_url:
          "https://firebasestorage.googleapis.com/v0/b/monatourist-d447f.appspot.com/o/image%2Fslide-3.jpg?alt=media&token=2bca355b-bd7b-4579-961c-c1036c09731e",
      },
    ]);

    const slide_index = ref(2);
    const slideChecked = ref(1);

    const backSlide = () => {
      slideChecked.value = slide_index.value;
      slide_index.value--;
      if (slide_index.value < 1) slide_index.value = 3;
    };
    const nextSlide = () => {
      slideChecked.value = slide_index.value;
      slide_index.value++;
      if (slide_index.value > 3) slide_index.value = 1;
    };

    onMounted(() => {
      setInterval(nextSlide, 4000);
    });

    return {
      carousel,
      slide_index,
      slideChecked,
      backSlide,
      nextSlide,
    };
  },
});
</script>

<template>
  <div class="slider-wrapper">
    <div class="slider__main">
      <input
        type="radio"
        name="slider-radio-btn"
        id="radio1"
        :checked="slideChecked === 1"
      />
      <input
        type="radio"
        name="slider-radio-btn"
        id="radio2"
        :checked="slideChecked === 2"
      />
      <input
        type="radio"
        name="slider-radio-btn"
        id="radio3"
        :checked="slideChecked === 3"
      />
      <div
        v-for="(slide, index) in carousel"
        :key="index"
        :class="{ 'first-slide': index === 0 }"
        class="slide"
      >
        <a>
          <img :src="slide.img_url" class="slide-img" />
        </a>
        <div class="slide__content container">
          <p class="slide__content__subheading">{{ slide.subheading }}</p>
          <p class="slide__content__heading">{{ slide.heading }}</p>
          <p class="slide__content__text">
            {{ slide.content }}
          </p>
          <button class="slide__content__btn">Xem thêm</button>
        </div>
      </div>

      <div class="slider__nav-manual">
        <label
          for="radio1"
          class="slider__nav-manual__btn slider__nav-manual__btn1"
        ></label>
        <label
          for="radio2"
          class="slider__nav-manual__btn slider__nav-manual__btn2"
        ></label>
        <label
          for="radio3"
          class="slider__nav-manual__btn slider__nav-manual__btn3"
        ></label>
      </div>
    </div>
    <div
      class="slider__btn__wrapper slider__btn__wrapper-left"
      @click="backSlide"
    >
      <button class="slider__btn slider__btn-left">
        <span class="mdi mdi-arrow-left-drop-circle-outline"></span>
      </button>
    </div>
    <div
      class="slider__btn__wrapper slider__btn__wrapper-right"
      @click="nextSlide"
    >
      <button class="slider__btn slider__btn-right">
        <span class="mdi mdi-arrow-right-drop-circle-outline"></span>
      </button>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.slider-wrapper {
  background-color: $color-background;
  aspect-ratio: 28 / 8;
  position: relative;
  display: flex;
  overflow: hidden;
  &:hover {
    .slider__btn__wrapper {
      .slider__btn {
        display: block;
      }
      .slider__btn-left {
        animation: moveLeftShort 0.3s linear;
      }
      .slider__btn-right {
        animation: moveRightShort 0.3s linear;
      }
    }
    .slider__btn__wrapper-left {
      background-image: linear-gradient(to right, $gray-fog, $transparent);
    }
    .slider__btn__wrapper-right {
      background-image: linear-gradient(to left, $gray-fog, $transparent);
    }
  }
  input {
    display: none;
  }

  .slider__main {
    width: 100%;
    display: flex;
    .slider__nav-manual {
      width: 100%;
      position: absolute;
      bottom: 15px;
      display: flex;
      justify-content: center;
      align-items: center;
      .slider__nav-manual__btn {
        width: 12px;
        height: 12px;
        border-radius: 50%;
        background-color: $gray-dove;
        opacity: 0.7;
        transition: all 0.5s ease;
        cursor: pointer;
        &:hover {
          background-color: $white;
          opacity: 1;
        }
        &:not(:last-child) {
          margin-right: 8px;
        }
      }
    }

    #radio1:checked ~ .first-slide {
      margin-left: 0;
    }

    #radio2:checked ~ .first-slide {
      margin-left: -100%;
    }

    #radio3:checked ~ .first-slide {
      margin-left: -200%;
    }

    #radio1:checked ~ .slider__nav-manual .slider__nav-manual__btn1,
    #radio2:checked ~ .slider__nav-manual .slider__nav-manual__btn2,
    #radio3:checked ~ .slider__nav-manual .slider__nav-manual__btn3 {
      background-color: $white;
      opacity: 1;
      width: 18px;
      height: 18px;
    }

    .slide {
      width: 100%;
      position: relative;
      transition: all 0.5s ease;
      .slide-img {
        width: 100vw;
        margin-top: -6%;
      }
      .slide__content {
        width: 100%;
        height: 100%;
        color: $text-light-1;
        text-shadow: 0 0 4px $gray;
        position: absolute;
        top: 0;
        margin-left: 10%;
        transition: all 0.8s ease;
        display: flex;
        flex-direction: column;
        justify-content: center;
        gap: 16px;
        .slide__content__subheading {
          font-size: 2rem;
          font-weight: 500;
        }
        .slide__content__heading {
          font-size: 4rem;
          font-weight: 500;
          line-height: 1;
        }
        .slide__content__text {
          width: 40%;
          font-size: 1.2rem;
          font-weight: 400;
          overflow: hidden;
          text-overflow: ellipsis;
          word-wrap: break-word;
          display: -webkit-box;
          -webkit-line-clamp: 3;
          -webkit-box-orient: vertical;
        }
        .slide__content__btn {
          width: 8rem;
          padding: 6px 0;
          font-size: 1.2rem;
          color: $text-light-1;
          border: none;
          border-radius: 6px;
          box-shadow: 0 0 4px $white;
          background-color: $marigold;
          &:hover {
            opacity: 0.85;
          }
        }
      }
    }
  }
  .slider__btn__wrapper {
    width: 8%;
    height: 100%;
    position: absolute;
    top: 0;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    .slider__btn {
      width: 40px;
      aspect-ratio: 1/1;
      background: none;
      border-radius: 50%;
      color: $white;
      font-size: 3rem;
      display: none;
      transition: all 0.2s ease;
      cursor: pointer;
      box-sizing: border-box;
    }
  }
  .slider__btn__wrapper-left {
    left: 0;
  }
  .slider__btn__wrapper-right {
    right: 0;
  }
  .slider__btn__wrapper-left:hover .slider__btn .mdi,
  .slider__btn__wrapper-right:hover .slider__btn .mdi {
    color: $primary;
    border: none;
  }
}
</style>
