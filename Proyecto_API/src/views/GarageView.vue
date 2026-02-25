<template>
  <div class="garage-view">
    <!-- Partículas de fondo -->
    <div class="floating-particles">
      <div class="particle" v-for="i in 20" :key="i" :style="getParticleStyle(i)">⚙️</div>
    </div>
    
    <div class="garage-view__container">
      <header class="garage-view__header">
        <h1 class="garage-title">
          <span class="icon-bounce">🏗️</span>
          El Taller
          <span class="subtitle-tag">(The Garage)</span>
        </h1>
        <p class="garage-subtitle">Construye y personaliza tu robot de combate</p>
      </header>

      <div class="garage-view__layout">
        <!-- Sección de Previsualización -->
        <div class="garage-view__preview">
          <div class="control-panel">
            <div class="control-item">
              <label for="robot-name">Nombre del Robot:</label>
              <input
                id="robot-name"
                v-model="gameStore.robot.name"
                type="text"
                class="input"
                placeholder="Mi Mecha"
                maxlength="50"
              />
            </div>

            <div class="control-item">
              <div class="budget-display">
                <span class="budget-label">Presupuesto:</span>
                <span class="budget-value">{{ gameStore.robot.budget }} Cr</span>
              </div>
              <div class="budget-display">
                <span class="budget-label">Gastado:</span>
                <span class="budget-spent">{{ gameStore.robot.spentBudget }} Cr</span>
              </div>
              <div class="budget-display">
                <span class="budget-label">Disponible:</span>
                <span :class="['budget-remaining', gameStore.remainingBudget < 0 ? 'negative' : '']">
                  {{ gameStore.remainingBudget }} Cr
                </span>
              </div>
            </div>
          </div>

          <robot-preview
            :head="gameStore.robot.head"
            :body="gameStore.robot.body"
            :limbs="gameStore.robot.limbs"
            :weapon="gameStore.robot.weapon"
            :robot-name="gameStore.robot.name"
            :total-stats="gameStore.robot.totalStats"
            :budget="gameStore.robot.budget"
            :spent-budget="gameStore.robot.spentBudget"
            @selectPart="onSelectPart"
          />

          <div class="garage-view__actions">
            <button
              class="btn btn--success"
              :disabled="!gameStore.isRobotComplete || gameStore.remainingBudget < 0"
              @click="saveAndContinue"
            >
              ✅ Guardar y Continuar
            </button>
            <button class="btn btn--secondary" @click="resetRobot">
              🔄 Reiniciar
            </button>
          </div>
        </div>

        <!-- Nueva selección: solo muestra la lista de la categoría seleccionada -->
        <div class="garage-view__selector">
          <div class="parts-selector">
            <div class="category-section">
              <h3 class="category-title">{{ getCategoryLabel(selectedCategory) }}</h3>
              <div v-if="loading" class="spinner-small">⟳</div>
              <div v-else-if="error" class="error-message">{{ error }}</div>
              <div v-else>
                <!-- ...existing code... -->
                <div class="parts-grid" style="min-height:300px;display:flex;flex-direction:column;gap:1rem;">
                                                      <div v-for="part in filteredParts" :key="'test-'+part.id" style="color:red">TEST {{ part.name }}</div>
                                    <div style="color:yellow;font-size:12px;word-break:break-all;">DEBUG: {{ filteredParts }}</div>
                  <part-card
                    v-for="part in filteredParts"
                    :key="part.id"
                    :part="part"
                    :is-selected="isPartSelected(selectedCategory, part.id)"
                    :can-afford="canAffordPart(selectedCategory, part)"
                    @select="() => selectPart(selectedCategory, part)"
                  />
                  <div v-if="filteredParts.length === 0" style="color:#e74c3c;font-weight:bold;">No hay piezas para esta categoría</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Part } from '../types';
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useGameStore } from '../stores/gameStore'
// import { useParts } from '../composables'
// const getPartsByCategory = gameStore.getPartsByCategory

const router = useRouter()
const gameStore = useGameStore()


const loading = computed(() => gameStore.partsLoading)
const error = computed(() => gameStore.partsError)
const loadParts = gameStore.loadParts

const selectedCategory = ref('head')
const filteredParts = computed(() => gameStore.getPartsByCategory(selectedCategory.value));

function onSelectPart(cat: 'head' | 'body' | 'limbs' | 'weapon') {
  console.log('[Robot click] Categoría seleccionada:', cat)
  selectedCategory.value = cat
}

