<template>
  <div class="minecraft-world">
    <header class="minecraft-header">
      <div class="header-left">
        <h1>⛏️ Mecha-Craft Arena</h1>
        <p>{{ gameStore.world.message }}</p>
      </div>
      
      <!-- Inventario de Recursos -->
      <div class="inventory">
        <div class="resource-item">🌲 <strong>{{ gameStore.world.resources.wood }}</strong></div>
        <div class="resource-item">🪨 <strong>{{ gameStore.world.resources.stone }}</strong></div>
        <div class="resource-item">⛓️ <strong>{{ gameStore.world.resources.iron }}</strong></div>
        <div class="resource-item">💰 <strong>{{ gameStore.world.resources.gold }}</strong></div>
        <div class="resource-item">💵 <strong>{{ gameStore.world.resources.credits }}</strong></div>
      </div>

      <!-- Stats -->
      <div class="game-stats">
        <div class="stat">⚔️ Oleada: <strong>{{ gameStore.world.wave }}</strong></div>
        <div class="stat">🏆 Puntos: <strong>{{ gameStore.world.score }}</strong></div>
        <div class="stat">🏰 Base: <strong>{{ gameStore.world.baseHealth }}/{{ gameStore.world.baseMaxHealth }}</strong></div>
      </div>
    </header>

    <div class="main-container">
      <!-- Mapa del Mundo Minecraft -->
      <div class="game-grid">
        <div
          v-for="block in gameStore.world.blocks"
          :key="`${block.x}-${block.y}`"
          class="grid-cell"
          :class="getBlockClass(block)"
          :style="getBlockStyle(block)"
          @click="handleBlockClick(block)"
        >
          <!-- Jugador -->
          <div v-if="isPlayer(block.x, block.y)" class="player-char">🤖</div>
          
          <!-- Enemigos -->
          <div 
            v-else-if="getEnemyAt(block.x, block.y)" 
            class="enemy-char"
            :style="{ color: getEnemyAt(block.x, block.y)?.color }"
          >
            👾
          </div>
          
          <!-- Proyectiles -->
          <div 
            v-else-if="getProjectileAt(block.x, block.y)" 
            class="projectile"
          >
            💥
          </div>
          
          <!-- Icono del bloque -->
          <span v-else class="block-icon">{{ getBlockIcon(block.type) }}</span>
          
          <!-- Durabilidad -->
          <div v-if="block.durability && block.durability > 0" class="durability-bar">
            <div 
              class="durability-fill" 
              :style="{ width: (block.durability / getMaxDurability(block.type) * 100) + '%' }"
            ></div>
          </div>
        </div>
      </div>

      <!-- Panel de Construcción -->
      <div class="build-panel">
        <h3>🏗️ Construcción</h3>
        <p class="build-help">Haz clic en una casilla de césped para construir</p>
        
        <div class="build-options">
          <div
            v-for="structure in gameStore.structures"
            :key="structure.id"
            class="build-option"
            :class="{ 'build-option--selected': selectedStructure === structure.id, 'build-option--disabled': !canAfford(structure) }"
            @click="selectStructure(structure.id)"
          >
            <div class="build-icon">{{ getStructureIcon(structure.type) }}</div>
            <div class="build-name">{{ structure.name }}</div>
            <div class="build-cost">
              <span v-if="structure.cost.wood">🌲{{ structure.cost.wood }}</span>
              <span v-if="structure.cost.stone">🪨{{ structure.cost.stone }}</span>
              <span v-if="structure.cost.iron">⛓️{{ structure.cost.iron }}</span>
            </div>
            <div class="build-desc">{{ structure.description }}</div>
          </div>
        </div>

        <div class="controls">
          <h4>⌨️ Controles</h4>
          <div class="control-item">WASD / Flechas - Mover</div>
          <div class="control-item">E cerca de bloque - Minar</div>
          <div class="control-item">Espacio - Disparar</div>
          <div class="control-item">Click en césped - Construir</div>
        </div>

        <div v-if="gameStore.world.gameOver" class="game-over">
          <h2>💀 GAME OVER</h2>
          <p>{{ gameStore.world.message }}</p>
          <p>Puntuación Final: <strong>{{ gameStore.world.score }}</strong></p>
          <p>Oleadas Completadas: <strong>{{ gameStore.world.wave - 1 }}</strong></p>
          <button class="btn btn--primary" @click="restartGame">🔄 Reiniciar</button>
          <router-link to="/" class="btn btn--secondary">🏠 Menú Principal</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onBeforeUnmount, onMounted, ref } from 'vue'
