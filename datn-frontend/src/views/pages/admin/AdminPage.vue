<script lang="ts">
import {defineComponent} from "vue";
import type {User} from "@/base/model/user.model";

export default defineComponent({
    name: "AdminPage",
    components: {},
    data() {
        return {
            component: [
                {name: 'user', show: false, label: 'Quản lý người dùng', routerLink: '/admin/user'},
                {name: 'category', show: false, label: 'Quản lý danh mục', routerLink: '/admin/category'},
                {name: 'tour', show: false, label: 'Quản lý tour', routerLink: '/admin/tour'},
                {name: 'post', show: false, label: 'Quản lý bài viết', routerLink: '/admin/post'},
                {name: 'voucher', show: false, label: 'Quản lý voucher', routerLink: '/admin/voucher'},
                {name: 'Chat', show: false, label: 'Quản lý tin nhắn', routerLink: '/admin/message'},
                {name: 'order', show: false, label: 'Quản lý đơn hàng', routerLink: '/admin/order'},
            ],
            userCurrent: JSON.parse(localStorage.getItem('user') as string) as User
        }
    },
    methods: {
        logout() {
            this.$router.push('/logout');
        }
    }
})
</script>

<template>
    <div class="container-fluid">
        <div class="row flex-nowrap">
            <div class="col-auto col-md-3 col-xl-2 p-0 bg-dark">
                <div class="d-flex flex-column align-items-center align-items-sm-start text-white min-vh-100">
                    <div class="w-100 bg-primary ">
                        <a href="/admin"
                        class="d-flex align-items-center py-3 me-md-auto text-white text-decoration-none justify-content-center">
                        <span class="fs-5 d-none d-sm-inline text-uppercase fw-bold">Dashboard</span>
                    </a>
                    </div>
                    <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start overflow-auto min-vh-100"
                        id="menu">
                        <li class="nav-item px-4">
                            <a href="#" class="nav-link align-middle px-0" @click.prevent="$router.push('/admin/chart')">
                                <i class="bi bi-bar-chart-line-fill"></i> <span
                                class="ms-1 d-none d-sm-inline">Thống kê</span>
                            </a>
                        </li>
                        <li class="px-4">
                            <a href="#submenu1" data-bs-toggle="collapse" class="nav-link px-0 align-middle">
                                <i class="bi bi-database-fill"></i> <span class="ms-1 d-none d-sm-inline">Quản lý dữ liệu bảng</span>
                            </a>
                            <ul class="collapse show nav flex-column ms-1" id="submenu1" data-bs-parent="#menu">
                                <li class="w-100" v-for="(item,index) in component" :key="index">
                                    <a class="nav-link px-0 d-flex m-3" @click.prevent="$router.push(item.routerLink)"
                                        role="button">
                                        <span class="d-none d-sm-inline" :title="item.label">{{ item.label }}</span>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <div class="user-wrapper dropdown bg-primary col-auto col-md-3 col-xl-2">
                        <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle"
                            id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
                            <v-avatar size="50" :image="'data:image/png;base64,' + userCurrent?.avatar"></v-avatar>
                            <span class="d-none d-sm-inline mx-1">{{userCurrent?.username}}</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser1">
                            <li><a class="dropdown-item text-danger" @click.prevent="logout()">Đăng xuất</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col p-3">
                <slot></slot>
            </div>
        </div>
    </div>
</template>

<style scoped>

a {
    color: #fff;
}

.user-wrapper {
    position:absolute;
    bottom: 0;
}

</style>