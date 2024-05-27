<script>
import {defineComponent} from 'vue'
import OrderComponent from "@/components/OrderComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";

export default defineComponent({
    name: 'OrderPage',
    components: {FooterComponent, HeaderComponent, OrderComponent},
    data() {
        return {
            orderPage: {
                content: [],
                totalPages: 0,
                totalElements: 0,
                last: true,
                size: 0,
            },
            searchParams: {
                page: 0,
                size: 10,
                startDate: '',
                endDate: '',
            }
        }
    }

})
</script>

<template>
    <header-component/>
    <div class="container my-10">
        <order-component/>
    </div>
    <footer-component/>
</template>