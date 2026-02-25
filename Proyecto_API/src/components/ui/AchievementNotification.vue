<template>
  <transition name="achievement-slide">
    <div v-if="achievement" class="achievement-notification">
      <div class="achievement-glow"></div>
      <div class="achievement-icon">{{ achievement.icon }}</div>
      <div class="achievement-content">
        <div class="achievement-title">🏆 ¡LOGRO DESBLOQUEADO!</div>
        <div class="achievement-name">{{ achievement.name }}</div>
        <div class="achievement-description">{{ achievement.description }}</div>
      </div>
      <div class="achievement-sparkles">
        <span class="sparkle" v-for="i in 10" :key="i" :style="getSparkleStyle(i)">✨</span>
      </div>
    </div>
  </transition>
</template>

<script setup lang="ts">
import type { Achievement } from '../../types'

interface Props {
  achievement: Achievement | null
}

defineProps<Props>()

function getSparkleStyle(index: number) {
  const angle = (index / 10) * 360
  const distance = 80 + Math.random() * 40
  const x = Math.cos(angle * Math.PI / 180) * distance
  const y = Math.sin(angle * Math.PI / 180) * distance
  const delay = index * 0.1
  
  return {
    left: `calc(50% + ${x}px)`,
    top: `calc(50% + ${y}px)`,
    animationDelay: `${delay}s`
  }
}
</script>

<style scoped>
.achievement-notification {
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 9999;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: 3px solid #ffd700;
  border-radius: 16px;
  padding: 1.5rem;
  min-width: 350px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.8), 0 0 40px rgba(255, 215, 0, 0.6);
  animation: achievement-pulse 2s infinite;
  overflow: hidden;
}

.achievement-glow {
  position: absolute;
  inset: -50%;
  background: conic-gradient(from 0deg, #ffd700, #ffed4e, #ffd700);
  animation: achievement-rotate 3s linear infinite;
  opacity: 0.3;
  filter: blur(20px);
}

.achievement-icon {
  font-size: 4rem;
  text-align: center;
  margin-bottom: 0.5rem;
  animation: achievement-bounce 0.6s ease-in-out infinite;
  filter: drop-shadow(0 0 20px rgba(255, 215, 0, 0.8));
}

.achievement-content {
  position: relative;
  z-index: 2;
  text-align: center;
  color: #fff;
}

.achievement-title {
  font-size: 0.9rem;
  font-weight: 700;
  color: #ffd700;
  text-transform: uppercase;
  letter-spacing: 2px;
  margin-bottom: 0.5rem;
  text-shadow: 0 0 10px rgba(255, 215, 0, 0.8);
}

.achievement-name {
  font-size: 1.4rem;
  font-weight: 900;
  margin-bottom: 0.25rem;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);
}

.achievement-description {
  font-size: 0.9rem;
  opacity: 0.9;
  font-style: italic;
}

.achievement-sparkles {
  position: absolute;
  inset: 0;
  pointer-events: none;
}

.sparkle {
  position: absolute;
  font-size: 1.2rem;
  animation: sparkle-float 2s ease-in-out infinite;
  opacity: 0;
}

.achievement-slide-enter-active {
  animation: achievement-slide-in 0.6s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.achievement-slide-leave-active {
  animation: achievement-slide-out 0.4s ease-in;
}

@keyframes achievement-slide-in {
  from {
    transform: translateX(120%) scale(0.8) rotate(10deg);
    opacity: 0;
  }
  to {
    transform: translateX(0) scale(1) rotate(0deg);
    opacity: 1;
  }
}

@keyframes achievement-slide-out {
  from {
    transform: translateY(0) scale(1);
    opacity: 1;
  }
  to {
    transform: translateY(-100%) scale(0.8);
    opacity: 0;
  }
}

@keyframes achievement-pulse {
  0%, 100% {
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.8), 0 0 40px rgba(255, 215, 0, 0.6);
  }
  50% {
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.8), 0 0 60px rgba(255, 215, 0, 0.9);
  }
}

@keyframes achievement-bounce {
  0%, 100% {
    transform: translateY(0) scale(1);
  }
  50% {
    transform: translateY(-10px) scale(1.1);
  }
}

@keyframes achievement-rotate {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

@keyframes sparkle-float {
  0% {
    opacity: 0;
    transform: scale(0);
  }
  50% {
    opacity: 1;
    transform: scale(1);
  }
  100% {
    opacity: 0;
    transform: scale(0) translateY(-30px);
  }
}
</style>