import { useGameStore } from '../stores/gameStore'
import type { Block, BlockType, Structure } from '../types'

const gameStore = useGameStore()
const selectedStructure = ref<string | null>(null)
let gameLoop: number | null = null

const getBlockIcon = (type: BlockType): string => {
  const icons: Record<BlockType, string> = {
    grass: '🟩',
    stone: '⬜',
    iron: '⬛',
    gold: '🟨',
    wood: '🟫',
    water: '🟦',
    base: '🏰',
    tower: '🗼',
    wall: '🧱',
    empty: ''
  }
  return icons[type] || ''
}

const getStructureIcon = (type: string): string => {
  return type === 'base' ? '🏰' : type === 'tower' ? '🗼' : '🧱'
}

const getBlockClass = (block: Block): string => {
  const classes = [`block-${block.type}`]
  if (block.durability && block.durability > 0) {
    classes.push('block-minable')
  }
  return classes.join(' ')
}

const getBlockStyle = (block: Block): any => {
  const colors: Record<BlockType, string> = {
    grass: '#7cb342',
    stone: '#78909c',
    iron: '#424242',
    gold: '#ffd700',
    wood: '#8d6e63',
    water: '#1e88e5',
    base: '#e91e63',
    tower: '#9c27b0',
    wall: '#607d8b',
    empty: 'transparent'
  }
  return { backgroundColor: colors[block.type] || '#7cb342' }
}

const getMaxDurability = (type: BlockType): number => {
  const durabilities: Record<string, number> = {
    wood: 3,
    stone: 5,
    iron: 7,
    gold: 10
  }
  return durabilities[type] || 1
}

const isPlayer = (x: number, y: number): boolean => {
  return gameStore.world.player.x === x && gameStore.world.player.y === y
}

const getEnemyAt = (x: number, y: number) => {
  return gameStore.world.enemies.find(e => Math.floor(e.x) === x && Math.floor(e.y) === y)
}

const getProjectileAt = (x: number, y: number) => {
  return gameStore.world.projectiles.find(p => Math.floor(p.x) === x && Math.floor(p.y) === y)
}

const canAfford = (structure: Structure): boolean => {
  if (!structure.cost) return true
  const resources = gameStore.world.resources
  return (
    (!structure.cost.wood || resources.wood >= structure.cost.wood) &&
    (!structure.cost.stone || resources.stone >= structure.cost.stone) &&
    (!structure.cost.iron || resources.iron >= structure.cost.iron) &&
    (!structure.cost.gold || resources.gold >= structure.cost.gold)
  )
}

const selectStructure = (id: string) => {
  selectedStructure.value = id
}

const handleBlockClick = (block: Block) => {
  if (gameStore.world.gameOver) return
  
  if (selectedStructure.value && block.type === 'grass') {
    gameStore.buildStructure(block.x, block.y, selectedStructure.value)
    selectedStructure.value = null
  }
}

const handleKey = (event: KeyboardEvent) => {
  if (gameStore.world.gameOver) return
  
  const key = event.key.toLowerCase()
  
  switch (key) {
    case 'arrowup':
    case 'w':
      event.preventDefault()
      gameStore.moveMinecraftPlayer(0, -1)
      break
    case 'arrowdown':
    case 's':
      event.preventDefault()
      gameStore.moveMinecraftPlayer(0, 1)
      break
    case 'arrowleft':
    case 'a':
      event.preventDefault()
      gameStore.moveMinecraftPlayer(-1, 0)
      break
    case 'arrowright':
    case 'd':
      event.preventDefault()
      gameStore.moveMinecraftPlayer(1, 0)
      break
    case ' ':
      event.preventDefault()
      gameStore.shootPlayerWeapon()
      break
    case 'e':
      event.preventDefault()
      mineNearbyBlock()
      break
  }
}

