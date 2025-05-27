<template>
  <div>
    <button class="changeImgBtn" @click="clickInput">프로필 사진 변경</button>
    <input
      type="file"
      ref="fileInput"
      @change="handleImgUpload"
      accept="image/*"
      style="display: none"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";

import api from "@/api/axiosInstance";

const emit = defineEmits(["changeProfileImg"]);

const fileInput = ref(null);

const clickInput = () => {
  fileInput.value.click();
};

const handleImgUpload = async (event) => {
  console.log("[ImgUploadForm] 이미지 업로드 함수 호출");

  const file = event.target.files[0];

  const formData = new FormData();
  formData.append("file", file);

  try {
    const response = await api.post(
      "http://localhost:8080/api/user/uploadProfile",
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    );

    if (response.data) {
      console.log(response.data);
      emit("changeProfileImg", response.data);
    }
  } catch (err) {
    console.error("업로드 실패 : ", err);
  }
};
</script>

<style scoped>
.changeImgBtn {
  padding: 7px;
  border-radius: 5%;
  margin-left: 10px;
  position: relative;
  bottom: 12px;
  text-decoration: none;
  color: black;
  box-shadow: 0px 0px 1px 0.8px #7e7e7e;
  font-size: small;
  border: none;
  background-color: white;
  cursor: pointer;
}
</style>
