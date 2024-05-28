<script lang="ts">
import {defineComponent, inject} from "vue";
import {PostService} from "@/base/service/post.service";
import FooterComponent from "@/components/FooterComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";


export default defineComponent({
    name: 'PostPage',
    components: {HeaderComponent, FooterComponent},
    setup() {
        const headers = [
            {title: 'STT', value: 'index'},
            {title: 'Tiêu đề', value: 'title'},
            {title: 'Chi tiết bài viết', value: 'content'},
            {title: 'Hành động', value: 'action', sortable: false},
        ];
        return {
            headers,
            postService: inject('postService') as PostService
        }
    },
    data() {
        return {
            postPage: {
                content: [],
                totalPages: 0,
                totalElements: 0,
                last: true,
                size: 0,
            } as any,
            searchParams: {
                page: 0,
                size: 10,
                keyword: '',
            },
            isLoading: false,
        }
    },
    methods: {
        async search() {
            this.isLoading = true;
            this.postService.findBySearchUser(this.searchParams).then((data) => {
                this.postPage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        viewDetail(item: any) {
            this.$router.push('/post/' + item.id);
        },
    },
    created() {
        this.search();
    }
})

</script>

<template>
    <div class="text-dark">
        <header-component/>
        <div class="container my-10">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <h1 class="text-center">Danh sách bài viết</h1>
                    </div>
                    <div class="col-12">
                        <div class="d-flex justify-content-between">
                            <v-text-field v-model="searchParams.keyword" label="Tìm kiếm"></v-text-field>
                            <v-btn @click="search" color="primary">Tìm kiếm</v-btn>
                        </div>
                    </div>
                    <div class="col-12">
                       <v-data-table
                           :headers="headers"
                           :items="postPage.content"
                           :loading="isLoading"
                           :items-per-page="searchParams.size"
                           :on-update:items-per-page="search"
                           :page="searchParams.page"
                           :on-update:page="search"
                           :search="searchParams.keyword"
                           :total-items="postPage.totalElements"
                        >
                            <template v-slot:[`item.index`]="{index}">
                                {{index + 1}}
                            </template>
                            <template v-slot:[`item.content`]="{item}">
                                <div v-html="item.content"></div>
                            </template>
                            <template v-slot:[`item.action`]="{item}">
                                <v-btn @click="() => viewDetail(item)" color="primary">Xem chi tiết</v-btn>
                            </template>
                        </v-data-table>
                    </div>
                </div>
            </div>
        </div>
        <footer-component/>
    </div>

</template>