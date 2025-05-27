<template>
  <div class="sort-bar">
    <div class="dropdown" @click="toggleDropdown">
      <button class="sort-btn">
        <img :src="tuneImg" alt="정렬" class="sort-icon" />
        {{ sortLabelMap[sortType] }}
      </button>
      <ul v-if="dropdownOpen" class="dropdown-list">
        <li
          v-for="type in sortTypes"
          :key="type"
          :class="{ active: sortType === type }"
          @click.stop="selectSort(type)"
        >
          {{ sortLabelMap[type] }}
        </li>
      </ul>
    </div>
  </div>
</template>


<script setup>
import tuneImg from '@/assets/img/Tune.svg'

import { ref } from 'vue'

const props = defineProps({ sortType: String })
const emit = defineEmits(['sort'])

const sortTypes = ['views', 'likes']
const sortLabelMap = {
  views: '조회순',
  likes: '좋아요순'
}

const dropdownOpen = ref(false)

function toggleDropdown() {
  dropdownOpen.value = !dropdownOpen.value
}

function selectSort(type) {
  if (type !== props.sortType) {
    emit('sort', type)
  }
  dropdownOpen.value = false
}

// 바깥 클릭 시 드롭다운 닫기
function onClickOutside(event) {
  if (!event.target.closest('.dropdown')) {
    dropdownOpen.value = false
  }
}
window.addEventListener('click', onClickOutside)
</script>
<style scoped>
.sort-bar {
  margin: 10px 0 20px 0;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  width: 100%;
  max-width: 900px;
  box-sizing: border-box;
  padding-right: 10px;
}
.dropdown {
  position: relative;
}
.sort-btn {
  background: none;
  border: none;
  color: #000;
  font-size: 18px;
  padding: 4px 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
}
.sort-icon {
  width: 24px;
  height: 24px;
  margin-right: 4px;
  vertical-align: middle;
}
.dropdown-list {
  position: absolute;
  top: 110%;
  right: 0;
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 4px;
  min-width: 120px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  z-index: 10;
  padding: 0;
  margin: 0;
  list-style: none;
}
.dropdown-list li {
  padding: 10px 16px;
  cursor: pointer;
  transition: background 0.2s;
}
.dropdown-list li:hover,
.dropdown-list li.active {
  background: #f0f0f0;
  font-weight: bold;
}
@media (max-width: 700px) {
  .sort-bar { justify-content: center; padding-right: 0; }
}
</style>