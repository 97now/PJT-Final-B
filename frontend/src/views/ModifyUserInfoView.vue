<template>
  <div v-if="!authorized" class="user-form">
    <!-- 비밀번호 인증 -->
    <div class="page-title">
      <h1>비밀번호 인증</h1>
    </div>
    <form @submit.prevent="onAuthorize" class="input">
      <label for="pw-auth">비밀번호</label>
      <BaseInput
        v-model="authPw"
        type="password"
        id="pw-auth"
        placeholder="회원정보에 등록된 비밀번호를 입력해주세요"
        :required="true"
      />
      <BaseButton class="authorizeBtn" text="인증하기" />
    </form>
  </div>

  <!-- 회원 정보 수정 -->
  <div v-else class="user-form">
    <div class="page-title">
      <h1>회원 정보 수정</h1>
    </div>
    <!-- 프로필 사진 변경 -->
    <div class="profile-img-container">
      <ProfilePicture :img="newImgUrl" />
      <ImgUploadForm
        action="프로필 사진 등록"
        class="modify-info-btn-item"
        @changeProfileImg="updateProfileImg"
      />
      <ImgUploadForm
        action="프로필 사진 삭제"
        class="modify-info-btn-item"
        @deleteProfileImg="deleteProfileImg"
      />
    </div>
    <!-- 개인 정보 변경 -->
    <form @submit.prevent="onSubmit">
      <div class="input">
        <label for="pw">새 비밀번호</label>
        <BaseInput
          type="password"
          id="pw"
          placeholder="비밀번호를 입력해주세요 (문자, 숫자, 특수문자 포함 8~20자)"
          v-model="userPw"
          :required="false"
        />
        <p :class="{ errorMsg: !isValidPw }" v-if="pwErrorMsg">
          {{ pwErrorMsg }}
        </p>
      </div>
      <div class="input">
        <label for="pwCheck">비밀번호 확인</label>
        <BaseInput
          type="password"
          id="pwCheck"
          placeholder="비밀번호를 재입력해주세요"
          v-model="userPwCheck"
          :required="false"
        />
        <p
          :class="{
            errorMsg: isValidPw && !isSamePw,
            availableMsg: isValidPw && isSamePw,
          }"
          v-if="pwCheckErrorMsg"
        >
          {{ pwCheckErrorMsg }}
        </p>
      </div>
      <div class="input">
        <label for="name">닉네임</label>
        <BaseInput
          type="text"
          id="name"
          placeholder="닉네임을 입력해주세요"
          v-model="userName"
        />
      </div>
      <div class="input">
        <label for="phone">휴대폰 번호</label>
        <BaseInput
          type="text"
          id="phone"
          placeholder="휴대폰 번호 입력 ('-' 제외 11자리 입력)"
          v-model="userPhone"
          @input="restrictPhoneInput"
        />
        <p :class="{ errorMsg: !isValidPhone }" v-if="phoneErrorMsg">
          {{ phoneErrorMsg }}
        </p>
      </div>
      <div class="input">
        <label for="email">이메일 주소</label>
        <div class="emailInput">
          <BaseInput
            type="text"
            id="email"
            placeholder="이메일 아이디"
            v-model="userEmail"
          />
          @
          <BaseInput
            type="text"
            id="emailDomain"
            placeholder="도메인 입력"
            v-model="userEmailDomain"
          />
          <EmailDomainDropbox v-model="userEmailDomain" />
        </div>
        <p :class="{ errorMsg: !isValidEmail }" v-if="emailErrorMsg">
          {{ emailErrorMsg }}
        </p>
      </div>
      <div class="input">
        <label>생년월일</label>
        <div class="birth">
          <BaseDropBox
            :options="years"
            v-model="birthYear"
            placeholder="연도"
          />
          <BaseDropBox
            :options="months"
            v-model="birthMonth"
            placeholder="월"
          />
          <BaseDropBox :options="days" v-model="birthDay" placeholder="일" />
        </div>
      </div>
      <BaseButton type="submit" text="수정 완료" />
    </form>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import { useUserStore } from "@/stores/userStore";
