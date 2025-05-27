<template>
  <div class="user">
    <ProfilePicture
      :img="`http://localhost:8080${user.profileImg}`"
      alt="sadas"
    />
    <div class="userInfo">
      <p>
        <strong>{{ user.userNickName }}</strong>
      </p>
      <p>{{ user.userId }}</p>
    </div>
    <FollowButton
      :style="{ visibility: userId === user.userId ? 'hidden' : 'visible' }"
      :value="user.checkFollowed ? 'Following' : 'Follow'"
      :is-followed="user.checkFollowed"
      @toggle-follow="onToggleFollow(user)"
    />
  </div>
</template>

<script setup>
import ProfilePicture from "../common/ProfilePicture.vue";
import FollowButton from "../common/FollowButton.vue";
import { useUserStore } from "@/stores/userStore";

const userStore = useUserStore();

const userId = userStore.userId;
const prop = defineProps({
  user: Object,
});
const emit = defineEmits(["updateFollowingCnt"]);

const onToggleFollow = async (user) => {
  console.log(
    "[FollowListItem] onToggleFollow 호출, targetId = " + user.userId
  );

  try {
    if (!user.checkFollowed) {
      await userStore.followUser(user.userId);
    } else {
      await userStore.unfollowUser(user.userId);
    }
    user.checkFollowed = !user.checkFollowed;
    emit("updateFollowingCnt");
  } catch {
    console.error("팔로우 처리 중 오류 발생: ", error);
  }
};
</script>

<style scoped>
.user {
  display: flex;
  padding: 0 40px;
  justify-content: space-between;
  gap: 20px;
  height: 70px;
  align-items: center;
}

.userInfo {
  box-sizing: border-box;
  padding-left: 20px;
  width: 77%;
  text-align: left;
}
</style>
