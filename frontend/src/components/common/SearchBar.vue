<template>
  <div class="search-bar">
    <img :src="searchIcon" alt="검색" class="search-icon" />
    <input
      type="text"
      :placeholder="placeholder"
      @input="onInput"
      :value="searchValue"
    />
  </div>
</template>

<script setup>
import searchIcon from "@/assets/img/Search.svg";
import { watch } from "vue";

const searchValue = defineModel();

const props = defineProps({
  placeholder: String,
});

const emit = defineEmits(["search"]);

let debounceTimer = null;

function onInput(e) {
  searchValue.value = e.target.value;
}

watch(searchValue, (newValue) => {
  if (debounceTimer) clearTimeout(debounceTimer);
  debounceTimer = setTimeout(() => {
    emit("search", newValue);
  }, 300);
});
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
  .search-bar input {
    width: 100%;
    min-width: 0;
  }
}
</style>
