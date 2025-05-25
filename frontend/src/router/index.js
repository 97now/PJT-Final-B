import { createRouter, createWebHistory } from "vue-router";
import { useUserStore } from "@/stores/userStore";

import MainView from "@/views/MainView.vue";
import SignupView from "@/views/SignupView.vue";
import LoginView from "@/views/LoginView.vue";
import FindIdView from "@/views/FindIdView.vue";
import FindPwView from "@/views/FindPwView.vue";
import ResetPwView from "@/views/ResetPwView.vue";
import FollowListView from "@/views/FollowListView.vue";
import MyPageView from "@/views/MyPageView.vue";
import VideoDetailView from "@/views/VideoDetailView.vue";

const routes = [
  {
    path: "/",
    name: "main",
    component: MainView,
    meta: { requiresAuth: false },
  },
  {
    path: "/signUp",
    name: "signUp",
    component: SignupView,
    meta: { requiresAuth: false },
  },
  {
    path: "/logIn",
    name: "logIn",
    component: LoginView,
    meta: { requiresAuth: false },
  },
  {
    path: "/findId",
    name: "findId",
    component: FindIdView,
    meta: { requiresAuth: false },
  },
  {
    path: "/findPw",
    name: "findPw",
    component: FindPwView,
    meta: { requiresAuth: false },
  },
  {
    path: "/resetPw",
    name: "resetPw",
    component: ResetPwView,
    meta: { requiresAuth: false },
  },
  {
    path: "/:userId/myPage",
    name: "myPage",
    component: MyPageView,
    children: [
      {
        path: "follow-list/:relation",
        name: "followList",
        component: FollowListView,
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/:videoId/detail",
    name: "video-detail",
    component: VideoDetailView,
    meta: { requiresAuth: true },
  },
  {
    path: "/video/:id",
    component: VideoDetailView,
    meta: { requiresAuth: true },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const userStore = useUserStore();
  const isLoggedIn = userStore.isLoggedIn;

  if (to.meta.requiresAuth && !isLoggedIn) {
    alert("로그인이 필요한 서비스입니다");
    next("/login");
  } else {
    next();
  }
});

export default router;
