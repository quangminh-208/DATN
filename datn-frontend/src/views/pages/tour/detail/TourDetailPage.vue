<script lang="ts">
import { defineComponent, inject } from "vue";
import { TourService } from "@/base/service/tour.service";
import { Tour } from "@/base/model/tour.model";
import TourDetailComponent from "@/components/TourDetailComponent.vue";
import SlideComponent from "@/components/SlideComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";
import { toast } from "vue3-toastify";

export default defineComponent({
  name: "TourDetailPage",
  components: { FooterComponent, TourDetailComponent, HeaderComponent },
  setup() {
    return {
      tourService: inject("tourService") as TourService,
    };
  },
  data() {
    return {
      tour: new Tour(),
    };
  },
  methods: {
    getById() {
      const id = this.$route.params.id as string;
      this.tourService.getById(id).then((data) => {
        this.tour = data;
      });
    },
  },
  created() {
    this.getById();
  },
});
</script>

<template>
  <header-component />
  <div class="d-flex justify-content-center mb-10">
    <tour-detail-component :tour="tour" />
  </div>
  <footer-component />
</template>
