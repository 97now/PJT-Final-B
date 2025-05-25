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
        <label>닉네임</label>
        <BaseInput v-model="userNickName" placeholder="닉네임을 입력하세요" />
      </div>
      <div class="input">
        <label>{{ findBy }}</label>
        <div v-if="findBy === '이메일'">
          <div class="emailInput">
            <BaseInput
              type="text"
              id="email"
              placeholder="이메일 아이디"
              v-model="userEmail"
            />
            @
            <BaseInput
              type="text"
              placeholder="도메인 입력"
              id="emailDomain"
              v-model="userEmailDomain"
            />
            <EmailDomainDropbox v-model="userEmailDomain" />
          </div>
          <p :class="{ errorMsg: !isValidEmail }" v-if="emailErrorMsg">
            {{ emailErrorMsg }}
          </p>
        </div>
        <div v-else>
          <BaseInput
            type="text"
            id="phone"
            v-model="userPhone"
            placeholder="휴대폰 번호 입력 ('-' 제외 11자리)"
            @input="restrictPhoneInput"
          />
          <p :class="{ errorMsg: !isValidPhone }" v-if="phoneErrorMsg">
            {{ phoneErrorMsg }}
          </p>
        </div>
      </div>

      <BaseButton type="submit" text="아이디 찾기" />

      <nav>
        <RouterLink :to="{ name: 'findPw' }">비밀번호를 찾으시나요?</RouterLink>
      </nav>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { RouterLink, useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";

import BaseInput from "@/components/common/BaseInput.vue";
import BaseButton from "@/components/common/BaseButton.vue";
import EmailDomainDropbox from "@/components/common/EmailDomainDropbox.vue";

const userStore = useUserStore();
const router = useRouter();

const findBy = ref("전화번호");

const userNickName = ref("");

const userEmail = ref("");
const userEmailDomain = ref("");
const isValidEmail = ref(false);
const emailErrorMsg = ref("");

const userPhone = ref("");
const phoneErrorMsg = ref("");
const isValidPhone = ref(false);

// 휴대폰 번호에 숫자만 입력되게 제한
const restrictPhoneInput = (event) => {
  const input = event.target.value;
  // 숫자가 아닌 문자 제거
  const numbersOnly = input.replace(/[^0-9]/g, "");
  // 11자리로 제한
  userPhone.value = numbersOnly.slice(0, 11);
};

// 휴대폰 번호 정규식 검사
const phoneValidationCheck = () => {
  if (userPhone.value.length < 11) {
    isValidPhone.value = false;
    phoneErrorMsg.value = "전화번호는 010으로 시작하는 11자리 숫자여야 합니다";
    return;
  }

  const phoneReg = /^010[0-9]{8}$/;

  if (phoneReg.test(userPhone.value)) {
    isValidPhone.value = true;
    phoneErrorMsg.value = "";
  } else {
    isValidPhone.value = false;
    phoneErrorMsg.value = "전화번호는 010으로 시작하는 11자리 숫자여야 합니다";
  }
};

// 이메일 형식 검사
const emailValidationCheck = () => {
  const fullEmail = userEmail.value + "@" + userEmailDomain.value;
  const emailReg = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

  if (!emailReg.test(fullEmail)) {
    emailErrorMsg.value = "유효한 이메일 주소를 입력해주세요";
    isValidEmail.value = false;
  } else {
    emailErrorMsg.value = "";
    isValidEmail.value = true;
  }
};

const onSubmit = async () => {
  const findIdData = {
    findBy: "",
    userNickName: userNickName.value,
    userData: "",
  };

  if (findBy.value === "전화번호") {
    phoneValidationCheck();
    findIdData.findBy = "phone";
    findIdData.userData = userPhone.value;

    if (!isValidPhone.value) return;
  } else if (findBy.value === "이메일") {
    emailValidationCheck();
    findIdData.findBy = "email";
    findIdData.userData = userEmail.value + "@" + userEmailDomain.value;

    if (!isValidEmail.value) return;
  }

  try {
    const result = await userStore.findId(findIdData);
    alert(`회원님의 아이디는 ${result} 입니다`);
    router.push({ name: "logIn" });
  } catch (err) {
    console.log(err);
    alert("아이디 찾기 중 오류가 발생했습니다.");
  }
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

.errorMsg {
  color: red;
}
</style>
