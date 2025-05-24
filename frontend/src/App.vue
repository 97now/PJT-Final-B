<template>
  <div class="app-container">
    <header class="app">
      <Header />
    </header>

    <main class="mainapp">
      <router-link :to="{ name: 'video-detail', params: { videoId: 1 } }">
        상세 페이지 예시
      </router-link>
      <router-view></router-view>
    </main>

    <Footer />
  </div>
</template>

<script setup>
import { RouterView } from "vue-router";
import Header from "./components/layout/Header.vue";
import Footer from "./components/layout/Footer.vue";
</script>

<style scoped>
.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.app {
  padding-top: 20px;
  width: 60%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.page-title {
  text-align: center;
  margin-bottom: 40px;
}

.user-form {
  max-width: 500px;
  width: 90%;
  margin: 40px auto;
  padding: 40px 30px 20px;
  background-color: #fdfdfd;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

main.mainapp {
  width: 60%;
  padding-top: 20px;
  flex: 1;
}

body {
  font-family: Arial, sans-serif;
}

footer {
  padding: 20px;
  height: 50px;
  background-color: black;
  color: white;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
