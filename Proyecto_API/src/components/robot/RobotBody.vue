<template>
  <div class="body" :style="bodyStyle" @click="$emit('select')">
    <div class="panel"></div>
    <div v-if="part && props.showLabel" class="part-label">{{ part.name }}</div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import type { CSSProperties } from 'vue'
import type { Part } from '../../types'

interface Props {
  part?: Part | null
  showLabel?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  showLabel: true
})
defineEmits(['select'])

const bodyStyle = computed((): CSSProperties => ({
  backgroundColor: '#9ca3af',
  width: '150px',
  height: '200px',
  borderRadius: '12px',
  margin: '10px auto 0',
  cursor: 'pointer',
  transition: 'all 0.3s ease',
  border: '3px solid transparent',
  position: 'relative',
  boxShadow: 'none'
}))
</script>

<style scoped>
.body {
  border-radius: 12px;
  z-index: 20;
}

.body:hover {
  border-color: #f1c40f;
  box-shadow: 0 0 20px rgba(241, 196, 15, 0.5);
}

.panel {
  width: 60px;
  height: 40px;
  background-color: #374151;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 6px;
}

.part-label {
  position: absolute;
  bottom: -25px;
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
</style>
