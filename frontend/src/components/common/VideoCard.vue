<template>
  <router-link :to="`/video/${video.id}`" class="video-card-link">
    <div class="video-card">
      <div class="video-thumb">
        <img :src="getYoutubeThumbnail(video.url)" alt="썸네일" class="thumb"/>
      </div>
      <div class="video-info">
        <span class="video-title">{{ video.title }}</span>
        <span class="video-meta">
          <img :src="eyeIcon" alt="조회수" class="icon" />{{ video.views }}&nbsp;
          <img :src="heartIcon" alt="좋아요" class="icon" />{{ video.likes }}
        </span>
      </div>
    </div>
  </router-link>
</template>

<script setup>
import eyeIcon from '@/assets/img/Eye.png'
import heartIcon from '@/assets/img/Heart.png'
// import videoCallIcon from '@/assets/img/Video_Call.png'
const props = defineProps({ video: Object })

function getYoutubeThumbnail(url) {
  if (!url) return '';
  const regExp = /(?:youtube\.com\/(?:[^\/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([A-Za-z0-9_-]{11})/;
  const match = url.match(regExp);
  return match && match[1]
    ? `https://img.youtube.com/vi/${match[1]}/mqdefault.jpg`
    : '';
}

</script>

<style scoped>
.video-card {
  flex: 0 1 43%;
  box-sizing: border-box;
  background: #eee;
  border-radius: 15px;
  padding: 20px;
  margin-bottom: 20px;
  min-width: 300px;
  max-width: 400px;
}
.video-thumb {
  width: 100%;
  aspect-ratio: 16 / 9;
  background: #ccc;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  overflow: hidden;
  position: relative;
  min-height: 120px;
}
.video-thumb img {
  width: 100%;
  height: 100%;
  object-fit: contain;
  object-position: center;
  background: #fff;
}
.video-info {
  margin-top: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.video-title {
  font-weight: bold;
}
.video-meta {
  font-size: 15px;
  color: #555;
  display: flex;
  align-items: center;
  gap: 5px;
}
.icon {
  width: 20px;
  height: 20px;
  display: inline-block;
  vertical-align: middle;
}
@media (max-width: 700px) {
  .video-card { flex: 0 1 100%; max-width: 100%; }
}
.thumb {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
  background: #fff;
}
</style>
