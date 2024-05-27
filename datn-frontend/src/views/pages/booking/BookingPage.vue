<script lang="ts">
import {defineComponent, inject} from 'vue';
import HeaderComponent from "@/components/HeaderComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";
import {TourService} from "@/base/service/tour.service";
import {toast} from "vue3-toastify";
import {Tour} from "@/base/model/tour.model";
import formatMoney from "@/plugins/utils";
import "intro.js/minified/introjs.min.css";
import CustomerComponent from "@/components/CustomerComponent.vue";


export default defineComponent({
    name: 'BookingPage',
    components: {CustomerComponent, FooterComponent, HeaderComponent},
    setup() {
        return {
            tourService: inject('tourService') as TourService
        }
    },
    data() {
        return {
            model: {
                tourId: '',
                quantityForAdult: 0,
                quantityForChildren: 0,
                quantityForBaby: 0,
                voucherCode: '',
                customers: [] as any[]
            },
            tour: new Tour(),
            stepper: {
                currentStep: 1,
                items: ['Đặt Tour', 'Thanh toán', 'Hoàn thành'],
                success: {
                    1: false as boolean,
                    2: false as boolean,
                    3: false as boolean
                }
            },
            qrCode: {} as any
        }
    },
    methods: {
        async bookTour() {
            this.model.customers = [...this.getCustomer()]
            console.log(this.model)
            this.tourService.bookTour(this.model).then((response) => {
                toast.success(response?.message);
                this.getDetailTour();
                this.stepper.success[1] = true;
                this.stepper.currentStep = 2;
                this.getQRCode();
            }).catch((error) => {
                toast.error(error.response?.data?.message)
            });
        },
        copyClipboard() {
            navigator.clipboard.writeText(this.model.tourId).then(() => {
                toast.info('Đã copy mã tour');
            });
        },
        getDetailTour() {
            this.tourService.getById(this.model.tourId).then((data) => {
                this.tour = data;
            });
        },
        formatMoney,
        getQRCode() {
            this.tourService.getQRCodePurchase(this.model.tourId).then((response) => {
                this.qrCode = JSON.parse(response.qrCode).data?.qrDataURL;
                this.stepper.currentStep = response?.currentStep;
                if (response?.currentStep >= 2) {
                    this.stepper.success[1] = true;
                }
                if (response?.currentStep >= 3) {
                    this.stepper.success[2] = true;
                    this.stepper.success[3] = true;
                }
            });
        },
        successPurchase() {
            this.tourService.successPurchase(this.model.tourId).then((response) => {
                toast.success(response?.message);
                this.getQRCode();
            });
        },
        getCustomer(): any[] {
            const adults = this.$refs.customer?.adult as any[];
            const children = this.$refs.customer?.children as any[];
            const baby = this.$refs.customer?.baby as any[];
            return [...adults, ...children, ...baby];
        }
    },
    created() {
        this.model.tourId = this.$route.params.id as string;
        this.getDetailTour();
        this.getQRCode();
    }
});
</script>

