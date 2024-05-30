<script lang="ts">
// Header menu sticky
import router from "@/router";
import { defineComponent, inject, reactive, ref } from "vue";
import { Category } from "@/base/model/category.model";
import { CategoryService } from "@/base/service/category.service";

window.onscroll = function () {
  if (window.scrollY >= 104) {
    document
      .querySelector(".header__menu__wrapper")
      ?.classList.add("fixed-header");
  } else
    document
      .querySelector(".header__menu__wrapper")
      ?.classList.remove("fixed-header");
};

export default defineComponent({
  name: "HeaderComponent",
  data() {
    return {
      keyword: this.$route.query.keyword || "",
    };
  },
  methods: {
    router() {
      return router;
    },
    search(categoryId: string | null | undefined = null) {
      const isString = (value: any): value is string => {
        return typeof value === "string";
      };

      const validKeyword = isString(this.keyword) ? this.keyword : "";

      let validCategoryId = isString(categoryId) ? categoryId : "";
      if (this.$route.query.categoryId) {
        validCategoryId = this.$route.query.categoryId as string;
      }
      if (validKeyword !== "" || validCategoryId !== "") {
        document.location.href = `/search?keyword=${validKeyword}&categoryId=${validCategoryId}`;
      }
    },
  },
  props: {},
  setup() {
    const error = ref(null);
    const loggedIn = ref(false);
    const categoryList = reactive([] as Category[]);
    const categoryService = inject("categoryService") as CategoryService;
    const isAdmin = JSON.parse(
      localStorage.getItem("user") as string
    )?.roleList?.find((role: any) => role === "ROLE_ADMIN");

    if (
      localStorage.getItem("token") !== null &&
      localStorage.getItem("loggedIn") === "true"
    ) {
      loggedIn.value = true;
    }

    const items = [
      { title: "Tài khoản", path: "/profile", priority: 1 },
      { title: "Đơn hàng", path: "/order", priority: 2 },
      { title: "Tin nhắn", path: "/message", priority: 3 },
      { title: "Đăng xuất", path: "/logout", priority: 5 },
    ];

    if (isAdmin) {
      items.push({ title: "Quản lý", path: "/admin", priority: 4 });
    }

    items.sort((a, b) => a.priority - b.priority);

    const redirect = (path: any) => {
      router.push(path);
    };

    const fetchCategory = async () => {
      try {
        const response = await categoryService.findAllCategoryClient();
        categoryList.splice(0, categoryList.length, ...response); // Update categoryList with response
      } catch (err: any) {
        error.value = err.message || "Error fetching categories";
      }
    };

    fetchCategory(); // Call fetchCategory() to fetch categories asynchronously

    return { error, loggedIn, items, redirect, categoryList };
  },
  mounted() {},
});
</script>

