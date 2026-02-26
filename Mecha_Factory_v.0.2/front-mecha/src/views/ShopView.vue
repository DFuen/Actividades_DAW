<template>
  <div class="shop-container">
    <div class="shop-header">
      <h1>⚙️ CENTRO TECNOLÓGICO</h1>
      <p class="shop-subtitle">Mejora tu robot para dominar el campo de batalla</p>
    </div>

    <div class="shop-stats">
      <div class="stat-display">
        <span class="stat-label">Créditos:</span>
        <span class="stat-value credits">{{ gameStore.world.resources.credits }}</span>
      </div>
      <div class="stat-display">
        <span class="stat-label">Nivel:</span>
        <span class="stat-value level">{{ gameStore.world.level }}</span>
      </div>
      <div class="stat-display">
        <span class="stat-label">Experiencia:</span>
        <span class="stat-value exp">{{ gameStore.world.experience }} / {{ gameStore.world.level * 100 }}</span>
      </div>
    </div>

    <div class="upgrades-grid">
      <div v-for="upgrade in Object.values(gameStore.shopUpgrades)" :key="upgrade.id" class="upgrade-card">
        <div class="upgrade-header">
          <h3>{{ getUpgradeEmoji(upgrade.id) }} {{ upgrade.name }}</h3>
          <div class="upgrade-level">Nivel {{ upgrade.currentLevel }}/{{ upgrade.maxLevel }}</div>
        </div>

        <p class="upgrade-description">{{ upgrade.description }}</p>

        <div class="upgrade-progress">
          <div class="progress-bar">
            <div class="progress-fill" :style="{ width: (upgrade.currentLevel / upgrade.maxLevel) * 100 + '%' }"></div>
          </div>
        </div>

        <div class="upgrade-info">
          <span v-if="upgrade.currentLevel >= upgrade.maxLevel" class="maxed">✅ MÁXIMO NIVEL</span>
          <span v-else class="cost">Costo: {{ upgrade.costPerLevel * (upgrade.currentLevel + 1) }} Cr</span>
        </div>

        <button 
          v-if="upgrade.currentLevel < upgrade.maxLevel"
          @click="handleBuyUpgrade(upgrade.id)"
          :disabled="gameStore.world.resources.credits < upgrade.costPerLevel * (upgrade.currentLevel + 1)"
          :class="{ 
            'upgrade-btn': true, 
            'disabled': gameStore.world.resources.credits < upgrade.costPerLevel * (upgrade.currentLevel + 1)
          }"
        >
          Mejorar
        </button>
        <button v-else class="upgrade-btn disabled">Máximo</button>
      </div>
    </div>

    <div class="message-box" v-if="gameStore.world.message">
      {{ gameStore.world.message }}
    </div>

    <div class="shop-actions">
      <router-link to="/" class="btn btn-primary">← Volver al Mundo</router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useGameStore } from '../stores/gameStore'

const gameStore = useGameStore()

function getUpgradeEmoji(id: string): string {
  const emojis: Record<string, string> = {
    maxHealth: '❤️',
    attack: '⚔️',
    defense: '🛡️',
    speed: '⚡'
  }
  return emojis[id] || '🔧'
}

function buyUpgrade(upgradeId: 'maxHealth' | 'attack' | 'defense' | 'speed') {
  gameStore.buyUpgrade(upgradeId)
}

function handleBuyUpgrade(id: string) {
  if (id === 'maxHealth' || id === 'attack' || id === 'defense' || id === 'speed') {
    buyUpgrade(id)
  }
}
</script>

<style scoped>
.shop-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  color: #e0e0e0;
  padding: 20px;
  font-family: 'Courier New', monospace;
}

.shop-header {
  text-align: center;
  margin-bottom: 30px;
  border-bottom: 3px solid #f39c12;
  padding-bottom: 15px;
}

.shop-header h1 {
  font-size: 2.5rem;
  color: #f39c12;
  text-shadow: 0 0 10px #f39c12;
  margin: 0;
  letter-spacing: 2px;
}

.shop-subtitle {
  color: #bdc3c7;
  font-size: 1rem;
  margin: 10px 0 0 0;
  font-style: italic;
}

.shop-stats {
  display: flex;
  justify-content: space-around;
  margin-bottom: 30px;
  padding: 15px;
  background: rgba(0, 0, 0, 0.4);
  border-radius: 8px;
  border: 2px solid #34495e;
}

