<script setup lang="ts">
import { computed, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useCardStore } from '../stores/cardStore';
import { useGameStore } from '../stores/gameStore';

import RobotHead from '../components/robot/RobotHead.vue';
import RobotBody from '../components/robot/RobotBody.vue';
import RobotArms from '../components/robot/RobotArms.vue';
import RobotLegs from '../components/robot/RobotLegs.vue';

const cardStore = useCardStore();
const gameStore = useGameStore();
const router = useRouter();

onMounted(() => {
  cardStore.loadDeck();
  gameStore.loadCards();
  gameStore.startBattle();
});

const calculateTotalDamage = computed(() => {
  return gameStore.battle.battleLog
    .filter((log: any) => log.actorType === 'player' && log.damage > 0)
    .reduce((sum: number, log: any) => sum + log.damage, 0);
});

function retryBattle() {
  gameStore.startBattle();
}

function goToGarage() {
  router.push('/garage');
}

function goHome() {
  router.push('/');
}

watch(
  () => gameStore.battle.battleLog.length,
  (len) => {
    if (len > 0) {
      const log = gameStore.battle.battleLog[len - 1];
      if (!log) return;
      const isPlayer = log.actorType === 'player';
      const isAI = log.actorType === 'ai';
      if (isPlayer) {
        if (log.action && log.action.includes('cura')) {
          console.log(`[ARENA] El jugador se cura: +${log.damage} HP | Vida tras acción: ${gameStore.battle.playerHealth}`);
        } else {
          console.log(`[ARENA] El jugador ataca: -${log.damage} HP a IA | Vida IA tras acción: ${gameStore.battle.aiHealth}`);
        }
      } else if (isAI) {
        if (log.action && log.action.includes('cura')) {
          console.log(`[ARENA] La IA se cura: +${log.damage} HP | Vida IA tras acción: ${gameStore.battle.aiHealth}`);
        } else {
          console.log(`[ARENA] La IA ataca: -${log.damage} HP al jugador | Vida jugador tras acción: ${gameStore.battle.playerHealth}`);
        }
      }
    }
  }
);
</script>

