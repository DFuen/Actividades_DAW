<template>
  <Transition name="countdown-fade">
    <div v-if="visible" class="countdown-overlay">
      <div class="countdown-number" :class="`countdown-number--${count}`">
        {{ displayText }}
      </div>
    </div>
  </Transition>
</template>

<script setup lang="ts">
import { ref, watch, computed } from 'vue'

interface Props {
  start: boolean
}

const props = defineProps<Props>()
const emit = defineEmits<{
  complete: []
}>()

const visible = ref(false)
const count = ref(3)

const displayText = computed(() => {
  if (count.value === 0) return '¡PELEA!'
  return count.value
})

watch(() => props.start, (newVal) => {
  if (newVal) {
    startCountdown()
  }
})

function startCountdown() {
  visible.value = true
  count.value = 3
  
  const interval = setInterval(() => {
    count.value--
    
    if (count.value < 0) {
      clearInterval(interval)
      setTimeout(() => {
        visible.value = false
        emit('complete')
      }, 500)
    }
  }, 1000)
}
</script>

<style scoped>
.countdown-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
  backdrop-filter: blur(5px);
}

.countdown-number {
  font-size: 15rem;
  font-weight: 900;
  color: white;
  text-shadow: 0 0 30px rgba(255, 255, 255, 0.8),
               0 0 60px currentColor,
               0 10px 20px rgba(0, 0, 0, 0.8);
  animation: countdown-pulse 1s ease-out;
}

.countdown-number--3 {
  color: #e74c3c;
}

.countdown-number--2 {
  color: #f39c12;
}

.countdown-number--1 {
  color: #f1c40f;
}

.countdown-number--0 {
  color: #2ecc71;
  font-size: 10rem;
  letter-spacing: 10px;
}

@keyframes countdown-pulse {
  0% {
    transform: scale(0) rotate(-180deg);
    opacity: 0;
  }
  30% {
    transform: scale(1.3) rotate(10deg);
    opacity: 1;
  }
  100% {
    transform: scale(1) rotate(0deg);
    opacity: 1;
  }
}

.countdown-fade-enter-active,
.countdown-fade-leave-active {
  transition: opacity 0.3s ease;
}

.countdown-fade-enter-from,
.countdown-fade-leave-to {
  opacity: 0;
}

@media (max-width: 768px) {
  .countdown-number {
    font-size: 8rem;
  }
  
  .countdown-number--0 {
    font-size: 5rem;
  }
}
</style>
