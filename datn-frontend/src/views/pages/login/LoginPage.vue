<script lang="ts">
import {defineComponent, inject} from 'vue'
import {AuthService} from "@/base/service/auth.service";
import type {AuthModel} from "@/base/model/auth.model";

export default defineComponent({
    name: 'LoginView',
    setup(){
        return {
            authService: inject('authService') as AuthService
        }
    },
    data() {
        return {
            model: {
                username: '',
                password: '',
                rememberMe: false
            },
            error: ''
        }
    },
    methods: {
        async login() {
            this.authService.login(this.model as AuthModel).then((response) => {
                localStorage.setItem('token', response?.token);
                localStorage.setItem('user', JSON.stringify(response));
                localStorage.setItem('loggedIn', 'true');
                 const redirect = this.$route.query.redirect as string;
                if (redirect) {
                    this.$router.push(redirect);
                } else {
                    this.$router.push('/');
                }
            }).catch((error) => {
                this.error = error.response?.data?.message;
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
                        <!-- Email input -->
                        <div data-mdb-input-init class="form-outline mb-4">
                            <label class="form-label text-dark" for="username">Username</label>
                            <input
                                id="username"
                                class="form-control form-control-lg"
                                placeholder="Username"
                                name="email"
                                value
                                required
                                autofocus
                                v-model="model.username"
                            />
                        </div>

                        <!-- Password input -->
                        <div data-mdb-input-init class="form-outline mb-3">
                            <label class="form-label text-dark" for="password">Password</label>
                            <input
                                type="password"
                                id="password"
                                class="form-control form-control-lg"
                                placeholder="Enter password"
                                name="password"
                                required
                                v-model="model.password"
                            />
                        </div>

                        <!-- Fogot password -->
                        <div class="d-flex justify-content-between align-items-center">
                            <!-- Checkbox -->
                            <div class="form-check mb-0">
                                <input class="form-check-input me-2" type="checkbox" value="" id="form2Example3" v-model="model.rememberMe"/>
                                <label class="form-check-label text-dark" for="form2Example3"> Remember me </label>
                            </div>
                            <RouterLink to="" class="text-body">Forgot password?</RouterLink>
                        </div>

                        <!-- Login button -->
                        <div class="text-center text-lg-start mt-4 pt-2">
                            <button
                                type="submit"
                                data-mdb-button-init
                                data-mdb-ripple-init
                                class="btn btn-primary btn-lg"
                                style="padding-left: 2.5rem; padding-right: 2.5rem"
                            >
                                Login
                            </button>
                            <p class="text-dark fs-6 small mt-2 pt-1 mb-0">
                                Don't have an account?
                                <RouterLink to="/register" class="link-danger">Register</RouterLink>
                            </p>
                        </div>
                        <div class="divider d-flex align-items-center my-4">
                            <p class="text-dark text-center fw-bold mx-3 mb-0">Or</p>
                        </div>
                        <div
                            class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start"
                        >
                            <p class="text-dark lead fw-normal mb-0 me-3">Sign in with</p>
                            <button
                                type="button"
                                data-mdb-button-init
                                data-mdb-ripple-init
                                class="btn btn-primary btn-floating mx-1"
                            >
                                <i class="fa fa-google"></i>
                            </button>

                            <button
                                type="button"
                                data-mdb-button-init
                                data-mdb-ripple-init
                                class="btn btn-primary btn-floating mx-1"
                            >
                                <i class="fa fa-facebook-f"></i>
                            </button>
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
