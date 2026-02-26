<template>
  <div class="robot-preview">
    <div class="robot-preview__title">
      <h2>{{ robotName }}</h2>
      <div class="robot-preview__stats-summary">
        <div class="stat-item">
          <span class="stat-item__label">❤️ HP:</span>
          <span class="stat-item__value">{{ totalStats.health }}</span>
        </div>
        <div class="stat-item">
          <span class="stat-item__label">⚔️ ATK:</span>
          <span class="stat-item__value">{{ totalStats.attack }}</span>
        </div>
        <div class="stat-item">
          <span class="stat-item__label">⚡ SPD:</span>
          <span class="stat-item__value">{{ totalStats.speed }}</span>
        </div>
      </div>
      <!-- ...existing code... -->
    </div>

    <div class="robot-visualization">
      <div class="robot-body">
        <RobotHead :part="head ?? null" :showLabel="false" @select="$emit('selectPart', 'head')" />
        <RobotArms :part="weapon ?? { id: 'default-weapon', name: 'Sin arma', category: 'weapon', rarity: 'common', cost: 0, stats: { health: 0, attack: 0, speed: 0 }, color: '#4b5563', description: 'Sin arma equipada' }" :showLabel="false" @select="$emit('selectPart', 'weapon')" />
        <RobotBody :part="body ?? { id: 'default-body', name: 'Sin cuerpo', category: 'body', rarity: 'common', cost: 0, stats: { health: 0, attack: 0, speed: 0 }, color: '#9ca3af', description: 'Sin cuerpo equipado' }" :showLabel="false" @select="$emit('selectPart', 'body')" />
        <RobotLegs :part="limbs ?? { id: 'default-limbs', name: 'Sin piernas', category: 'limbs', rarity: 'common', cost: 0, stats: { health: 0, attack: 0, speed: 0 }, color: '#6b7280', description: 'Sin piernas equipadas' }" :showLabel="false" @select="$emit('selectPart', 'limbs')" />
        <div v-if="weapon" class="weapon" @click="$emit('selectPart', 'weapon')" :style="{ borderColor: weapon.color, boxShadow: '0 0 10px ' + weapon.color }">
          <div class="weapon-icon">🔫</div>
          <div class="weapon-name">{{ weapon.name }}</div>
        </div>
      </div>
    </div>

    <div class="robot-preview__indicators">
      <span
        v-for="part in ['head', 'body', 'limbs', 'weapon']"
        :key="part"
        :class="[
          'indicator',
          { 'indicator--filled': true }
        ]"
        :title="part"
      ></span>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Part, RobotStats } from '../../types'
import RobotHead from '../robot/RobotHead.vue'
import RobotBody from '../robot/RobotBody.vue'
import RobotArms from '../robot/RobotArms.vue'
import RobotLegs from '../robot/RobotLegs.vue'

interface Props {
  head: Part | null
  body: Part | null
  limbs: Part | null
  weapon: Part | null
  robotName: string
  totalStats: RobotStats
  budget: number
  spentBudget: number
}

defineProps<Props>()

defineEmits<{
  selectPart: [part: 'head' | 'body' | 'limbs' | 'weapon']
}>()
</script>

<style scoped>
.robot-preview {
  background: linear-gradient(135deg, #1a252f 0%, #2c3e50 100%);
  border: 3px solid #3498db;
  border-radius: 0.75rem;
  padding: 1.5rem;
  color: #ecf0f1;
}

.robot-preview__title {
  margin-bottom: 1.5rem;
  text-align: center;
}

.robot-preview__title h2 {
  margin: 0 0 0.5rem 0;
  font-size: 1.75rem;
  text-transform: uppercase;
  color: #f39c12;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
}

.robot-preview__stats-summary {
  display: flex;
  justify-content: center;
  gap: 1.5rem;
  flex-wrap: wrap;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.95rem;
}

.stat-item__label {
  font-weight: 600;
}

.stat-item__value {
  font-weight: 700;
  color: #f1c40f;
  font-size: 1.1rem;
}

.robot-visualization {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 2rem 0;
  padding: 2rem;
  background: radial-gradient(circle, rgba(52, 73, 94, 0.5) 0%, rgba(44, 62, 80, 0.8) 100%);
  border-radius: 1rem;
  border: 2px solid #3498db;
  min-height: 400px;
}

.robot-body {
  position: relative;
  width: 300px;
  height: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
}

.weapon {
  position: absolute;
  right: -80px;
  top: 120px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.weapon:hover {
  transform: scale(1.1);
}

.weapon-icon {
  font-size: 3rem;
  filter: drop-shadow(0 0 10px rgba(241, 196, 15, 0.5));
  transform: scaleX(-1);
}

.weapon-name {
  font-size: 0.75rem;
  font-weight: bold;
  color: #f1c40f;
  background: rgba(0, 0, 0, 0.7);
  padding: 4px 8px;
  border-radius: 3px;
  white-space: nowrap;
}

.robot-preview__container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.robot-part {
  background-color: #34495e;
  border: 2px solid #7f8c8d;
  border-radius: 0.5rem;
  padding: 1rem;
  min-height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  transition: all 0.3s ease;
}

.robot-part:hover {
  border-color: #3498db;
  background-color: #3d5060;
}

.robot-part__content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
}

.robot-part__icon {
  font-size: 2rem;
}

.robot-part__name {
  font-size: 0.85rem;
  font-weight: 600;
  color: #f1c40f;
  line-height: 1.2;
}

.robot-part__empty {
  color: #7f8c8d;
  font-size: 0.95rem;
  font-weight: 600;
  text-transform: uppercase;
}

.robot-preview__indicators {
  display: flex;
  justify-content: center;
  gap: 0.75rem;
}

.indicator {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  transition: all 0.3s ease;
}

.indicator--filled {
  background-color: #2ecc71;
  box-shadow: 0 0 8px #2ecc71;
}

.indicator--empty {
  background-color: #e74c3c;
  box-shadow: 0 0 8px #e74c3c;
}
</style>
