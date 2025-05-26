import axios from "axios";
import { useUserStore } from "@/stores/userStore";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

export function setAuthInterceptor(getToken) {
  api.interceptors.request.use((config) => {
    const token = getToken();
    // console.log("[인터셉터] 현재 토큰:", token);

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  });

  // 응답 인터셉터 추가
  api.interceptors.response.use(
    (response) => response,
    (error) => {
      if (error.response?.status === 401) {
        const userStore = useUserStore();
        userStore.logout();
        window.location.href = "/login";
      }
      return Promise.reject(error);
    }
  );
}

export default api;
