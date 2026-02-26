<template>
  <div class="battle-hud">
    <div class="hud-section">
      <div class="combatant combatant--player">
        <div class="combatant__header">
          <h3 class="combatant__name">🤖 Tu Robot</h3>
        </div>
        <stat-bar
          label="Salud"
          :current="playerHealth"
          :max="playerMaxHealth"
          color="#2ecc71"
        />
        <div class="combatant__stats">
          <div class="stat">ATK: <strong>{{ playerStats.attack }}</strong></div>
          <div class="stat">SPD: <strong>{{ playerStats.speed }}</strong></div>
        </div>
      </div>

      <div class="vs-indicator">VS</div>

      <div class="combatant combatant--ai">
        <div class="combatant__header">
          <h3 class="combatant__name">🦾 IA Defensor</h3>
        </div>
        <stat-bar label="Salud" :current="aiHealth" :max="aiMaxHealth" color="#e74c3c" />
        <div class="combatant__stats">
          <div class="stat">ATK: <strong>{{ aiStats.attack }}</strong></div>
          <div class="stat">SPD: <strong>{{ aiStats.speed }}</strong></div>
        </div>
      </div>
    </div>

    <div class="battle-log">
      <h4 class="battle-log__title">📋 Registro de Combate</h4>
      <div class="battle-log__container">
        <div
          v-for="log in battleLog"
          :key="log.id"
          :class="['log-entry', `log-entry--${log.actorType}`]"
        >
          <span class="log-entry__text">{{ log.action }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { RobotStats, BattleLog } from '../../types'
import StatBar from '../ui/StatBar.vue'

interface Props {
  playerHealth: number
  playerMaxHealth: number
  aiHealth: number
  aiMaxHealth: number
  playerStats: RobotStats
  aiStats: RobotStats
  battleLog: BattleLog[]
}

defineProps<Props>()
</script>

<style scoped>
.battle-hud {
  background: linear-gradient(135deg, #1a252f 0%, #2c3e50 100%);
  border: 2px solid #3498db;
  border-radius: 0.75rem;
  padding: 1.5rem;
  color: #ecf0f1;
}

.hud-section {
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  gap: 1.5rem;
  align-items: center;
  margin-bottom: 2rem;
}

.combatant {
  background-color: #34495e;
  border: 2px solid #7f8c8d;
  border-radius: 0.5rem;
  padding: 1rem;
}

.combatant--player {
  border-color: #2ecc71;
  background: linear-gradient(135deg, #2d5a3d 0%, #34495e 100%);
}

.combatant--ai {
  border-color: #e74c3c;
  background: linear-gradient(135deg, #5a2d2d 0%, #34495e 100%);
}

.combatant__header {
  margin-bottom: 1rem;
}

.combatant__name {
  margin: 0;
  font-size: 1.1rem;
  font-weight: 700;
}

.combatant__stats {
  display: flex;
  gap: 1rem;
  margin-top: 0.75rem;
  font-size: 0.9rem;
}

.stat {
  display: flex;
  gap: 0.25rem;
  color: #bdc3c7;
}

.vs-indicator {
  font-size: 1.5rem;
  font-weight: 700;
  color: #f39c12;
  text-transform: uppercase;
  text-align: center;
}

.battle-log {
  background-color: #2c3e50;
  border: 1px solid #7f8c8d;
  border-radius: 0.5rem;
  padding: 1rem;
}

.battle-log__title {
  margin: 0 0 1rem 0;
  font-size: 0.95rem;
  font-weight: 700;
  text-transform: uppercase;
  color: #f39c12;
}

.battle-log__container {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  max-height: 200px;
  overflow-y: auto;
}

.log-entry {
  padding: 0.5rem;
  border-radius: 0.25rem;
  font-size: 0.85rem;
  line-height: 1.3;
}

.log-entry--player {
  background-color: rgba(46, 204, 113, 0.2);
  border-left: 3px solid #2ecc71;
  color: #a8e6c5;
}

.log-entry--ai {
  background-color: rgba(231, 76, 60, 0.2);
  border-left: 3px solid #e74c3c;
  color: #f5a8a8;
}

.log-entry__text {
  display: block;
  word-break: break-word;
}
</style>
