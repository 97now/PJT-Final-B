<template>
  <div>
    <button @click="toggleFollow" :class="{ isFollowed: isFollowed }">
      {{ value }}
    </button>
  </div>
</template>

<script setup>
defineProps({
  value: String,
  isFollowed: Boolean,
});

const emit = defineEmits(["toggleFollow"]);

const toggleFollow = () => {
  emit("toggleFollow");
};
</script>

<style scoped>
button {
  height: 35px;
  width: 90px;
  border: 1px solid #ddd;
  box-shadow: 1px 1px 2px #ccc;
  border-radius: 5px;
}

button:hover {
  cursor: pointer;
}

.isFollowed {
  background-color: gray;
  color: white;
}
</style>
