<template>
  <div class="container">
    <div class="video-viewer">
      <button
        class="arrow left"
        @click="prevVideo"
        :disabled="currentIndex === 0"
        :class="{ invisible: currentIndex === 0 }"
      >◀</button>
      <VideoList :videos="[filteredVideos[currentIndex]]" />
      <button
        class="arrow right"
        @click="nextVideo"
        :disabled="currentIndex === filteredVideos.length - 1"
        :class="{ invisible: currentIndex === filteredVideos.length - 1 }"
      >▶</button>
    </div>
  </div>
</template>


<script setup>
import { ref, computed, watch } from "vue";

import VideoList from "@/components/common/VideoList.vue";

const categories = ["등", "가슴", "하체", "팔", "코어", "전신"];
const videos = ref([
  {
    id: 1,
    title: "푸쉬업 완벽 가이드",
    category: "가슴",
    thumb: "../img/Video%20Call.png",
    views: 150,
    likes: 35,
  },
  {
    id: 2,
    title: "스쿼트 제대로 하는 법",
    category: "하체",
    thumb: "../img/Video%20Call.png",
    views: 200,
    likes: 52,
  },
  {
    id: 3,
    title: "플랭크 5분 챌린지",
    category: "코어",
    thumb: "../img/Video%20Call.png",
    views: 180,
    likes: 40,
  },
  {
    id: 4,
    title: "풀업 입문자 강좌",
    category: "등",
    thumb: "../img/Video%20Call.png",
    views: 120,
    likes: 25,
  },
]);

const searchKeyword = ref("");
const selectedCategory = ref("");
const sortType = ref("views"); // 조회순

const onSearch = (keyword) => {
  searchKeyword.value = keyword;
};
const onCategorySelect = (cat) => {
  selectedCategory.value = cat;
};
const onSort = () => {
  sortType.value = sortType.value === "views" ? "likes" : "views";
};

const filteredVideos = computed(() => {
  let result = videos.value;
  if (searchKeyword.value) {
    result = result.filter((v) => v.title.includes(searchKeyword.value));
  }
  if (selectedCategory.value) {
    result = result.filter((v) => v.category === selectedCategory.value);
  }
  result = [...result].sort((a, b) => b[sortType.value] - a[sortType.value]);
  return result;
});
const currentIndex = ref(0);
watch(filteredVideos, () => { currentIndex.value = 0; });

function prevVideo() { if (currentIndex.value > 0) currentIndex.value--; }
function nextVideo() { if (currentIndex.value < filteredVideos.value.length - 1) currentIndex.value++; }
</script>

<style scoped>
.arrow.invisible {
  visibility: hidden; /* 자리는 차지하지만 안 보임 */
}
.container {
  width: 100%;
  max-width: 900px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.video-viewer {
  display: flex;
  align-items: center;
  width: 100%;
  justify-content: center;
}
.arrow {
  font-size: 2rem;
  background: none;
  border: none;
  cursor: pointer;
  padding: 0 10px;
  color: #333;
  user-select: none;
}
.arrow:disabled {
  opacity: 0.3;
  cursor: not-allowed;
}
@media (max-width: 700px) {
  .container {
    max-width: 100%;
    padding: 0 10px;
  }
}
</style>