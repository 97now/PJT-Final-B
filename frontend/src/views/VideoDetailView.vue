<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";
import eyeIcon from "@/assets/img/Eye.png";
import heartIcon from "@/assets/img/Heart.png";

const route = useRoute();

const videoTitle = ref("");
const likes = ref(0);
const views = ref(0);
const videoUrl = ref("");
const comments = ref([]);

function getYoutubeId(url) {
  if (!url) return '';
  const regExp = /(?:youtube\.com.*[?&]v=|youtu\.be\/)([A-Za-z0-9_-]{11})/;
  const match = url.match(regExp);
  return match && match[1] ? match[1] : '';
}

const youtubeEmbedUrl = computed(() => {
  const id = getYoutubeId(videoUrl.value);
  return id ? `https://www.youtube.com/embed/${id}` : '';
});

function getYoutubeThumbnail(url) {
  if (!url) return '';
  const regExp = /(?:youtube\.com.*[?&]v=|youtu\.be\/)([A-Za-z0-9_-]{11})/;
  const match = url.match(regExp);
  return match && match[1]
    ? `https://img.youtube.com/vi/${match[1]}/mqdefault.jpg`
    : '';
}

onMounted(() => {
  const videoId = route.params.id;

  axios.get(`/api/video/${videoId}`).then(res => {
    videoTitle.value = res.data.videoTitle;
    likes.value = res.data.videoLikeCnt;
    views.value = res.data.videoViewCnt;
    videoUrl.value = res.data.videoUrl;
  });

  axios.get('/api/review', {
    params: {
      key: 'video_id',
      word: videoId
    }
  }).then(res => {
    comments.value = res.data.map(r => ({
      id: r.reviewId,
      text: r.reviewContent,
      user: r.reviewUserNickName,
      date: r.reviewRegistDate
    }));
  });
});

</script>
<template>
  <div class="video-detail">
    <div class="video-area">
      <div class="video-thumb">
        <iframe
          v-if="youtubeEmbedUrl"
          width="100%"
          height="340"
          :src="youtubeEmbedUrl"
          frameborder="0"
          allow="autoplay; encrypted-media"
          allowfullscreen>
        </iframe>
</div>
      <div class="video-meta-bar">
        <div class="video-title">{{ videoTitle }}</div>
        <div class="video-stats-col">
          <span class="icon-wrap">
            <img :src="eyeIcon" alt="조회수" class="icon" />
            {{ views }}
          </span>
          <span class="icon-wrap">
            <img :src="heartIcon" alt="좋아요" class="icon" />
            {{ likes }}
          </span>
        </div>
      </div>
    </div>
    <hr class="divider" />
    <ul class="comment-list">
      <li v-for="(comment, idx) in comments" :key="comment.id" class="comment-item">
        <span class="comment-index">{{ idx + 1 }}.</span>
        <span class="comment-text">
          <strong>{{ comment.user }}</strong> : {{ comment.text }}
          <span style="color:#888; font-size:12px;" v-if="comment.date">({{ comment.date }})</span>
        </span>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.video-detail {
  width: 100%;
  max-width: 650px;
  margin: 0 auto;
}
.video-area {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 32px;
}
.video-thumb {
  width: 100%;
  max-width: 600px;
  height: 340px;
  background: #eee;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 18px;
  overflow: hidden;
}
.video-thumb img {
  width: 90%;
  height: 90%;
  object-fit: contain;
}
.video-meta-bar {
  width: 100%;
  max-width: 600px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: flex-end;
  margin-top: 18px;
  padding: 0 8px;
  box-sizing: border-box;
}
.video-title {
  font-size: 22px;
  font-weight: 700;
  color: #222;
  flex: 1;
  text-align: left;
  white-space: pre-line;
  word-break: break-all;
}
.video-stats-col {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 10px;
  min-width: 80px;
}
.icon-wrap {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 16px;
  color: #444;
}
.icon {
  width: 22px;
  height: 22px;
  display: inline-block;
  vertical-align: middle;
}
.divider {
  margin: 28px 0 8px 0;
  border: none;
  border-bottom: 1.5px solid #bbb;
}
.comment-list {
  list-style: none;
  padding: 0;
  margin: 0;
}
.comment-item {
  display: flex;
  align-items: center;
  border-bottom: 1px solid #eee;
  padding: 8px 0;
  font-size: 15px;
}
.comment-index {
  width: 24px;
  color: #888;
}
.comment-text {
  flex: 1;
}
.comment-actions {
  color: #666;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 6px;
}
.comment-actions i {
  margin-right: 2px;
}
</style>
