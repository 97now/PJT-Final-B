<template>
  <router-link :to="`/video/${video.id}`" class="video-card-link">
    <div class="video-card">
      <div class="video-thumb">
        <img :src="getYoutubeThumbnail(video.url)" alt="썸네일" class="thumb" />
      </div>
      <div class="video-info">
        <span class="video-title">{{ video.title }}</span>
        <div class="video-meta-group">
          <span class="video-meta">
            <img :src="eyeIcon" alt="조회수" class="icon" />{{ video.views }}
          </span>
          <span class="video-meta">
            <img :src="heartIcon" alt="좋아요" class="icon" />{{ video.likes }}
          </span>
  </div>
</div>

    </div>
  </router-link>
</template>
<script setup>
import eyeIcon from "@/assets/img/Eye.svg";
import heartIcon from "@/assets/img/Heart.svg";
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
.videos {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
  width: 100%;
  max-width: 900px;
  box-sizing: border-box;
}

.video-card {
  flex: 0 1 calc(50% - 10px);
  display: flex;
  flex-direction: column;
  background: #eee;
  border-radius: 15px;
  padding: 0;
  margin-bottom: 0;
  width: 100%;
  max-width: 300px;
  min-width: 150px;
  overflow: hidden;
  box-sizing: border-box;
}

.video-thumb {
  width: 100%;
  aspect-ratio: 16 / 9;
  background: #ccc;
  overflow: hidden;
}

.video-thumb img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
  display: block;
}

.video-info {
  padding: 18px;
  display: flex;
  flex-direction: row;
  justify-content: space-between; /* 좌우로 공간 배분 */
  align-items: center;            /* 세로 중앙 정렬 */
  gap: 10px;
}

.video-title {
  font-weight: bold;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex: 1;
  min-width: 0;
  text-align: left;
  /* 필요에 따라 width: 0; 추가 */
}

.video-meta-group {
  display: flex;
  flex-direction: column;
  align-items: flex-end; /* 오른쪽 정렬 */
  gap: 4px;
}


.video-meta {
  font-size: 15px;
  color: #555;
  display: flex;
  align-items: center;
  gap: 5px;
  flex-shrink: 0;
}

.icon {
  width: 10px;
  height: 10px;
  display: inline-block;
  vertical-align: middle;
  object-fit: contain;
}


@media (max-width: 700px) {
  .videos {
    gap: 10px;
  }
  .video-card {
    flex: 0 1 100%;
    max-width: 100%;
  }
}
</style>

