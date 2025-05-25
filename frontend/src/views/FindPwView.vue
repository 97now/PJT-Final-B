<template>
  <div class="user-form">
    <div class="page-title">
      <h1>비밀번호 찾기</h1>
    </div>
    <form @submit.prevent="onSubmit">
      <div class="input">
        <label>아이디</label>
        <BaseInput placeholder="가입한 아이디를 입력하세요" />
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
          <button @click.prevent="identify">본인인증</button>
        </div>
      </div>
      <BaseButton :action="onSubmit" text="비밀번호 찾기" />
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

import BaseInput from "@/components/common/BaseInput.vue";
import BaseButton from "@/components/common/BaseButton.vue";

const router = useRouter();

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

const onSubmit = () => {
  console.log("비밀번호 찾기 로직 만들어야 됨 // 유저 pk 갖고 가야할 듯?");
  // 방금 넘어온 정보에 해당하는 유저 존재하면 그 유저의 pk 넘겨주기
  router.replace({ name: "resetPw" });
  // 왜 replace 했냐면...뒤로가기 쓸 이유가 없으니까..? 뭔가? 보안상? 더 나을 것 같아서?
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