import { useRouter } from "vue-router";
import BaseInput from "@/components/common/BaseInput.vue";
import EmailDomainDropbox from "@/components/common/EmailDomainDropbox.vue";
import BaseButton from "@/components/common/BaseButton.vue";
import BaseDropBox from "@/components/common/BaseDropBox.vue";
import ImgUploadForm from "@/components/common/ImgUploadForm.vue";
import ProfilePicture from "@/components/common/ProfilePicture.vue";
const userStore = useUserStore();
const router = useRouter();

const userId = userStore.userId;
const user = ref(null);

// 담아줄 데이터
const userPw = ref("");
const userPwCheck = ref("");
const userName = ref("");
const userPhone = ref("");
const userEmail = ref("");
const userEmailDomain = ref("");
const birthYear = ref("");
const birthMonth = ref("");
const birthDay = ref("");

// 생년월일 목록
const years = Array.from({ length: 100 }, (_, i) => 2024 - i);
const months = Array.from({ length: 12 }, (_, i) => i + 1);
const days = Array.from({ length: 31 }, (_, i) => i + 1);

// 유효성 검사 결과
const isValidPw = ref(false); // 비밀번호 정규식 검사
const isSamePw = ref(true); // 비밀번호 확인란 일치 여부 검사
const isValidPhone = ref(false); // 휴대폰 번호 정규식 검사
const isValidEmail = ref(false); // 이메일 번호 정규식 검사

// 결과 메시지
const pwErrorMsg = ref("");
const pwCheckErrorMsg = ref("");
const phoneErrorMsg = ref("");
const emailErrorMsg = ref("");

// 초기값 세팅
onMounted(async () => {
  user.value = await userStore.fetchUserInfo(userId);
  userName.value = user.value.userNickName;
  userPhone.value = user.value.userPhone;
  const userEmailArray = user.value.userEmail.split("@");
  userEmail.value = userEmailArray[0];
  userEmailDomain.value = userEmailArray[1];
  const userBirthArray = user.value.userBirth.split("-");
  birthYear.value = userBirthArray[0];
  birthMonth.value = Number(userBirthArray[1]);
  birthDay.value = Number(userBirthArray[2]);
});

// 비밀번호 인증
const authorized = ref(false);
const authPw = ref("");

const onAuthorize = async () => {
  const userData = {
    userId,
    userPw: authPw.value,
  };
  const result = await userStore.verifyPassword(userData);

  if (result) {
    authorized.value = true;
  } else {
    authorized.value = false;
    alert("비밀번호 인증 실패");
    router.replace({ name: "myPage", params: { userId } });
  }
};

/* Validation 함수 */
// 비밀번호 정규식 검사
const pwValidationCheck = () => {
  const pwReg = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\W_]).{8,20}$/;
  if (!userPw.value) {
    isValidPw.value = false;
  } else if (userPw.value && !pwReg.test(userPw.value)) {
    isValidPw.value = false;
    pwErrorMsg.value =
      "비밀번호는 문자, 숫자, 특수문자를 포함한 8~20자로 입력해주세요";
  } else {
    pwErrorMsg.value = "";
    isValidPw.value = true;
  }

  // console.log(pwErrorMsg.value);
  // console.log(isValidPw.value);
};

// 비밀번호란 바뀔 때마다 검사
watch(userPw, () => {
  if (!userPw.value) {
    pwCheckErrorMsg.value = "";
    return;
  }
  pwCheckErrorMsg.value = "";
  pwValidationCheck();
  pwSameCheck(userPwCheck.value);
});

// 비밀번호 확인란 일치 여부 검사
const pwSameCheck = (checking) => {
  if (!isValidPw.value) {
    return;
  }

  if (userPwCheck.value && checking !== userPw.value) {
    isSamePw.value = false;
    pwCheckErrorMsg.value = "비밀번호가 일치하지 않습니다.";
  } else if (userPw.value && checking === userPw.value) {
    isSamePw.value = true;
    pwCheckErrorMsg.value = "비밀번호가 일치합니다.";
  } else {
    pwCheckErrorMsg.value = "";
  }
};

