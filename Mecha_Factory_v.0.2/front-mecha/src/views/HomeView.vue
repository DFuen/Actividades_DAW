<template>
  <div class="home-view">
    <!-- Fondo animado con estrellas -->
    <div class="stars-bg">
      <div class="star" v-for="i in 50" :key="i" :style="getStarStyle(i)"></div>
    </div>
    
    <div class="home-view__container">
      <div class="home-view__hero">
        <div class="title-glow"></div>
        <h1 class="home-view__title">
          <span class="title-word">Mecha</span>
          <span class="title-word">Factory</span>
          <span class="title-word">Arena</span>
        </h1>
        <p class="home-view__subtitle">Construye tu robot y domina el combate</p>
        
        <!-- Estadísticas del Jugador -->
        <div v-if="gameStore.playerStats.totalBattles > 0" class="player-stats-mini">
          <div class="stat-item">
            <span class="stat-icon">⚔️</span>
            <span class="stat-value">{{ gameStore.playerStats.wins }}W - {{ gameStore.playerStats.losses }}L</span>
          </div>
          <div class="stat-item">
            <span class="stat-icon">📊</span>
            <span class="stat-value">{{ gameStore.winRate }}% WR</span>
          </div>
          <div class="stat-item">
            <span class="stat-icon">🏆</span>
            <span class="stat-value">{{ unlockedCount }}/{{ gameStore.achievements.length }}</span>
          </div>
        </div>
      </div>

      <div class="home-view__content">
        <div class="feature-card">
          <div class="feature-card__icon">🏗️</div>
          <h3>El Taller (The Garage)</h3>
          <p>Personaliza tu robot eligiendo cabeza, cuerpo, extremidades y arma. Cada pieza afecta tus estadísticas.</p>
        </div>

        <div class="feature-card">
          <div class="feature-card__icon">⚔️</div>
          <h3>La Arena (The Arena)</h3>
          <p>Enfrenta a la IA en combates por turnos. Usa estrategia para vencer a tu enemigo.</p>
        </div>

        <div class="feature-card">
          <div class="feature-card__icon">💰</div>
          <h3>Gestión de Presupuesto</h3>
          <p>Comienza con 1000 créditos. Gasta sabiamente para crear el mejor robot posible.</p>
        </div>
      </div>

      <div class="home-view__actions">
        <router-link to="/garage" class="btn btn--primary">
          🔧 Comienza a Construir
        </router-link>
        <router-link v-if="gameStore.playerStats.totalBattles > 0" to="/stats" class="btn btn--stats">
          📊 Ver Estadísticas
        </router-link>
      </div>

      <div class="home-view__footer">
        <p>Curso: Desarrollo Web con Vue.js | Proyecto Académico</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { RouterLink } from 'vue-router'
import { useGameStore } from '../stores/gameStore'

const gameStore = useGameStore()

const unlockedCount = computed(() => 
  gameStore.achievements.filter(a => a.unlocked).length
)

const getStarStyle = (index: number) => {
  const size = Math.random() * 3 + 1
  const x = Math.random() * 100
  const y = Math.random() * 100
  const duration = Math.random() * 3 + 2
  const delay = Math.random() * 2
  
  return {
    left: `${x}%`,
    top: `${y}%`,
    width: `${size}px`,
    height: `${size}px`,
    animationDuration: `${duration}s`,
    animationDelay: `${delay}s`
  }
}
</script>

