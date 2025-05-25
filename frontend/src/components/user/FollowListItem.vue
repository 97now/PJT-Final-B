<template>
  <div class="user">
    <ProfilePicture :img="imgUrl" alt="sadas" />
    <div class="userInfo">
      <p>
        <strong>{{ user.userNickName }}</strong>
      </p>
      <p>{{ user.userId }}</p>
    </div>
    <FollowButton
      :value="user.isFollowed ? 'Following' : 'Follow'"
      :is-followed="user.checkFollowed"
      @toggle-follow="onToggleFollow(user)"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import ProfilePicture from "../common/ProfilePicture.vue";
import FollowButton from "../common/FollowButton.vue";
import { useUserStore } from "@/stores/userStore";

const userStore = useUserStore();

const imgUrl = ref("/User.png");
const prop = defineProps({
  user: Object,
});
const emit = defineEmits(["updateFollowingCnt"]);

const onToggleFollow = (user) => {
  console.log(
    "[FollowListItem] onToggleFollow 호출, targetId = " + user.userId
  );

  if (!user.checkFollowed) {
    userStore.followUser(user.userId);
    emit("updateFollowingCnt", 1);
  } else {
    userStore.unfollowUser(user.userId);
    emit("updateFollowingCnt", -1);
  }

  user.checkFollowed = !user.checkFollowed;
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
