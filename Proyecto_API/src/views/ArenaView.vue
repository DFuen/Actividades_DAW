<template>
  <div class="arena-view" :class="{ 'arena-view--shake': gameStore.cameraShake }">
    <!-- Fondo épico con efectos -->
    <div class="epic-bg">
      <div class="epic-glow epic-glow--1"></div>
      <div class="epic-glow epic-glow--2"></div>
      <div class="epic-glow epic-glow--3"></div>
    </div>
    
    <!-- Notificación de Logros -->
    <AchievementNotification :achievement="gameStore.recentAchievement" />
    
    <!-- Combo Counter -->
    <transition name="combo-pop">
      <div v-if="gameStore.combo.count > 1" class="combo-counter">
        <div class="combo-shine"></div>
        <div class="combo-count">{{ gameStore.combo.count }}</div>
        <div class="combo-label">COMBO</div>
        <div class="combo-multiplier">x{{ gameStore.combo.multiplier.toFixed(1) }}</div>
      </div>
    </transition>
    
    <!-- Partículas -->
    <div class="particle-container">
      <transition-group name="particle">
        <div
          v-for="particle in gameStore.particleEffects"
          :key="particle.id"
          :class="['particle', `particle--${particle.type}`]"
          :style="{ left: particle.x + '%', top: particle.y + '%' }"
        >
          {{ particle.type === 'critical' ? '💥' : particle.type === 'hit' ? '⚡' : '💢' }}
        </div>
      </transition-group>
    </div>
    
    <div class="arena-view__container">
      <header class="arena-view__header">
        <h1 class="epic-title">⚔️ LA ARENA ÉPICA ⚔️</h1>
      </header>

      <div class="arena-view__content">
        <div v-if="!gameStore.battle.isFinished" class="battle-active">
          <!-- HUD de Batalla Compacto (Arriba) -->
          <div class="battle-hud-compact">
            <div class="hud-player">
              <div class="hud-name">{{ gameStore.robot.name }}</div>
              <div class="hud-health">
                <div class="health-bar">
                  <div class="health-fill" :style="{ width: gameStore.playerHealthPercent + '%' }"></div>
                </div>
                <span class="health-text">{{ gameStore.battle.playerHealth }}/{{ gameStore.battle.playerMaxHealth }}</span>
              </div>
            </div>
            
            <div class="hud-vs">VS</div>
            
            <div class="hud-ai">
              <div class="hud-name">IA Defensor</div>
              <div class="hud-health">
                <div class="health-bar">
                  <div class="health-fill" :style="{ width: gameStore.aiHealthPercent + '%' }"></div>
                </div>
                <span class="health-text">{{ gameStore.battle.aiHealth }}/{{ gameStore.battle.aiMaxHealth }}</span>
              </div>
            </div>
          </div>

          <!-- Robots -->
          <div class="arena-robots">
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

          <!-- Indicador de Turno -->
          <div class="turn-indicator">
            <span v-if="gameStore.battle.currentTurn === 'player'" class="turn-label turn-label--player">
              🎮 TU TURNO
            </span>
            <span v-else class="turn-label turn-label--ai">
              🤖 TURNO DE LA IA
            </span>
          </div>

          <!-- Botones de Acción -->
          <div class="arena-view__actions">
            <button
              class="action-btn action-btn--attack"
              :disabled="gameStore.battle.currentTurn !== 'player'"
              @click="gameStore.playerAttack"
            >
              <span class="action-btn__icon">⚔️</span>
              <span>ATACAR</span>
            </button>
            <button
              class="action-btn action-btn--heal"
              :disabled="gameStore.battle.currentTurn !== 'player'"
              @click="gameStore.playerHeal"
            >
              <span class="action-btn__icon">💊</span>
              <span>CURARSE</span>
            </button>
          </div>
        </div>

        <div v-else class="battle-finished">
          <div
            :class="[
              'result-card',
              gameStore.battle.winner === 'player' ? 'result-card--victory' : 'result-card--defeat'
            ]"
          >
            <div class="result-icon">
              {{ gameStore.battle.winner === 'player' ? '🏆' : '💀' }}
            </div>
            <h2 class="result-title">
              {{ gameStore.battle.winner === 'player' ? '¡VICTORIA!' : 'DERROTA' }}
            </h2>
            <p class="result-message">
              {{
                gameStore.battle.winner === 'player'
                  ? '¡Has derrotado a la IA y ganado el combate!'
                  : 'La IA te ha vencido. Intenta de nuevo.'
              }}
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
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useGameStore } from '../stores/gameStore'
import BattleHUDComponent from '../components/arena/BattleHUD.vue'
import RobotHead from '../components/robot/RobotHead.vue'
import RobotBody from '../components/robot/RobotBody.vue'
import RobotArms from '../components/robot/RobotArms.vue'
import RobotLegs from '../components/robot/RobotLegs.vue'
import AchievementNotification from '../components/ui/AchievementNotification.vue'

