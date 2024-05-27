<template>
    <div class="container">
        <section class="py-5 my-5">
            <div class="container">
                <h1 class="mb-5 text-dark">Thông tin cá nhân</h1>
                <div class="bg-white shadow rounded-lg d-block d-sm-flex">
                    <div class="profile-tab-nav border-right">
                        <div class="p-4">
                            <div class="img-circle text-center mb-3">
                                <v-avatar :image="'data:image/png;base64,' + user.avatar" size="80"
                                          @click.prevent="chooseImage" class="border-dark"></v-avatar>
                            </div>
                            <small v-if="!user.avatar" class="text-danger">Bạn chưa có ảnh</small>
                            <br>
                            <form>
                                <div class="form-group">
                                    <v-file-input id="file" accept="image/*" placeholder="Chọn ảnh đại diện"
                                                  v-model="files"
                                                  :chips="true"
                                                  v-show="!user.avatar"
                                                  prepend-icon="mdi-camera" outlined></v-file-input>
                                </div>
                            </form>
                            <br>
                            <br>
                        </div>
                        <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist"
                             aria-orientation="vertical">
                            <a class="nav-link d-flex align-items-center" @click.prevent="tabPane.account = true;tabPane.password = false;tabPane.history = false"
                               v-bind:class="{active: tabPane.account}"
                               role="tab" aria-controls="account" aria-selected="true">
                                <i class="bi bi-person-circle me-2"></i>
                                <span class="d-flex align-items-center">Tài khoản</span>
                            </a>
                            <a class="nav-link d-flex align-items-center" @click.prevent="tabPane.password = true ;tabPane.account = false;tabPane.history = false"
                                 v-bind:class="{active: tabPane.password}"
                               role="tab" aria-controls="password" aria-selected="false">
                                <i class="bi bi-key-fill me-2"></i>
                                <span class="d-flex align-items-center">Mật khẩu</span>
                            </a>
                            <a class="nav-link d-flex align-items-center" @click.prevent="tabPane.history = true ;tabPane.account = false;tabPane.password = false"
                                    v-bind:class="{active: tabPane.history}"
                               role="tab" aria-controls="history" aria-selected="false">
                                <i class="bi bi-receipt me-2"></i>
                                <span class="d-flex align-items-center">Lịch sử mua hàng</span>
                            </a>
                            <router-link to="/" class="nav-link d-flex align-items-center">
                                <i class="bi bi-house-door-fill me-2"></i>
                                Quay về trang chủ
                            </router-link>
                        </div>
                    </div>
                    <div class="tab-content p-4 p-md-5" id="v-pills-tabContent">
                        <div v-if="tabPane.account">
                            <h3 class="mb-4">Tài khoản</h3>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Username</label>
                                        <input type="text" class="form-control" v-model="user.username"
                                               :disabled="true">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Địa chỉ</label>
                                        <input type="text" class="form-control" v-model="user.address">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Email</label>
                                        <input type="text" class="form-control" v-model="user.email"
                                               placeholder="Bạn chưa có email">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Ngày tham gia</label>
                                        <v-text-field class="fw-bold" type="datetime-local"
                                                      :model-value="user.createDate"
                                                      :disabled="true"></v-text-field>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <button class="btn btn-primary" @click.prevent="update">Cập nhật</button>
                            </div>
                        </div>
                        <div v-if="tabPane.password">
                            <h3 class="mb-4">Đổi mật khẩu</h3>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Mật khẩu cũ</label>
                                        <input type="password" class="form-control"
                                               v-model="changePasswordRequest.oldPassword">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Mật khẩu mới</label>
                                        <input type="password" class="form-control"
                                               v-model="changePasswordRequest.newPassword">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Nhập lại mật khẩu</label>
                                        <input type="password" class="form-control"
                                               v-model="changePasswordRequest.confirmPassword">
                                    </div>
                                </div>
                            </div>
                            <div>
                                <button class="btn btn-primary" @click.prevent="changePassword">Cập nhật</button>
                            </div>
                        </div>
                        <div v-if="tabPane.history">
                            <h3 class="mb-4">history Settings</h3>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Login</label>
                                        <input type="text" class="form-control">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Two-factor auth</label>
                                        <input type="text" class="form-control">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="" id="recovery">
                                            <label class="form-check-label" for="recovery">
                                                Recovery
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <button class="btn btn-primary">Update</button>
                                <button class="btn btn-light">Cancel</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>

<script lang="ts">
import {defineComponent, inject} from 'vue';
import {toast} from 'vue3-toastify';
import {User} from "@/base/model/user.model";
import type {AuthService} from "@/base/service/auth.service";
import type {UserService} from "@/base/service/user.service";

export default defineComponent({
    name: 'ProfileComponent',
    data() {
        return {
            user: new User(),
            changePasswordRequest: {
                oldPassword: '',
                newPassword: '',
                confirmPassword: ''
            },
            files: [] as File[],
            tabPane: {
                account: true,
                password: false,
                history: false
            }
        }
    },
    methods: {
        changePassword() {

        }
        ,
        async update() {
            if (this.files.length > 0) {
                const reader = new FileReader();
                // Create a Promise to handle the file reading operation
                const readFilePromise = new Promise((resolve, reject) => {
                    reader.onload = () => {
                        resolve(reader.result);
                    };
                    reader.onerror = (error) => {
                        reject(error);
                    };
                });

                // Start reading the file
                reader.readAsDataURL(this.files[0]);

                try {
                    // Wait for the file reading operation to complete
                    const avatarData = await readFilePromise;
                    this.user.avatar = avatarData as string;

                    // Continue with updating the user
                    await this.updateUser();
                } catch (error) {
                    console.error('Error reading file:', error);
                    // Handle error (e.g., show error message)
                    toast.error('Error reading file');
                }
            } else {
                // No file selected, proceed directly to update the user
                await this.updateUser();
            }
        },

        async updateUser() {
            const user = {
                username: this.user.username,
                address: this.user.address,
                avatarData: this.user.avatar,
                email: this.user.email
            };

            try {
                console.log('User data to be updated:', user);
                const response = await this.userService.update(user);
                toast.success('Cập nhật thành công');
                this.user = response; // Update user with response data
            } catch (error) {
                console.error('Error updating user:', error);
                toast.error('Cập nhật thất bại');
            }
        },
        async getUser() {
            this.authService.getProfile().then((response) => {
                this.user = response;
            }).catch((error) => {
                toast.error(error.response.data.message);
            });
        },
        chooseImage() {
            document.getElementById('file')?.click();
        },
    },
    setup() {
        const authService = inject('authService') as AuthService;
        const userService = inject('userService') as UserService;
        return {
            authService,
            userService
        }
    },
    created() {
        this.getUser();
    }

})

</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap");

body {
    background: #f9f9f9;
    font-family: "Roboto", sans-serif;
}

.shadow {
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1) !important;
}

.profile-tab-nav {
    min-width: 250px;
}

.tab-content {
    flex: 1;
}

.form-group {
    margin-bottom: 1.5rem;
}

.nav-pills a.nav-link {
    padding: 15px 20px;
    border-bottom: 1px solid #ddd;
    border-radius: 0;
    color: #333;
}

.nav-pills a.nav-link i {
    width: 20px;
}

.img-circle img {
    height: 100px;
    width: 100px;
    border-radius: 100%;
    border: 5px solid #fff;
}</style>