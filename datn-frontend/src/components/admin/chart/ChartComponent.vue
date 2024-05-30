<script lang="ts">
import {defineComponent, inject} from "vue";
import {Bar} from 'vue-chartjs'
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip} from 'chart.js'
import type {OrderService} from "@/base/service/order.service";

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default defineComponent({
    components: {
        Bar
    }, setup() {
        return {
            orderService: inject('orderService') as OrderService
        }
    },
    data() {
        const currentDate = new Date();
        const currentMonth = currentDate.getMonth();
        const currentYear = currentDate.getFullYear()
        return {
            chartData: {
                labels: ['Tour xác nhận thanh toán ', 'Tour đã hủy', 'Tour đã đặt'],
                datasets: [
                    {
                        label: 'Doanh thu tháng ' + currentMonth + "/" + currentYear,
                        backgroundColor: ['#f87979', '#f87979', '#f87979'],
                        data:[33900000,0,33900000]
                    }]
            },
            chartOptions: {
                responsive: true
            }
        }
    }, created() {
        this.orderService.chart().then((data) => {
            if (data) {
                this.chartData.datasets[0].data = data
            }
        });
    }
})
</script>

<template>
    <Bar
        id="my-chart-id"
        :options="chartOptions"
        :data="chartData"
    />
</template>