<template>
  <div class="arena-view" :class="{ 'arena-view--shake': gameStore.cameraShake }">
    <div class="epic-bg">
      <div class="epic-glow epic-glow--1"></div>
      <div class="epic-glow epic-glow--2"></div>
      <div class="epic-glow epic-glow--3"></div>
    </div>
    <span class="health-text">{{ gameStore.battle.aiHealth }}/{{ gameStore.battle.aiMaxHealth }}</span>

    <div class="arena-battle-flex-grid">
      <div class="arena-battle-col arena-battle-col--left">
        <div class="arena-robot-action-buttons-grid">
          <button class="action-btn action-btn--attack" :disabled="gameStore.battle.currentTurn !== 'player'" @click="gameStore.playerAttack">
            <span class="action-btn__icon">⚔️</span>
            <span>ATACAR</span>
          </button>
          <button class="action-btn action-btn--heal" :disabled="gameStore.battle.currentTurn !== 'player'" @click="gameStore.playerHeal">
            <span class="action-btn__icon">💊</span>
            <span>CURARSE</span>
          </button>
        </div>
      </div>
      <div class="arena-battle-col arena-battle-col--center">
        <div class="arena-robots arena-robots--hud-style">
          <div class="arena-robot arena-robot--player">
            <div class="arena-robot__visual">
              <RobotHead :part="gameStore.robot.head" :showLabel="false" />
              <RobotArms :part="gameStore.robot.limbs" :showLabel="false" />
              <RobotBody :part="gameStore.robot.body" :showLabel="false" />
              <RobotLegs :part="gameStore.robot.limbs" :showLabel="false" />
              <div v-if="gameStore.robot.weapon" class="arena-robot__weapon">
                <span class="arena-robot__weapon-icon">🔫</span>
              </div>
            </div>
          </div>
          <div class="arena-robot__vs">VS</div>
          <div class="arena-robot arena-robot--ai">
            <div class="arena-robot__visual">
              <RobotHead :part="gameStore.enemyParts.head" :showLabel="false" />
              <RobotArms :part="gameStore.enemyParts.limbs" :showLabel="false" />
              <RobotBody :part="gameStore.enemyParts.body" :showLabel="false" />
              <RobotLegs :part="gameStore.enemyParts.limbs" :showLabel="false" />
              <div v-if="gameStore.enemyParts.weapon" class="arena-robot__weapon">
                <span class="arena-robot__weapon-icon">🔫</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="arena-battle-col arena-battle-col--right">
        <div class="battle-log" style="margin-bottom:0;max-height:350px;overflow-y:auto;background:rgba(0,0,0,0.25);border-radius:8px;padding:0.5rem 1rem;">
          <div v-for="log in gameStore.battle.battleLog" :key="log.id" style="margin-bottom:0.25rem;">
            <span :style="{color: log.actorType === 'player' ? '#2ecc71' : '#e74c3c', fontWeight:'bold'}">
              {{ log.actorType === 'player' ? gameStore.robot.name : 'IA' }}
            </span>
            <span> — </span>
            <span>{{ log.action }}</span>
            <span v-if="log.damage > 0"> ({{ log.damage > 0 ? (log.actorType === 'player' ? '-' : '-') + log.damage : '' }} HP)</span>
          </div>
        </div>
      </div>
    </div>
    <div class="turn-indicator">
      <span v-if="gameStore.battle.currentTurn === 'player'" class="turn-label turn-label--player">
        🎮 TU TURNO
      </span>
      <span v-else class="turn-label turn-label--ai">
        🤖 TURNO DE LA IA
      </span>
    </div>
    <div v-if="gameStore.battle.error" class="battle-error" style="padding:2rem;text-align:center;color:#e74c3c;font-size:1.3rem;">
      <strong>{{ gameStore.battle.error }}</strong>
    </div>
    <div v-else-if="gameStore.battle.isFinished && ((gameStore.battle.winner === 'player' && gameStore.battle.aiHealth === 0) || (gameStore.battle.winner === 'ai' && gameStore.battle.playerHealth === 0))" class="battle-finished">
      <div :class="['result-card', gameStore.battle.winner === 'player' ? 'result-card--victory' : 'result-card--defeat']">
        <div class="result-icon">
          {{ gameStore.battle.winner === 'player' ? '🏆' : '💀' }}
        </div>
        <h2 class="result-title">
          {{ gameStore.battle.winner === 'player' ? '¡VICTORIA!' : 'DERROTA' }}
        </h2>
        <p class="result-message">
          {{ gameStore.battle.winner === 'player' ? '¡Has derrotado a la IA y ganado el combate!' : 'La IA te ha vencido. Intenta de nuevo.' }}
        </p>
        <div class="result-stats">
          <div class="stat-row">
            <span>Turnos jugados:</span>
            <strong>{{ gameStore.battle.battleLog.length }}</strong>
          </div>
          <div class="stat-row">
            <span>Daño total infligido:</span>
            <strong>{{ calculateTotalDamage }}</strong>
          </div>
        </div>
      </div>
      <div class="result-actions">
        <button class="btn btn--primary" @click="retryBattle">
          🔄 Reintentar
        </button>
        <button class="btn btn--secondary" @click="goToGarage">
          🏗️ Volver al Taller
        </button>
        <button class="btn btn--secondary" @click="goHome">
          🏠 Ir a Inicio
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.arena {
  padding: 2rem;
}
.cards, .played {
  margin-bottom: 2rem;
}
.card {
  display: inline-block;
  margin: 0.5rem;
  padding: 0.5rem;
  border-radius: 8px;
  background: #f9f9f9;
}
.arena-battle-col--left, .arena-battle-col--center, .arena-battle-col--right {
  background: linear-gradient(135deg, #1a252f 0%, #2c3e50 100%);
  border: 3px solid #3498db;
  border-radius: 0.75rem;
  margin: 0.5rem 0.5rem;
  padding: 1rem 0.5rem;
  min-height: 420px;
  box-sizing: border-box;
}
.arena-battle-col--center {
  border-color: #f39c12;
}
.arena-battle-col--left {
  border-color: #2ecc71;
}
.arena-battle-col--right {
  border-color: #e74c3c;
}
</style>
