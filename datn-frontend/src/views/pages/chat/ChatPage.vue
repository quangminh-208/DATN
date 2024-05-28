<script lang="ts">
import {defineComponent} from "vue";
import ChatComponent from "@/components/ChatComponent.vue";
export default defineComponent({
    name: 'ChatPage',
    components: {ChatComponent}
})
</script>

<template>
    <div>
        <chat-component/>
    </div>
</template>