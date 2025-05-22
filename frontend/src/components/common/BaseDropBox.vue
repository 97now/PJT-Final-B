<!-- SignUpView.vue 에서 생년월일 선택 dropbox -->
<template>
  <select
    :value="modelValue"
    @change="$emit('update:modelValue', $event.target.value)"
    required
  >
    <option disabled value="">{{ placeholder }}</option>
    <option v-for="item in options" :key="item" :value="item">
      {{ item }}
    </option>
  </select>
</template>

<script setup>
defineProps({
  modelValue: [String, Number],
  options: Array,
  placeholder: String,
});
defineEmits(["update:modelValue"]);
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
  background-color: #ddd;
}

option {
  background-color: white;
}
</style>
