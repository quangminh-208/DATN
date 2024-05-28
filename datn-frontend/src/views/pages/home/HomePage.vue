<script lang="tsx">
import {defineComponent, inject, reactive, ref} from "vue";
import HeaderComponent from "@/components/HeaderComponent.vue";
import SlideComponent from "@/components/SlideComponent.vue";
import type {TourService} from "@/base/service/tour.service";
import type {Tour} from "@/base/model/tour.model";
import TourCardComponent from "@/components/TourCardComponent.vue";
import {PRODUCT_RESPONSIVE_CLASS} from "@/plugins/utils";
import FooterComponent from "@/components/FooterComponent.vue";
export default defineComponent({
    name: 'HomePage',
    components: {
        HeaderComponent,
        SlideComponent,
        TourCardComponent,
        FooterComponent
    },
    setup() {
        let newTours = ref([] as Tour[]);
        const tourService = inject('tourService') as TourService;
        const fetchNewTours = async () => {
            tourService.findByNewest().then((data) => {
                newTours.value = data;
            });
        }
        const initData = async () => {
            await fetchNewTours();
        }
        initData();
        return () => (
            <div>
                <header-component/>
                <slide-component/>
                <div class="d-flex justify-content-center">
                    <div class="container">
                        <div class="col-md-12 mt-3">
                            <div class="section-heading">
                                <h2 class="heading text-dark">Chùm Tour Mới</h2>
                                <span class="badge heading__badge">New</span>
                            </div>
                        </div>
                        <div class="row align-items-center">
                            {
                                newTours.value.map((tour, index) => (
                                    <tour-card-component className={PRODUCT_RESPONSIVE_CLASS.four} tour={tour}/>
                                ))
                            }
                        </div>
                    </div>
                </div>
                <footer-component/>
            </div>
        )
    }
})
</script>

<style lang="scss" scoped>
@import "style";
</style>