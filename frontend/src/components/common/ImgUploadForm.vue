<template>
  <div>
    <button class="changeImgBtn" @click="clickInput">{{ action }}</button>
    <input
      v-if="action === '프로필 사진 등록'"
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

const props = defineProps({
  action: String,
});

const emit = defineEmits(["changeProfileImg", "deleteProfileImg"]);

// 프로필 이미지 업로드
const handleImgUpload = async (event) => {
  console.log("[ImgUploadForm] 이미지 업로드 함수 호출");

  const file = event.target.files[0];

  if (!file) return;

  // 미리보기용
  const previewUrl = URL.createObjectURL(file);
  emit("changeProfileImg", { file, previewUrl });
};

const fileInput = ref(null);

const clickInput = () => {
  if (props.action === "프로필 사진 등록") fileInput.value.click();
  else {
    emit("deleteProfileImg");
  }
};
</script>

<style scoped>
.changeImgBtn {
  padding: 7px;
  border-radius: 5px;
  margin-left: 10px;
  position: relative;
  text-decoration: none;
  color: black;
  box-shadow: 0px 0px 1px 0.8px #7e7e7e;
  font-size: small;
  border: none;
  background-color: white;
  cursor: pointer;
}
</style>
