<script lang="ts">
import {defineComponent, inject} from 'vue'
import {OrderService} from "@/base/service/order.service";
import {Order, OrderStatusText} from "@/base/model/order.model";
import formatMoney from "@/plugins/utils";
import {CustomerTypeText} from "@/base/model/customer.model";

export default defineComponent({
    name: 'OrderDetailComponent',
    props: {
        order: {
            type: Order,
            required: true
        }
    },
    setup() {
        return {
            orderService: inject('orderService') as OrderService,
        }
    },
    methods: {
        formatMoney,
        OrderStatusText,
        CustomerTypeText
    }
})
</script>

<template>
    <div class="text-dark">
        <div class="flex justify-between items-center">
            <h1 class="text-2xl font-bold">Chi tiết đơn hàng</h1>
            <div>
                <router-link to="/order" class="btn btn-primary">Quay lại</router-link>
            </div>
        </div>
        <div class="mt-5">
            <div class="flex justify-between">
                <div>
                    <p><span class="font-bold">Mã đơn hàng:</span> {{ order.id }}</p>
                    <p><span class="font-bold">Ngày đặt:</span> {{ order.createDate }}</p>
                    <p><span class="font-bold">Tên tour:</span> {{ order?.tour?.name }}</p>
                    <p><span class="font-bold">Tổng tiền:</span> {{ formatMoney(order.totalPayment) }}</p>
                    <p><span class="font-bold">Trạng thái:</span> {{ OrderStatusText(order.status) }}</p>
                </div>
            </div>
            <div class="mt-5">
                <h2 class="text-center">Danh sách khách hàng</h2>
                <table class="table-auto w-full mt-3">
                    <thead>
                    <tr>
                        <th class="border px-4 py-2 fw-bold">STT</th>
                        <th class="border px-4 py-2 fw-bold">Họ tên</th>
                        <th class="border px-4 py-2 fw-bold">Địa chỉ</th>
                        <th class="border px-4 py-2 fw-bold">Email</th>
                        <td class="border px-4 py-2 fw-bold">Loại khách hàng</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(customer, index) in order.customers" :key="customer.id">
                        <td class="border px-4 py-2">{{ index + 1 }}</td>
                        <td class="border px-4 py-2">{{ customer?.name }}</td>
                        <td class="border px-4 py-2">{{ customer?.address }}</td>
                        <td class="border px-4 py-2">{{ customer.email }}</td>
                        <td class="border px-4 py-2">{{ CustomerTypeText(customer?.type) }}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>