const router = useRouter()
const gameStore = useGameStore()

onMounted(() => {
  gameStore.startBattle()
})

const calculateTotalDamage = computed(() => {
  return gameStore.battle.battleLog
    .filter(log => log.actorType === 'player' && log.damage > 0)
    .reduce((sum, log) => sum + log.damage, 0)
})

function retryBattle() {
  gameStore.startBattle()
}

function goToGarage() {
  router.push('/garage')
}

function goHome() {
  router.push('/')
}
</script>

<style scoped>
.arena-view {
  min-height: 100vh;
  background: linear-gradient(135deg, #0f1419 0%, #1a252f 100%);
  color: #ecf0f1;
  padding: 2rem 1rem;
  position: relative;
  overflow: hidden;
}

/* Fondo épico con brillos */
.epic-bg {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
  z-index: 0;
}

.epic-glow {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.15;
}

.epic-glow--1 {
  width: 600px;
  height: 600px;
  background: radial-gradient(circle, #e74c3c, transparent);
  top: -200px;
  left: -200px;
  animation: glow-move-1 8s ease-in-out infinite;
}

.epic-glow--2 {
  width: 500px;
  height: 500px;
  background: radial-gradient(circle, #3498db, transparent);
  bottom: -150px;
  right: -150px;
  animation: glow-move-2 10s ease-in-out infinite;
}

.epic-glow--3 {
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, #f39c12, transparent);
  top: 50%;
  right: 10%;
  animation: glow-move-3 12s ease-in-out infinite;
}

@keyframes glow-move-1 {
  0%, 100% { transform: translate(0, 0); }
  50% { transform: translate(100px, 100px); }
}

@keyframes glow-move-2 {
  0%, 100% { transform: translate(0, 0); }
  50% { transform: translate(-100px, -100px); }
}

@keyframes glow-move-3 {
  0%, 100% { transform: translate(0, 0); }
  50% { transform: translate(50px, -50px); }
}

.arena-view__container {
  position: relative;
  z-index: 1;
}

.epic-title {
  animation: title-glow 2s ease-in-out infinite;
  text-shadow: 0 0 20px rgba(231, 76, 60, 0.8),
               0 0 40px rgba(231, 76, 60, 0.4),
               0 0 60px rgba(241, 196, 15, 0.3);
  letter-spacing: 3px;
}

@keyframes title-glow {
  0%, 100% { 
    text-shadow: 0 0 20px rgba(231, 76, 60, 0.8),
                 0 0 40px rgba(231, 76, 60, 0.4),
                 0 0 60px rgba(241, 196, 15, 0.3);
  }
  50% { 
    text-shadow: 0 0 30px rgba(231, 76, 60, 1),
                 0 0 60px rgba(231, 76, 60, 0.6),
                 0 0 90px rgba(241, 196, 15, 0.5);
  }
}

.arena-view__container {
  max-width: 1200px;
  margin: 0 auto;
}

.arena-view__header {
  text-align: center;
  margin-bottom: 2rem;
}

.arena-view__header h1 {
  margin: 0;
  font-size: 2rem;
  color: #e74c3c;
  text-transform: uppercase;
}

/* HUD Compacto */
.battle-hud-compact {
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  gap: 2rem;
  align-items: center;
  padding: 1rem;
  background: linear-gradient(135deg, #1a252f 0%, #2c3e50 100%);
  border: 2px solid #3498db;
  border-radius: 0.75rem;
  margin-bottom: 1rem;
}

.hud-player, .hud-ai {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.hud-name {
  font-weight: 700;
  font-size: 0.95rem;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.hud-player .hud-name {
  color: #2ecc71;
}

.hud-ai .hud-name {
  color: #e74c3c;
}

.hud-health {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.health-bar {
  width: 100%;
  height: 20px;
  background: #000;
  border: 2px solid #7f8c8d;
  border-radius: 0.25rem;
  overflow: hidden;
}

.health-fill {
  height: 100%;
  background: linear-gradient(90deg, #2ecc71, #f1c40f);
  transition: width 0.3s ease;
}

.health-text {
  font-size: 0.8rem;
  font-weight: 700;
  color: #f1c40f;
}

.hud-vs {
  font-size: 1.5rem;
  font-weight: 900;
  color: #e74c3c;
  text-align: center;
}

.arena-view__content {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.battle-active {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.arena-view__actions {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
}

.arena-robots {
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  gap: 1.5rem;
  align-items: center;
  padding: 1rem;
  background: linear-gradient(135deg, #1a252f 0%, #2c3e50 100%);
  border: 2px solid #3498db;
  border-radius: 0.75rem;
}

.arena-robot {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}

.arena-robot__visual {
  position: relative;
  width: 300px;
  height: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  padding: 1rem 0;
}

.arena-robot--player .arena-robot__name {
  color: #2ecc71;
}

.arena-robot--ai .arena-robot__name {
  color: #e74c3c;
}

.arena-robot--ai .arena-robot__visual {
  filter: hue-rotate(330deg) saturate(1.2) brightness(0.95);
}

.arena-robot__weapon {
  position: absolute;
  right: -70px;
  top: 120px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
}

.arena-robot__weapon-icon {
  font-size: 2.5rem;
  transform: scaleX(-1);
  filter: drop-shadow(0 0 10px rgba(241, 196, 15, 0.5));
}

.arena-robot__weapon-name {
  font-size: 0.7rem;
  font-weight: 700;
  color: #f1c40f;
  background: rgba(0, 0, 0, 0.7);
  padding: 3px 6px;
  border-radius: 3px;
  white-space: nowrap;
}

.arena-robot__vs {
  font-size: 2rem;
  font-weight: 800;
  color: #f39c12;
  text-transform: uppercase;
}

.action-btn {
  padding: 1rem 1.5rem;
  font-size: 1rem;
  font-weight: 700;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.action-btn:hover:not(:disabled) {
  transform: translateY(-4px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.5);
}

.action-btn:active:not(:disabled) {
  transform: translateY(-2px);
}

.action-btn--attack {
  background: linear-gradient(135deg, #e74c3c, #c0392b);
  color: white;
  box-shadow: 0 0 20px rgba(231, 76, 60, 0.5);
}

.action-btn--attack:hover:not(:disabled) {
  background: linear-gradient(135deg, #e74c3c, #a93226);
  box-shadow: 0 0 40px rgba(231, 76, 60, 0.8),
              0 0 20px rgba(231, 76, 60, 0.6) inset;
}

.action-btn--heal {
  background: linear-gradient(135deg, #2ecc71, #27ae60);
  color: white;
  box-shadow: 0 0 20px rgba(46, 204, 113, 0.5);
}

.action-btn--heal:hover:not(:disabled) {
  background: linear-gradient(135deg, #2ecc71, #1e8449);
  box-shadow: 0 0 40px rgba(46, 204, 113, 0.8),
              0 0 20px rgba(46, 204, 113, 0.6) inset;
}

.action-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.action-btn__icon {
  font-size: 1.25rem;
}

.turn-indicator {
  text-align: center;
  padding: 1rem;
  background-color: #34495e;
  border-radius: 0.5rem;
  border: 2px solid #3498db;
}

.turn-label {
  font-size: 1.2rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.turn-label--player {
  color: #2ecc71;
}

.turn-label--ai {
  color: #e74c3c;
}

.battle-finished {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.result-card {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  border: 3px solid #3498db;
  border-radius: 1rem;
  padding: 2rem;
  text-align: center;
}

.result-card--victory {
  border-color: #2ecc71;
  background: linear-gradient(135deg, #1e4620 0%, #2c3e50 100%);
}

.result-card--defeat {
  border-color: #e74c3c;
  background: linear-gradient(135deg, #461e1e 0%, #2c3e50 100%);
}

.result-icon {
  font-size: 4rem;
  margin-bottom: 1rem;
}

.result-title {
  margin: 0 0 0.5rem 0;
  font-size: 2rem;
  font-weight: 900;
  text-transform: uppercase;
}

.result-card--victory .result-title {
  color: #2ecc71;
}

.result-card--defeat .result-title {
  color: #e74c3c;
}

.result-message {
  margin: 0 0 1.5rem 0;
  font-size: 1.1rem;
  color: #bdc3c7;
}

.result-stats {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  padding-top: 1.5rem;
  border-top: 2px solid #7f8c8d;
}

.stat-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.95rem;
}

.stat-row span {
  color: #bdc3c7;
}

.stat-row strong {
  color: #f1c40f;
  font-size: 1.1rem;
}

.result-actions {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 1rem;
}

.btn {
  padding: 0.75rem 1.5rem;
  font-size: 0.95rem;
  font-weight: 600;
  border: none;
  border-radius: 0.375rem;
  cursor: pointer;
  transition: all 0.2s ease;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
}

.btn--primary {
  background-color: #3498db;
  color: white;
}

.btn--primary:hover:not(:disabled) {
  background-color: #2980b9;
}

.btn--secondary {
  background-color: #95a5a6;
  color: white;
}

.btn--secondary:hover:not(:disabled) {
  background-color: #7f8c8d;
}

/* Efectos Épicos */
.arena-view--shake {
  animation: camera-shake 0.3s ease-in-out;
}

@keyframes camera-shake {
  0%, 100% { transform: translate(0, 0) rotate(0deg); }
  10% { transform: translate(-5px, -5px) rotate(-1deg); }
  20% { transform: translate(5px, 5px) rotate(1deg); }
  30% { transform: translate(-5px, 5px) rotate(-1deg); }
  40% { transform: translate(5px, -5px) rotate(1deg); }
  50% { transform: translate(-5px, -5px) rotate(-1deg); }
  60% { transform: translate(5px, 5px) rotate(1deg); }
  70% { transform: translate(-5px, 5px) rotate(-1deg); }
  80% { transform: translate(5px, -5px) rotate(1deg); }
  90% { transform: translate(-5px, -5px) rotate(-1deg); }
}

.combo-counter {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
  text-align: center;
  pointer-events: none;
  animation: combo-pulse 0.5s ease-in-out infinite;
}

.combo-shine {
  position: absolute;
  top: -50px;
  left: 50%;
  transform: translateX(-50%);
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, rgba(255, 215, 0, 0.6), transparent 70%);
  border-radius: 50%;
  animation: combo-shine-pulse 1s ease-in-out infinite;
}

@keyframes combo-shine-pulse {
  0%, 100% { 
    transform: translateX(-50%) scale(1);
    opacity: 0.3;
  }
  50% { 
    transform: translateX(-50%) scale(1.2);
    opacity: 0.6;
  }
}

.combo-count {
  font-size: 6rem;
  font-weight: 900;
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  filter: drop-shadow(0 0 30px rgba(240, 147, 251, 1))
          drop-shadow(0 0 60px rgba(245, 87, 108, 0.8));
}

.combo-label {
  font-size: 2.5rem;
  font-weight: 900;
  background: linear-gradient(135deg, #00d4ff, #0099ff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 0 20px rgba(0, 212, 255, 0.8);
  letter-spacing: 5px;
  filter: drop-shadow(0 0 15px rgba(0, 212, 255, 0.6));
}

.combo-multiplier {
  font-size: 3.5rem;
  font-weight: 900;
  color: #ffd700;
  text-shadow: 0 0 30px rgba(255, 215, 0, 1),
               0 0 60px rgba(255, 215, 0, 0.6);
  filter: drop-shadow(0 0 20px rgba(255, 215, 0, 0.8));
}

.combo-pop-enter-active {
  animation: combo-pop-in 0.4s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.combo-pop-leave-active {
  animation: combo-pop-out 0.3s ease-in;
}

@keyframes combo-pop-in {
  from {
    transform: translate(-50%, -50%) scale(0);
    opacity: 0;
  }
  to {
    transform: translate(-50%, -50%) scale(1);
    opacity: 1;
  }
}

@keyframes combo-pop-out {
  from {
    transform: translate(-50%, -50%) scale(1);
    opacity: 1;
  }
  to {
    transform: translate(-50%, -50%) scale(1.5);
    opacity: 0;
  }
}

@keyframes combo-pulse {
  0%, 100% {
    transform: translate(-50%, -50%) scale(1);
  }
  50% {
    transform: translate(-50%, -50%) scale(1.1);
  }
}

.particle-container {
  position: fixed;
  inset: 0;
  pointer-events: none;
  z-index: 999;
}

.particle {
  position: absolute;
  font-size: 3rem;
  animation: particle-explode 1s ease-out forwards;
}

.particle--critical {
  font-size: 4rem;
}

@keyframes particle-explode {
  0% {
    transform: scale(0) rotate(0deg);
    opacity: 1;
  }
  100% {
    transform: scale(2) rotate(720deg) translateY(-200px);
    opacity: 0;
  }
}
</style>

