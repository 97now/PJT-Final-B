import { createApp } from "vue";
import { createPinia } from "pinia";
import mitt from "mitt";

import App from "./App.vue";
import router from "./router";

import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

const app = createApp(App);

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

const eventBus = mitt();

app.use(router);
app.use(pinia);
app.provide("eventBus", eventBus);

app.mount("#app");