<template>
    <div>
        <header-component/>
        <div class="container my-10">
            <div class="row text-dark">
                <v-stepper>
                    <v-stepper-header v-model="stepper.currentStep">
                        <v-stepper-item
                            title="Đặt tour"
                            :value="1"
                            :complete="stepper?.success[1]"
                            :color="stepper?.success[1] ? 'success' : 'primary'"
                        ></v-stepper-item>

                        <v-divider></v-divider>

                        <v-stepper-item
                            :rules="[() => stepper.success[1]]"
                            title="Thanh toán"
                            :value="2"
                            :color="stepper?.success[2] ? 'success' : 'primary'"
                            :complete="stepper?.success[2]"
                        ></v-stepper-item>
                        <v-divider></v-divider>
                        <v-stepper-item
                            :rules="[() => stepper.success[2]]"
                            title="Hoàn thành"
                            :value="2"
                            :color="stepper?.success[3] ? 'success' : 'primary'"
                            :complete="stepper?.success[3]"
                        ></v-stepper-item>

                        <v-divider></v-divider>
                    </v-stepper-header>

                    <v-stepper-window v-model="stepper.currentStep">
                        <v-stepper-window-item :value="1">
                            <v-card title="Đặt tour" :flat="true">
                                <div class="row">
                                    <v-form class="col-6" @submit="bookTour" @keyup.enter="bookTour">
                                        <v-text-field type="number"
                                                      data-intro="Nhập số lượng người lớn"
                                                      v-model="model.quantityForAdult"
                                                      label="Người lớn"
                                                      outlined
                                                      required/>
                                        <v-text-field type="number"
                                                      v-model="model.quantityForChildren"
                                                      label="Trẻ em"
                                                      outlined
                                                      required/>
                                        <v-text-field type="number"
                                                      v-model="model.quantityForBaby"
                                                      label="Em bé"
                                                      outlined
                                                      required/>
                                        <customer-component :numberAdult="Number(model.quantityForAdult)"
                                                            :numberChildren="Number(model.quantityForChildren)"
                                                            :numberBaby="Number(model.quantityForBaby)"
                                                            ref="customer"/>
                                        <v-text-field v-model="model.voucherCode" label="Phiếu giảm giá"/>
                                        <v-btn color="primary" @click.prevent="bookTour">Đặt tour</v-btn>
                                    </v-form>
                                    <div class="col-6">
                                        <v-card>
                                            <v-card-title>
                                                <h5>Mã tour: {{ model.tourId }} <i title="Copy mã tour"
                                                                                   @click="copyClipboard"
                                                                                   class="mdi mdi-content-copy"
                                                                                   role="button"></i></h5>
                                            </v-card-title>
                                            <v-card-text>
                                                <h4>{{ tour?.name }}</h4>
                                                <p>Giá: {{ formatMoney(tour?.priceAdult) }}</p>
                                                <p>Giá trẻ em: {{ formatMoney(tour?.priceChild) }}</p>
                                                <p>Giá em bé: {{ formatMoney(tour?.priceBaby) }}</p>
                                            </v-card-text>
                                            <v-card-text>
                                                <p>Người lớn: còn
                                                    <b>{{
                                                            Number(tour?.quantityForAdult) - Number(tour?.soldForAdult)
                                                        }}</b>
                                                </p>
                                                <p>Trẻ em: còn
                                                    <b>{{
                                                            Number(tour?.quantityForChild) - Number(tour?.soldForChild)
                                                        }}</b>
                                                </p>
                                                <p>Em bé: còn
                                                    <b>{{
                                                            Number(tour?.quantityForBaby) - Number(tour?.soldForBaby)
                                                        }}</b>
                                                </p>
                                            </v-card-text>
                                        </v-card>
                                    </div>
                                </div>
                            </v-card>
                        </v-stepper-window-item>
                        <v-stepper-window-item :value="2">
                            <v-card title="Thanh toán" :flat="true">
                                <div class="d-flex justify-content-center">
                                    <img :src="qrCode" class="w-25"/>
                                </div>
                                <div class="d-flex justify-content-center">
                                    <v-btn color="primary" @click.prevent="successPurchase">Đã thanh toán</v-btn>
                                </div>
                            </v-card>
                        </v-stepper-window-item>
                        <v-stepper-window-item :value="3">
                            <v-card :flat="true">
                                <!--                                 chúng tôi đang xử lý đơn hàng của bạn, vui lòng chờ trong giây lát-->
                                <div class="text-danger" v-if="stepper.currentStep === 3">
                                    <h4>Chúng tôi đang xử lý đơn hàng của bạn, vui lòng chờ trong giây lát</h4>
                                </div>
                                <div class="text-success" v-if="stepper.currentStep === 4">
                                    <h4>Đơn hàng của bạn đã được xác nhận</h4>
                                </div>
                            </v-card>
                        </v-stepper-window-item>
                    </v-stepper-window>
                </v-stepper>
            </div>
        </div>
        <footer-component/>
    </div>
</template>

<style lang="scss">

</style>