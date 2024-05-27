import axios from "axios";
import type {AuthModel} from "@/base/model/auth.model";
import {UserRegisterModel} from "@/base/model/auth.model";

export class AuthService {
    public url = '/v2';

    async login(model: AuthModel) {
        const response = await axios.post(`${this.url}/login`, model);
        return response.data;
    }

    async logout() {
        const response = await axios.post(`${this.url}/logout`, {}, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }

    async register(model: UserRegisterModel) {
        const response = await axios.post(`${this.url}/register`, model);
        return response.data;
    }

    async getProfile() {
        const response = await axios.get(`${this.url}/profile`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }
}