<template>
  <div
    class="part-card"
    :class="{ 
      'part-card--selected': isSelected,
      'part-card--legendary': part.rarity === 'legendary',
      'part-card--epic': part.rarity === 'epic',
      'part-card--disabled': !canAfford && !isSelected
    }"
    :style="{ '--rarity-color': part.color }"
    @click="$emit('select')"
  >
    <div v-if="part.rarity === 'legendary'" class="part-card__glow"></div>
    <div class="part-card__icon">
      <span v-if="part.category === 'head'">🧠</span>
      <span v-else-if="part.category === 'body'">🎯</span>
      <span v-else-if="part.category === 'limbs'">💪</span>
      <span v-else-if="part.category === 'weapon'">🔫</span>
    </div>
    <div class="part-card__header">
      <h3 class="part-card__name">{{ part.name }}</h3>
      <span class="part-card__rarity">{{ part.rarity.toUpperCase() }}</span>
    </div>

    <p class="part-card__description">{{ part.description }}</p>

    <div class="part-card__stats">
      <div class="stat">
        <span class="stat__label">❤️ Salud</span>
        <span class="stat__value">+{{ part.stats.health }}</span>
      </div>
      <div class="stat">
        <span class="stat__label">⚔️ Ataque</span>
        <span class="stat__value">+{{ part.stats.attack }}</span>
      </div>
      <div class="stat">
        <span class="stat__label">⚡ Velocidad</span>
        <span class="stat__value">+{{ part.stats.speed }}</span>
      </div>
    </div>

    <div class="part-card__footer">
      <span class="part-card__cost">💰 {{ part.cost }} Cr</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Part } from '../../types'

interface Props {
  part: Part
  isSelected?: boolean
  canAfford?: boolean
}

withDefaults(defineProps<Props>(), {
  isSelected: false,
  canAfford: true
})

defineEmits<{
  select: []
}>()
</script>

<style scoped>
.part-card__icon {
  font-size: 2.5rem;
  text-align: center;
  margin-bottom: 0.5rem;
}
.part-card {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  border: 2px solid #7f8c8d;
  border-radius: 0.5rem;
  padding: 1rem;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.part-card:hover {
  transform: translateY(-4px);
  border-color: var(--rarity-color);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.5), 0 0 20px rgba(var(--rarity-color), 0.3);
}

.part-card--selected {
  border-color: var(--rarity-color);
  background: linear-gradient(135deg, #3a5a7a 0%, #4a6a8a 100%);
  box-shadow: inset 0 0 20px rgba(var(--rarity-color), 0.2), 0 0 20px rgba(var(--rarity-color), 0.5);
}

.part-card--legendary {
  animation: legendary-pulse 2s infinite;
  border: 3px solid var(--rarity-color);
}

.part-card--legendary:hover {
  animation: legendary-pulse-intense 0.6s infinite;
  box-shadow: 0 0 30px rgba(var(--rarity-color), 0.8), 0 0 60px rgba(var(--rarity-color), 0.6);
}

.part-card--epic {
  animation: epic-glow 3s infinite;
}

.part-card--epic:hover {
  animation: epic-glow 1s infinite;
}

.part-card--disabled {
  opacity: 0.5;
  cursor: not-allowed;
  filter: grayscale(0.5);
}

.part-card--disabled:hover {
  transform: none;
  box-shadow: none;
}

.part-card__glow {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  border-radius: 0.5rem;
  background: radial-gradient(circle at center, rgba(var(--rarity-color), 0.2) 0%, transparent 70%);
  pointer-events: none;
}

@keyframes legendary-pulse {
  0%, 100% {
    box-shadow: 0 0 10px rgba(var(--rarity-color), 0.4), inset 0 0 10px rgba(var(--rarity-color), 0.1);
  }
  50% {
    box-shadow: 0 0 30px rgba(var(--rarity-color), 0.8), inset 0 0 20px rgba(var(--rarity-color), 0.3);
  }
}

@keyframes legendary-pulse-intense {
  0%, 100% {
    box-shadow: 0 0 20px rgba(var(--rarity-color), 0.6), inset 0 0 15px rgba(var(--rarity-color), 0.2);
  }
  50% {
    box-shadow: 0 0 50px rgba(var(--rarity-color), 1), inset 0 0 30px rgba(var(--rarity-color), 0.5);
  }
}

@keyframes epic-glow {
  0%, 100% {
    box-shadow: 0 0 15px rgba(var(--rarity-color), 0.5);
  }
  50% {
    box-shadow: 0 0 25px rgba(var(--rarity-color), 0.7);
  }
}

.part-card__header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 0.5rem;
}

.part-card__name {
  margin: 0;
  font-size: 1rem;
  font-weight: 700;
  color: #ecf0f1;
}

.part-card__rarity {
  font-size: 0.7rem;
  font-weight: 700;
  padding: 0.25rem 0.5rem;
  background-color: var(--rarity-color);
  color: white;
  border-radius: 0.25rem;
  text-transform: uppercase;
}

.part-card__description {
  margin: 0;
  font-size: 0.85rem;
  color: #bdc3c7;
  line-height: 1.3;
}

.part-card__stats {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 0.5rem;
  padding: 0.75rem 0;
  border-top: 1px solid #7f8c8d;
  border-bottom: 1px solid #7f8c8d;
}

.stat {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.25rem;
}

.stat__label {
  font-size: 0.7rem;
  color: #95a5a6;
  font-weight: 600;
}

.stat__value {
  font-size: 0.9rem;
  font-weight: 700;
  color: #f1c40f;
}

.part-card__footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.part-card__cost {
  font-size: 0.9rem;
  font-weight: 700;
  color: #f1c40f;
}
</style>
