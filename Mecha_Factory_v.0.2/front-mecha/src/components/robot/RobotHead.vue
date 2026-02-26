<template>
  <div class="head" :style="headStyle" @click="$emit('select')">
    <div class="eye left"></div>
    <div class="eye right"></div>
    <div class="mouth"></div>
    <div class="earL"></div>
    <div class="earR"></div>
    <div v-if="part && props.showLabel" class="part-label">{{ part.name }}</div>
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

const headStyle = computed(() => ({
  backgroundColor: '#d1d5db',
  width: '120px',
  height: '120px',
  borderRadius: '16px',
  boxShadow: 'none',
  border: '3px solid transparent'
}))
</script>

<style scoped>
.head {
  position: relative;
  margin: 0 auto;
  z-index: 5;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 3px solid transparent;
}

.head:hover {
  border-color: #f1c40f;
  box-shadow: 0 0 20px rgba(241, 196, 15, 0.5);
  transform: scale(1.05);
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

.earL {
  width: 20px;
  height: 50px;
  background: rgb(56, 55, 55);
  position: absolute;
  top: 35px;
  left: -18px;
  border-radius: 3px;
  z-index: 2;
}

.earR {
  width: 20px;
  height: 50px;
  background: rgb(56, 55, 55);
  position: absolute;
  top: 35px;
  left: 118px;
  border-radius: 3px;
  z-index: 1;
}

.eye {
  width: 40px;
  height: 40px;
  background: white;
  border: 6px solid rgb(56, 55, 55);
  border-radius: 50%;
  position: absolute;
  top: 25px;
}

.eye.left {
  left: 10px;
}

.eye.right {
  right: 10px;
}

.mouth {
  width: 50px;
  height: 20px;
  border-radius: 5px;
  background: white;
  border: 6px solid rgb(56, 55, 55);
  position: absolute;
  bottom: 15px;
  left: 50%;
  transform: translateX(-50%);
}
</style>
