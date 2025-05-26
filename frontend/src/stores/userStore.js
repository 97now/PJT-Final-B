import { defineStore } from "pinia";
import api from "@/api/axiosInstance";

export const useUserStore = defineStore("user", {
  state: () => ({
    token: null,
    userId: null,
    isLoggedIn: false,
    error: null,
  }),

  actions: {
    /*
    유저 관리
    */
    // 아이디 중복 확인
    async checkDuplicatedId(userId) {
      console.log("[userStore.js] 아이디 중복 확인 함수 호출");

      try {
        const response = await api.get(
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
        const response = await api.post(
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
      console.log("[userStore.js] 로그인 함수 호출, loginData = ", loginData);

      try {
        const response = await api.post(
          "http://localhost:8080/api/user/login",
          loginData
        );

        this.token = response.data.token;
        this.isLoggedIn = true;
        this.userId = response.data.userId;

        console.log("[userStore.js] 로그인 후 token = " + this.token);
        console.log("[userStore.js] 로그인 후 isLoggedIn = " + this.isLoggedIn);
        console.log("[userStore.js] 로그인 후 userId = " + this.userId);
      } catch (error) {
        const msg =
          typeof error.response?.data === "string"
            ? error.response.data
            : error.response?.data?.message || "로그인 중 오류가 발생했습니다.";
        this.error = msg;
        throw error;
      }
    },

    // 로그아웃
    logout() {
      try {
        this.userId = null;
        this.isLoggedIn = false;
        this.token = null;

        console.log("[userStore.js] 로그아웃 후 token = " + this.token);
        console.log(
          "[userStore.js] 로그아웃 후 isLoggedIn = " + this.isLoggedIn
        );
        console.log("[userStore.js] 로그아웃 후 userId = " + this.userId);
      } catch (error) {
        this.error =
          error.response?.data?.message || "로그아웃 중 오류가 발생했습니다.";
        throw error;
      }
    },

    // 사용자 정보 조회
    async fetchUserInfo(userId) {
      console.log("[userStore.js] 사용자 정보 조회 함수 호출");

      try {
        const response = await api.get(
          `http://localhost:8080/api/user/${userId}`
        );

        console.log(response.data);
        return response.data;
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "사용자 정보 조회 중 오류가 발생했습니다.";
        throw error;
      }
    },

    async fetchUserList() {
      try {
        const response = await api.get(`http://localhost:8080/api/user`);

        return response.data;
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "사용자 목록 조회 중 오류가 발생했습니다.";
        throw error;
      }
    },

    // 아이디 찾기
    async findId(findIdData) {
      console.log(
        "[userStore.js] 아이디 찾기 함수 호출, data = " +
          JSON.stringify(findIdData)
      );

      try {
        const response = await api.post(
          `http://localhost:8080/api/user/findId`,
          findIdData
        );

        return response.data;
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "아이디 찾기 중 오류가 발생했습니다.";
        throw error;
      }
    },

    // 비밀번호 찾기
    async findPw(findPwRequest) {
      console.log(
        "[userStore.js] 비밀번호 찾기 함수 호출, data = " +
          JSON.stringify(findPwRequest)
      );

      try {
        const response = await api.post(
          `http://localhost:8080/api/user/findPw`,
          findPwRequest
        );

        return response.data;
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "비밀번호 찾기 중 오류가 발생했습니다.";
        throw error;
      }
    },

    // 비밀번호 재설정
    async resetPw(resetPwRequest) {
      console.log(
        "[userStore.js] 비밀번호 재설정 함수 호출, data = " +
          JSON.stringify(resetPwRequest)
      );

      try {
        await api.post(
          `http://localhost:8080/api/user/resetPw`,
          resetPwRequest
        );
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "비밀번호 재설정 중 오류가 발생했습니다.";
        throw error;
      }
    },

    // 비밀번호 인증
    async verifyPassword(userData) {
      // console.log(
      //   "[userStore.js] 비밀번호 인증 함수 호출, userData = " +
      //     JSON.stringify(userData)
      // );

      try {
        const response = await api.post(
          `http://localhost:8080/api/user/verifyPw`,
          userData
        );

        // console.log(
        //   "[userStore.js] 비밀번호 인증 함수 결과 = " + response.data
        // );

        return response.data;
      } catch (error) {
        this.error =
          error.response?.data.message ||
          "비밀번호 인증 처리 중 오류가 발생했습니다";
      }
    },

    // 사용자 정보 수정
    async updateUser(userId, userData) {
      console.log(
        "[userStore.js] 사용자 정보 수정 함수 호출, userData = ",
        userData
      );

      try {
        const response = await api.put(
          `http://localhost:8080/api/user/${userId}`,
          userData
        );

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
        const response = await api.delete(
          `http://localhost:8080/api/user/${userId}`
        );
        this.userId = null;
        this.isLoggedIn = false;
        this.token = null;

        return response.data;
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
      console.log(
        "[userStore.js] fetchFollowingList 호출 / 현재 토큰 : " + this.token
      );

      try {
        const response = await api.get(
          `http://localhost:8080/api/follow/following`
        );

        return response.data;
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "팔로잉 목록 조회 중 오류가 발생했습니다.";
      }
    },

    // 나를 팔로우하는 목록
    async fetchFollowerList(userId) {
      console.log(
        "[userStore.js] fetchFollowerList 호출 / 현재 토큰 : " + this.token
      );

      try {
        const response = await api.get(
          `http://localhost:8080/api/follow/follower`
        );

        return response.data;
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "팔로워 목록 조회 중 오류가 발생했습니다.";
      }
    },

    // 팔로우 추가
    async followUser(targetId) {
      try {
        await api.post(`http://localhost:8080/api/follow/${targetId}`, {
          followee: targetId,
        });
      } catch (error) {
        this.error =
          error.response?.data?.message || "팔로우 중 오류가 발생했습니다.";
        throw error;
      }
    },

    // 언팔로우
    async unfollowUser(targetId) {
      try {
        await api.delete(`http://localhost:8080/api/follow/${targetId}`);
      } catch (error) {
        this.error =
          error.response?.data?.message || "언팔로우 중 오류가 발생했습니다.";
        throw error;
      }
    },

    // 팔로우 여부 체크
    async checkFollowed(targetId) {
      try {
        const response = await api.get(
          `http://localhost:8080/api/follow/${targetId}/check`
        );

        return response.data;
      } catch (error) {
        this.error =
          error.response?.data?.message ||
          "팔로우 여부 확인 중 오류가 발생했습니다.";
      }
    },

    // 에러 초기화
    clearError() {
      this.error = null;
    },
  },

  persist: {
    paths: ["token", "userId", "isLoggedIn"],
    storage: localStorage,
  },
});
