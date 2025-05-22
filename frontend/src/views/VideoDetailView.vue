<script setup>
import { ref } from "vue";

const comments = ref([
  { id: 1, text: "정말 재미있어요" },
  { id: 2, text: "유익해요" },
  { id: 3, text: "근성장 맛보기" },
  { id: 4, text: "3대 500가즈아" },
  { id: 5, text: "집중력 최고" },
  { id: 6, text: "발린이를 위한 운동" },
]);
const likes = ref(100);
const views = ref(1234);

import eyeIcon from "@/assets/img/Eye.png";
import heartIcon from "@/assets/img/Heart.png";
import videoCallIcon from "@/assets/img/Video_Call.png";
</script>

<template>
  <div class="video-detail">
    <div class="video-area">
      <div class="video-thumb">
        <img :src="videoCallIcon" alt="썸네일" />
      </div>
      <div class="video-meta-bar">
        <div class="video-title">{{ "OOOOO 영상" }}</div>
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
      <li
        v-for="(comment, idx) in comments"
        :key="comment.id"
        class="comment-item"
      >
        <span class="comment-index">{{ idx + 1 }}.</span>
        <span class="comment-text">{{ comment.text }}</span>
        <span class="comment-actions">
          <i class="fas fa-edit"></i> 수정
          <i class="fas fa-trash" style="margin-left: 10px"></i> 삭제
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
