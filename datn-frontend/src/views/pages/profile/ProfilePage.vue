<script lang="ts">
import {defineComponent} from 'vue'
import ProfileComponent from '@/components/ProfileComponent.vue'
import FooterComponent from "@/components/FooterComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";
import SlideComponent from "@/components/SlideComponent.vue";

export default defineComponent({
    components: {
        HeaderComponent, FooterComponent,
        ProfileComponent
    },
});
</script>

<template>
    <header-component/>
    <div class="d-flex justify-content-center">
        <div class="container">
            <profile-component/>
        </div>
    </div>
    <footer-component/>
</template>