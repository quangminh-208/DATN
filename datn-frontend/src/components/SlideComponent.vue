<script lang="tsx">
import { defineComponent, ref } from 'vue'
export default defineComponent({
    name: 'SlideComponent',
    setup() {
        const carousel = ref([
        'https://firebasestorage.googleapis.com/v0/b/monatourist-d447f.appspot.com/o/image%2Fslide-1.jpg?alt=media&token=ef98f6ef-bc12-4135-954e-617d5e3bfd33',
        'https://firebasestorage.googleapis.com/v0/b/monatourist-d447f.appspot.com/o/image%2Fslide-2.jpg?alt=media&token=54a50cf0-6cdb-455e-93b3-3ad3c1287070',
        'https://firebasestorage.googleapis.com/v0/b/monatourist-d447f.appspot.com/o/image%2Fslide-3.jpg?alt=media&token=2bca355b-bd7b-4579-961c-c1036c09731e',
        ])
        return () => (
        <div>
            (
            <v-carousel>
                {carousel.value?.map((item, index) => (
                <v-carousel-item key={index}>
                    <v-img
                    aspect-ratio="16/9"
                    cover={true}
                    src={item}
                    ></v-img>
                </v-carousel-item>
                ))}
            </v-carousel>
            )
        </div>
        )
    }
})
</script>

<style></style>