const mineNearbyBlock = () => {
  const { x, y } = gameStore.world.player
  // Buscar bloques adyacentes minables
  const adjacentPositions = [
    { x: x - 1, y },
    { x: x + 1, y },
    { x, y: y - 1 },
    { x, y: y + 1 }
  ]
  
  for (const pos of adjacentPositions) {
    const block = gameStore.getBlock(pos.x, pos.y)
    if (block && block.durability && block.durability > 0) {
      gameStore.mineBlock(pos.x, pos.y)
      return
    }
  }
  
  gameStore.world.message = '❌ No hay bloques minables cerca'
}

const restartGame = () => {
  gameStore.initMinecraftWorld()
}

onMounted(() => {
  gameStore.initMinecraftWorld()
  window.addEventListener('keydown', handleKey)
  
  // Game loop para actualizar el juego
  gameLoop = window.setInterval(() => {
    if (!gameStore.world.gameOver) {
      gameStore.updateMinecraftGame()
    }
  }, 100)
})

onBeforeUnmount(() => {
  window.removeEventListener('keydown', handleKey)
  if (gameLoop) clearInterval(gameLoop)
})
</script>

<style scoped>
.minecraft-world {
  min-height: 100vh;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%);
  color: #ecf0f1;
  padding: 1rem;
  overflow: hidden;
}

.minecraft-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 1.5rem;
  margin-bottom: 1rem;
  flex-wrap: wrap;
  background: rgba(0, 0, 0, 0.4);
  padding: 1rem;
  border-radius: 8px;
  border: 2px solid #3498db;
}

.header-left h1 {
  margin: 0;
  font-size: 2rem;
  color: #2ecc71;
  text-shadow: 0 0 10px rgba(46, 204, 113, 0.5);
}

.header-left p {
  margin: 0.25rem 0 0;
  color: #bdc3c7;
  font-size: 0.9rem;
}

