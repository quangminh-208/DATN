<script lang="ts">
import {defineComponent, inject} from "vue";
import {PostService} from "@/base/service/post.service";
import {Post} from "@/base/model/post.model";
import HeaderComponent from "@/components/HeaderComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";

export default defineComponent({
    name: 'PostDetailPage',
    components: {
        HeaderComponent,
        FooterComponent
    },
    setup() {
        return {
            postService: inject('postService') as PostService
        }
    },
    data() {
        return {
            post: {
                title: '',
                content: ''
            } as Post,
            id: '' as string
        }
    },
    methods: {},
    created() {
        this.id = this.$route.params.id as string;
        this.postService.getDetail(this.id).then((data: Post) => {
            this.post = data;
        });
    }
});
</script>
<template>
    <div class="text-dark text-center">
        <header-component/>
        <div class="mb-5">
            <br/><br/>
            <div>
                <h1>Chi tiết bài viết</h1>
            </div>
            <div class="d-flex justify-content-center">
                <div>
                    <h2>{{ post.title }}</h2>
                    <div v-html="post.content"></div>
                </div>
            </div>
        </div>
        <footer-component/>
    </div>
</template>