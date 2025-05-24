<template>
  <div class="user-form">
    <div class="page-title">
      <h1>회원가입</h1>
    </div>
    <form @submit.prevent="onSubmit">
      <div class="input">
        <label for="id">아이디</label>
        <div class="idInput">
          <BaseInput
            type="text"
            id="id"
            placeholder="아이디를 입력해주세요 (영어 소문자 또는 숫자 8~20자)"
            v-model="userId"
          />
          <button @click.prevent="checkDuplicatedId">중복 확인</button>
        </div>
        <p
          :class="{
            errorMsg: idErrorMsg,
            availableMsg: isValidId && isIdChecked,
          }"
          v-if="idErrorMsg"
        >
          {{ idErrorMsg }}
        </p>
      </div>
      <div class="input">
        <label for="pw">비밀번호</label>
        <BaseInput
          type="password"
          id="pw"
          placeholder="비밀번호를 입력해주세요 (문자, 숫자, 특수문자 포함 8~20자)"
          v-model="userPw"
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
            placeholder="이메일 계정 아이디"
            v-model="userEmail"
          />
          @
          <BaseInput
            type="text"
            id="emailDomain"
            placeholder="이메일 도메인"
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
      <BaseButton type="submit" text="가입하기" />
    </form>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import { useUserStore } from "@/stores/userStore";
import { useRouter } from "vue-router";
import BaseInput from "@/components/common/BaseInput.vue";
import EmailDomainDropbox from "@/components/common/EmailDomainDropbox.vue";
import BaseButton from "@/components/common/BaseButton.vue";
import BaseDropBox from "@/components/common/BaseDropBox.vue";

const userStore = useUserStore();
const router = useRouter();

// 담아줄 데이터
const userId = ref("");
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
const isValidId = ref(false); // 아이디 정규식 검사
const isIdChecked = ref(false); // 아이디 중복확인 완료 여부 검사
const isValidPw = ref(false); // 비밀번호 정규식 검사
const isSamePw = ref(true); // 비밀번호 확인란 일치 여부 검사
const isValidPhone = ref(false); // 휴대폰 번호 정규식 검사
const isValidEmail = ref(false); // 이메일 번호 정규식 검사

// 결과 메시지
const idErrorMsg = ref("");
const pwErrorMsg = ref("");
const pwCheckErrorMsg = ref("");
const phoneErrorMsg = ref("");
const emailErrorMsg = ref("");

/* Validation 함수 */
// 아이디 정규식 검사
const idValidationCheck = () => {
  const idReg = /^[a-z0-9]{8,20}$/;
  if (!userId.value) {
    // id 입력값 없는 경우
    idErrorMsg.value = "아이디를 입력해주세요.";
    isValidId.value = false;
  } else if (!idReg.test(userId.value)) {
    // id 입력값은 있지만 정규식과 일치하지 않는 경우
    idErrorMsg.value =
      "아이디는 영어 소문자와 숫자로 구성된 8~20자리 문자열이어야 합니다.";
    isValidId.value = false;
  } else {
    // 정규식 일치하는 입력값인 경우
    idErrorMsg.value = "사용 가능한 아이디입니다";
    isValidId.value = true;
  }
};

// 아이디 입력값 변경 감지
watch(userId, () => {
  isIdChecked.value = false; // 아이디가 변경되면 중복 확인 상태 초기화
  idErrorMsg.value = "";
});

// 아이디 중복 확인
const checkDuplicatedId = async () => {
  if (!userId.value) {
    // 아이디 입력값 없는 경우
    idErrorMsg.value = "아이디를 입력해주세요.";
    isIdChecked.value = false;
    isValidId.value = false;
    return;
  }

  // 입력값 있으면 정규식 검사
  idValidationCheck();
  if (!isValidId.value) {
    // 정규식과 일치하지 않으면 false 반환
    isIdChecked.value = false;
    isValidId.value = false;
    return;
  }

  // 중복 검사
  try {
    // userStore의 중복 검사 실행해서 넘어온 responst를 result에 담아줌
    const result = await userStore.checkDuplicatedId(userId.value);
    idErrorMsg.value = result.message;
    isValidId.value = !result.isDuplicated;
  } catch (error) {
    // 오류 발생
    idErrorMsg.value = "아이디 중복 확인 중 오류가 발생했습니다.";
    isValidId.value = false;
    isIdChecked.value = false;
  }

  isIdChecked.value = true;
};

// 비밀번호 정규식 검사
const pwValidationCheck = () => {
  const pwReg = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\W_]).{8,20}$/;
  if (!userPw.value) {
    pwErrorMsg.value = "비밀번호를 입력해주세요";
    isValidPw.value = false;
  } else if (!pwReg.test(userPw.value)) {
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
  } else if (checking === userPw.value) {
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

// 제출
const onSubmit = async () => {
  if (!isIdChecked.value) {
    idErrorMsg.value = "아이디 중복검사를 해주세요";
    return;
  }

  idValidationCheck();
  phoneValidationCheck();
  emailValidationCheck();

  if (
    !isValidId.value ||
    !isValidPw.value ||
    !isSamePw.value ||
    !isValidPhone.value ||
    !isValidEmail.value
  ) {
    console.log("isValidId : " + isValidId.value + " " + idErrorMsg.value);
    console.log("isValidPw : " + isValidPw.value + " " + pwErrorMsg.value);
    console.log("isSamePw : " + isSamePw.value + " " + pwCheckErrorMsg.value);
    console.log(
      "isValidPhone : " + isValidPhone.value + " " + phoneErrorMsg.value
    );
    console.log(
      "isValidEmail : " + isValidEmail.value + " " + emailErrorMsg.value
    );
    return;
  }

  const user = {
    userId: userId.value.trim(),
    userPw: userPw.value.trim(),
    userNickName: userName.value.trim(),
    userEmail: `${userEmail.value.trim()}@${userEmailDomain.value.trim()}`,
    userPhone: userPhone.value.trim(),
    userBirth: `${birthYear.value}-${String(birthMonth.value).padStart(
      2,
      "0"
    )}-${String(birthDay.value).padStart(2, "0")}`,
  };

  try {
    await userStore.register(user);
    alert("회원가입 성공 ⭐");
    router.push("/login");
  } catch (error) {
    alert("회원가입 중 오류 발생ㅠㅠ");
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

.idInput {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: center;
}

.idInput button {
  width: 100px;
  margin-top: 10px;
  padding: 12px 0px;
  background-color: #aaa;
  border: none;
  border-radius: 5px;
  box-shadow: 1px 1px 2px #ccc;
  font-weight: bold;
}

.idInput button:hover {
  background-color: #808080;
  cursor: pointer;
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
</style>
