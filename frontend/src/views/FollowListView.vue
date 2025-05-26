<template>
  <div class="follow-list">
    <FollowListItem
      v-if="list.length !== 0"
      v-for="f in list"
      :key="f.userId"
      :user="f"
      @update-following-cnt="updateFollowingCnt"
    />
    <div
      v-else-if="list.length === 0 && relation !== 'default'"
      class="no-result"
    >
      <p v-if="relation === 'following'">팔로잉 목록이 없습니다</p>
      <p v-else-if="relation === 'follower'">팔로워 목록이 없습니다</p>
      <p v-else-if="relation === 'search'">
        "{{ keyword }}"에 대한 검색 결과가 없습니다
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, toRef, watch } from "vue";
import { useRoute } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import FollowListItem from "@/components/user/FollowListItem.vue";

const props = defineProps({
  keyword: String,
});

const keyword = toRef(props, "keyword");

const emit = defineEmits(["updateFollowingCnt"]);

const userStore = useUserStore();
const route = useRoute();
const relation = computed(() => route.params.relation);

const followingList = ref([]);
const followerList = ref([]);
const allUserList = ref([]);

const userId = userStore.userId;

onMounted(async () => {
  if (userId) {
    followingList.value = await userStore.fetchFollowingList(userId);
    followerList.value = await userStore.fetchFollowerList(userId);
    allUserList.value = await userStore.fetchUserList();
    console.log("[FollowListView] allUserList = " + allUserList.value);
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
  if (relation.value === "follower") {
    return followerList.value;
  } else if (relation.value === "following") {
    return followingList.value;
  } else if (relation.value === "search") {
    const searchResult = allUserList.value.filter((user) =>
      user.userNickName.toLowerCase().includes(keyword.value.toLowerCase())
    );

    searchResult.forEach(async (user) => {
      const isFollowed = await userStore.checkFollowed(user.userId);
      user.checkFollowed = isFollowed;
    });

    console.log(
      "[FollowListView] searchResult = " + JSON.stringify(searchResult)
    );
    return searchResult;
  } else {
    return [];
  }
});

watch(keyword, async (newValue) => {
  if (newValue && relation.value === "search") {
    const searchResult = allUserList.value.filter((user) =>
      user.userNickName.toLowerCase().includes(newValue.toLowerCase())
    );

    for (const user of searchResult) {
      const isFollowed = await userStore.checkFollowed(user.userId);
      user.checkFollowed = isFollowed;
    }
  }
});
</script>

<style scoped>
.follow-list {
  padding: 20px;
}

.no-result {
  text-align: center;
  padding: 40px 20px;
  background-color: #f8f8f8;
  border-radius: 8px;
  margin: 20px 0;
}

p {
  padding: 20px;
  text-align: center;
  font-size: 20px;
  color: #666;
}
</style>
