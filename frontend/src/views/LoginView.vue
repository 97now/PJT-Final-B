<template>
  <div class="user-form">
    <div class="page-title">
      <h1>로그인</h1>
    </div>
    <form @submit.prevent="onSubmit">
      <div class="input">
        <label>아이디</label>
        <BaseInput placeholder="아이디를 입력하세요" v-model="loginId" />
      </div>
      <div class="input">
        <label>비밀번호</label>
        <BaseInput
          type="password"
          placeholder="비밀번호를 입력하세요 (문자, 숫자, 특수문자 포함 8~20자)"
          v-model="loginPw"
        />
      </div>
      <nav>
        <router-link :to="{ name: 'signUp' }">회원가입</router-link> |
        <router-link :to="{ name: 'findId' }">아이디 찾기</router-link> |
        <router-link :to="{ name: 'findPw' }">비밀번호 찾기</router-link>
      </nav>

      <BaseButton type="submit" text="로그인" :action="onSubmit" />
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { RouterLink } from "vue-router";
import BaseInput from "@/components/common/BaseInput.vue";
import BaseButton from "@/components/common/BaseButton.vue";

const loginId = ref("");
const loginPw = ref("");

const onSubmit = () => {
  console.log("onSubmit 호출 : 로그인 로직 만들어야 됨");
};
</script>

<style scoped>
.page-title {
  text-align: center;
  margin-bottom: 40px;
}

.input {
  margin: 10px 0;
}

.input label {
  font-weight: 600;
  display: block;
  text-align: left;
}

nav {
  text-align: center;
  margin-top: 30px;
}

nav > a {
  color: black;
  text-decoration: none;
}
</style>
