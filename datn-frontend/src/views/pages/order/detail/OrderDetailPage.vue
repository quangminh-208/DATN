<script lang="ts">
import {defineComponent, inject} from 'vue'
import FooterComponent from "@/components/FooterComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";
import {Order} from "@/base/model/order.model";
import {OrderService} from "@/base/service/order.service";
import OrderDetailComponent from "@/components/OrderDetailComponent.vue";

export default defineComponent({
    name: 'OrderDetailPage',
    components: {OrderDetailComponent, HeaderComponent, FooterComponent},
    data() {
        return {
            orderId: '' as string,
            order: new Order()
        }
    },
    methods: {
        async getOrder() {
            this.orderService.get(this.orderId).then((data) => {
                this.order = data;
            });
        }
    },
    created() {
        this.orderId = this.$route.params.id as string;
        this.getOrder();
    },
    setup() {
        return {
            orderService: inject('orderService') as OrderService,
        }
    }

})
</script>

<template>
    <header-component/>
    <div class="container my-10 d-flex justify-content-center">
        <order-detail-component :order="order"/>
    </div>
    <footer-component/>
</template>