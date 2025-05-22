<template>
 
  <div class="categories">
     <button
      class="underline-btn menu-btn"
      :class="{ active: selected === '' }"
      @click="select('')"
      aria-label="전체"
      >
      <img :src="menuImg" alt="전체" class="menu-icon" />
    </button>
    <button
      v-for="cat in categories"
      :key="cat"
      class="underline-btn"
      :class="{active: selected === cat}"
      @click="select(cat)"
      >{{ cat }}
    </button>
  </div>
</template>

<script setup>
import menuImg from '@/assets/img/Menu.png'

const props = defineProps({
  categories: Array,
  selected: String
})
const emit = defineEmits(['select'])
function select(cat) {
  emit('select', cat)
}
</script>

<style scoped>
.categories {
  margin: 30px 0 0 0;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  width: 100%;
}
.underline-btn {
  background: none;
  border: none;
  border-bottom: 2px solid #000;
  color: #000;
  font-size: 18px;
  padding: 4px 10px;
  cursor: pointer;
  transition: border-color 0.2s, color 0.2s;
}
.underline-btn:hover,
.underline-btn.active {
  border-bottom: 2.5px solid #000;
  color: #222;
}
.menu-btn {
  border-bottom: none !important;
}
.menu-btn.active {
  color: #222;
  /* 원하는 active 스타일만 적용, 밑줄 없음 */
}
</style>
