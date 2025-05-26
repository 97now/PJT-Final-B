<template>
  <div class="input-wrapper">
    <div class="inputArea">
      <input
        v-model="modelValue"
        :id="id"
        :type="type"
        :placeholder="placeholder"
        :required="required"
      />
    </div>
  </div>
</template>

<script setup>
const props = defineProps([
  "id",
  "type",
  "placeholder",
  "required",
  // "modelValue", // v-model의 입력값
]);

const modelValue = defineModel();
// const emit = defineEmits(["update:modelValue"]); // v-model용 이벤트

// const onInput = (e) => {
//   emit("update:modelValue", e.target.value);
// };
</script>

<style scoped>
.input-wrapper {
  width: 100%;
}

.inputArea {
  margin-top: 10px;
  display: flex;
  column-gap: 10px;
  align-items: stretch;
}

.inputArea input {
  width: 100%;
  height: 25px;
  padding: 10px;
  border-radius: 5px;
  border: none;
  box-shadow: 1px 1px 2px #ddd;
  background-color: #ddd;
}

.checkDuplicated {
  width: 85px;
  background-color: #ccc;
  border: none;
  border-radius: 5px;
  box-shadow: 1px 1px 2px rgb(60, 60, 60);
  font-weight: bold;
}
</style>
