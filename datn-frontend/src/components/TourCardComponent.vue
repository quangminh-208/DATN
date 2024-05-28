<script lang="tsx">
import {defineComponent} from "vue";
import {Tour} from "@/base/model/tour.model";
import formatMoney from "@/plugins/utils";

export default defineComponent({
    name: 'TourCardComponent',
    props: {
        tour: {
            type: Tour,
            default: () => new Tour(),
            required: true
        }
    },
    methods: {
        detail() {
            this.$router.push("/tour/" + this.tour?.id);
        },
        formatMoney
    }
});
</script>

<template>
    <div>
        <div class="tour-card border-1 mb-7">
            <div role="button" title={tour.value?.name} class="tour-image"
                 :style="'background-image :url(data:image/png;base64,' + tour?.image + ')'"></div>
            <div class="tour-info">
                <div class="tour-title text-dark limit-line-1">{{ tour?.name }}</div>
                <div class="tour-price text-dark">{{ formatMoney(tour?.priceAdult) }}</div>
                <div class="tour-rating d-flex">
                    <div>
                    </div>
                    <div class="ml-auto">
                        <v-btn @click="detail" title="Chi tiết" density="default" color="blue-lighten-2">
                            <span>Chi tiết</span>
                        </v-btn>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.tour-card {
    width: 100%;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    overflow: hidden;
    transition: transform 0.3s ease;
}

.tour-card:hover {
    transform: scale(1.05);
}

.tour-image {
    width: 100%;
    height: 175px;
    background-size: cover;
    background-position: center;
}

.tour-info {
    padding: 10px;
}

.tour-title {
    font-size: 1rem;
    font-weight: bold;
    margin-bottom: 10px;
}

.tour-price {
    color: #f33f3f;
    font-size: 0.8rem;
}

.total-reviews {
    font-size: 0.7rem;
}

@media (max-width: 768px) {
    .tour-card {
        max-width: 100%;
    }
}

@media (max-width: 576px) {
    .tour-card {
        margin: 20px 0;
    }
}

.tour-description {
    margin: 10px 0;
    font-size: 0.7rem;
}

</style>
