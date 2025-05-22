<template>
  <div class="user">
    <ProfilePicture :img="imgUrl" alt="sadas" />
    <div class="userInfo">
      <p>
        <strong>{{ user.id }}</strong>
      </p>
      <p>{{ user.statusMsg }}</p>
    </div>
    <FollowButton
      :value="user.isFollowed ? 'Following' : 'Follow'"
      :is-followed="user.isFollowed"
      @toggle-follow="onToggleFollow(user)"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import ProfilePicture from "../common/ProfilePicture.vue";
import FollowButton from "../common/FollowButton.vue";

const imgUrl = ref("/User.png");
const prop = defineProps({
  user: Object,
});

const onToggleFollow = (user) => {
  console.log(
    "지금은 버튼만 바뀌는데 나중에 실제 팔로우 데이터에서 추가하고 삭제하는 로직 구현해야 함"
  );
  user.isFollowed = !user.isFollowed;
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
