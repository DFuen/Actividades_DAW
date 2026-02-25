// Partes disponibles
  const parts = ref<Part[]>([])
  const partsLoading = ref(false)
  const partsError = ref<string|null>(null)

  // ...existing code...

  // ...existing code...

  async function loadParts() {
    partsLoading.value = true
    partsError.value = null
    try {
      parts.value = await robotService.getParts()
    } catch (err) {
      partsError.value = 'Error cargando piezas'
      console.error(err)
    } finally {
      partsLoading.value = false
    }
  }

  function getPartsByCategory(category: string) {
    return parts.value.filter(
      p => String(p.category).trim().toLowerCase() === String(category).trim().toLowerCase()
    )
  }

  // Generar un robot enemigo difícil (solo partes épicas o legendarias)
  // Guardar partes del enemigo para visualización
  const enemyParts = reactive({
    head: null,
    body: null,
    limbs: null,
    weapon: null
  })

  async function generateHardEnemyStats() {
    const allParts = await robotService.getParts();
    function getRandomPart(category: Part['category']): Part | null {
      const filtered = allParts.filter(p => p.category === category && (p.rarity === 'epic' || p.rarity === 'legendary'));
      return filtered.length > 0 ? filtered[Math.floor(Math.random() * filtered.length)] ?? null : null;
    }
    const enemyParts: Robot = {
      id: 'enemy-hard',
      name: 'Hard Enemy',
      head: getRandomPart('head'),
      body: getRandomPart('body'),
      limbs: getRandomPart('limbs'),
      weapon: getRandomPart('weapon'),
      totalStats: { health: 0, attack: 0, speed: 0 },
      budget: 0,
      spentBudget: 0
    };
    return calculateRobotStats(enemyParts);
  }
import { defineStore } from 'pinia'
import { computed, reactive, ref } from 'vue'
import type { Robot, Part, BattleState, BattleLog, RobotStats, Achievement, PlayerStats } from '../types'
import {
  calculateRobotStats,
  calculateDamage,
  isCriticalHit,
  getAIDecision
} from '../utils/calculations'
import { robotService } from '../services/robotService'

