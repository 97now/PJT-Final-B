<template>
  <ul class="comment-list">
    <li
      v-for="(comment, idx) in comments"
      :key="comment.id"
      class="comment-item"
    >
      <span class="comment-index">{{ idx + 1 }}.</span>
      <span class="comment-text">
        <strong>{{ comment.user }}</strong> :
        <template v-if="editingCommentId === comment.id">
          <CommentInput
            :editMode="true"
            :initialContent="editingContent"
            @submit="onSubmitEdit"
            @cancel="onCancelEdit"
          />
        </template>
        <template v-else>
          {{ comment.text }}
          <span style="color: #888; font-size: 12px" v-if="comment.date">
            ({{ comment.date }})
          </span>
        </template>
      </span>
      <span class="comment-actions" v-if="comment.userId === myUserId">
        <a @click="onStartEdit(comment)" class="action-link">
          <img :src="pencilIcon" alt="수정" class="action-icon" />
          수정
        </a>
        <span class="action-divider">|</span>
        <a @click="onDelete(comment.id)" class="action-link">
          <img :src="deleteIcon" alt="삭제" class="action-icon" />
          삭제
        </a>
      </span>
    </li>
  </ul>
</template>

<script setup>
import CommentInput from "@/components/layout/CommentInput.vue";
import pencilIcon from "@/assets/img/Pencil.png";
import deleteIcon from "@/assets/img/delete.png";

const props = defineProps({
  comments: Array,
  myUserId: [String, Number],
  editingCommentId: [String, Number, null],
  editingContent: String,
});

const emit = defineEmits([
  "startEdit",
  "cancelEdit",
  "submitEdit",
  "deleteComment",
]);

function onStartEdit(comment) {
  emit("startEdit", comment);
}
function onCancelEdit() {
  emit("cancelEdit");
}
function onSubmitEdit(content) {
  emit("submitEdit", content);
}
function onDelete(commentId) {
  emit("deleteComment", commentId);
}
</script>

<style scoped>
.comment-list {
  list-style: none;
  padding: 0;
  margin: 0;
}
.comment-item {
  display: flex;
  align-items: center;
  border-bottom: 1px solid #eee;
  padding: 8px 0;
  font-size: 15px;
}
.comment-index {
  width: 24px;
  color: #888;
}
.comment-text {
  flex: 1;
}
.comment-actions {
  color: #666;
  font-size: 13px;
  display: flex;
  align-items: center;
  gap: 4px;
}
.action-link {
  color: #666;
  text-decoration: none;
  cursor: pointer;
}
.action-link:hover {
  color: #333;
  text-decoration: underline;
}
.action-divider {
  color: #ccc;
}
</style>
