import { createApp } from "vue";
import { createPinia } from "pinia";
import { setAuthInterceptor } from "@/api/axiosInstance";
import { useUserStore } from "./stores/userStore";

import App from "./App.vue";
import router from "./router";

import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

const app = createApp(App);

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

app.use(router);
app.use(pinia);

const store = useUserStore();
console.log("[main.js] 초기 store.token:", store.token);
setAuthInterceptor(() => store.token);

app.mount("#app");
