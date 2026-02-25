<template>
  <div class="arms" :style="armsStyle" @click="$emit('select')">
    <div class="arm left"></div>
    <div class="arm right"></div>
    <div class="hand left"></div>
    <div class="hand right"></div>
    <div v-if="part && props.showLabel" class="part-label">{{ part.name }}</div>
    <div v-if="part" class="equipped-indicator">
      <span class="equipped-icon">✔️</span>
      <span class="equipped-text">Equipada</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import type { Part } from '../../types'

interface Props {
  part?: Part | null
  showLabel?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  showLabel: true
})
defineEmits(['select'])

const armsStyle = computed(() => ({
  backgroundColor: '#4b5563',
  '--arm-length': '110px',
  '--arm-thickness': '16px',
  cursor: 'pointer',
  transition: 'all 0.3s ease',
  boxShadow: 'none',
  border: '3px solid transparent'
} as any))
</script>

<style scoped>
.arms {
  position: absolute;
  top: 150px;
  left: 50%;
  transform: translateX(-50%);
  width: 0;
  height: var(--arm-thickness);
  pointer-events: none;
}

.arms:hover .arm {
  box-shadow: 0 0 15px rgba(241, 196, 15, 0.6);
}

.arm {
  position: absolute;
  width: var(--arm-length);
  height: var(--arm-thickness);
  background-color: #4b5563;
  border-radius: 10px;
  pointer-events: auto;
  transition: all 0.3s ease;
}

.arm.left {
  right: 60px;
}

.arm.right {
  left: 60px;
}

.hand {
  position: absolute;
  width: 40px;
  height: 40px;
  background-color: rgb(56, 55, 55);
  border-radius: 10%;
  top: 50%;
  transform: translateY(-50%);
  pointer-events: auto;
}

.hand.left {
  right: calc(60px + var(--arm-length) - 12px);
}

.hand.right {
  left: calc(60px + var(--arm-length) - 12px);
}

.part-label {
  position: absolute;
  top: -25px;
  left: 50%;
  transform: translateX(-50%);
  font-size: 0.75rem;
  font-weight: bold;
  color: #ecf0f1;
  white-space: nowrap;
  background: rgba(0, 0, 0, 0.7);
  padding: 2px 6px;
  border-radius: 3px;
}

.equipped-indicator {
  position: absolute;
  bottom: 10px;
  right: 10px;
  background: rgba(44, 62, 80, 0.85);
  color: #f1c40f;
  border-radius: 6px;
  padding: 2px 8px;
  font-size: 0.8rem;
  display: flex;
  align-items: center;
  gap: 4px;
  z-index: 10;
}

.equipped-icon {
  font-size: 1.1em;
}

.equipped-text {
  font-weight: bold;
}
</style>