export const useGameStore = defineStore('game', () => {
  // Estado del robot
  const robot = reactive<Robot>({
    id: 'robot_' + Date.now(),
    name: 'Mecha Prototype',
    head: null,
    body: null,
    limbs: null,
    weapon: null,
    totalStats: { health: 0, attack: 0, speed: 0 },
    budget: 1000,
    spentBudget: 0
  })

  // Estado de la batalla
  const battle = reactive<BattleState>({
    playerHealth: 0,
    playerMaxHealth: 0,
    aiHealth: 0,
    aiMaxHealth: 0,
    currentTurn: 'player',
    battleLog: [],
    isFinished: false,
    winner: null
  })

  // Estado del combo
  const combo = reactive({
    count: 0,
    multiplier: 1,
    lastHitTime: 0
  })

  const aiStats = reactive({
    health: 120,
    attack: 22,
    speed: 12
  })

  // Sistema de Estadísticas
  const playerStats = reactive<PlayerStats>({
    totalBattles: 0,
    wins: 0,
    losses: 0,
    totalDamageDealt: 0,
    totalDamageTaken: 0,
    criticalHits: 0,
    perfectVictories: 0,
    longestCombo: 0,
    highestDamage: 0,
    robotsBuilt: 0,
    creditsSpent: 0
  })

  // Sistema de Logros
  const achievements = reactive<Achievement[]>([
    { id: 'first_blood', name: 'Primera Sangre', description: 'Gana tu primera batalla', icon: '🏆', unlocked: false },
    { id: 'perfect', name: 'Perfecto', description: 'Gana sin recibir daño', icon: '💎', unlocked: false },
    { id: 'combo_master', name: 'Maestro del Combo', description: 'Alcanza un combo de 5', icon: '⚡', unlocked: false },
    { id: 'critical_king', name: 'Rey Crítico', description: 'Haz 10 golpes críticos', icon: '👑', unlocked: false },
    { id: 'big_spender', name: 'Gran Gastador', description: 'Gasta 5000 créditos', icon: '💰', unlocked: false },
    { id: 'collector', name: 'Coleccionista', description: 'Construye 5 robots diferentes', icon: '🤖', unlocked: false },
    { id: 'legendary', name: 'Legendario', description: 'Equipa una pieza legendaria', icon: '🌟', unlocked: false },
    { id: 'warrior', name: 'Guerrero', description: 'Gana 10 batallas', icon: '⚔️', unlocked: false }
  ])

  const recentAchievement = ref<Achievement | null>(null)
  const cameraShake = ref(false)
  const particleEffects = ref<any[]>([])

  // Funciones de gestión del robot
  function setPart(category: Part['category'], part: Part | null) {
    // Equip or unequip part
    robot[category] = part;
    // Recalculate spentBudget from all equipped parts
    robot.spentBudget = [robot.head, robot.body, robot.limbs, robot.weapon]
      .filter(p => p)
      .reduce((sum, p) => sum + (p?.cost || 0), 0);
    updateRobotStats()
  }

  function updateRobotStats() {
    robot.totalStats = calculateRobotStats(robot)
  }

  function setRobotName(name: string) {
    robot.name = name
  }

  function saveRobot() {
    robotService.saveRobot(robot)
  }

  function loadSavedRobot() {
    const saved = robotService.loadRobot()
    if (saved) {
      Object.assign(robot, saved)
      updateRobotStats()
    }
  }

  // Funciones de batalla
  function startBattle(enemyStats?: RobotStats) {
    battle.isFinished = false
    battle.winner = null
    battle.battleLog = []
    battle.currentTurn = 'player'

    battle.playerMaxHealth = robot.totalStats.health
    battle.playerHealth = robot.totalStats.health

    if (enemyStats) {
      battle.aiMaxHealth = enemyStats.health
      battle.aiHealth = enemyStats.health
      aiStats.health = enemyStats.health
      aiStats.attack = enemyStats.attack
      aiStats.speed = enemyStats.speed
    } else {
      // Si no se pasa enemyStats, generar uno difícil y guardar partes
      generateHardEnemyStats().then(stats => {
        battle.aiMaxHealth = stats.health;
        battle.aiHealth = stats.health;
        aiStats.health = stats.health;
        aiStats.attack = stats.attack;
        aiStats.speed = stats.speed;
      });
    }
  }

  function playerAttack() {
    if (battle.currentTurn !== 'player' || battle.isFinished) return

    const isCritical = isCriticalHit(robot.totalStats.speed)
    const damage = calculateDamage(robot.totalStats, aiStats, isCritical, true)
    battle.aiHealth = Math.max(0, battle.aiHealth - damage)

    const log: BattleLog = {
      id: 'log_' + Date.now(),
      timestamp: Date.now(),
      action: isCritical
        ? `¡Golpe crítico de ${robot.name}!`
        : `${robot.name} ataca`,
      damage,
      actorType: 'player'
    }
    battle.battleLog.push(log)

    if (battle.aiHealth <= 0) {
      battle.isFinished = true
      battle.winner = 'player'
      return
    }

    battle.currentTurn = 'ai'
    setTimeout(aiTurn, 1500)
  }

  function playerHeal() {
    if (battle.currentTurn !== 'player' || battle.isFinished) return

    const healAmount = Math.floor(robot.totalStats.attack * 0.5) + 10
    const oldHealth = battle.playerHealth
    battle.playerHealth = Math.min(battle.playerMaxHealth, battle.playerHealth + healAmount)
    const actualHeal = battle.playerHealth - oldHealth

    const log: BattleLog = {
      id: 'log_' + Date.now(),
      timestamp: Date.now(),
      action: `${robot.name} se cura`,
      damage: actualHeal,
      actorType: 'player'
    }
    battle.battleLog.push(log)

    battle.currentTurn = 'ai'
    setTimeout(aiTurn, 1500)
  }

  function aiTurn() {
    if (battle.currentTurn !== 'ai' || battle.isFinished) return

    const decision = getAIDecision(battle.playerHealth, battle.aiHealth, aiStats)

    if (decision === 'heal') {
      const healAmount = Math.floor(aiStats.attack * 0.5) + 10
      const oldHealth = battle.aiHealth
      battle.aiHealth = Math.min(battle.aiMaxHealth, battle.aiHealth + healAmount)
      const actualHeal = battle.aiHealth - oldHealth

      const log: BattleLog = {
        id: 'log_' + Date.now(),
        timestamp: Date.now(),
        action: 'La IA se cura',
        damage: actualHeal,
        actorType: 'ai'
      }
      battle.battleLog.push(log)
    } else {
      const isCritical = isCriticalHit(aiStats.speed)
      const damage = calculateDamage(aiStats, robot.totalStats, isCritical, false)
      battle.playerHealth = Math.max(0, battle.playerHealth - damage)

      const log: BattleLog = {
        id: 'log_' + Date.now(),
        timestamp: Date.now(),
        action: isCritical ? '¡Golpe crítico de la IA!' : 'La IA ataca',
        damage,
        actorType: 'ai'
      }
      battle.battleLog.push(log)

      if (battle.playerHealth <= 0) {
        battle.isFinished = true
        battle.winner = 'ai'
        return
      }
    }

    battle.currentTurn = 'player'
  }

  function resetGame() {
    robot.name = 'Mecha Prototype'
    robot.head = null
    robot.body = null
    robot.limbs = null
    robot.weapon = null
    robot.spentBudget = 0
    robot.totalStats = { health: 0, attack: 0, speed: 0 }

    battle.isFinished = false
    battle.winner = null
    battle.battleLog = []
    battle.currentTurn = 'player'
  }

  // ==================== SISTEMA ÉPICO ====================

  // Sistema de Logros
  function unlockAchievement(id: string) {
    const achievement = achievements.find(a => a.id === id)
    if (achievement && !achievement.unlocked) {
      achievement.unlocked = true
      achievement.unlockedAt = Date.now()
      recentAchievement.value = achievement
      
      // Guardar en localStorage
      savePlayerProgress()
      
      setTimeout(() => {
        recentAchievement.value = null
      }, 5000)
    }
  }

  function checkAchievements() {
    if (playerStats.wins >= 1) unlockAchievement('first_blood')
    if (playerStats.wins >= 10) unlockAchievement('warrior')
    if (playerStats.criticalHits >= 10) unlockAchievement('critical_king')
    if (playerStats.creditsSpent >= 5000) unlockAchievement('big_spender')
    if (playerStats.robotsBuilt >= 5) unlockAchievement('collector')
    
    // Verificar pieza legendaria
    if (robot.head?.rarity === 'legendary' || 
        robot.body?.rarity === 'legendary' || 
        robot.limbs?.rarity === 'legendary' || 
        robot.weapon?.rarity === 'legendary') {
      unlockAchievement('legendary')
    }
  }

  // Efectos visuales épicos
  function triggerCameraShake() {
    cameraShake.value = true
    setTimeout(() => {
      cameraShake.value = false
    }, 300)
  }

  function addParticleEffect(type: string, x: number, y: number) {
    const particle = {
      id: Date.now() + Math.random(),
      type,
      x,
      y,
      timestamp: Date.now()
    }
    particleEffects.value.push(particle)
    
    setTimeout(() => {
      particleEffects.value = particleEffects.value.filter(p => p.id !== particle.id)
    }, 1000)
  }

  // Guardar progreso
  function savePlayerProgress() {
    localStorage.setItem('mecha-stats', JSON.stringify(playerStats))
    localStorage.setItem('mecha-achievements', JSON.stringify(achievements))
  }

  function loadPlayerProgress() {
    const savedStats = localStorage.getItem('mecha-stats')
    const savedAchievements = localStorage.getItem('mecha-achievements')
    
    if (savedStats) {
      Object.assign(playerStats, JSON.parse(savedStats))
    }
    
    if (savedAchievements) {
      const loaded = JSON.parse(savedAchievements)
      loaded.forEach((saved: Achievement) => {
        const achievement = achievements.find(a => a.id === saved.id)
        if (achievement && saved.unlocked) {
          achievement.unlocked = true
          achievement.unlockedAt = saved.unlockedAt
        }
      })
    }
  }

  // Sobrescribir setPart para trackear estadísticas
  const originalSetPart = setPart
  function enhancedSetPart(category: Part['category'], part: Part | null) {
    const oldPart = robot[category]
    originalSetPart(category, part)
    
    if (part && !oldPart) {
      // Nueva pieza agregada
      const isComplete = robot.head && robot.body && robot.limbs && robot.weapon
      if (isComplete) {
        playerStats.robotsBuilt++
        checkAchievements()
      }
    }
    
    if (part) {
      playerStats.creditsSpent += part.cost
      checkAchievements()
    }
  }

  // Inicializar progreso
  loadPlayerProgress()

  // Computed properties
  const remainingBudget = computed(() => robot.budget - robot.spentBudget)
  const isRobotComplete = computed(
    () => Boolean(robot.head && robot.body && robot.limbs && robot.weapon)
  )
  const playerHealthPercent = computed(() => {
    if (battle.playerMaxHealth === 0) return 0
    return (battle.playerHealth / battle.playerMaxHealth) * 100
  })
  const aiHealthPercent = computed(() => {
    if (battle.aiMaxHealth === 0) return 0
    return (battle.aiHealth / battle.aiMaxHealth) * 100
  })
  const winRate = computed(() => {
    if (playerStats.totalBattles === 0) return 0
    return (playerStats.wins / playerStats.totalBattles * 100).toFixed(1)
  })

  return {
    // State
    robot,
    battle,
    aiStats,
    enemyParts,
    playerStats,
    achievements,
    recentAchievement,
    cameraShake,
    particleEffects,
    combo,
    // Computed
    remainingBudget,
    isRobotComplete,
    playerHealthPercent,
    aiHealthPercent,
    winRate,
    // Parts system
    parts,
    partsLoading,
    partsError,
    loadParts,
    getPartsByCategory,
    // Actions
    setPart: enhancedSetPart,
    updateRobotStats,
    setRobotName,
    saveRobot,
    loadSavedRobot,
    startBattle,
    playerAttack,
    playerHeal,
    aiTurn,
    resetGame,
    unlockAchievement,
    checkAchievements,
    triggerCameraShake,
    addParticleEffect,
    savePlayerProgress,
    loadPlayerProgress
  }
})
