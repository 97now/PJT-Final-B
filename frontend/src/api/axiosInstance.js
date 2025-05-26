import axios from "axios";

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
}

export default api;