<template>
  <div id="header">
    <!-- Header navbar -->
    <div class="header__navbar d-flex justify-content-center">
      <div class="container-xl">
        <ul class="header__navbar__list">
          <li class="header__navbar__item">
            <RouterLink to="" class="header__navbar__item__link">
              <span class="mdi mdi-map-marker"></span>
              Minh Khai, Bắc Từ Liêm, Hà Nội
            </RouterLink>
          </li>
          <li class="header__navbar__item">
            <a href="tel:19001900" class="header__navbar__item__link">
              <span class="mdi mdi-phone"></span>
              1900 1900
            </a>
          </li>
          <li class="header__navbar__item">
            <RouterLink to="" class="header__navbar__item__link mx-1">
              <span class="mdi mdi-facebook"></span>
            </RouterLink>
            <RouterLink to="" class="header__navbar__item__link mx-1">
              <span class="mdi mdi-instagram"></span>
            </RouterLink>
            <RouterLink to="" class="header__navbar__item__link mx-1">
              <span class="mdi mdi-youtube"></span>
            </RouterLink>
          </li>
        </ul>
        <ul class="header__navbar__list" v-if="!loggedIn">
          <li class="header__navbar__item header__navbar__item--separate">
            <RouterLink
              to="/register"
              class="header__navbar__item__link header__navbar__item__link--strong"
            >
              Đăng ký
            </RouterLink>
          </li>
          <li class="header__navbar__item">
            <RouterLink
              to="/login"
              class="header__navbar__item__link header__navbar__item__link--strong"
            >
              Đăng nhập
            </RouterLink>
          </li>
        </ul>
      </div>
    </div>

    <!-- Header main -->
    <div class="header__main d-flex justify-content-center">
      <div class="container-xl">
        <RouterLink to="/" class="router-link">
          <img
            src="../assets/img/logo.png"
            alt="Logo Mona Tourist"
            class="header__main__logo__img"
          />
        </RouterLink>

        <div class="header__main__search">
          <input
            type="text"
            class="header__main__search-box"
            placeholder="Tìm kiếm..."
            v-model="keyword"
          />
          <button class="header__main__search-btn" @click.prevent="search">
            <span class="mdi mdi-magnify"></span>
          </button>
        </div>
        <ul class="header__main__list mr-6" v-if="loggedIn">
          <li class="header__main__option">
            <v-menu>
              <template v-slot:activator="{ props }">
                <v-btn color="primary" v-bind="props">
                  <span class="mdi mdi-account fs-3"></span>
                </v-btn>
              </template>
              <v-list>
                <v-list-item
                  v-for="(item, index) in items"
                  :key="index"
                  :value="index"
                >
                  <v-list-item-title
                    @click="redirect(item?.path)"
                    :class="{ 'text-danger': item.priority === 5 }"
                    >{{ item.title }}
                  </v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </li>
        </ul>
      </div>
    </div>

    <!-- Header menu -->
    <div
      class="header__menu__wrapper hide-on-mobile d-flex justify-content-center"
    >
      <div class="header__menu container-xxl">
        <ul class="header__menu__list">
          <li class="header__menu__item">
            <RouterLink to="/" class="header__menu__item__link">
              Trang chủ</RouterLink
            >
          </li>
          <li
            class="header__menu__item dropdown-center"
            v-for="(item, index) in categoryList"
            :key="index"
          >
            <a class="header__menu__item__link">{{ item?.name }}</a>
            <div class="dropdown-menu container">
              <div
                class="row justify-content-around"
                v-if="item?.children && item?.children?.length > 0"
              >
                <div
                  class="col-4"
                  v-for="(subItem, subIndex) in item?.children"
                  :key="subIndex"
                >
                  <div role="button" class="dropdown-header fw-bold">
                    <h5 @click.prevent="search(subItem?.id)">
                      {{ subItem?.name }}
                    </h5>
                  </div>
                  <div
                    class="text-dark dropdown-item"
                    v-for="(location, indexTour) in subItem?.children"
                    :key="indexTour"
                  >
                    <p @click.prevent="search(location?.id)">
                      {{ location?.name }}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </li>
          <li class="header__menu__item">
            <RouterLink to="/post" class="header__menu__item__link">
              Tin tức</RouterLink
            >
          </li>
          <li class="header__menu__item">
            <RouterLink to="/about" class="header__menu__item__link">
              Giới thiệu</RouterLink
            >
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.fixed-header {
  position: fixed !important;
  top: 0 !important;
  right: 0;
  left: 0;
  color: $white-dove !important;
  background-color: $primary !important;
  animation: moveDown ease 0.6s;
  z-index: 100;
}

