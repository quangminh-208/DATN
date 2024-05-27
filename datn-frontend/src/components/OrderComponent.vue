<script lang="ts">
import {defineComponent, inject} from 'vue'
import {OrderService} from "@/base/service/order.service";
import {OrderStatusText} from "@/base/model/order.model";
import formatMoney from "@/plugins/utils";

export default defineComponent({
    name: 'OrderComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index'},
            {title: 'Mã đơn hàng', value: 'id'},
            {title: 'Ngày đặt', value: 'createDate'},
            {title: 'Tên tour', value: 'tourName'},
            {title: 'Tổng tiền', value: 'totalPayment'},
            {title: 'Trạng thái', value: 'status'},
            {title: 'Hành động', value: 'action'}
        ];
        return {
            orderService: inject('orderService') as OrderService,
            headers,
        }
    },
    data() {
        return {
            orderPage: {
                content: [],
                totalPages: 0,
                totalElements: 0,
                last: true,
                size: 0,
            } as any,
            searchParams: {
                page: 0,
                size: 10,
                startDate: null,
                endDate: null,
            },
            isLoading: false,
        }
    },
    methods: {
        async search() {
            this.isLoading = true;
            this.orderService.findBySearchForUser(this.searchParams).then((data) => {
                this.orderPage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        OrderStatusText,
        viewDetail(item: any) {
            this.$router.push('/order/' + item.id);
        },
        formatMoney,
        deleteOrder(item: any) {
            if (item) {
                this.orderService.delete(item?.id).then(() => {
                    this.search();
                });
            }
        }
    },
    created() {
        this.search();
    }

})
</script>

<template>
    <div class="text-dark">
        <div class="h3 text-center">
            Đơn hàng của bạn
        </div>
        <form>
            <div class="form-group row">
                <div class="col-sm-6">
                    <v-text-field label="Từ ngày" v-model="searchParams.startDate" id="startDate"
                                  type="datetime-local"/>
                </div>
                <div class="col-sm-6">
                    <v-text-field label="Đến ngày" v-model="searchParams.endDate" id="endDate" type="datetime-local"/>
                </div>
            </div>
            <div>
                <v-btn color="primary" @click.prevent="search" class="m-1">Tìm kiếm</v-btn>
                <v-btn color="secondary"
                       @click.prevent="searchParams = {page: 0, size: 10, startDate: null, endDate: null}">Xóa bộ lọc
                </v-btn>
            </div>
        </form>
        <div>
            <v-data-table
                :headers="headers"
                :items="orderPage.content"
                :items-per-page="searchParams.size"
                :total-items="orderPage.totalElements"
                :server-items-length="orderPage.totalElements"
                @update:items-per-page="searchParams.size = $event; search()"
                :loading="isLoading">
                <template v-slot:[`item.index`]="{index}">
                    {{ index + 1 }}
                </template>
                <template v-slot:[`item.createDate`]="{item}">
                    {{ new Date(item?.createDate).toLocaleString() }}
                </template>
                <template v-slot:[`item.tourName`]="{item}">
                    {{ item?.tour?.name }}
                </template>
                <template v-slot:[`item.status`]="{item}">
                    {{ OrderStatusText(item?.status) }}
                </template>
                <template v-slot:[`item.totalPayment`]="{item}">
                    {{ formatMoney(item?.totalPayment) }}
                </template>
                <template v-slot:[`item.action`]="{item}">
                    <v-icon @click.prevent="viewDetail(item)" title="Xem đơn hàng" class="m-1">mdi-eye</v-icon>
                    <v-icon title="Hủy đơn" @click.prevent="deleteOrder(item)">mdi-close-circle</v-icon>
                </template>
            </v-data-table>
        </div>
    </div>
</template>

<style lang="scss">

</style>