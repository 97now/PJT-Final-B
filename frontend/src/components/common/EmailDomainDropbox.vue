<!-- Dropbox.vue -->
<template>
  <select name="emailDomain" @change="onChange" :value="modelValue">
    <option value="도메인 선택" disabled selected>도메인 선택</option>
    <option v-for="domain in emailDomain" :key="domain" :value="domain">
      {{ domain }}
    </option>
    <option value="">직접 입력</option>
  </select>
</template>

<script setup>
const props = defineProps({
  emailDomain: Array,
  modelValue: String,
});
const emit = defineEmits(["update:modelValue"]);

const onChange = (e) => {
  emit("update:modelValue", e.target.value);
};

const emailDomain = [
  "naver.com",
  "gamil.com",
  "hanmail.net",
  "nate.com",
  "kakao.com",
];
</script>

<style scoped>
select {
  width: 100%;
  height: 45px;
  margin-top: 10px;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ddd;
  box-shadow: 1px 1px 2px #ddd;
  background-color: white;
}
</style>
