<template>
  <router-link :to="`/video/${video.id}`" class="video-card-link">
    <div class="video-card">
      <div class="video-thumb">
        <img :src="getYoutubeThumbnail(video.url)" alt="썸네일" class="thumb" />
      </div>
      <div class="video-info">
        <span class="video-title">{{ video.title }}</span>
        <span class="video-meta">
          <img :src="eyeIcon" alt="조회수" class="icon" />{{
            video.views
          }}&nbsp; <img :src="heartIcon" alt="좋아요" class="icon" />{{
            video.likes
          }}
        </span>
      </div>
    </div>
  </router-link>
</template>
<script setup>
import eyeIcon from "@/assets/img/Eye.png";
import heartIcon from "@/assets/img/Heart.png";
// import videoCallIcon from '@/assets/img/Video_Call.png'
const props = defineProps({ video: Object });

function getYoutubeThumbnail(url) {
  if (!url) return "";
  const regExp =
    /(?:youtube\.com\/(?:[^\/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([A-Za-z0-9_-]{11})/;
  const match = url.match(regExp);
  return match && match[1]
    ? `https://img.youtube.com/vi/${match[1]}/mqdefault.jpg`
    : "";
}
</script>

<style scoped>
.video-card {
  flex: 0 1 calc(50% - 15px); /* 2열로 배치, gap 고려 */
  box-sizing: border-box;
  background: #eee;
  border-radius: 15px;
  padding: 20px;
  margin-bottom: 20px;
  width: 100%;
  max-width: 400px;
  min-width: 280px;
}

.video-thumb {
  width: 100%;
  aspect-ratio: 16 / 9;
  background: #ccc;
  border-radius: 10px;
  overflow: hidden;
  flex-shrink: 0;
}
.video-thumb img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
  display: block;
}

.video-info {
  margin-top: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
}

.video-title {
  font-weight: bold;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex: 1;
  min-width: 0; /* 텍스트 오버플로우를 위해 필요 */
}

.video-meta {
  font-size: 15px;
  color: #555;
  display: flex;
  align-items: center;
  gap: 5px;
  flex-shrink: 0; /* 메타 정보는 줄어들지 않도록 */
}

.icon {
  width: 20px;
  height: 20px;
  display: inline-block;
  vertical-align: middle;
}

@media (max-width: 700px) {
  .video-card {
    flex: 0 1 100%;
    max-width: 100%;
  }
}
</style>