watch(userPwCheck, (checking) => {
  pwSameCheck(checking);
});

// 휴대폰 번호에 숫자만 입력되게 제한
const restrictPhoneInput = (event) => {
  const input = event.target.value;
  // 숫자가 아닌 문자 제거
  const numbersOnly = input.replace(/[^0-9]/g, "");
  // 11자리로 제한
  userPhone.value = numbersOnly.slice(0, 11);
};

// 휴대폰 번호 정규식 검사
const phoneValidationCheck = () => {
  if (userPhone.value.length < 11) {
    isValidPhone.value = false;
    phoneErrorMsg.value = "전화번호는 010으로 시작하는 11자리 숫자여야 합니다";
    return;
  }

  const phoneReg = /^010[0-9]{8}$/;

  if (phoneReg.test(userPhone.value)) {
    isValidPhone.value = true;
    phoneErrorMsg.value = "";
  } else {
    isValidPhone.value = false;
    phoneErrorMsg.value = "전화번호는 010으로 시작하는 11자리 숫자여야 합니다";
  }
};

// 이메일 형식 검사
const emailValidationCheck = () => {
  const fullEmail = userEmail.value + "@" + userEmailDomain.value;
  const emailReg = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

  if (!emailReg.test(fullEmail)) {
    emailErrorMsg.value = "유효한 이메일 주소를 입력해주세요";
    isValidEmail.value = false;
  } else {
    emailErrorMsg.value = "";
    isValidEmail.value = true;
  }
};

// 프로필 사진 관련
const newImgUrl = ref(userStore.profileImg);
const profileImg = ref(null);

const updateProfileImg = ({ file, previewUrl }) => {
  newImgUrl.value = previewUrl;
  profileImg.value = file;
};

const deleteProfileImg = () => {
  userStore.deleteProfileImg();
  newImgUrl.value = userStore.DEFAULT_PROFILE_IMG;
  userStore.profileImg = userStore.DEFAULT_PROFILE_IMG;
};

// 제출 시
const onSubmit = async () => {
  phoneValidationCheck();
  emailValidationCheck();

  if (
    (userPw.value && !isValidPw.value) ||
    !isSamePw.value ||
    !isValidPhone.value ||
    !isValidEmail.value
  ) {
    console.log("유효성 검사 실패");
    return;
  }

  if (profileImg.value) {
    await userStore.uploadProfileImg(profileImg.value);
  }

  const user = {
    userId: userId,
    userPw: userPw.value ? userPw.value.trim() : authPw.value,
    userNickName: userName.value.trim(),
    userEmail: `${userEmail.value.trim()}@${userEmailDomain.value.trim()}`,
    userPhone: userPhone.value.trim(),
    userBirth: `${birthYear.value}-${String(birthMonth.value).padStart(
      2,
      "0"
    )}-${String(birthDay.value).padStart(2, "0")}`,
  };

  try {
    await userStore.updateUser(userId, user);
    alert("회원 정보 수정 완료 👍 !!!");
    router.replace("/");
  } catch (error) {
    alert("회원 정보 수정 중 오류 발생 ;ㅅ;");
  }
};
</script>

<style scoped>
.page-title {
  text-align: center;
  margin-bottom: 40px;
}

form {
  display: flex;
  flex-direction: column;
}

.input {
  margin: 10px 0;
}

.input label {
  font-weight: 600;
  display: block;
  text-align: left;
}

.errorMsg {
  color: red;
}

.availableMsg {
  color: blue;
}

.emailInput {
  display: flex;
  align-items: center;
  gap: 10px;
}

.emailInput > * {
  flex: 1;
}

.emailInput input {
  width: 100%;
}

.birth {
  display: flex;
  gap: 10px;
}

.authorizeBtn {
  margin-top: 20px;
}

.profile-img-container {
  display: flex;
  align-items: center;
  gap: 10px;
}
</style>
