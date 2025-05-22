<template>
  <div>
    <FollowListItem v-for="f in list" :key="f.id" :user="f" />
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from "vue";
import { useRoute } from "vue-router";
import FollowListItem from "@/components/user/FollowListItem.vue";

const route = useRoute();
const relation = computed(() => route.params.relation);

const followerList = [
  {
    id: "o_oz97_",
    statusMsg: "Hi, I'm Eonji",
    isFollowed: true,
    img: "@/assets/img/User.png",
  },
  {
    id: "97.now",
    statusMsg: "Hi, I'm Hyunjae",
    isFollowed: false,
    img: "@/assets/img/User.png",
  },
  {
    id: "b_rkdnjs",
    statusMsg: "Hi, I'm beautiful and gorgeous gawon",
    isFollowed: false,
    img: "@/assets/img/User.png",
  },
  {
    id: "j_yeoon_",
    statusMsg: "플러팅 슈웃~!",
    isFollowed: false,
    img: "@/assets/img/User.png",
  },
  {
    id: "dojin9654",
    statusMsg: "Hi, I love BL",
    isFollowed: false,
    img: "@/assets/img/User.png",
  },
  {
    id: "joohyun_0913",
    statusMsg: "Hi, Watch your girlfriend dohyun",
    isFollowed: false,
    img: "@/assets/img/User.png",
  },
];

const followingList = [
  {
    id: "o_oz97_",
    statusMsg: "Hi, I'm Eonji",
    isFollowed: true,
    img: "@/assets/img/User.png",
  },
  {
    id: "naninaniyoyoyoyo",
    statusMsg: "MZ Girl",
    isFollowed: true,
    img: "@/assets/img/User.png",
  },
  {
    id: "sy_sysysy",
    statusMsg: "Cute creatures save the world",
    isFollowed: true,
    img: "@/assets/img/User.png",
  },
  {
    id: "peaceful.coco",
    statusMsg: "Hi, I'm YeoMiSae",
    isFollowed: true,
    img: "@/assets/img/User.png",
  },
];

const list = computed(() => {
  return relation.value === "follower" ? followerList : followingList;
});
</script>

<style scoped></style>