.inventory {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.resource-item {
  background: rgba(44, 62, 80, 0.8);
  padding: 0.5rem 1rem;
  border-radius: 4px;
  border: 2px solid #34495e;
  font-size: 0.95rem;
}

.game-stats {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.stat {
  background: rgba(231, 76, 60, 0.2);
  padding: 0.5rem 1rem;
  border-radius: 4px;
  border: 2px solid #e74c3c;
  font-size: 0.95rem;
}

.main-container {
  display: grid;
  grid-template-columns: 1fr 300px;
  gap: 1rem;
  max-width: 1600px;
  margin: 0 auto;
}

.game-grid {
  display: grid;
  grid-template-columns: repeat(30, 24px);
  grid-template-rows: repeat(20, 24px);
  gap: 1px;
  background: #000;
  border: 4px solid #2c3e50;
  border-radius: 8px;
  padding: 4px;
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.8);
}

.grid-cell {
  position: relative;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.2s ease;
  border: 1px solid rgba(0, 0, 0, 0.2);
}

.grid-cell:hover {
  border-color: #fff;
  transform: scale(1.1);
  z-index: 10;
}

.block-grass { background-color: #7cb342; }
.block-stone { background-color: #78909c; }
.block-iron { background-color: #424242; }
.block-gold { background-color: #ffd700; }
.block-wood { background-color: #8d6e63; }
.block-water { background-color: #1e88e5; }
.block-base { background-color: #e91e63; animation: pulse 1.5s ease-in-out infinite; }
.block-tower { background-color: #9c27b0; animation: pulse 1.5s ease-in-out infinite; }
.block-wall { background-color: #607d8b; }

.block-minable {
  animation: shimmer 2s ease-in-out infinite;
}

.block-icon {
  font-size: 14px;
  pointer-events: none;
}

.player-char {
  font-size: 20px;
  animation: bounce 0.5s ease-in-out infinite;
  z-index: 10;
}

.enemy-char {
  font-size: 18px;
  animation: float 2s ease-in-out infinite;
  z-index: 9;
}

.projectile {
  font-size: 12px;
  animation: spin 0.5s linear infinite;
  z-index: 8;
}

.durability-bar {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: rgba(0, 0, 0, 0.5);
}

.durability-fill {
  height: 100%;
  background: linear-gradient(90deg, #e74c3c, #f39c12, #2ecc71);
  transition: width 0.3s ease;
}

.build-panel {
  background: rgba(0, 0, 0, 0.6);
  border: 2px solid #3498db;
  border-radius: 8px;
  padding: 1rem;
  max-height: calc(100vh - 180px);
  overflow-y: auto;
}

.build-panel h3 {
  margin: 0 0 0.5rem;
  color: #3498db;
}

.build-help {
  font-size: 0.85rem;
  color: #95a5a6;
  margin-bottom: 1rem;
}

.build-options {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  margin-bottom: 1.5rem;
}

.build-option {
  background: rgba(44, 62, 80, 0.8);
  border: 2px solid #34495e;
  border-radius: 6px;
  padding: 0.75rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.build-option:hover {
  border-color: #3498db;
  transform: translateX(5px);
  box-shadow: 0 0 15px rgba(52, 152, 219, 0.4);
}

.build-option--selected {
  border-color: #2ecc71;
  background: rgba(46, 204, 113, 0.2);
  box-shadow: 0 0 20px rgba(46, 204, 113, 0.5);
}

.build-option--disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.build-icon {
  font-size: 2rem;
  text-align: center;
  margin-bottom: 0.5rem;
}

.build-name {
  font-weight: 700;
  color: #ecf0f1;
  margin-bottom: 0.25rem;
}

.build-cost {
  display: flex;
  gap: 0.5rem;
  font-size: 0.85rem;
  color: #f39c12;
  margin-bottom: 0.25rem;
}

.build-desc {
  font-size: 0.8rem;
  color: #95a5a6;
}

.controls {
  background: rgba(52, 73, 94, 0.6);
  border-radius: 6px;
  padding: 0.75rem;
  margin-bottom: 1rem;
}

.controls h4 {
  margin: 0 0 0.5rem;
  color: #f39c12;
  font-size: 0.95rem;
}

.control-item {
  font-size: 0.8rem;
  color: #bdc3c7;
  margin-bottom: 0.25rem;
}

.game-over {
  background: rgba(231, 76, 60, 0.3);
  border: 2px solid #e74c3c;
  border-radius: 8px;
  padding: 1.5rem;
  text-align: center;
}

.game-over h2 {
  margin: 0 0 1rem;
  color: #e74c3c;
  font-size: 2rem;
}

.game-over p {
  margin: 0.5rem 0;
  color: #ecf0f1;
}

.btn {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 6px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.3s ease;
  text-decoration: none;
  display: inline-block;
  margin: 0.5rem 0.25rem;
}

.btn--primary {
  background: #2ecc71;
  color: #fff;
}

.btn--primary:hover {
  background: #27ae60;
  transform: scale(1.05);
  box-shadow: 0 0 20px rgba(46, 204, 113, 0.5);
}

.btn--secondary {
  background: #34495e;
  color: #fff;
}

.btn--secondary:hover {
  background: #2c3e50;
  transform: scale(1.05);
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.7; }
}

@keyframes shimmer {
  0%, 100% { filter: brightness(1); }
  50% { filter: brightness(1.3); }
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-4px); }
}

@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-3px); }
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

@media (max-width: 1400px) {
  .game-grid {
    grid-template-columns: repeat(30, 20px);
    grid-template-rows: repeat(20, 20px);
  }
  
  .grid-cell {
    width: 20px;
    height: 20px;
    font-size: 14px;
  }
}

@media (max-width: 1200px) {
  .main-container {
    grid-template-columns: 1fr;
  }
  
  .build-panel {
    max-height: none;
  }
}

@media (max-width: 768px) {
  .game-grid {
    grid-template-columns: repeat(30, 16px);
    grid-template-rows: repeat(20, 16px);
  }
  
  .grid-cell {
    width: 16px;
    height: 16px;
    font-size: 12px;
  }
  
  .minecraft-header {
    flex-direction: column;
  }
}
</style>
