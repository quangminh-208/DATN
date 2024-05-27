<script lang="ts">
import {defineComponent, inject} from 'vue'
import {AuthService} from "@/base/service/auth.service";
import {UserRegisterModel} from "@/base/model/auth.model";
import {toast} from 'vue3-toastify';

export default defineComponent({
    name: 'LoginView',
    setup() {
        return {
            authService: inject('authService') as AuthService
        }
    },
    data() {
        return {
            model: {
                email: '',
                username: '',
                password: '',
                confirmPassword: '',
            } as UserRegisterModel,
            error: ''
        }
    },
    methods: {
        async login() {
            this.authService.register(this.model).then((response) => {
                toast.success(response?.message);
                this.$router.push('/login')
            }).catch((error) => {
                this.error = error.response.data.message;
            });
        }
    },
})
;
</script>

<template>
    <section class="vh-100">
        <div class="container-fluid h-custom">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-md-9 col-lg-6 col-xl-5">
                    <img
                        src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                        class="img-fluid"
                        alt="Sample image"
                    />
                </div>
                <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                    <div class="row justify-content-center mb-5">
                        <img
                            src="@/assets/images/logo-2.png"
                            class="header__main__logo__img img-fluid col-8"
                        />
                    </div>
                    <div v-if="error" class="alert alert-danger">{{ error }}</div>
                    <form @submit.prevent="login">
                        <div class="form-outline mb-4">
                            <label class="form-label text-dark" for="email">Email</label>
                            <input
                                id="email"
                                class="form-control form-control-lg"
                                placeholder="email"
                                value
                                required
                                autofocus
                                v-model="model.email"
                            />
                        </div>
                        <div class="form-outline mb-4">
                            <label class="form-label text-dark" for="username">Username</label>
                            <input
                                id="username"
                                class="form-control form-control-lg"
                                placeholder="Username"
                                value
                                required
                                autofocus
                                v-model="model.username"
                            />
                        </div>

                        <!-- Password input -->
                        <div class="form-outline mb-3">
                            <label class="form-label text-dark" for="password">Password</label>
                            <input
                                type="password"
                                id="password"
                                class="form-control form-control-lg"
                                placeholder="Enter password"
                                required
                                v-model="model.password"
                            />
                        </div>

                        <div class="form-outline mb-3">
                            <label class="form-label text-dark" for="confirmPassword">Confirm Password</label>
                            <input
                                type="password"
                                id="confirmPassword"
                                class="form-control form-control-lg"
                                placeholder="Confirm password"
                                required
                                v-model="model.confirmPassword"
                            />
                        </div>

                        <div class="text-center text-lg-start mt-4 pt-2">
                            <button
                                type="submit"
                                class="btn btn-primary btn-lg register">
                                Register
                            </button>
                            <p class="text-dark fs-6 small mt-2 pt-1 mb-0">
                                Have an account?
                                <RouterLink to="/login" class="link-danger">Login</RouterLink>
                            </p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div
            class="d-flex flex-column flex-md-row text-center text-md-start justify-content-between py-4 px-4 px-xl-5 bg-primary"
        >
            <!-- Copyright -->
            <div class="text-white mb-3 mb-md-0">Copyright © 2024. All rights reserved.</div>


            <!-- Right -->
            <div>
                <RouterLink to="">
                    <i class="fa fa-facebook-square"></i>
                </RouterLink>
                <RouterLink to="">
                    <i class="fa fa-twitter"></i>
                </RouterLink>
                <RouterLink to="">
                    <i class="fa fa-linkedin-square"></i>
                </RouterLink>
            </div>
        </div>
    </section>
</template>

<style lang="scss">
@import "style";
</style>
