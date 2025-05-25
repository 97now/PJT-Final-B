<template>
  <div>
    <FollowListItem
      v-if="list.length !== 0"
      v-for="f in list"
      :key="f.userId"
      :user="f"
      @update-following-cnt="updateFollowingCnt"
    />
    <p v-else-if="relation === 'following'">팔로잉 목록이 없습니다</p>
    <p v-else-if="relation === 'follower'">팔로워 목록이 없습니다</p>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import FollowListItem from "@/components/user/FollowListItem.vue";

const emit = defineEmits(["updateFollowingCnt"]);

const userStore = useUserStore();
const route = useRoute();
const relation = computed(() => route.params.relation);

const followingList = ref([]);
const followerList = ref([]);

const userId = userStore.userId;

onMounted(async () => {
  if (userId) {
    followingList.value = await userStore.fetchFollowingList(userId);
    followerList.value = await userStore.fetchFollowerList(userId);
  }
});

const updateFollowingCnt = async () => {
  console.log("[FollowListView] updateUserInfo 호출");
  followingList.value = await userStore.fetchFollowingList(userId);
  followerList.value = await userStore.fetchFollowerList(userId);
  console.log(
    "[FollowListView] followingList 길이 : " + followingList.value.length
  );
  emit("updateFollowingCnt", followingList.value.length);
};

const list = computed(() => {
  return relation.value === "follower"
    ? followerList.value
    : followingList.value;
});
</script>

<style scoped>
p {
  padding: 20px;
  text-align: center;
  font-size: 20px;
}
</style>
