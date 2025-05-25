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
          id="newPw"
          placeholder="새로운 비밀번호를 입력해주세요"
        />
      </div>
      <div class="input">
        <label>새로운 비밀번호 확인</label>
        <BaseInput
          type="password"
          id="newPwCheck"
          placeholder="새로운 비밀번호를 다시 입력해주세요"
        />
      </div>

      <BaseButton text="확인" />
    </form>
  </div>
</template>

<script setup>
import BaseInput from "@/components/common/BaseInput.vue";
import BaseButton from "@/components/common/BaseButton.vue";

console.log(sessionStorage.getItem("resetUserId"));

const onSubmit = () => {
  console.log("비밀번호 재설정 로직 수행해줄거임");
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
</style>
