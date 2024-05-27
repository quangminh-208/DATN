<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <h2>Thông tin khách hàng</h2>
            </div>
        </div>

        <!-- Thông tin người lớn -->
        <div class="card mb-2" v-if="numberAdult > 0">
            <div class="card-body">
                <div class="row">
                    <div class="col-12">
                        <h4>Người lớn</h4>
                    </div>
                </div>
                <div v-for="(item, index) in numberAdult" :key="index" class="mb-2">
                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <v-text-field type="text" :label="'Họ và tên ' + (index + 1)" class="form-control"
                                              :id="'adultName' + index" placeholder="Họ và tên"
                                              v-model="adult[index].name"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <div class="form-group">
                                <v-text-field type="text" :label="'Địa chỉ ' + (index + 1)" class="form-control"
                                              :id="'adultAddress' + index" placeholder="Địa chỉ"
                                              v-model="adult[index].address"/>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="form-group">
                                <v-text-field type="email" :label="'Email ' + (index + 1)" class="form-control"
                                              :id="'adultEmail' + index" placeholder="Email"
                                              v-model="adult[index].email"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Thông tin trẻ em -->
        <div class="card mb-2" v-if="numberChildren > 0">
            <div class="card-body">
                <div class="row">
                    <div class="col-12">
                        <h4>Trẻ em</h4>
                    </div>
                </div>
                <div v-for="(item, index) in numberChildren" :key="index" class="mb-2">
                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <v-text-field type="text" :label="'Họ và tên ' + (index + 1)" class="form-control"
                                              :id="'childName' + index" placeholder="Họ và tên"
                                              v-model="children[index].name"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <div class="form-group">
                                <v-text-field type="text" :label="'Địa chỉ ' + (index + 1)" class="form-control"
                                              :id="'childAddress' + index" placeholder="Địa chỉ"
                                              v-model="children[index].address"/>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="form-group">
                                <v-text-field type="email" :label="'Email ' + (index + 1)" class="form-control"
                                              :id="'childEmail' + index" placeholder="Email"
                                              v-model="children[index].email"/>
                            </div>
                        </div>
                    </div>
                </div>
                <hr/>
            </div>
        </div>

        <!-- Thông tin em bé -->
        <div class="card mb-2" v-if="numberBaby > 0">
            <div class="card-body">
                <div class="row">
                    <div class="col-12">
                        <h4>Em bé</h4>
                    </div>
                </div>
                <div v-for="(item, index) in numberBaby" :key="index" class="mb-2">
                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <v-text-field type="text" :label="'Họ và tên ' + (index + 1)" class="form-control"
                                              :id="'babyName' + index" placeholder="Họ và tên"
                                              v-model="baby[index].name"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <div class="form-group">
                                <v-text-field type="text" :label="'Địa chỉ ' + (index + 1)" class="form-control"
                                              :id="'babyAddress' + index" placeholder="Địa chỉ"
                                              v-model="baby[index].address"/>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="form-group">
                                <v-text-field type="email" :label="'Email ' + (index + 1)" class="form-control"
                                              :id="'babyEmail' + index" placeholder="Email"
                                              v-model="baby[index].email"/>
                            </div>
                        </div>
                    </div>
                </div>
                <hr/>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import {Customer} from '@/base/model/customer.model';

export default defineComponent({
    name: 'CustomerComponent',
    data() {
        return {
            adult: [{
                name: '',
                address: '',
                email: '',
                type: Customer.TYPE_ADULT,
            }] as Customer[],
            children: [{
                name: '',
                address: '',
                email: '',
                type: Customer.TYPE_CHILDREN,
            }] as Customer[],
            baby: [{
                name: '',
                address: '',
                email: '',
                type: Customer.TYPE_BABY,
            }] as Customer[],
        };
    },
    props: {
        numberAdult: {
            type: Number,
            default: 0,
        },
        numberChildren: {
            type: Number,
            default: 0,
        },
        numberBaby: {
            type: Number,
            default: 0,
        },
    },
    methods: {
        updateArray(arr: Customer[], length: number) {
            if (length > arr.length) {
                for (let i = arr.length; i < length; i++) {
                    arr.push({
                        name: '',
                        address: '',
                        email: '',
                        type: arr[0].type,
                    });
                }
            } else if (length < arr.length) {
                arr.splice(length);
            }
        },
    },
    watch: {
        numberAdult(newVal) {
            this.updateArray(this.adult, newVal);
        },
        numberChildren(newVal) {
            this.updateArray(this.children, newVal);
        },
        numberBaby(newVal) {
            this.updateArray(this.baby, newVal);
        },
    },
});
</script>
