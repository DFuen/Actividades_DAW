import type { Part, Robot, RobotStats } from '../types'

/**
 * Calcular estadísticas totales del robot
 */
export function calculateRobotStats(robot: Robot): RobotStats {
  const stats: RobotStats = {
    health: 0,
    attack: 0,
    speed: 0
  }

  const parts = [robot.head, robot.body, robot.limbs, robot.weapon].filter(
    (p): p is Part => p !== null
  )

  parts.forEach(part => {
    stats.health += part.stats.health
    stats.attack += part.stats.attack
    stats.speed += part.stats.speed
  })

  // Asegurar valores mínimos
  stats.health = Math.max(100, stats.health)
  stats.attack = Math.max(1, stats.attack)
  stats.speed = Math.max(1, Math.abs(stats.speed))

  return stats
}

/**
 * Calcular presupuesto restante
 */
export function calculateRemainingBudget(robot: Robot): number {
  return robot.budget - robot.spentBudget
}

/**
 * Validar que el robot tenga todas las partes necesarias
 */
export function isRobotComplete(robot: Robot): boolean {
  return Boolean(robot.head && robot.body && robot.limbs && robot.weapon)
}

/**
 * Validar que el robot tenga un nombre válido
 */
export function isValidRobotName(name: string): boolean {
  return name.trim().length >= 3 && name.trim().length <= 50
}

/**
 * Calcular daño en combate con variación por tipo de ataque
 * Balanceado para combates más largos
 */
export function calculateDamage(
  attackerStats: RobotStats,
  defenderStats: RobotStats,
  critical: boolean = false,
  isPlayerAttack: boolean = false
): number {
  // Ajuste para batallas más largas:
  const variance = 0.85 + Math.random() * 0.22
  let baseDamage = attackerStats.attack * 1.1 + Math.random() * 10

  if (critical) {
    baseDamage *= 1.5
  }

  if (isPlayerAttack) {
    baseDamage *= 1.12
  }

  const defense = defenderStats.health * 0.4
  const finalDamage = Math.max(5, Math.floor((baseDamage * variance) - defense))
  return finalDamage
}

/**
 * Calcular probabilidad de golpe crítico
 */
export function isCriticalHit(speed: number): boolean {
  const critChance = Math.min(0.1969, speed / 80 * 0.5625)
  return Math.random() < critChance
}

/**
 * Simular turno de IA
 */
export function getAIDecision(
  playerHealth: number,
  aiHealth: number,
  aiStats: RobotStats
): 'attack' | 'heal' {
  const healthPercentage = aiHealth / (aiStats.health * 1.5)

  // Si la IA está en mal estado, intenta curarse
  if (healthPercentage < 0.3) {
    return 'heal'
  }

  // Si la salud del jugador es baja, ataca
  if (playerHealth < 30) {
    return 'attack'
  }

  // Por defecto, ataca
  return Math.random() > 0.4 ? 'attack' : 'heal'
}

/**
 * Obtener color del rareza
 */
export function getRarityColor(rarity: string): string {
  const colors: Record<string, string> = {
    common: '#808080',
    uncommon: '#00AA00',
    rare: '#0055FF',
    epic: '#AA00FF',
    legendary: '#FFAA00'
  }
  return colors[rarity] || '#808080'
}

/**
 * Obtener CSS variables por rareza
 */
export function getRarityStyles(rarity: string): Record<string, string> {
  const rarityColors: Record<string, string> = {
    common: '#808080',
    uncommon: '#2ECC71',
    rare: '#3498DB',
    epic: '#9B59B6',
    legendary: '#F39C12'
  }

  return {
    '--color-rarity': rarityColors[rarity] || '#808080',
    '--rarity': rarity
  }
}
