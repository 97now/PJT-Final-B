<template>
  <div class="my-page-view" v-if="user">
    <div class="myInfo-container">
      <div class="myInfo">
        <ProfilePicture
          :img="imgUrl"
          alt="내 프로필 이미지"
          @click="goToDefault"
          style="cursor: pointer"
        />
        <div class="nickname" @click="goToDefault" style="cursor: pointer">
          <p>{{ user.userNickName }}</p>
        </div>
      </div>
      <div class="modify-info-btn">
        <RouterLink :to="{ name: 'modifyUserInfo', params: { userId } }"
          >회원정보수정</RouterLink
        >
      </div>
    </div>

    <!-- 유저 검색 -->
    <SearchBar
      class="searchBar"
      v-model="searchKeyword"
      @search="onSearch"
      placeholder="닉네임을 입력하세요"
    />

    <!-- follow 정보 -->
    <div class="follow">
      <div
        :class="{ active: currentView === 'following' }"
        class="follow-btn"
        @click="goToFollowing"
      >
        Following &nbsp; {{ user.followingCnt }} 명
      </div>
      <div
        :class="{ active: currentView === 'follower' }"
        class="follow-btn"
        @click="goToFollower"
      >
        Follower &nbsp; {{ user.followerCnt }} 명
      </div>
    </div>

    <!-- 화면 전환 -->
    <LikeVideoView v-if="currentView === 'default'" />
    <FollowListView
      v-else
      :relation="currentView"
      :keyword="searchKeyword"
      @update-following-cnt="onUpdateFollowingCnt"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import ProfilePicture from "@/components/common/ProfilePicture.vue";
import LikeVideoView from "@/views/LikeVideoView.vue";
import FollowListView from "@/views/FollowListView.vue";
import SearchBar from "@/components/common/SearchBar.vue";

const userStore = useUserStore();
const route = useRoute();
const router = useRouter();

const { userId } = storeToRefs(userStore);

const user = ref(null);
const imgUrl = ref("/User.png");

onMounted(async () => {
  user.value = await userStore.fetchUserInfo(userId.value);
  // console.log("로그인된 유저 : " + userId.value);
  console.log(user.value);
});

const onUpdateFollowingCnt = (newVal) => {
  console.log("[MyPageView] onUpdateUserInfo 호출");
  user.value.followingCnt = newVal;
  console.log("[MyPageView] user 정보 = " + user.value);
};

// 유저 검색
const searchKeyword = ref("");
const onSearch = (keyword) => {
  console.log("[MyPageView] 검색 키워드 = " + keyword);
  searchKeyword.value = keyword;

  if (keyword.trim()) {
    router.push(`/${userId.value}/myPage/follow-list/search`);
  } else {
    goToDefault();
  }
};

// 보여줄 내용 구분
const currentView = computed(() => {
  const relation = route.params.relation;
  if (searchKeyword.value.trim()) {
    return "search";
  }
  return relation || "default";
});

function goToDefault() {
  searchKeyword.value = "";
  router.push(`/${userId.value}/myPage`);
}

function goToFollowing() {
  router.push(`/${userId.value}/myPage/follow-list/following`);
}

function goToFollower() {
  router.push(`/${userId.value}/myPage/follow-list/follower`);
}
</script>

<style scoped>
.my-page-view {
  width: 100%;
}

.myInfo-container {
  display: flex;
  justify-content: center;
  align-items: baseline;
  width: 100%;
  position: relative;
  left: 55px;
}

.myInfo {
  margin-top: 20px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.modify-info-btn {
  padding: 10px;
  margin-left: 25px;
  border-radius: 10px;
  line-height: 100%;
  position: relative;
  bottom: 12px;
}

.modify-info-btn a {
  text-decoration: none;
  color: black;
}

.nickname {
  height: 80%;
  width: 200px;
  border-bottom: 3px solid #7e7e7e;
  box-shadow: 0px 2px 1px 0.5 #ccc;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.nickname p {
  font-size: 1.5rem;
}

.searchBar {
  background-color: #eee;
  padding: 3px 25px;
  width: fit-content;
  justify-self: center;
  border-radius: 10px;
}

.follow {
  display: flex;
  padding: 0px 60px;
  justify-content: space-between;
  height: 40px;
  align-items: end;
  align-items: center;
  border-bottom: 3px solid #7e7e7e;
  margin: 20px 0px;
}

.follow-btn {
  display: flex;
  height: 40px;
  text-align: center;
  align-items: center;
  font-weight: 500;
  color: #555;
  cursor: pointer;
  user-select: none;
  padding: 0 8px;
  border-radius: 4px;
  transition: background 0.2s;
}

.follow-btn.active {
  color: black;
  font-weight: bold;
  text-decoration: underline;
}
</style>
