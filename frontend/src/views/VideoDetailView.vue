<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";
import eyeIcon from "@/assets/img/Eye.png";
import heartIcon from "@/assets/img/Heart.png";
import pencilIcon from "@/assets/img/Pencil.png";
import deleteIcon from "@/assets/img/delete.png";
import CommentInput from "@/components/layout/CommentInput.vue"; 

import { useUserStore } from "@/stores/userStore";
const userStore = useUserStore();
const isLiked = ref(false);

const route = useRoute();

const videoTitle = ref("");
const likes = ref(0);
const views = ref(0);
const videoUrl = ref("");

const myUserId = computed(() => userStore.user.userId);
const comments = ref([]);
const editingCommentId = ref(null);
const editingContent = ref('');

function onLikeClick() {
  if (!userStore.isLoggedIn) {
    alert("로그인 후 이용해 주세요.");
    return;
  }
  const videoId = route.params.id;
  const userId = userStore.user.userId;

  if (!isLiked.value) {
    // 좋아요 추가
    axios.post("/api/video-like", {
      userId,
      videoId
    }).then(() => {
      likes.value += 1;
      isLiked.value = true;
    }).catch(err => {
      alert("좋아요 처리 중 오류가 발생했습니다.");
    });
  } else {
    // 좋아요 취소
    axios.delete(`/api/video-like/user/${userId}/video/${videoId}`)
      .then(() => {
        likes.value -= 1;
        isLiked.value = false;
      }).catch(err => {
        alert("좋아요 취소 중 오류가 발생했습니다.");
      });
  }
}


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
  const userId = userStore.user.userId;

  if (userStore.isLoggedIn) {
    axios.get(`/api/video-like/user/${userId}/video/${videoId}/check`)
      .then(res => {
        isLiked.value = res.data === true;
      });
  }

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
    console.log('서버에서 받아온 댓글 데이터:', res.data);  // 서버에서 받아온 데이터 확인
    comments.value = res.data.map(r => ({
      id: r.reviewId,
      text: r.reviewContent,
      user: r.reviewUserNickName,
      date: r.reviewRegistDate,
      userId: r.reviewUserId
    }));
    console.log('변환된 댓글 목록:', comments.value);  // 변환된 댓글 목록 확인
  });
});

// 댓글 등록
async function addComment(content) {
  const videoId = Number(route.params.id);
  const reviewUserId = userStore.user.userId;
  const reviewUserNickName = userStore.user.userNickName;
  const payload = {
    videoId,
    reviewUserId,
    reviewUserNickName,
    reviewContent: content,
  };
  console.log('댓글 등록 payload:', payload);
  try {
    await axios.post('/api/review', payload);
    // 댓글 목록 새로고침
    const res = await axios.get('/api/review', {
      params: {
        key: 'video_id',
        word: videoId
      }
    });
    comments.value = res.data.map(r => ({
      id: r.reviewId,
      text: r.reviewContent,
      user: r.reviewUserNickName,
      date: r.reviewRegistDate,
      userId: r.reviewUserId
    }));
  } catch (error) {
    console.log('댓글 등록 에러:', error.response?.data || error);
    alert('댓글 등록 중 오류가 발생했습니다: ' + (error.response?.data?.message || ''));
  }
}


// 댓글 삭제
async function deleteComment(commentId) {
  if (!confirm('정말 삭제하시겠습니까?')) return;
  await axios.delete(`/api/review/${commentId}`);
  comments.value = comments.value.filter(c => c.id !== commentId);
}

// 댓글 수정 시작
function startEdit(comment) {
  editingCommentId.value = comment.id;
  editingContent.value = comment.text;
}

// 댓글 수정 취소
function cancelEdit() {
  editingCommentId.value = null;
  editingContent.value = '';
}

// 댓글 수정 완료
async function submitEdit(content) {
  const commentId = editingCommentId.value;
  try {
    const res = await axios.put(`/api/review/${commentId}`, {
      reviewContent: content,
      reviewId: commentId
    });
    console.log('댓글 수정 응답:', res.data);
    const target = comments.value.find(c => c.id === commentId);
    if (target) target.text = content;
    editingCommentId.value = null;
    editingContent.value = '';
  } catch (error) {
    console.error('댓글 수정 에러:', error.response?.data || error);
    alert('댓글 수정 중 오류가 발생했습니다: ' + (error.response?.data?.message || ''));
  }
}

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
          <span class="icon-wrap" @click="onLikeClick" style="cursor:pointer;">
          <img
            :src="heartIcon"
            alt="좋아요"
            class="icon"
            :style="{ filter: isLiked ? 'invert(33%) sepia(98%) saturate(7495%) hue-rotate(344deg) brightness(98%) contrast(105%)' : 'none' }"
          />
          {{ likes }}
        </span>
        </div>
      </div>
    </div>
    <hr class="divider" />
    <ul class="comment-list">
    <li
      v-for="(comment, idx) in comments"
      :key="comment.id"
      class="comment-item"
    >
      <span class="comment-index">{{ idx + 1 }}.</span>
      <span class="comment-text">
        <strong>{{ comment.user }}</strong> : 
        <template v-if="editingCommentId === comment.id">
          <CommentInput
            :editMode="true"
            :initialContent="editingContent"
            @submit="submitEdit"
            @cancel="cancelEdit"
          />
        </template>
        <template v-else>
          {{ comment.text }}
          <span style="color:#888; font-size:12px;" v-if="comment.date">({{ comment.date }})</span>
        </template>
      </span>
      <span class="comment-actions" v-if="comment.userId === myUserId">
  <a @click="startEdit(comment)" class="action-link">
    <img :src="pencilIcon" alt="수정" class="action-icon" />
    수정
  </a>
  <span class="action-divider">|</span>
  <a @click="deleteComment(comment.id)" class="action-link">
    <img :src="deleteIcon" alt="삭제" class="action-icon" />
    삭제
  </a>
</span>

    </li>
  </ul>
  <!-- 댓글 입력창 (수정 중이 아닐 때만) -->
  <CommentInput
    v-if="!editingCommentId"
    @submit="addComment"
  />
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
  font-size: 13px;
  display: flex;
  align-items: center;
  gap: 4px;
}
.action-link {
  color: #666;
  text-decoration: none;
  cursor: pointer;
}
.action-link:hover {
  color: #333;
  text-decoration: underline;
}
.action-divider {
  color: #ccc;
}
</style>