onMounted(async () => {
  await loadParts()
  gameStore.updateRobotStats()
  console.log('[DEBUG] Piezas cargadas:', gameStore.parts)
})

function getCategoryLabel(category: string): string {
  const labels: Record<string, string> = {
    head: '🧠 Cabeza',
    body: '🎯 Cuerpo',
    limbs: '💪 Extremidades',
    weapon: '🔫 Arma'
  }
  return labels[category] || category
}

function isPartSelected(category: string, partId: string): boolean {
  const categoryMap: Record<string, 'head' | 'body' | 'limbs' | 'weapon'> = {
    head: 'head',
    body: 'body',
    limbs: 'limbs',
    weapon: 'weapon'
  }
  const key = categoryMap[category]
  if (!key) return false
  const selectedPart = gameStore.robot[key]
  return selectedPart?.id === partId
}

function canAffordPart(category: string, part: Part): boolean {
  const categoryMap: Record<string, 'head' | 'body' | 'limbs' | 'weapon'> = {
    head: 'head',
    body: 'body',
    limbs: 'limbs',
    weapon: 'weapon'
  }
  const key = categoryMap[category]
  if (!key) return true
  
  const currentPartCost = gameStore.robot[key]?.cost || 0
  const newCost = part.cost - currentPartCost
  
  return gameStore.remainingBudget - newCost >= 0
}

function selectPart(category: any, part: Part) {
  if (isPartSelected(category, part.id)) {
    // Deseleccionar pieza
    gameStore.setPart(category, null)
  } else {
    // Calcular si se puede comprar
    // Ensure category is a valid robot key
    const categoryMap: Record<string, 'head' | 'body' | 'limbs' | 'weapon'> = {
      head: 'head',
      body: 'body',
      limbs: 'limbs',
      weapon: 'weapon'
    }
    const key = categoryMap[category]
    const currentPartCost = key ? gameStore.robot[key]?.cost || 0 : 0
    const newCost = part.cost - currentPartCost
    
    if (gameStore.remainingBudget - newCost < 0) {
      alert(`❌ No tienes suficiente presupuesto para esta pieza.\n\nNecesitas: ${part.cost} Cr\nDisponible: ${gameStore.remainingBudget + currentPartCost} Cr\nFaltan: ${newCost - gameStore.remainingBudget} Cr`)
      return
    }
    
    gameStore.setPart(category, part)
    
    // Efectos visuales épicos para piezas raras
    if (part.rarity === 'legendary') {
      showLegendaryEffect()
    } else if (part.rarity === 'epic') {
      showEpicEffect()
    }
  }
}

function showLegendaryEffect() {
  const notification = document.createElement('div')
  notification.className = 'legendary-notification'
  notification.innerHTML = '✨ ¡PIEZA LEGENDARIA EQUIPADA! ✨'
  document.body.appendChild(notification)
  
  setTimeout(() => {
    notification.remove()
  }, 3000)
}

function showEpicEffect() {
  const notification = document.createElement('div')
  notification.className = 'epic-notification'
  notification.innerHTML = '⚡ ¡Pieza Épica Equipada! ⚡'
  document.body.appendChild(notification)
  
  setTimeout(() => {
    notification.remove()
  }, 2000)
}

function getParticleStyle(index: number) {
  const size = Math.random() * 20 + 15
  // index is used for styling, if not used, ignore this warning
  const x = Math.random() * 100
  const delay = Math.random() * 5
  const duration = Math.random() * 10 + 10
  
  return {
    left: `${x}%`,
    fontSize: `${size}px`,
    animationDelay: `${delay}s`,
    animationDuration: `${duration}s`
  }
}

function saveAndContinue() {
  if (gameStore.isRobotComplete && gameStore.remainingBudget >= 0) {
    gameStore.saveRobot()
    router.push('/arena')
  }
}

function resetRobot() {
  if (confirm('¿Estás seguro de que quieres reiniciar el robot?')) {
    gameStore.resetGame()
  }
}
</script>