<style scoped>
.home-view {
  min-height: 100vh;
  background: linear-gradient(135deg, #0f1419 0%, #1a252f 100%);
  color: #ecf0f1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem 1rem;
  position: relative;
  overflow: hidden;
}

/* Fondo de estrellas animadas */
.stars-bg {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
  z-index: 0;
}

.star {
  position: absolute;
  background: white;
  border-radius: 50%;
  animation: twinkle 3s infinite;
  box-shadow: 0 0 10px rgba(255, 255, 255, 0.8);
}

@keyframes twinkle {
  0%, 100% { opacity: 0.3; }
  50% { opacity: 1; }
}

.home-view__container {
  max-width: 1000px;
  width: 100%;
  position: relative;
  z-index: 1;
}

.home-view__hero {
  text-align: center;
  margin-bottom: 3rem;
  position: relative;
}

/* Efecto de brillo detrás del título */
.title-glow {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 600px;
  height: 600px;
  background: radial-gradient(circle, rgba(52, 152, 219, 0.3), transparent 70%);
  animation: pulse-glow 3s ease-in-out infinite;
  z-index: -1;
}

@keyframes pulse-glow {
  0%, 100% { transform: translate(-50%, -50%) scale(0.9); opacity: 0.4; }
  50% { transform: translate(-50%, -50%) scale(1.1); opacity: 0.7; }
}

.home-view__title {
  margin: 0;
  font-size: 4rem;
  font-weight: 900;
  letter-spacing: 4px;
  text-transform: uppercase;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.title-word {
  display: inline-block;
  background: linear-gradient(135deg, #3498db, #f39c12, #e74c3c);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  animation: text-shimmer 3s infinite;
  background-size: 200% 200%;
}

.title-word:nth-child(1) { animation-delay: 0s; }
.title-word:nth-child(2) { animation-delay: 0.3s; }
.title-word:nth-child(3) { animation-delay: 0.6s; }

@keyframes text-shimmer {
  0%, 100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

.home-view__subtitle {
  margin: 1.5rem 0 0 0;
  font-size: 1.5rem;
  color: #bdc3c7;
  font-style: italic;
  animation: fadeInUp 1s ease-out;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Estadísticas mini del jugador */
.player-stats-mini {
  display: flex;
  justify-content: center;
  gap: 2rem;
  margin-top: 2rem;
  padding: 1rem 2rem;
  background: rgba(0, 0, 0, 0.3);
  border-radius: 1rem;
  border: 1px solid rgba(52, 152, 219, 0.3);
  backdrop-filter: blur(10px);
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.stat-icon {
  font-size: 1.5rem;
}

.stat-value {
  font-size: 1.2rem;
  font-weight: 700;
  color: #f39c12;
}

.home-view__content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.feature-card {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  border: 2px solid #3498db;
  border-radius: 0.75rem;
  padding: 1.5rem;
  text-align: center;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.feature-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.1), transparent);
  transition: left 0.5s ease;
}

.feature-card:hover::before {
  left: 100%;
}

.feature-card:hover {
  transform: translateY(-8px) scale(1.02);
  border-color: #f39c12;
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.5);
}

.feature-card__icon {
  font-size: 3rem;
  margin-bottom: 0.5rem;
  animation: bounce 2s infinite;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.feature-card h3 {
  margin: 1rem 0 0.5rem 0;
  font-size: 1.2rem;
  font-weight: 700;
  color: #f39c12;
}

.feature-card p {
  margin: 0;
  font-size: 0.95rem;
  color: #bdc3c7;
  line-height: 1.6;
}

.home-view__actions {
  display: flex;
  justify-content: center;
  gap: 1rem;
  margin-bottom: 2rem;
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
  position: relative;
  overflow: hidden;
}

.btn::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 0;
  height: 0;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  transform: translate(-50%, -50%);
  transition: width 0.6s, height 0.6s;
}

.btn:hover::before {
  width: 300px;
  height: 300px;
}

.btn--primary {
  background: linear-gradient(135deg, #3498db, #2980b9);
  color: white;
  box-shadow: 0 4px 15px rgba(52, 152, 219, 0.3);
}

.btn--primary:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 20px rgba(52, 152, 219, 0.5);
}

.btn--stats {
  background: linear-gradient(135deg, #9b59b6, #8e44ad);
  color: white;
  box-shadow: 0 4px 15px rgba(155, 89, 182, 0.3);
}

.btn--stats:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 20px rgba(155, 89, 182, 0.5);
}

.home-view__footer {
  text-align: center;
  color: #7f8c8d;
  font-size: 0.9rem;
}

@media (max-width: 768px) {
  .home-view__title {
    font-size: 2.5rem;
  }
  
  .player-stats-mini {
    flex-direction: column;
    gap: 1rem;
  }
}
</style>
