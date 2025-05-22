<template>
  <div class="search-bar">
    <img :src="searchIcon" alt="검색" class="search-icon" />
    <input
      type="text"
      placeholder="검색어를 입력하시오."
      @input="onInput"
    />
  </div>
</template>

<script setup>
import searchIcon from '@/assets/img/Search.png'

const emit = defineEmits(['search'])
function onInput(e) {
  emit('search', e.target.value)
}
</script>

<style scoped>
.search-bar {
  margin-top: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}
.search-bar input {
  width: 400px;
  padding: 8px 8px 6px 8px;
  margin-left: 10px;
  border: none;
  border-bottom: 2px solid #000;
  outline: none;
  font-size: 17px;
  background: transparent;
  color: #000;
  transition: border-color 0.2s;
}
.search-bar input:focus {
  border-bottom: 2.5px solid #000;
}
.search-icon {
  width: 24px;
  height: 24px;
  display: inline-block;
  vertical-align: middle;
}
@media (max-width: 700px) {
  .search-bar input { width: 100%; min-width: 0; }
}
</style>