.stat-display {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
}

.stat-label {
  color: #95a5a6;
  font-size: 0.9rem;
  text-transform: uppercase;
}

.stat-value {
  font-size: 1.5rem;
  font-weight: bold;
  text-shadow: 0 0 5px;
}

.stat-value.credits {
  color: #f1c40f;
  text-shadow: 0 0 5px #f1c40f;
}

.stat-value.level {
  color: #e74c3c;
  text-shadow: 0 0 5px #e74c3c;
}

.stat-value.exp {
  color: #3498db;
  text-shadow: 0 0 5px #3498db;
}

.upgrades-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.upgrade-card {
  background: linear-gradient(135deg, rgba(52, 152, 219, 0.15), rgba(155, 89, 182, 0.15));
  border: 2px solid #34495e;
  border-radius: 10px;
  padding: 20px;
  transition: all 0.3s ease;
}

.upgrade-card:hover {
  border-color: #f39c12;
  box-shadow: 0 0 15px rgba(243, 156, 18, 0.3);
  transform: translateY(-5px);
}

.upgrade-header {
  display: flex;
  justify-content: space-between;
  align-items: start;
  margin-bottom: 10px;
}

.upgrade-header h3 {
  margin: 0;
  font-size: 1.2rem;
  color: #ecf0f1;
}

.upgrade-level {
  background: rgba(46, 204, 113, 0.3);
  color: #2ecc71;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 0.8rem;
  border: 1px solid #2ecc71;
}

.upgrade-description {
  color: #bdc3c7;
  font-size: 0.9rem;
  margin: 10px 0;
  line-height: 1.4;
}

.upgrade-progress {
  margin: 15px 0;
}

.progress-bar {
  width: 100%;
  height: 8px;
  background: rgba(0, 0, 0, 0.5);
  border-radius: 4px;
  overflow: hidden;
  border: 1px solid #34495e;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #3498db, #2ecc71);
  transition: width 0.3s ease;
  box-shadow: 0 0 10px rgba(52, 152, 219, 0.6);
}

.upgrade-info {
  margin-bottom: 15px;
  font-size: 0.9rem;
}

.cost {
  color: #f39c12;
  font-weight: bold;
}

.maxed {
  color: #2ecc71;
  font-weight: bold;
}

.upgrade-btn {
  width: 100%;
  padding: 10px;
  background: linear-gradient(135deg, #3498db, #2980b9);
  color: white;
  border: none;
  border-radius: 6px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  text-transform: uppercase;
  font-size: 0.9rem;
  letter-spacing: 1px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
}

.upgrade-btn:hover:not(.disabled) {
  background: linear-gradient(135deg, #2980b9, #3498db);
  box-shadow: 0 0 20px rgba(52, 152, 219, 0.6);
  transform: scale(1.05);
}

.upgrade-btn.disabled {
  opacity: 0.5;
  cursor: not-allowed;
  background: linear-gradient(135deg, #7f8c8d, #95a5a6);
}

.message-box {
  background: rgba(52, 152, 219, 0.2);
  border: 2px solid #3498db;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 20px;
  color: #ecf0f1;
  text-align: center;
  font-weight: bold;
  text-shadow: 0 0 5px rgba(52, 152, 219, 0.5);
}

.shop-actions {
  display: flex;
  gap: 10px;
  justify-content: center;
}

.btn {
  padding: 12px 30px;
  font-size: 1rem;
  font-weight: bold;
  border-radius: 6px;
  text-decoration: none;
  transition: all 0.3s ease;
  cursor: pointer;
  text-transform: uppercase;
  letter-spacing: 1px;
  border: none;
}

.btn-primary {
  background: linear-gradient(135deg, #e74c3c, #c0392b);
  color: white;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
}

.btn-primary:hover {
  background: linear-gradient(135deg, #c0392b, #e74c3c);
  box-shadow: 0 0 20px rgba(231, 76, 60, 0.6);
  transform: scale(1.05);
}

@media (max-width: 768px) {
  .shop-header h1 {
    font-size: 1.8rem;
  }

  .upgrades-grid {
    grid-template-columns: 1fr;
  }

  .shop-stats {
    flex-direction: column;
    gap: 10px;
  }
}
</style>
