<template>
  <div class="legs" :style="legsStyle" @click="$emit('select')">
    <div class="leg left"></div>
    <div class="leg right"></div>
    <div class="footL"></div>
    <div class="footR"></div>
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

const legsStyle = computed(() => ({
  width: '160px',
  height: '120px',
  gap: '20px',
  cursor: 'pointer',
  transition: 'all 0.3s ease',
  border: '3px solid transparent',
  boxShadow: 'none',
  borderColor: 'transparent'
} as any))
</script>

<style scoped>
.legs {
  display: flex;
  justify-content: center;
  margin: 0 auto 0;
  position: relative;
}

.legs:hover {
  border-color: #f1c40f;
}

.leg {
  width: 30px;
  height: 100%;
  background-color: #6b7280;
  border-bottom-right-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.leg:hover {
  box-shadow: 0 0 15px rgba(241, 196, 15, 0.6);
}

.footL {
  width: 70px;
  height: 40px;
  background-color: rgb(56, 55, 55);
  border-top-right-radius: 20px;
  border-top-left-radius: 15px;
  position: absolute;
  left: calc(50% - 50px);
  bottom: 0;
  cursor: pointer;
  transition: all 0.3s ease;
}

.footR {
  width: 70px;
  height: 40px;
  background-color: rgb(56, 55, 55);
  border-top-right-radius: 20px;
  border-top-left-radius: 15px;
  position: absolute;
  right: calc(40% - 50px);
  bottom: 0;
  cursor: pointer;
  transition: all 0.3s ease;
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
