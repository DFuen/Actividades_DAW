<template>
  <div class="spinner">
    <div class="spinner__inner"></div>
    <p v-if="text" class="spinner__text">{{ text }}</p>
  </div>
</template>

<script setup lang="ts">
interface Props {
  text?: string
}

defineProps<Props>()
</script>

<style scoped>
.spinner {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 1rem;
}

.spinner__inner {
  width: 3rem;
  height: 3rem;
  border: 4px solid rgba(52, 152, 219, 0.3);
  border-top-color: #3498db;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

.spinner__text {
  font-size: 0.95rem;
  color: #95a5a6;
  text-transform: uppercase;
  letter-spacing: 1px;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
