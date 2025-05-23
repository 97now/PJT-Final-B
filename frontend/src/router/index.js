import { createRouter, createWebHistory } from "vue-router";

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
  },
  {
    path: "/signUp",
    name: "signUp",
    component: SignupView,
  },
  {
    path: "/logIn",
    name: "logIn",
    component: LoginView,
  },
  {
    path: "/findId",
    name: "findId",
    component: FindIdView,
  },
  {
    path: "/findPw",
    name: "findPw",
    component: FindPwView,
  },
  {
    path: "/resetPw",
    name: "resetPw",
    component: ResetPwView,
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
  },
  {
    path: "/:videoId/detail",
    name: "video-detail",
    component: VideoDetailView,
  },
  { path: '/video/:id', 
    component: VideoDetailView },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
