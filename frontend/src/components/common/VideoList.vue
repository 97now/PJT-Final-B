<template>
  <div class="videos">
    <VideoCard v-for="video in videos" :key="video.id" :video="video" />
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import VideoCard from "./VideoCard.vue";
const props = defineProps({ videos: Array });

onMounted(() =>
  console.log("[VideoList] videos = " + JSON.stringify(props.videos))
);
</script>

<style scoped>
.videos {
  display: flex;
  flex-wrap: wrap;
  gap: 30px;
  justify-content: center;
  width: 100%;
  max-width: 900px;
  box-sizing: border-box;
}
@media (max-width: 700px) {
  .videos {
    gap: 16px;
  }
}
</style>
