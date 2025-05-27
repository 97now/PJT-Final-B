<template>
  <div class="user-form">
    <div class="page-title">
      <h1>비밀번호 재설정</h1>
    </div>
    <form @submit.prevent="onSubmit">
      <div class="input">
        <label>새로운 비밀번호</label>
        <BaseInput
          type="password"
          id="userPw"
          placeholder="새로운 비밀번호를 입력해주세요"
          v-model="userPw"
        />
        <p
          v-if="pwErrorMsg"
          :class="{
            errMsg: !isValidPw,
          }"
        >
          {{ pwErrorMsg }}
        </p>
      </div>
      <div class="input">
        <label>새로운 비밀번호 확인</label>
        <BaseInput
          type="password"
          id="userPwCheck"
          placeholder="새로운 비밀번호를 다시 입력해주세요"
          v-model="userPwCheck"
        />
        <p
          :class="{
            errMsg: isValidPw && !isSamePw,
            availableMsg: isValidPw && isSamePw,
          }"
        >
          {{ pwCheckErrorMsg }}
        </p>
      </div>

      <BaseButton text="확인" />
    </form>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import { useUserStore } from "@/stores/userStore";
import { useRouter } from "vue-router";

import BaseInput from "@/components/common/BaseInput.vue";
import BaseButton from "@/components/common/BaseButton.vue";

const userStore = useUserStore();
const router = useRouter();

const userPw = ref("");
const isValidPw = ref(false);
const pwErrorMsg = ref("");

const userPwCheck = ref("");
const isSamePw = ref(false);
const pwCheckErrorMsg = ref("");

// 비밀번호 정규식 검사
const pwValidationCheck = () => {
  const pwReg = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\W_]).{8,20}$/;
  if (!userPw.value) {
    pwErrorMsg.value = "비밀번호를 입력해주세요";
    isValidPw.value = false;
  } else if (!pwReg.test(userPw.value)) {
    isValidPw.value = false;
    pwErrorMsg.value =
      "비밀번호는 문자, 숫자, 특수문자를 포함한 8~20자로 입력해주세요";
  } else {
    pwErrorMsg.value = "";
    isValidPw.value = true;
  }
};

// 비밀번호란 바뀔 때마다 검사
watch(userPw, () => {
  if (!userPw.value) {
    pwCheckErrorMsg.value = "";
    return;
  }
  pwCheckErrorMsg.value = "";
  pwValidationCheck();
  pwSameCheck(userPwCheck.value);
});

// 비밀번호 확인란 일치 여부 검사
const pwSameCheck = (checking) => {
  if (!isValidPw.value) {
    return;
  }

  if (userPwCheck.value && checking !== userPw.value) {
    isSamePw.value = false;
    pwCheckErrorMsg.value = "비밀번호가 일치하지 않습니다.";
  } else if (checking === userPw.value) {
    isSamePw.value = true;
    pwCheckErrorMsg.value = "비밀번호가 일치합니다.";
  } else {
    pwCheckErrorMsg.value = "";
  }
};

watch(userPwCheck, (checking) => {
  pwSameCheck(checking);
});

// 비밀번호 재설정 요청
const onSubmit = async () => {
  if (!isValidPw.value || !isSamePw.value) return;

  const resetPwRequest = {
    userId: sessionStorage.getItem("resetUserId"),
    newPw: userPw.value,
  };

  try {
    await userStore.resetPw(resetPwRequest);
    sessionStorage.removeItem("resetUserId");
    router.replace({ name: "logIn" });
  } catch (error) {
    console.log(error);
    const errMsg = error.response.data.message
      ? error.response.data.message
      : "비밀번호 재설정 중 오류 발생.....";
    alert(errMsg);
    router.replace({ name: "findPw" });
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
  margin: 30px 0;
}

.input label {
  font-weight: 600;
  display: block;
  text-align: left;
}

.errMsg {
  color: red;
}

.availableMsg {
  color: blue;
}
</style>
