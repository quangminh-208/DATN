import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            component: () => import('@/views/pages/home/HomePage.vue'),
            meta: {
                requiresAuth: false
            }
        },
        {
            path: '/home',
            component: () => import('@/views/pages/home/HomePage.vue'),
            meta: {
                requiresAuth: false
            }
        },
        {
            path: '/index',
            component: () => import('@/views/pages/home/HomePage.vue'),
            meta: {
                requiresAuth: false
            }
        },
        {
            path: '/login',
            component: () => import('@/views/pages/login/LoginPage.vue'),
            meta: {
                requiresAuth: false
            }
        },
        {
            path: '/register',
            component: () => import('@/views/pages/register/RegisterPage.vue'),
            meta: {
                requiresAuth: false
            }
        },
        {
            path: '/about',
            component: () => import('@/views/pages/about/AboutPage.vue'),
            meta: {
                requiresAuth: false
            }
        },
        {
            path: '/tour/:id',
            component: () => import('@/views/pages/tour/detail/TourDetailPage.vue'),
            name: 'tourDetail',
            meta :{
                requiresAuth: false
            }
        },
        {
            path: '/booking/:id',
            component: () => import('@/views/pages/booking/BookingPage.vue'),
            meta: {
                requiresAuth: true
            }
        },
        {
            path: '/logout',
            component: () => import('@/views/pages/logout/LogoutPage.vue'),
            meta: {
                requiresAuth: true
            }
        },
        {
            path: '/profile',
            component: () => import('@/views/pages/profile/ProfilePage.vue'),
            meta: {
                requiresAuth: true
            }
        },
        {
            path: '/order',
            component: () => import('@/views/pages/order/OrderPage.vue'),
            meta: {
                requiresAuth: true
            }
        },
        {
            path: '/order/:id',
            component: () => import('@/views/pages/order/detail/OrderDetailPage.vue'),
            meta :{
                requiresAuth: true
            }
        },
        {
            path: '/admin',
            component: () => import('@/views/pages/admin/AdminPage.vue'),
            meta :{
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/user',
            component: () => import('@/views/pages/admin/user/UserPage.vue'),
            meta :{
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/user/:id',
            component: () => import('@/views/pages/admin/user/UserDetailPage.vue'),
            meta :{
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/category',
            component: () => import('@/views/pages/admin/category/CategoryPage.vue'),
            meta :{
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/category/:id',
            component: () => import('@/views/pages/admin/category/CategoryDetailPage.vue'),
            meta :{
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/tour',
            component: () => import('@/views/pages/admin/tour/TourPage.vue'),
            meta :{
                requiresAuth: true,
                isAdmin: true
            }
        },
        {
            path: '/admin/tour/:id',
            component: () => import('@/views/pages/admin/tour/TourDetailPage.vue'),
        },
        {
            path: '/admin/order',
            component: () => import('@/views/pages/admin/order/OrderPage.vue'),
        },
        {
            path: '/admin/order/:id',
            component: () => import('@/views/pages/admin/order/OrderDetailPage.vue'),
        },
        {
            path: '/admin/post',
            component: () => import('@/views/pages/admin/post/PostPage.vue'),
        },
        {
            path: '/admin/post/:id',
            component: () => import('@/views/pages/admin/post/PostDetailPage.vue'),
        },
        {
            path: '/admin/chart',
            component: () => import('@/views/pages/admin/chart/ChartPage.vue'),
        },
        {
            path: '/post',
            component: () => import('@/views/pages/post/PostPage.vue'),
        },
        {
            path: '/post/:id',
            component: () => import('@/views/pages/post/PostDetailPage.vue'),
        },
        {
            path: '/admin/voucher',
            component: () => import('@/views/pages/admin/voucher/VoucherPage.vue'),
        },
        {
            path: '/admin/message',
            component: () => import('@/views/pages/admin/chat/ChatPage.vue'),
        },
        {
            path: '/admin/voucher/:id',
            component: () => import('@/views/pages/admin/voucher/VoucherDetailPage.vue'),
        },
        {
            path: '/message',
            component: () => import('@/views/pages/chat/ChatPage.vue'),
        },
        {
            path: '/:pathMatch(.*)*',
            component: () => import('@/components/NotFoundComponent.vue')
        },

    ]
})

router.beforeEach((to, from, next) => {
    const isAuthenticated = localStorage.getItem('token');
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    const isAdmin = user.roleList?.find((role : any) => role === 'ROLE_ADMIN');
    if (to.meta.requiresAuth && !isAuthenticated) {
        next({
            path: '/login',
            query: { redirect: to.fullPath }
        });
    } else if (to.meta.isAdmin && !isAdmin) {
        next('/home');
    } else {
        next();
    }
});
export default router
