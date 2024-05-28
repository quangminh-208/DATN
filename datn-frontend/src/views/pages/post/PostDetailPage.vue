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
    <div class="text-dark">
        <header-component/>
        <div class="mb-10">
            <div class="d-flex justify-content-center ">
                <div class="container">
                    <h2 class="py-15 text-center">{{ post.title }}</h2>
                    <div v-html="post.content" class=" text-justify"></div>
                </div>
            </div>
        </div>
        <footer-component/>
    </div>
</template>