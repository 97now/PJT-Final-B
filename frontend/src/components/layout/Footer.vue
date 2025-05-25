<template>
  <footer>
    <button class="link" v-if="userId" @click="deleteUser">회원탈퇴</button>
  </footer>
</template>

<script setup>
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import { storeToRefs } from "pinia";

const router = useRouter();
const userStore = useUserStore();

const { userId } = storeToRefs(userStore);

const deleteUser = () => {
  userStore.deleteUser(userId.value);
  router.push("/");
};
</script>

<style scoped>
.link {
  background-color: black;
  color: #777;
  border: none;
}

.link:hover {
  cursor: pointer;
}
</style>