/* Header navbar */
.header__navbar {
  background-color: $primary-blur-2;

  .container-xl {
    display: flex;
    justify-content: space-between;

    .header__navbar__list {
      list-style: none;
      padding-left: 0;
      margin: 0;

      .header__navbar__item {
        margin: 0 8px;
        position: relative;
      }

      .header__navbar__item,
      .header__navbar__item__link {
        display: inline-block;
        color: $white;
        font-size: 0.9rem;
        text-decoration: none;
        font-weight: 400;
        .mdi {
          font-size: 1rem;
        }
      }

      .header__navbar__item__link {
        &:hover {
          opacity: 0.8;
          cursor: pointer;
        }
      }

      .header__navbar__item__link .header__navbar__item__link--strong {
        font-weight: 400;
      }

      .header__navbar__item--separate::after {
        content: "";
        display: block;
        position: absolute;
        border-left: 1px solid $text-light-1;
        height: 16px;
        top: 4px;
        right: -9px;
      }

      .header__navbar__icon {
        font-size: 0.9rem;
        text-decoration: none;
        margin: 0 4px;
      }
    }
  }
}

/* Header main */
.header__main {
  background-color: $primary;

  .container-xl {
    padding: 10px 0;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .header__main__logo__img {
      width: 300px;
    }

    .header__main__search {
      height: 40px;
      display: flex;
      width: 35%;

      .header__main__search-box {
        width: 100%;
        height: 100%;
        padding: 10px;
        font-size: 1rem;
        border-style: none;
        border-top-left-radius: 5px;
        border-bottom-left-radius: 5px;
        transition: all 0.3s ease;
        outline: none;
        background-color: $white;
        &:focus {
          box-shadow: 0 0 5px $bright-light-1;
        }
      }

      .header__main__search-btn {
        width: 60px;
        height: 100%;
        font-size: 1.5rem;
        background-color: $primary-dark;
        color: $white;
        cursor: pointer;
        border-style: none;
        border-top-right-radius: 5px;
        border-bottom-right-radius: 5px;

        &:hover {
          background-color: $primary-dark-2;
        }
      }
    }

    .header__main__list {
      list-style: none;
      margin: 0;
      padding: 0;

      .header__main__option {
        display: inline-block;

        .header__main__option__link {
          cursor: pointer;

          &:hover .header__main-icon {
            color: $primary-dark-2;
          }
        }
      }
    }

    .header__main-icon {
      font-size: 2rem;
      color: $white;
      margin: 0 16px;
    }
  }
}

/* Header menu */
.header__menu__wrapper {
  background-color: $white-soft;
  box-shadow: 0 2px 10px $gray-soft;
  .header__menu {
    display: flex;
    align-items: center;
    justify-content: space-around;

    .header__menu__list {
      display: inline-block;
      list-style: none;
      padding: 0;
      margin: 0;

      .header__menu__item {
        display: inline-block;
        margin: 0 20px;
        padding: 8px;

        .header__menu__item__link {
          text-decoration: none;
          border: 2px solid $transparent;
          color: $text-dark-1;
          font-size: 1rem;
          font-weight: 500;
          text-transform: uppercase;
          transition: all 0.4s ease;

          &:hover {
            color: $primary-dark-2;
            border-bottom: 2px solid $primary-dark;
          }
        }

        .dropdown-menu {
          min-width: 60vw;
          left: -150%;
        }
      }

      .dropdown-center:hover > .dropdown-menu {
        display: block;
      }

      .dropdown-center > .dropdown-toggle:active {
        /*Without this, clicking will make it sticky*/
        pointer-events: none;
      }
    }
  }
}

.header__submenu_link {
  text-decoration: none;
  color: $text-dark-1;
  font-weight: 500;

  &:hover {
    color: $primary-dark-2;
  }
}

// ========== RESPONSIVE ==========
/* Desktop */
@media (max-width: 1200px) {
  .header__menu__item__link {
    font-size: 0.8rem !important;
  }
}

/* Mobile & Tablet */
@media (max-width: 1023px) {
}

/* Tablet */
@media (min-width: 740px) and (max-width: 1023px) {
  html {
    font-size: 14px;
  }
}

/* Mobile */
@media (max-width: 739px) {
  html {
    font-size: 12px;
  }
}
</style>
