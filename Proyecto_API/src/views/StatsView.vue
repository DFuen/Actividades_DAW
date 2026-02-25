<template>
  <div class="stats-view">
    <!-- Fondo con efecto de matriz -->
    <div class="matrix-bg">
      <div class="matrix-line" v-for="i in 15" :key="i" :style="getMatrixStyle(i)">
        {{ getMatrixText() }}
      </div>
    </div>
    
    <div class="stats-view__container">
      <header class="stats-view__header">
        <h1 class="stats-title">
          <span class="title-icon">📊</span>
          Estadísticas del Jugador
          <span class="title-icon">📊</span>
        </h1>
        <p class="stats-subtitle">Tu rendimiento en el campo de batalla</p>
      </header>

      <!-- Resumen General -->
      <div class="stats-summary">
        <div class="summary-card summary-card--battles">
          <div class="card-icon">⚔️</div>
          <div class="card-content">
            <div class="card-value">{{ gameStore.playerStats.totalBattles }}</div>
            <div class="card-label">Batallas Totales</div>
          </div>
        </div>

        <div class="summary-card summary-card--wins">
          <div class="card-icon">🏆</div>
          <div class="card-content">
            <div class="card-value">{{ gameStore.playerStats.wins }}</div>
            <div class="card-label">Victorias</div>
          </div>
        </div>

        <div class="summary-card summary-card--losses">
          <div class="card-icon">💀</div>
          <div class="card-content">
            <div class="card-value">{{ gameStore.playerStats.losses }}</div>
            <div class="card-label">Derrotas</div>
          </div>
        </div>

        <div class="summary-card summary-card--winrate">
          <div class="card-icon">📈</div>
          <div class="card-content">
            <div class="card-value">{{ gameStore.winRate }}%</div>
            <div class="card-label">Tasa de Victoria</div>
          </div>
        </div>
      </div>

      <!-- Estadísticas Detalladas -->
      <div class="stats-grid">
        <!-- Combate -->
        <div class="stat-section">
          <h2 class="section-title">
            <span class="section-icon">⚔️</span>
            Estadísticas de Combate
          </h2>
          <div class="stat-list">
            <div class="stat-item">
              <span class="stat-icon">💥</span>
              <span class="stat-name">Daño Total Infligido</span>
              <span class="stat-value">{{ gameStore.playerStats.totalDamageDealt }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-icon">🛡️</span>
              <span class="stat-name">Daño Total Recibido</span>
              <span class="stat-value">{{ gameStore.playerStats.totalDamageTaken }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-icon">👊</span>
              <span class="stat-name">Daño Más Alto</span>
              <span class="stat-value stat-value--highlight">{{ gameStore.playerStats.highestDamage }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-icon">💎</span>
              <span class="stat-name">Golpes Críticos</span>
              <span class="stat-value stat-value--critical">{{ gameStore.playerStats.criticalHits }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-icon">⚡</span>
              <span class="stat-name">Combo Más Largo</span>
              <span class="stat-value stat-value--combo">{{ gameStore.playerStats.longestCombo }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-icon">✨</span>
              <span class="stat-name">Victorias Perfectas</span>
              <span class="stat-value stat-value--perfect">{{ gameStore.playerStats.perfectVictories }}</span>
            </div>
          </div>
        </div>

        <!-- Construcción -->
        <div class="stat-section">
          <h2 class="section-title">
            <span class="section-icon">🔧</span>
            Estadísticas de Construcción
          </h2>
          <div class="stat-list">
            <div class="stat-item">
              <span class="stat-icon">🤖</span>
              <span class="stat-name">Robots Construidos</span>
              <span class="stat-value">{{ gameStore.playerStats.robotsBuilt }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-icon">💰</span>
              <span class="stat-name">Créditos Gastados</span>
              <span class="stat-value">{{ gameStore.playerStats.creditsSpent }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-icon">📊</span>
              <span class="stat-name">Promedio por Robot</span>
              <span class="stat-value">{{ averageSpentPerRobot }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Logros -->
      <div class="achievements-section">
        <h2 class="section-title">
          <span class="section-icon">🏆</span>
          Logros Desbloqueados ({{ unlockedCount }}/{{ gameStore.achievements.length }})
        </h2>
        <div class="achievements-grid">
          <div
            v-for="achievement in gameStore.achievements"
            :key="achievement.id"
            :class="['achievement-card', achievement.unlocked ? 'achievement-card--unlocked' : 'achievement-card--locked']"
          >
            <div class="achievement-icon">{{ achievement.icon }}</div>
            <div class="achievement-content">
              <div class="achievement-name">{{ achievement.name }}</div>
              <div class="achievement-description">{{ achievement.description }}</div>
              <div v-if="achievement.unlocked && achievement.unlockedAt" class="achievement-date">
                Desbloqueado: {{ formatDate(achievement.unlockedAt) }}
              </div>
            </div>
            <div v-if="achievement.unlocked" class="achievement-badge">✓</div>
          </div>
        </div>
      </div>

      <!-- Botón de Regreso -->
      <div class="stats-view__actions">
        <router-link to="/" class="btn btn--primary">
          ⬅️ Volver al Inicio
        </router-link>
        <button @click="resetStats" class="btn btn--danger">
          🗑️ Resetear Estadísticas
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useGameStore } from '../stores/gameStore'
import type { Achievement } from '../types'

const gameStore = useGameStore()

const unlockedCount = computed(() => 
  gameStore.achievements.filter((a: Achievement) => a.unlocked).length
)

const averageSpentPerRobot = computed(() => {
  if (gameStore.playerStats.robotsBuilt === 0) return 0
  return Math.round(gameStore.playerStats.creditsSpent / gameStore.playerStats.robotsBuilt)
})

function formatDate(timestamp: number): string {
  const date = new Date(timestamp)
  return date.toLocaleDateString('es-ES', { 
    day: '2-digit', 
    month: '2-digit', 
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

function resetStats() {
  if (confirm('⚠️ ¿Estás seguro? Esto borrará todas tus estadísticas y logros de forma permanente.')) {
    // Reset stats
    gameStore.playerStats.totalBattles = 0
    gameStore.playerStats.wins = 0
    gameStore.playerStats.losses = 0
    gameStore.playerStats.totalDamageDealt = 0
    gameStore.playerStats.totalDamageTaken = 0
    gameStore.playerStats.criticalHits = 0
    gameStore.playerStats.perfectVictories = 0
    gameStore.playerStats.longestCombo = 0
    gameStore.playerStats.highestDamage = 0
    gameStore.playerStats.robotsBuilt = 0
    gameStore.playerStats.creditsSpent = 0
    
    // Reset achievements
    gameStore.achievements.forEach((achievement: Achievement) => {
      achievement.unlocked = false
      achievement.unlockedAt = undefined
    })
    
    // Save to localStorage
    gameStore.savePlayerProgress()
    
    alert('✅ Estadísticas reseteadas correctamente')
  }
}

function getMatrixStyle(index: number) {
  const x = (index * 7) % 100
  const duration = Math.random() * 10 + 10
  const delay = Math.random() * 5
  
  return {
    left: `${x}%`,
    animationDuration: `${duration}s`,
    animationDelay: `${delay}s`
  }
}

function getMatrixText(): string {
  const chars = '01アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲン'
  let text = ''
  for (let i = 0; i < 20; i++) {
    text += chars[Math.floor(Math.random() * chars.length)]
  }
  return text
}
</script>

<style scoped>
.stats-view {
  min-height: 100vh;
  background: linear-gradient(135deg, #0f1419 0%, #1a252f 100%);
  color: #ecf0f1;
  padding: 2rem 1rem;
  position: relative;
  overflow: hidden;
}

/* Efecto Matrix de fondo */
.matrix-bg {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
  z-index: 0;
  opacity: 0.1;
}

.matrix-line {
  position: absolute;
  color: #2ecc71;
  font-family: 'Courier New', monospace;
  font-size: 12px;
  white-space: nowrap;
  animation: matrix-fall linear infinite;
}

@keyframes matrix-fall {
  0% {
    transform: translateY(-100%);
  }
  100% {
    transform: translateY(100vh);
  }
}

.stats-view__container {
  max-width: 1200px;
  margin: 0 auto;
  position: relative;
  z-index: 1;
}

.stats-view__header {
  text-align: center;
  margin-bottom: 3rem;
}

.stats-title {
  margin: 0;
  font-size: 3rem;
  font-weight: 900;
  color: #3498db;
  text-transform: uppercase;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
}

.title-icon {
  font-size: 2.5rem;
  animation: pulse-icon 2s ease-in-out infinite;
}

@keyframes pulse-icon {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.2); }
}

.stats-subtitle {
  margin: 0.5rem 0 0 0;
  font-size: 1.3rem;
  color: #bdc3c7;
  font-style: italic;
}

/* Resumen */
.stats-summary {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
  margin-bottom: 3rem;
}

.summary-card {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  border: 2px solid #3498db;
  border-radius: 1rem;
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.summary-card::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.1), transparent 60%);
  animation: rotate-gradient 8s linear infinite;
}

@keyframes rotate-gradient {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.summary-card:hover {
  transform: translateY(-5px) scale(1.02);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
}

.summary-card--wins { border-color: #2ecc71; }
.summary-card--losses { border-color: #e74c3c; }
.summary-card--winrate { border-color: #f39c12; }

.card-icon {
  font-size: 3rem;
  position: relative;
  z-index: 1;
}

.card-content {
  position: relative;
  z-index: 1;
}

.card-value {
  font-size: 2rem;
  font-weight: 900;
  color: #f39c12;
  line-height: 1;
}

.card-label {
  font-size: 0.85rem;
  color: #bdc3c7;
  text-transform: uppercase;
  margin-top: 0.25rem;
}

/* Grid de Estadísticas */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 2rem;
  margin-bottom: 3rem;
}

.stat-section {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  border: 2px solid #3498db;
  border-radius: 1rem;
  padding: 1.5rem;
}

.section-title {
  margin: 0 0 1.5rem 0;
  font-size: 1.5rem;
  color: #3498db;
  text-transform: uppercase;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  border-bottom: 2px solid #3498db;
  padding-bottom: 0.75rem;
}

.section-icon {
  font-size: 1.8rem;
}

.stat-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 0.75rem;
  background: rgba(0, 0, 0, 0.3);
  border-radius: 0.5rem;
  border-left: 3px solid #3498db;
  transition: all 0.3s ease;
}

.stat-item:hover {
  background: rgba(0, 0, 0, 0.5);
  border-left-width: 5px;
  transform: translateX(5px);
}

.stat-icon {
  font-size: 1.5rem;
}

.stat-name {
  flex: 1;
  font-weight: 600;
  color: #bdc3c7;
}

.stat-value {
  font-size: 1.2rem;
  font-weight: 700;
  color: #f39c12;
}

.stat-value--highlight {
  color: #e74c3c;
  font-size: 1.4rem;
}

.stat-value--critical {
  color: #9b59b6;
}

.stat-value--combo {
  color: #3498db;
}

.stat-value--perfect {
  color: #2ecc71;
}

/* Logros */
.achievements-section {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  border: 2px solid #3498db;
  border-radius: 1rem;
  padding: 1.5rem;
  margin-bottom: 2rem;
}

.achievements-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1rem;
}

.achievement-card {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
  background: rgba(0, 0, 0, 0.3);
  border-radius: 0.75rem;
  border: 2px solid transparent;
  transition: all 0.3s ease;
  position: relative;
}

.achievement-card--unlocked {
  border-color: #2ecc71;
  background: rgba(46, 204, 113, 0.1);
}

.achievement-card--locked {
  opacity: 0.5;
  filter: grayscale(1);
}

.achievement-card--unlocked:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 20px rgba(46, 204, 113, 0.3);
}

.achievement-icon {
  font-size: 2.5rem;
}

.achievement-content {
  flex: 1;
}

.achievement-name {
  font-size: 1.1rem;
  font-weight: 700;
  color: #f39c12;
  margin-bottom: 0.25rem;
}

.achievement-description {
  font-size: 0.85rem;
  color: #bdc3c7;
}

.achievement-date {
  font-size: 0.75rem;
  color: #7f8c8d;
  margin-top: 0.25rem;
  font-style: italic;
}

.achievement-badge {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
  width: 30px;
  height: 30px;
  background: #2ecc71;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 900;
  font-size: 1.2rem;
}

/* Acciones */
.stats-view__actions {
  display: flex;
  justify-content: center;
  gap: 1rem;
  flex-wrap: wrap;
}

.btn {
  padding: 1rem 2rem;
  font-size: 1rem;
  font-weight: 700;
  text-decoration: none;
  border-radius: 0.5rem;
  transition: all 0.3s ease;
  text-transform: uppercase;
  display: inline-block;
  cursor: pointer;
  border: none;
}

.btn--primary {
  background: linear-gradient(135deg, #3498db, #2980b9);
  color: white;
}

.btn--primary:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 20px rgba(52, 152, 219, 0.5);
}

.btn--danger {
  background: linear-gradient(135deg, #e74c3c, #c0392b);
  color: white;
}

.btn--danger:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 20px rgba(231, 76, 60, 0.5);
}

@media (max-width: 768px) {
  .stats-title {
    font-size: 2rem;
    flex-direction: column;
    gap: 0.5rem;
  }
  
  .stats-grid {
    grid-template-columns: 1fr;
  }
  
  .achievements-grid {
    grid-template-columns: 1fr;
  }
}
</style>
