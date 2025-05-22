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
  width: 100%;
  border-radius: 100%;
}
</style>
