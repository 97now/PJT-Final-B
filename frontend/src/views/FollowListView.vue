<template>
  <div>
    <FollowListItem
      v-for="f in list"
      :key="f.userId"
      :user="f"
      @update-following-cnt="updateFollowingCnt"
    />
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

onMounted(async () => {
  const userId = userStore.userId;
  if (userId) {
    followingList.value = await userStore.fetchFollowingList(userId);
    followerList.value = await userStore.fetchFollowerList(userId);
  }
});

const updateFollowingCnt = (change) => {
  console.log("[FollowListView] updateUserInfo 호출");
  emit("updateFollowingCnt", change);
};

const list = computed(() => {
  return relation.value === "follower"
    ? followerList.value
    : followingList.value;
});
</script>

<style scoped></style>