<style scoped>
.garage-view {
  min-height: 100vh;
  background: linear-gradient(135deg, #0f1419 0%, #1a252f 100%);
  color: #ecf0f1;
  padding: 2rem 1rem;
  position: relative;
  overflow: hidden;
}

/* Partículas flotantes */
.floating-particles {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
  z-index: 0;
}

.particle {
  position: absolute;
  opacity: 0.2;
  animation: float-up infinite linear;
}

@keyframes float-up {
  0% {
    transform: translateY(100vh) rotate(0deg);
    opacity: 0;
  }
  10% {
    opacity: 0.3;
  }
  90% {
    opacity: 0.3;
  }
  100% {
    transform: translateY(-100px) rotate(360deg);
    opacity: 0;
  }
}

.garage-view__container {
  max-width: 1400px;
  margin: 0 auto;
  position: relative;
  z-index: 1;
}

.garage-view__header {
  text-align: center;
  margin-bottom: 2rem;
}

.garage-title {
  margin: 0;
  font-size: 2.5rem;
  color: #f39c12;
  text-transform: uppercase;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
}

.icon-bounce {
  display: inline-block;
  animation: bounce-icon 1.5s ease-in-out infinite;
}

@keyframes bounce-icon {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.subtitle-tag {
  font-size: 1.2rem;
  color: #3498db;
  font-style: italic;
  font-weight: normal;
}

.garage-subtitle {
  margin: 0.5rem 0 0 0;
  font-size: 1.1rem;
  color: #bdc3c7;
}

.garage-view__layout {
  display: grid;
  grid-template-columns: 1fr 1.2fr;
  gap: 2rem;
}

.garage-view__preview {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.control-panel {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  border: 2px solid #3498db;
  border-radius: 0.75rem;
  padding: 1.5rem;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
  transition: all 0.3s ease;
}

.control-panel:hover {
  box-shadow: 0 6px 20px rgba(52, 152, 219, 0.4);
  transform: translateY(-2px);
}

.control-item {
  margin-bottom: 1rem;
}

.control-item:last-child {
  margin-bottom: 0;
}

.control-item label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 600;
  font-size: 0.9rem;
  text-transform: uppercase;
  color: #3498db;
}

.input {
  width: 100%;
  padding: 0.75rem;
  background-color: #1a252f;
  border: 2px solid #7f8c8d;
  border-radius: 0.375rem;
  color: #ecf0f1;
  font-size: 1rem;
  font-family: inherit;
  transition: all 0.3s ease;
}

.input:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 8px rgba(52, 152, 219, 0.5);
  transform: scale(1.02);
}

.budget-display {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.75rem;
  background-color: rgba(0, 0, 0, 0.3);
  border-left: 3px solid #3498db;
  border-radius: 0.375rem;
  margin-bottom: 0.5rem;
  transition: all 0.3s ease;
}

.budget-display:hover {
  background-color: rgba(0, 0, 0, 0.5);
  border-left-width: 5px;
}

.budget-label {
  font-weight: 600;
  font-size: 0.85rem;
}

.budget-value,
.budget-spent,
.budget-remaining {
  font-weight: 700;
  font-size: 1rem;
  color: #f1c40f;
}

.budget-remaining.negative {
  color: #e74c3c;
  animation: pulse-red 1s ease-in-out infinite;
}

@keyframes pulse-red {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.6; }
}

.garage-view__actions {
  display: flex;
  gap: 1rem;
}

.btn {
  flex: 1;
  padding: 0.75rem 1.5rem;
  font-size: 0.95rem;
  font-weight: 600;
  border: none;
  border-radius: 0.375rem;
  cursor: pointer;
  transition: all 0.2s ease;
  text-transform: uppercase;
  letter-spacing: 0.5px;
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

.btn:hover:not(:disabled)::before {
  width: 300px;
  height: 300px;
}

.btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
}

