import { defineStore } from "pinia";
import axios from "axios";

export const useUserStore = defineStore("user", {
  state: () => ({
    user: null,
    isLoggedIn: false,
    error: null,
    followingList: [],
    followerList: [],
    isFollowed: false,
  }),

  getters: {
    getUser: (state) => state.user,
    getIsLoggedIn: (state) => state.isLoggedIn,
    getError: (state) => state.error,
  },

  actions: {
    /*
    유저 관리
    */
    // 아이디 중복 확인
    async checkDuplicatedId(userId) {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/user/${userId}`
        );
        return {
          isDuplicated: true,
          message: "이미 사용 중인 아이디입니다.",
        };
      } catch (error) {
        if (error.response?.status === 404) {
          return {
            isDuplicated: false,
            message: "사용 가능한 아이디입니다.",
          };
        }
        throw error;
      }
    },

    // 회원가입
    async register(userData) {
      try {
        const response = await axios.post(
          "http://localhost:8080/api/user",
          userData
        );
        return response.data;
      } catch (error) {
        const msg =
          error.response?.data?.message || "알 수 없는 오류가 발생했습니다";
        console.log(msg);
        throw error;
      }
    },

    // 로그인
    async login(loginData) {
      try {
        const response = await axios.post(
          "http://localhost:8080/api/user/login",
          loginData
        );
        this.isLoggedIn = true;
        this.user = response.data;

        console.log(this.user);
      } catch (error) {
        const msg =
          error.response?.data?.message || "로그인 중 오류가 발생했습니다.";
        this.error = msg;
        throw error;
      }
    },

    // 로그아웃
    async logout() {
      try {
        await axios.get("http://localhost:8080/api/user/logout");
        this.user = null;
        this.isLoggedIn = false;
      } catch (error) {
        this.error =
          error.response?.data?.message || "로그아웃 중 오류가 발생했습니다.";
        throw error;
      }
    },

    // 사용자 정보 조회
    async fetchUserInfo(userId) {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/user/${userId}`
        );
        this.user = response.data;
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "사용자 정보 조회 중 오류가 발생했습니다.";
        throw error;
      }
    },

    // 사용자 정보 수정
    async updateUser(userId, userData) {
      try {
        const response = await axios.put(
          `http://localhost:8080/api/user/${userId}`,
          userData
        );
        this.user = response.data;
        return response.data;
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "사용자 정보 수정 중 오류가 발생했습니다.";
        throw error;
      }
    },

    // 사용자 삭제
    async deleteUser(userId) {
      try {
        await axios.delete(`http://localhost:8080/api/user/${userId}`);
        this.user = null;
        this.isLoggedIn = false;
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "사용자 삭제 중 오류가 발생했습니다.";
        throw error;
      }
    },

    /*
    팔로우 관리
    */
    // 내가 팔로잉하는 목록
    async fetchFollowingList(userId) {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/follow/${userId}/following`
        );
        this.followingList = response.data || [];
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "팔로잉 목록 조회 중 오류가 발생했습니다.";
        this.followingList = [];
      }
    },

    // 나를 팔로우하는 목록
    async fetchFollowerList(userId) {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/follow/${userId}/follower`
        );
        this.followerList = response.data || [];
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "팔로워 목록 조회 중 오류가 발생했습니다.";
        this.followerList = [];
      }
    },

    // 팔로우 추가
    async followUser(targetId) {
      try {
        await axios.post(`http://localhost:8080/api/follow`, {
          follower: this.user.userId,
          followee: targetId,
        });
        this.isFollowed = true;
      } catch (error) {
        this.error =
          error.response?.data?.message || "팔로우 중 오류가 발생했습니다.";
        throw error;
      }
    },

    // 언팔로우
    async unfollowUser(targetId) {
      try {
        await axios.delete(`http://localhost:8080/api/follow/${targetId}`);
        this.isFollowed = false;
      } catch (error) {
        this.error =
          error.response?.data?.message || "언팔로우 중 오류가 발생했습니다.";
        throw error;
      }
    },

    // 팔로우 여부 체크
    async checkFollowed(targetId) {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/follow/${targetId}/check`
        );
        this.isFollowed = response.data;
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "팔로우 여부 확인 중 오류가 발생했습니다.";
        this.isFollowed = false;
      }
    },

    // 에러 초기화
    clearError() {
      this.error = null;
    },
  },

  persist: true,
});
