<template>
  <div class="profile-picture">
    <img class="profile-img" :src="img" :alt="alt" />
  </div>
</template>

<script setup>
defineProps({
  img: String,
  alt: String,
});
</script>

<style scoped>
.profile-picture {
  width: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.profile-img {
  border: 3px #ccc solid;
  width: 50px;
  height: 50px;
  border-radius: 100%;
}
</style>