.btn--success {
  background: linear-gradient(135deg, #2ecc71, #27ae60);
  color: white;
}

.btn--success:hover:not(:disabled) {
  box-shadow: 0 6px 20px rgba(46, 204, 113, 0.4);
}

.btn--secondary {
  background: linear-gradient(135deg, #95a5a6, #7f8c8d);
  color: white;
}

.btn--secondary:hover:not(:disabled) {
  box-shadow: 0 6px 20px rgba(149, 165, 166, 0.4);
}

.btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.garage-view__selector {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  border: 2px solid #3498db;
  border-radius: 0.75rem;
  padding: 1.5rem;
  max-height: 800px;
  overflow-y: auto;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
}

.garage-view__selector::-webkit-scrollbar {
  width: 10px;
}

.garage-view__selector::-webkit-scrollbar-track {
  background: #1a252f;
  border-radius: 5px;
}

.garage-view__selector::-webkit-scrollbar-thumb {
  background: #3498db;
  border-radius: 5px;
}

.garage-view__selector::-webkit-scrollbar-thumb:hover {
  background: #2980b9;
}

.parts-selector {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.category-section {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.category-title {
  margin: 0;
  font-size: 1.1rem;
  font-weight: 700;
  color: #f39c12;
  text-transform: uppercase;
  border-bottom: 2px solid #3498db;
  padding-bottom: 0.75rem;
  position: relative;
}

.category-title::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 0;
  height: 2px;
  background: #f39c12;
  transition: width 0.3s ease;
}

.category-section:hover .category-title::after {
  width: 100%;
}

.parts-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1rem;
}

.spinner-small {
  text-align: center;
  font-size: 1.5rem;
  animation: spin 0.8s linear infinite;
}

.error-message {
  color: #e74c3c;
  text-align: center;
  padding: 1rem;
}

/* Notificaciones épicas */
:deep(.legendary-notification) {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 10000;
  padding: 2rem 3rem;
  background: linear-gradient(135deg, #f39c12, #e74c3c);
  border: 3px solid #f1c40f;
  border-radius: 1rem;
  font-size: 1.8rem;
  font-weight: 900;
  color: white;
  text-align: center;
  box-shadow: 0 10px 40px rgba(243, 156, 18, 0.8);
  animation: legendary-appear 3s ease-in-out;
}

@keyframes legendary-appear {
  0% {
    transform: translate(-50%, -50%) scale(0) rotate(0deg);
    opacity: 0;
  }
  10% {
    transform: translate(-50%, -50%) scale(1.2) rotate(5deg);
    opacity: 1;
  }
  20%, 80% {
    transform: translate(-50%, -50%) scale(1) rotate(-2deg);
  }
  25%, 75% {
    transform: translate(-50%, -50%) scale(1) rotate(2deg);
  }
  90% {
    transform: translate(-50%, -50%) scale(1.1) rotate(0deg);
    opacity: 1;
  }
  100% {
    transform: translate(-50%, -50%) scale(0) rotate(0deg);
    opacity: 0;
  }
}

:deep(.epic-notification) {
  position: fixed;
  top: 20%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 10000;
  padding: 1.5rem 2.5rem;
  background: linear-gradient(135deg, #9b59b6, #8e44ad);
  border: 2px solid #c39bd3;
  border-radius: 0.75rem;
  font-size: 1.5rem;
  font-weight: 700;
  color: white;
  text-align: center;
  box-shadow: 0 8px 30px rgba(155, 89, 182, 0.6);
  animation: epic-appear 2s ease-in-out;
}

@keyframes epic-appear {
  0% {
    transform: translate(-50%, -100px) scale(0.5);
    opacity: 0;
  }
  20% {
    transform: translate(-50%, -50%) scale(1.1);
    opacity: 1;
  }
  80% {
    transform: translate(-50%, -50%) scale(1);
    opacity: 1;
  }
  100% {
    transform: translate(-50%, 50px) scale(0.5);
    opacity: 0;
  }
}

.legendary-notification {
  position: fixed;
  top: 30%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: linear-gradient(90deg, #ffd700 0%, #ff8c00 100%);
  color: #fff;
  font-size: 2rem;
  font-weight: bold;
  padding: 1.5rem 3rem;
  border-radius: 16px;
  box-shadow: 0 0 40px 10px #ffd700;
  z-index: 9999;
  animation: legendary-pop 0.5s;
}

@keyframes legendary-pop {
  0% { transform: scale(0.7) translate(-50%, -50%); opacity: 0; }
  60% { transform: scale(1.1) translate(-50%, -50%); opacity: 1; }
  100% { transform: scale(1) translate(-50%, -50%); opacity: 1; }
}

.epic-notification {
  position: fixed;
  top: 30%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: linear-gradient(90deg, #8e44ad 0%, #3498db 100%);
  color: #fff;
  font-size: 1.5rem;
  font-weight: bold;
  padding: 1rem 2rem;
  border-radius: 12px;
  box-shadow: 0 0 30px 8px #8e44ad;
  z-index: 9999;
  animation: epic-pop 0.5s;
}

@keyframes epic-pop {
  0% { transform: scale(0.7) translate(-50%, -50%); opacity: 0; }
  60% { transform: scale(1.1) translate(-50%, -50%); opacity: 1; }
  100% { transform: scale(1) translate(-50%, -50%); opacity: 1; }
}

@media (max-width: 1024px) {
  .garage-view__layout {
    grid-template-columns: 1fr;
  }

  .garage-view__selector {
    max-height: none;
  }
  
  .garage-title {
    font-size: 2rem;
    flex-direction: column;
    gap: 0.5rem;
  }
}
</style>
