<template>
  <div class="stat-bar" :style="{ '--percentage': percentage }">
    <div class="stat-bar__label">{{ label }}</div>
    <div class="stat-bar__container">
      <div class="stat-bar__fill"></div>
      <div class="stat-bar__text">{{ current }} / {{ max }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  label: string
  current: number
  max: number
  color?: string
}

const props = withDefaults(defineProps<Props>(), {
  color: '#3498db'
})

const percentage = computed(() => (props.current / props.max) * 100)
</script>

<style scoped>
.stat-bar {
  --percentage: 100%;
  --color: v-bind('props.color');
}

.stat-bar__label {
  font-size: 0.875rem;
  font-weight: 600;
  text-transform: uppercase;
  margin-bottom: 0.5rem;
  color: #ecf0f1;
}

.stat-bar__container {
  position: relative;
  height: 2rem;
  background-color: #34495e;
  border: 2px solid #2c3e50;
  border-radius: 0.25rem;
  overflow: hidden;
}

.stat-bar__fill {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: var(--percentage);
  background: linear-gradient(90deg, var(--color), rgb(from var(--color) r g b / 0.7));
  transition: width 0.3s ease;
}

.stat-bar__text {
  position: relative;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.875rem;
  font-weight: bold;
  color: #ecf0f1;
  z-index: 1;
}
</style>
