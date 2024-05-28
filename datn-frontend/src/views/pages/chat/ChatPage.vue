<script lang="ts">
import {defineComponent} from "vue";
import ChatComponent from "@/components/ChatComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";
export default defineComponent({
    name: 'ChatPage',
    components: {ChatComponent, HeaderComponent, FooterComponent}
})
</script>

<template>
    <div>
        <header-component/>
        <chat-component/>
        <footer-component/>
    </div>
</template>