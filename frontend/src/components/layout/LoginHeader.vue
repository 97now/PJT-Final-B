<template>

  <header class="header-bar">
    <div class="header-title">SSAFIT</div>
    <div class="header-actions">
      <span class="welcome-message">
        환영합니다. <strong>{{ nickname }}</strong>님
      </span>
    </div>
  </header>
  <hr class="header-divider" />
</template>

<script setup>
const props = defineProps({
  nickname: {
    type: String,
    required: true
  }
})
</script>

<style scoped>
.header-bar {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  padding: 26px 24px 0 24px;
  width: 100%;
  box-sizing: border-box;
}
.header-title {
  font-size: 28px;
  font-weight: 500;
  letter-spacing: 2px;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}
.header-actions {
  display: flex;
  align-items: center;
  font-size: 16px;
  position: absolute;
  right: 24px;
  top: 26px;
}
.welcome-message {
  color: #222;
  font-size: 16px;
}
.welcome-message strong {
  color: #2a6be6;
  font-weight: 700;
}
.header-divider {
  margin-top: 32px;
  border: none;
  border-bottom: 2px solid #bbb;
  width: 100%;
}
</style>

