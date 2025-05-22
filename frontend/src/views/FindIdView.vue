<template>
  <div class="user-form">
    <div class="page-title">
      <h1>아이디 찾기</h1>
    </div>
    <!-- 아이디 찾기 방법 선택 -->
    <div class="methodSelect">
      <div class="radioInput">
        <input
          type="radio"
          name="method"
          id="by-phone"
          value="전화번호"
          v-model="findBy"
          checked
        />
        <label for="by-phone">휴대전화</label>
      </div>
      <div class="radioInput">
        <input
          type="radio"
          name="method"
          id="by-email"
          v-model="findBy"
          value="이메일"
        />
        <label for="by-email">이메일</label>
      </div>
    </div>
    <!-- 폼 입력 -->
    <form @submit.prevent="onSubmit">
      <div class="input">
        <label>이름</label>
        <BaseInput placeholder="이름을 입력하세요" />
      </div>
      <div class="input">
        <label>{{ findBy }}</label>
        <div class="emailInput" v-if="findBy === '이메일'">
          <BaseInput
            type="text"
            id="email"
            placeholder="이메일 주소 입력"
            v-model="userEmail"
          />
          @
          <BaseInput type="text" id="emailDomain" v-model="userEmailDomain" />
          <EmailDomainDropbox v-model="userEmailDomain" />
        </div>
        <div v-else>
          <BaseInput placeholder="휴대폰 번호 입력 ('-' 제외 11자리)" />
        </div>
      </div>

      <BaseButton type="submit" :action="onSubmit" text="아이디 찾기" />

      <nav>
        <RouterLink :to="{ name: 'findPw' }">비밀번호를 찾으시나요?</RouterLink>
      </nav>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { RouterLink } from "vue-router";

import BaseInput from "@/components/common/BaseInput.vue";
import BaseButton from "@/components/common/BaseButton.vue";
import EmailDomainDropbox from "@/components/common/EmailDomainDropbox.vue";

const findBy = ref("전화번호");
const userEmail = ref("");
const userEmailDomain = ref("");

const onSubmit = () => {
  console.log("아이디 찾기 로직 만들어서 넣어줄 거임");
};
</script>

<style scoped>
.user-form {
  max-width: 500px;
  width: 90%;
  margin: 40px auto;
  padding: 40px 30px 20px;
  background-color: #fdfdfd;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.page-title {
  text-align: center;
  margin-bottom: 40px;
}

.methodSelect {
  display: flex;
  justify-content: stretch;
}

.radioInput {
  width: 50%;
  border: 1px #ccc solid;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
}

input[type="radio"] {
  margin-right: 7px;
}

.input {
  margin: 10px 0;
}

.input label {
  font-weight: 600;
  display: block;
  text-align: left;
}

.emailInput {
  display: flex;
  align-items: center;
  gap: 10px;
}

.emailInput > * {
  flex: 1;
}

.emailInput input {
  width: 100%;
}

nav {
  text-align: right;
  margin-top: 20px;
}

nav > a {
  color: black;
  text-decoration: none;
}
</style>
