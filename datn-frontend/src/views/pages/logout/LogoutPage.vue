<script lang="ts">
import {defineComponent, inject} from "vue";
import {AuthService} from "@/base/service/auth.service";
import {toast} from "vue3-toastify";

export default defineComponent({
    name: 'LogoutPage',
    data() {
        return {
            second : 3
        }
    },
    methods: {
        logout() {
            this.authService.logout().then((response) => {
                toast.success(response?.message);
                localStorage.removeItem('token');
                localStorage.removeItem('user');
                localStorage.removeItem('loggedIn');
                setTimeout(() => {
                    this.$router.push('/');
                }, 3000);
            }).catch((error) => {
                toast.error(error.response?.data?.message)
            });
        }
    },
    created() {
        this.logout();
    },
    setup() {
        const authService = inject('authService') as AuthService;
        return {
            authService
        }
    }
})
</script>

<template>
    <div class="text-center text-dark">
        <h1>Logging out...</h1>
    </div>
</template>