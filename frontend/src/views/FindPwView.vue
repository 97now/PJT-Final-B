<template>
  <div class="user-form">
    <div class="page-title">
      <h1>비밀번호 찾기</h1>
    </div>
    <form @submit.prevent="">
      <div class="input">
        <label>아이디</label>
        <BaseInput v-model="userId" placeholder="가입한 아이디를 입력하세요" />
      </div>
      <div class="input">
        <label>전화번호</label>
        <div class="phoneInput">
          <BaseInput
            @input="restrictPhoneInput"
            type="text"
            id="phone"
            placeholder="휴대폰 번호 입력 ('-' 제외 11자리 입력)"
            v-model="userPhone"
          />
          <button @click.prevent="onSubmit">본인인증</button>
        </div>
      </div>
      <BaseButton text="비밀번호 재설정" />
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";

import BaseInput from "@/components/common/BaseInput.vue";
import BaseButton from "@/components/common/BaseButton.vue";

const router = useRouter();
const userStore = useUserStore();

const userId = ref("");
const userPhone = ref("");

// 휴대폰 번호에 숫자만 입력되게 제한
const restrictPhoneInput = (event) => {
  const input = event.target.value;
  // 숫자가 아닌 문자 제거
  const numbersOnly = input.replace(/[^0-9]/g, "");
  // 11자리로 제한
  userPhone.value = numbersOnly.slice(0, 11);
};

const identify = () => {
  console.log("본인인증해주기.....");
};

const onSubmit = async () => {
  try {
    const findPwRequest = {
      userId: userId.value,
      userPhone: userPhone.value,
    };
    const result = await userStore.findPw(findPwRequest);
    if (result) {
      sessionStorage.setItem("resetUserId", userId.value);
      alert("인증에 성공했습니다. 비밀번호를 재설정해주세요.");
      router.push({ name: "resetPw" });
    } else {
      alert("입력하신 정보와 일치하는 사용자가 없습니다");
    }
  } catch (error) {
    console.log(error);
    alert("비밀번호 찾기 중 오류가 발생했습니다.");
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

.input {
  margin: 50px 0;
}

.input label {
  font-weight: 600;
  display: block;
  text-align: left;
}

.phoneInput {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: center;
}

.phoneInput button {
  width: 100px;
  margin-top: 10px;
  padding: 12px 0px;
  background-color: #aaa;
  border: none;
  border-radius: 5px;
  box-shadow: 1px 1px 2px #ccc;
  font-weight: bold;
}

.phoneInput button:hover {
  background-color: #808080;
  cursor: pointer;
}
</style>
