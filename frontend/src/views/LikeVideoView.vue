<script setup>
import { ref, computed, watch, onMounted, inject } from "vue";
import api from "@/api/axiosInstance";
import VideoList from "@/components/common/VideoList.vue";
import { useUserStore } from "@/stores/userStore";
import { useRoute } from "vue-router";

// 사용자 정보
const userStore = useUserStore();
const route = useRoute();
const userId = computed(() => userStore.userId);

// 영상 데이터
const videos = ref([]);
const searchKeyword = ref("");
const selectedCategory = ref("");
const sortType = ref("views");
const currentIndex = ref(0);

// 좋아요한 영상 목록 불러오기
async function fetchLikedVideos() {
  try {
    const res = await api.get(`/api/video-like/user/${userId.value}`);
    videos.value = res.data.map((v) => ({
      id: v.videoId,
      title: v.videoTitle,
      category: v.videoPart,
      thumb: v.unqImgName,
      views: v.videoViewCnt,
      likes: v.videoLikeCnt,
      url: v.videoUrl,
    }));
    currentIndex.value = 0;
  } catch (error) {
    console.error("좋아요한 영상 목록을 가져오는데 실패했습니다:", error);
    videos.value = [];
  }
}

onMounted(() => {
  fetchLikedVideos();
});

// 이벤트 버스(옵션): 좋아요 상태 변경 시 목록 갱신
const eventBus = inject("eventBus");
if (eventBus) {
  eventBus.on("videoLikeChanged", () => {
    fetchLikedVideos();
  });
}

// 검색/카테고리/정렬
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
watch(filteredVideos, () => {
  currentIndex.value = 0;
});

function prevVideo() {
  if (currentIndex.value > 0) currentIndex.value--;
}
function nextVideo() {
  if (currentIndex.value < filteredVideos.value.length - 1)
    currentIndex.value++;
}
</script>

<template>
  <div class="container">
    <div v-if="filteredVideos.length > 0" class="video-viewer">
      <button
        class="arrow left"
        @click="prevVideo"
        :disabled="currentIndex === 0"
        :class="{ invisible: currentIndex === 0 }"
      >
        ◀
      </button>
      <div class="video-center">
        <VideoList :videos="[filteredVideos[currentIndex]]" />
      </div>
      <button
        class="arrow right"
        @click="nextVideo"
        :disabled="currentIndex === filteredVideos.length - 1"
        :class="{ invisible: currentIndex === filteredVideos.length - 1 }"
      >
        ▶
      </button>
    </div>
    <div v-else style="margin-top: 40px; text-align: center; color: #888">
      좋아요한 영상이 없습니다.
    </div>
  </div>
</template>

<style scoped>
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
  min-height: 400px;
}
.arrow {
  font-size: 2.5rem;
  background: none;
  border: none;
  cursor: pointer;
  padding: 0 20px;
  color: #333;
  user-select: none;
  transition: opacity 0.2s;
  height: 100%;
  display: flex;
  align-items: center;
}
.arrow:disabled {
  opacity: 0.3;
  cursor: not-allowed;
}
.arrow.invisible {
  visibility: hidden;
}
.video-center {
  flex: 1 1 0;
  display: flex;
  justify-content: center;
  align-items: center;
  min-width: 320px;
  max-width: 700px;
}
@media (max-width: 700px) {
  .container {
    max-width: 100%;
    padding: 0 10px;
  }
  .video-center {
    min-width: 0;
    max-width: 100%;
  }
}
</style>
