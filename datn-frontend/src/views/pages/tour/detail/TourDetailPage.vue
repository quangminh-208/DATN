<script lang="ts">
import {defineComponent, inject} from 'vue'
import {TourService} from "@/base/service/tour.service";
import {Tour} from "@/base/model/tour.model";
import TourDetailComponent from "@/components/TourDetailComponent.vue";
import SlideComponent from "@/components/SlideComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";
import {toast} from "vue3-toastify";

export default defineComponent({
    name: 'TourDetailPage',
    components: {FooterComponent, TourDetailComponent, HeaderComponent, SlideComponent},
    setup() {
        return {
            tourService: inject('tourService') as TourService
        }
    },
    data() {
        return {
            tour: new Tour()
        }
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
    }
})
</script>

<template>
    <header-component/>
    <slide-component/>
    <div class="d-flex justify-content-center">
        <div class="container">
            <tour-detail-component :tour="tour"/>
        </div>
    </div>
    <footer-component/>
</template>

<style lang="scss" scoped>
.heading {
    font-weight: 500;
    text-align: center;
    margin: 40px 0;
    .heading__badge {
        background-color: $livingCoral;
    }
}
</style>