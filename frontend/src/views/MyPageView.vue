<template>
  <div class="my-page-view">
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
    <FollowListView v-else :relation="currentView" />
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import ProfilePicture from "@/components/common/ProfilePicture.vue";
import LikeVideoView from "@/views/LikeVideoView.vue";
import FollowListView from "@/views/FollowListView.vue";

const userStore = useUserStore();
const route = useRoute();
const router = useRouter();

const { user } = storeToRefs(userStore);

const imgUrl = ref("/User.png");

const currentView = computed(() => {
  const relation = route.params.relation;
  return relation || "default";
});

function goToDefault() {
  router.push(`/${user.value.userId}/myPage`);
}

function goToFollowing() {
  router.push(`/${user.value.userId}/myPage/follow-list/following`);
}

function goToFollower() {
  router.push(`/${user.value.userId}/myPage/follow-list/follower`);
}
</script>

<style scoped>
.my-page-view {
  width: 100%;
}

.myInfo {
  margin-top: 20px;
  height: 50px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
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

.follow {
  display: flex;
  padding: 0px 60px;
  justify-content: space-between;
  height: 70px;
  align-items: end;
  border-bottom: 3px solid #7e7e7e;
  margin-bottom: 20px;
}

.follow-btn {
  display: flex;
  height: 40px;
  text-align: center;
  align-items: center;
  font-weight: 500;
  color: black;
  cursor: pointer;
  user-select: none;
  padding: 0 8px;
  border-radius: 4px;
  transition: background 0.2s;
}

.follow-btn.active {
  font-weight: bold;
  text-decoration: underline;
  background: #f0f0f0;
}
</style>
