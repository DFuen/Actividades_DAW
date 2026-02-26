<template>
  <div class="victory-container">
    <div class="victory-banner">
      <div class="victory-text">🎖️ ¡VICTORIA! 🎖️</div>
    </div>

    <div class="rewards-section">
      <h2>Recompensas Obtenidas</h2>
      
      <div class="rewards-grid">
        <div class="reward-card credits-reward">
          <div class="reward-icon">💰</div>
          <div class="reward-amount">+{{ lastVictoryRewards.credits }}</div>
          <div class="reward-label">Créditos</div>
        </div>

        <div class="reward-card scrap-reward">
          <div class="reward-icon">⚙️</div>
          <div class="reward-amount">+{{ lastVictoryRewards.scrap }}</div>
          <div class="reward-label">Chatarra</div>
        </div>

        <div class="reward-card exp-reward">
          <div class="reward-icon">⭐</div>
          <div class="reward-amount">+{{ lastVictoryRewards.experience }}</div>
          <div class="reward-label">Experiencia</div>
        </div>
      </div>
    </div>

    <div class="stats-section">
      <h3>Estadísticas Actuales</h3>
      <div class="stats-display">
        <div class="stat-item">
          <span class="stat-icon">❤️</span>
          <span class="stat-name">Salud</span>
          <span class="stat-value">{{ gameStore.robot.totalStats.health }}</span>
        </div>
        <div class="stat-item">
          <span class="stat-icon">⚔️</span>
          <span class="stat-name">Ataque</span>
          <span class="stat-value">{{ gameStore.robot.totalStats.attack }}</span>
        </div>
        <div class="stat-item">
          <span class="stat-icon">🛡️</span>
          <span class="stat-name">Defensa</span>
          <span class="stat-value">{{ gameStore.world.defense }}</span>
        </div>
        <div class="stat-item">
          <span class="stat-icon">⚡</span>
          <span class="stat-name">Velocidad</span>
          <span class="stat-value">{{ gameStore.robot.totalStats.speed }}</span>
        </div>
      </div>
    </div>

    <div class="action-buttons">
      <router-link to="/world" class="btn btn-continue">
        ⚔️ Continuar Explorando
      </router-link>
      <router-link to="/shop" class="btn btn-shop">
        🛠️ Ir a la Tienda
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useGameStore } from '../stores/gameStore'
import { computed } from 'vue'

const gameStore = useGameStore()

// Guardar las recompensas antes de abandonar el componente
const lastVictoryRewards = computed(() => {
  // Estas son las recompensas calculadas en el último combate
  // Las obtenemos del estado del mundo
  return {
    credits: gameStore.world.resources.credits,
    scrap: gameStore.world.resources.scrap,
    experience: gameStore.world.experience
  }
})
</script>

<style scoped>
.victory-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  color: #e0e0e0;
  padding: 40px 20px;
  font-family: 'Courier New', monospace;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.victory-banner {
  margin-bottom: 40px;
  animation: pulse-glow 1s ease-in-out;
}

.victory-text {
  font-size: 3.5rem;
  font-weight: bold;
  color: #f1c40f;
  text-shadow: 0 0 30px #f1c40f, 0 0 60px rgba(241, 196, 15, 0.5);
  letter-spacing: 3px;
  animation: bounce 0.6s ease-in-out;
}

@keyframes bounce {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
}

@keyframes pulse-glow {
  0% {
    transform: scale(0.8);
    opacity: 0;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

.rewards-section {
  margin-bottom: 40px;
  width: 100%;
  max-width: 800px;
}

.rewards-section h2 {
  text-align: center;
  font-size: 1.8rem;
  color: #f39c12;
  margin-bottom: 20px;
  text-shadow: 0 0 10px #f39c12;
}

.rewards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.reward-card {
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  border: 3px solid;
  background: rgba(0, 0, 0, 0.3);
  transition: all 0.3s ease;
  animation: slide-in 0.6s ease-out;
}

.reward-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
}

@keyframes slide-in {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.credits-reward {
  border-color: #f1c40f;
  box-shadow: 0 0 20px rgba(241, 196, 15, 0.3);
}

.scrap-reward {
  border-color: #3498db;
  box-shadow: 0 0 20px rgba(52, 152, 219, 0.3);
}

.exp-reward {
  border-color: #2ecc71;
  box-shadow: 0 0 20px rgba(46, 204, 113, 0.3);
}

.reward-icon {
  font-size: 2.5rem;
  margin-bottom: 10px;
}

.reward-amount {
  font-size: 2rem;
  font-weight: bold;
  margin: 10px 0;
}

.credits-reward .reward-amount {
  color: #f1c40f;
  text-shadow: 0 0 10px #f1c40f;
}

.scrap-reward .reward-amount {
  color: #3498db;
  text-shadow: 0 0 10px #3498db;
}

.exp-reward .reward-amount {
  color: #2ecc71;
  text-shadow: 0 0 10px #2ecc71;
}

.reward-label {
  color: #bdc3c7;
  font-size: 0.9rem;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.stats-section {
  background: rgba(0, 0, 0, 0.4);
  padding: 30px;
  border-radius: 10px;
  border: 2px solid #34495e;
  margin-bottom: 40px;
  width: 100%;
  max-width: 800px;
}

.stats-section h3 {
  text-align: center;
  font-size: 1.4rem;
  color: #ecf0f1;
  margin-bottom: 20px;
}

.stats-display {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 15px;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding: 15px;
  background: rgba(52, 152, 219, 0.1);
  border-radius: 8px;
  border: 1px solid #3498db;
}

.stat-icon {
  font-size: 1.5rem;
}

.stat-name {
  color: #bdc3c7;
  font-size: 0.85rem;
  text-transform: uppercase;
}

.stat-value {
  color: #2ecc71;
  font-size: 1.5rem;
  font-weight: bold;
  text-shadow: 0 0 5px #2ecc71;
}

.action-buttons {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
  justify-content: center;
}

.btn {
  padding: 15px 40px;
  font-size: 1.1rem;
  font-weight: bold;
  border-radius: 8px;
  text-decoration: none;
  transition: all 0.3s ease;
  cursor: pointer;
  text-transform: uppercase;
  letter-spacing: 2px;
  border: 3px solid;
  background: transparent;
  min-width: 200px;
  text-align: center;
}

.btn-continue {
  border-color: #2ecc71;
  color: #2ecc71;
  box-shadow: 0 0 15px rgba(46, 204, 113, 0.3);
}

.btn-continue:hover {
  background: rgba(46, 204, 113, 0.1);
  box-shadow: 0 0 30px rgba(46, 204, 113, 0.6);
  transform: scale(1.05);
}

.btn-shop {
  border-color: #f39c12;
  color: #f39c12;
  box-shadow: 0 0 15px rgba(243, 156, 18, 0.3);
}

.btn-shop:hover {
  background: rgba(243, 156, 18, 0.1);
  box-shadow: 0 0 30px rgba(243, 156, 18, 0.6);
  transform: scale(1.05);
}

@media (max-width: 768px) {
  .victory-text {
    font-size: 2.2rem;
  }

  .rewards-grid {
    grid-template-columns: 1fr;
  }

  .action-buttons {
    flex-direction: column;
  }

  .btn {
    width: 100%;
  }
}
</style>
