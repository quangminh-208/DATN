export class AuthModel {
    username?: string;
    password?: string;
    rememberMe?: boolean;
}

export class UserRegisterModel {
    username?: string;
    password?: string;
    confirmPassword?: string;
    email?: string;
}