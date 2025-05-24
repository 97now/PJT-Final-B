<template>
  <div>
    <FollowListItem v-for="f in list" :key="f.userId" :user="f" />
  </div>
</template>

<script setup>
import { computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import FollowListItem from "@/components/user/FollowListItem.vue";
import { storeToRefs } from "pinia";

const userStore = useUserStore();
const route = useRoute();
const relation = computed(() => route.params.relation);

const { followerList, followingList } = storeToRefs(userStore);

onMounted(() => {
  const userId = userStore.user?.userId;
  if (userId) {
    userStore.fetchFollowingList(userId);
    userStore.fetchFollowerList(userId);
  }
});

const list = computed(() => {
  return relation.value === "follower" ? followerList : followingList;
});
</script>

<style scoped></style>
