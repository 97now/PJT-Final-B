<template>
  <div class="base-button">
    <button :type="type" @click="action">{{ text }}</button>
  </div>
</template>

<script setup>
defineProps({
  type: String,
  text: String,
  action: Function,
});
</script>

<style scoped>
button {
  width: 100%;
  height: 45px;
  margin-top: 30px;
  padding: 12px;
  font-size: 16px;
  font-weight: bold;
  background-color: #ccc;
  border: 1px solid black;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
}

button:hover {
  background-color: #808080;
}
</style>
