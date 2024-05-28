<script lang="ts">
import { defineComponent, inject } from "vue";
import { PostService } from "@/base/service/post.service";
import FooterComponent from "@/components/FooterComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";

export default defineComponent({
  name: "AboutPage",
  components: { HeaderComponent, FooterComponent },
  setup() {
    const aboutContent = {
      title:
        "Chào mừng bạn đến với Monatourist – Khám phá thế giới theo cách riêng của bạn!",
      content: [
        {
          index: 1,
          context:
            "Tại Monatourist, chúng tôi tin rằng mỗi chuyến đi là một câu chuyện, một trải nghiệm độc đáo và đáng nhớ. Với niềm đam mê du lịch cháy bỏng và sự am hiểu sâu sắc về các điểm đến trên khắp thế giới, chúng tôi tự hào mang đến cho bạn những hành trình đầy cảm hứng, mở ra những chân trời mới và đánh thức những khát khao khám phá tiềm ẩn trong bạn.",
        },
        {
          index: 2,
          context:
            "Chúng tôi không chỉ là một công ty booking tour du lịch thông thường. Chúng tôi là những người bạn đồng hành, những người kể chuyện và những người kiến tạo trải nghiệm. Chúng tôi lắng nghe những mong muốn của bạn, tư vấn tận tâm và thiết kế những hành trình cá nhân hóa, phù hợp với sở thích, phong cách và ngân sách của bạn.",
        },
        {
          index: 3,
          context:
            "Dù bạn là người yêu thích những bãi biển cát trắng trải dài, những dãy núi hùng vĩ hay những thành phố cổ kính đầy mê hoặc, Monatourist luôn có những lựa chọn đa dạng và hấp dẫn để đáp ứng mọi nhu cầu của bạn. Chúng tôi cam kết mang đến những dịch vụ chất lượng cao, từ việc lựa chọn khách sạn, đặt vé máy bay, sắp xếp lịch trình tham quan cho đến việc hỗ trợ bạn trong suốt hành trình.",
        },
        {
          index: 4,
          context:
            "Hãy để Monatourist đồng hành cùng bạn trên những chuyến phiêu lưu kỳ thú, khám phá những nền văn hóa độc đáo và tạo nên những kỷ niệm khó quên. Bắt đầu hành trình của bạn ngay hôm nay!",
        },
      ],
      img_url:
        "https://firebasestorage.googleapis.com/v0/b/monatourist-d447f.appspot.com/o/image%2Fbanner-5.jpg?alt=media&token=b8decdcc-e780-446a-9338-ec566bafecb6",
    };
    return {
      aboutContent,
    };
  },
});
</script>

<template>
  <header-component />
  <div class="text-dark">
    <div class="container d-flex flex-column align-items-center">
      <img :src="aboutContent.img_url" class="w-50"/>
      <h2 class="text-center mb-4 py-10">{{ aboutContent.title }}</h2>
      <p
        v-for="(item, i) in aboutContent.content"
        :key="i"
        class="text-justify"
      >
        {{ item.context }}
      </p>
    </div>
  </div>
  <footer-component />
</template>
