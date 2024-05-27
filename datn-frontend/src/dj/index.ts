import {ProductService} from '@/base/service/product.service';
import {AuthService} from "@/base/service/auth.service";
import {TourService} from "@/base/service/tour.service";
import {UserService} from "@/base/service/user.service";
import {OrderService} from "@/base/service/order.service";
import {CategoryService} from "@/base/service/category.service";
import {PostService} from "@/base/service/post.service";
import {VoucherService} from "@/base/service/voucher.service";
import {ChatService} from "@/base/service/chat.service";
export const DependencyInjection = {
    install: (app: any) => {
        app.provide('authService', new AuthService());
        app.provide('productService', new ProductService());
        app.provide('tourService', new TourService());
        app.provide('userService', new UserService());
        app.provide('orderService', new OrderService());
        app.provide('categoryService', new CategoryService());
        app.provide('postService', new PostService());
        app.provide('voucherService', new VoucherService());
        app.provide('chatService', new ChatService());
    }
};

// create socket.io connection