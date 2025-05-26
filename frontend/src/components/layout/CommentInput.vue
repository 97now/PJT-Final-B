<template>
  <div class="comment-input">
    <textarea
      v-model="content"
      placeholder="댓글을 입력하세요"
      rows="3"
    ></textarea>
    <div class="input-actions">
      <button @click="onSubmit" :disabled="!content.trim()">
        {{ editMode ? '수정' : '등록' }}
      </button>
      <button v-if="editMode" @click="onCancel">취소</button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, defineProps, defineEmits } from 'vue';

const props = defineProps({
  editMode: Boolean,
  initialContent: String,
});
const emit = defineEmits(['submit', 'cancel']);

const content = ref(props.initialContent || '');

watch(
  () => props.initialContent,
  (val) => (content.value = val || '')
);

function onSubmit() {
  emit('submit', content.value);
  content.value = '';
}
function onCancel() {
  emit('cancel');
  content.value = '';
}
</script>

<style scoped>
.comment-input {
  margin: 16px 0;
}
textarea {
  width: 100%;
  resize: none;
  font-size: 15px;
  padding: 8px;
  border-radius: 6px;
  border: 1px solid #ccc;
}
.input-actions {
  margin-top: 6px;
  display: flex;
  gap: 8px;
}
button {
  padding: 4px 14px;
  border: none;
  border-radius: 5px;
  background: #1976d2;
  color: white;
  cursor: pointer;
}
button[disabled] {
  background: #ccc;
  cursor: not-allowed;
}
</style>
