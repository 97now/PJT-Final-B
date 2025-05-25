<template>
  <div class="container">
    <SearchBar @search="onSearch" />
    <CategoryButtons
      :categories="categories"
      :selected="selectedCategory"
      @select="onCategorySelect"
    />
    <SortBar :sortType="sortType" @sort="onSort" />
    <VideoList :videos="filteredVideos" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import api from "@/api/axiosInstance";
import SearchBar from "@/components/common/SearchBar.vue";
import CategoryButtons from "@/components/common/CategoryButtons.vue";
import SortBar from "@/components/common/SortBar.vue";
import VideoList from "@/components/common/VideoList.vue";

const categories = ["등", "가슴", "하체", "팔", "코어", "전신", "유산소"];

const videos = ref([]);

onMounted(() => {
  api
    .get("/api/video")
    .then((res) => {
      videos.value = res.data.map((v) => ({
        id: v.videoId,
        title: v.videoTitle,
        category: v.videoPart,
        thumb: v.unqImgName, // 썸네일 이미지 경로
        views: v.videoViewCnt,
        likes: v.videoLikeCnt ?? 0, // 좋아요 수 없으면 0
        url: v.videoUrl,
      }));

      console.log("[MainView] videos = " + JSON.stringify(videos.value));
    })
    .catch((error) => {
      console.error("비디오 데이터 불러오기 실패:", error);
    });
});

const searchKeyword = ref("");
const selectedCategory = ref("");
const sortType = ref("views"); // 조회순

const onSearch = (keyword) => {
  searchKeyword.value = keyword;
};
const onCategorySelect = (cat) => {
  selectedCategory.value = cat;
};
const onSort = (type) => {
  sortType.value = type;
};

const filteredVideos = computed(() => {
  console.log("[MainView] selectedCategory : " + selectedCategory.value);
  console.log("[MainView] searchKeyword : " + searchKeyword.value);
  console.log("[MainView] sortType : " + sortType.value);
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
</script>

<style scoped>
.container {
  width: 100%;
  max-width: 900px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}
@media (max-width: 700px) {
  .container {
    max-width: 100%;
    padding: 0 10px;
  }
}
</style>
