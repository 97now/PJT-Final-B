<template>
  <div class="header-bar">
    <div class="header-title">
      <router-link to="/">SSAFIT</router-link>
    </div>
    <!-- 로그인 안 돼 있을 때 -->
    <nav v-if="!token" class="header-actions">
      <!-- 회원가입 -->
      <i class="fas fa-user header-icon"></i>
      <img :src="adduserIcon" alt="signup" class="icon" />
      <router-link :to="{ name: 'signUp' }" class="header-link"
        >sign up</router-link
      >

      <i class="fas fa-sign-in-alt header-icon" style="margin-left: 18px"></i>
      <img :src="loginIcon" alt="login" class="icon" />
      <router-link :to="{ name: 'logIn' }" class="header-link"
        >login</router-link
      >
    </nav>
    <!-- 로그인 돼 있을 때 -->
    <nav v-else class="header-actions">
      <!-- 로그아웃 -->
      <img :src="logoutIcon" alt="signup" class="icon" />
      <router-link to="/" @click="logout" class="header-link"
        >logout</router-link
      >

      <!-- 마이 페이지 -->
      <img
        v-if="profileImg"
        :src="profileImg"
        alt="signup"
        class="icon profile-img"
      />
      <router-link
        class="header-link"
        :to="{
          name: 'myPage',
          params: { userId: 'LEE_EONJI' },
        }"
        >my page</router-link
      >
    </nav>
  </div>
  <hr class="header-divider" />
</template>

<script setup>
import { storeToRefs } from "pinia";
import { RouterLink } from "vue-router";
import { useUserStore } from "@/stores/userStore";

import adduserIcon from "@/assets/img/Add_User.png";
import loginIcon from "@/assets/img/Login.png";
import logoutIcon from "@/assets/img/Logout.png";

const userStore = useUserStore();
const { token, profileImg } = storeToRefs(userStore);

const user = ref(null);

watch(token, (newVal) => {
  if (newVal) {
    userStore.fetchUserInfo(userStore.userId).then((res) => {
      user.value = res.data;
    });
  }
});

const logout = () => {
  userStore.logout();
  console.log("[Header] 토큰 : " + token.value);
};
</script>

<style scoped>
.header-bar {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  padding: 30px 30px 0 24px;
  height: 70px;
  width: 100%;
  box-sizing: border-box;
}
.header-title {
  font-size: 28px;
  font-weight: 500;
  letter-spacing: 2px;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}

.header-title a {
  color: #222;
  text-decoration: none;
  font-size: 2rem;
}
.header-actions {
  display: flex;
  align-items: center;
  font-size: 16px;
  gap: 4px;
  position: absolute;
  right: 24px;
  top: 26px;
}
.header-icon {
  font-size: 20px;
  margin-right: 4px;
  color: #222;
}
.header-link {
  margin-right: 16px;
  color: #222;
  font-size: 16px;
}
.header-link:last-child {
  margin-right: 0;
}
.header-divider {
  margin-top: 32px;
  border: none;
  border-bottom: 2px solid #bbb;
  width: 100%;
}

.profile-img {
  width: 30px;
  height: 30px;
  border-radius: 100%;
  border: 2px solid #ccc;
}
</style>
