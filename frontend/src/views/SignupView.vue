<template>
  <div class="user-form">
    <div class="page-title">
      <h1>회원가입</h1>
    </div>
    <form @submit.prevent="onSubmit">
      <div class="input">
        <label for="id">아이디</label>
        <div class="idInput">
          <BaseInput
            type="text"
            id="id"
            placeholder="아이디를 입력해주세요 (8~20자)"
            v-model="userId"
          />
          <button @click.prevent="checkDuplicatedId">중복 확인</button>
        </div>
      </div>
      <div class="input">
        <label for="pw">비밀번호</label>
        <BaseInput
          type="password"
          id="pw"
          placeholder="비밀번호를 입력해주세요 (문자, 숫자, 특수문자 포함 8~20자)"
          v-model="userPw"
        />
      </div>
      <div class="input">
        <label for="pwCheck">비밀번호 확인</label>
        <BaseInput
          type="password"
          id="pwCheck"
          placeholder="비밀번호를 재입력해주세요"
          v-model="userPwCheck"
        />
        <p style="color: red; text-align: left" v-if="!isSamePw">
          비밀번호가 일치하지 않습니다
        </p>
      </div>
      <div class="input">
        <label for="name">이름</label>
        <BaseInput
          type="text"
          id="name"
          placeholder="이름을 입력해주세요"
          v-model="userName"
        />
      </div>
      <div class="input">
        <label for="phone">휴대폰 번호</label>
        <BaseInput
          type="text"
          id="phone"
          placeholder="휴대폰 번호 입력 ('-' 제외 11자리 입력)"
          v-model="userPhone"
        />
      </div>
      <div class="input">
        <label for="email">이메일 주소</label>
        <div class="emailInput">
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
      </div>
      <div class="input">
        <label>생년월일</label>
        <div class="birth">
          <BaseDropBox
            :options="years"
            v-model="birthYear"
            placeholder="연도"
          />
          <BaseDropBox
            :options="months"
            v-model="birthMonth"
            placeholder="월"
          />
          <BaseDropBox :options="days" v-model="birthDay" placeholder="일" />
        </div>
      </div>
      <BaseButton type="submit" text="가입하기" :action="onSubmit" />
    </form>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import BaseInput from "@/components/common/BaseInput.vue";
import EmailDomainDropbox from "@/components/common/EmailDomainDropbox.vue";
import BaseButton from "@/components/common/BaseButton.vue";
import BaseDropBox from "@/components/common/BaseDropBox.vue";

const userId = ref("");
const userPw = ref("");
const userPwCheck = ref("");
const userName = ref("");
const userPhone = ref("");
const userEmail = ref("");
const userEmailDomain = ref("");
const birthYear = ref("");
const birthMonth = ref("");
const birthDay = ref("");

// 생년월일 목록
const years = Array.from({ length: 100 }, (_, i) => 2024 - i);
const months = Array.from({ length: 12 }, (_, i) => i + 1);
const days = Array.from({ length: 31 }, (_, i) => i + 1);

// 중복 확인
const checkDuplicatedId = () => {
  console.log("아이디 중복확인 로직 넣어줄 거임");
};

// 비밀번호 확인
const isSamePw = ref(true);
watch(userPwCheck, (checking) => {
  if (checking.length >= pw.value.length && checking !== pw.value) {
    isSamePw.value = false;
  } else if (checking === pw.value) {
    isSamePw.value = true;
  }
});

// 제출
const onSubmit = () => {
  const user = {
    id: userId.value,
    pw: userPw.value,
    pwCheck: userPwCheck.value,
    name: userName.value,
    phone: userPhone.value,
    email: `${userEmail.value}@${userEmailDomain.value}`,
    birth: `${birthYear.value}-String(${birthMonth.value}).padStart(2, '0')-String(${birthDay.value}).padStart(2, '0')`,
  };
  console.log("onSubmit 호출 : 회원가입 로직 만들어야 됨");
};
</script>

<style scoped>
.page-title {
  text-align: center;
  margin-bottom: 40px;
}

form {
  display: flex;
  flex-direction: column;
}

.input {
  margin: 10px 0;
}

.input label {
  font-weight: 600;
  display: block;
  text-align: left;
}

.idInput {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: center;
}

.idInput button {
  width: 100px;
  margin-top: 10px;
  padding: 12px 0px;
  background-color: #aaa;
  border: none;
  border-radius: 5px;
  box-shadow: 1px 1px 2px #ccc;
  font-weight: bold;
}

.idInput button:hover {
  background-color: #808080;
  cursor: pointer;
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

.birth {
  display: flex;
  gap: 10px;
}
</style>
