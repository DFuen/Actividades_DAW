<template>
  <Transition name="sound-fade">
    <div v-if="visible" class="sound-effect" :class="`sound-effect--${type}`">
      <span class="sound-icon">{{ icon }}</span>
      <span class="sound-text">{{ text }}</span>
    </div>
  </Transition>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'

interface Props {
  type: 'hit' | 'critical' | 'heal' | 'miss' | 'victory' | 'defeat'
  show: boolean
}

const props = defineProps<Props>()

const visible = ref(false)

const soundEffects = {
  hit: { icon: '💥', text: 'WHACK!' },
  critical: { icon: '⚡', text: 'CRITICAL!' },
  heal: { icon: '💚', text: 'HEAL!' },
  miss: { icon: '💨', text: 'MISS!' },
  victory: { icon: '🏆', text: 'VICTORY!' },
  defeat: { icon: '💀', text: 'DEFEAT!' }
}

const icon = ref(soundEffects[props.type].icon)
const text = ref(soundEffects[props.type].text)

watch(() => props.show, (newVal) => {
  if (newVal) {
    icon.value = soundEffects[props.type].icon
    text.value = soundEffects[props.type].text
    visible.value = true
    
    setTimeout(() => {
      visible.value = false
    }, 800)
  }
})
</script>

<style scoped>
.sound-effect {
  position: fixed;
  top: 15%;
  left: 50%;
  transform: translateX(-50%);
  z-index: 1000;
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem 2rem;
  border-radius: 1rem;
  font-weight: 900;
  font-size: 2rem;
  pointer-events: none;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
}

.sound-effect--hit {
  background: linear-gradient(135deg, #e74c3c, #c0392b);
  color: white;
}

.sound-effect--critical {
  background: linear-gradient(135deg, #f39c12, #e67e22);
  color: white;
  animation: critical-shake 0.2s ease-in-out 3;
}

.sound-effect--heal {
  background: linear-gradient(135deg, #2ecc71, #27ae60);
  color: white;
}

.sound-effect--miss {
  background: linear-gradient(135deg, #95a5a6, #7f8c8d);
  color: white;
}

.sound-effect--victory {
  background: linear-gradient(135deg, #f1c40f, #f39c12);
  color: white;
  font-size: 2.5rem;
}

.sound-effect--defeat {
  background: linear-gradient(135deg, #34495e, #2c3e50);
  color: #e74c3c;
  font-size: 2.5rem;
}

.sound-icon {
  font-size: 3rem;
  animation: icon-pop 0.3s ease-out;
}

.sound-text {
  letter-spacing: 2px;
  animation: text-zoom 0.3s ease-out;
}

@keyframes icon-pop {
  0% {
    transform: scale(0) rotate(0deg);
  }
  50% {
    transform: scale(1.3) rotate(180deg);
  }
  100% {
    transform: scale(1) rotate(360deg);
  }
}

@keyframes text-zoom {
  0% {
    transform: scale(0);
    opacity: 0;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes critical-shake {
  0%, 100% { transform: translateX(-50%) rotate(0deg); }
  25% { transform: translateX(-48%) rotate(-5deg); }
  75% { transform: translateX(-52%) rotate(5deg); }
}

.sound-fade-enter-active {
  animation: sound-enter 0.3s ease-out;
}

.sound-fade-leave-active {
  animation: sound-leave 0.5s ease-in;
}

@keyframes sound-enter {
  0% {
    transform: translateX(-50%) translateY(-50px) scale(0.5);
    opacity: 0;
  }
  100% {
    transform: translateX(-50%) translateY(0) scale(1);
    opacity: 1;
  }
}

@keyframes sound-leave {
  0% {
    transform: translateX(-50%) translateY(0) scale(1);
    opacity: 1;
  }
  100% {
    transform: translateX(-50%) translateY(50px) scale(0.5);
    opacity: 0;
  }
}
</style>
