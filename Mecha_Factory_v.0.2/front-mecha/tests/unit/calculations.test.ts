import { describe, it, expect } from 'vitest'
import { calculateDamage, calculateRobotStats, isRobotComplete, getRarityColor } from '../utils/calculations'
import type { Robot, Part } from '../types'

describe('Cálculos de Daño', () => {
  it('debe calcular daño correctamente', () => {
    const attacker = { health: 30, attack: 15, speed: 8 }
    const defender = { health: 20, attack: 10, speed: 5 }

    const damage = calculateDamage(attacker, defender)
    expect(damage).toBeGreaterThan(0)
    expect(damage).toBeLessThanOrEqual(25)
  })

  it('debe aplicar daño crítico', () => {
    const attacker = { health: 30, attack: 15, speed: 8 }
    const defender = { health: 20, attack: 10, speed: 5 }

    const normalDamage = calculateDamage(attacker, defender, false)
    const criticalDamage = calculateDamage(attacker, defender, true)

    expect(criticalDamage).toBeGreaterThan(normalDamage * 1.3)
  })
})

describe('Estadísticas del Robot', () => {
  it('debe calcular estadísticas totales correctamente', () => {
    const robot: Robot = {
      id: 'test_1',
      name: 'Test Robot',
      head: {
        id: 'head_1',
        name: 'Head',
        category: 'head',
        rarity: 'common',
        cost: 50,
        stats: { health: 10, attack: 5, speed: 5 },
        color: '#808080',
        description: 'Test head'
      },
      body: {
        id: 'body_1',
        name: 'Body',
        category: 'body',
        rarity: 'common',
        cost: 75,
        stats: { health: 15, attack: 8, speed: 10 },
        color: '#808080',
        description: 'Test body'
      },
      limbs: {
        id: 'limbs_1',
        name: 'Limbs',
        category: 'limbs',
        rarity: 'common',
        cost: 60,
        stats: { health: 10, attack: 8, speed: 6 },
        color: '#808080',
        description: 'Test limbs'
      },
      weapon: {
        id: 'weapon_1',
        name: 'Weapon',
        category: 'weapon',
        rarity: 'common',
        cost: 100,
        stats: { health: 0, attack: 15, speed: 2 },
        color: '#808080',
        description: 'Test weapon'
      },
      totalStats: { health: 0, attack: 0, speed: 0 },
      budget: 1000,
      spentBudget: 285
    }

    const stats = calculateRobotStats(robot)

    expect(stats.health).toBe(35)
    expect(stats.attack).toBe(36)
    expect(stats.speed).toBe(23)
  })

  it('debe asegurar valores mínimos de estadísticas', () => {
    const robot: Robot = {
      id: 'test_2',
      name: 'Minimal Robot',
      head: null,
      body: null,
      limbs: null,
      weapon: null,
      totalStats: { health: 0, attack: 0, speed: 0 },
      budget: 1000,
      spentBudget: 0
    }

    const stats = calculateRobotStats(robot)

    expect(stats.health).toBeGreaterThanOrEqual(10)
    expect(stats.attack).toBeGreaterThanOrEqual(1)
    expect(stats.speed).toBeGreaterThanOrEqual(1)
  })
})

describe('Validación de Robot', () => {
  it('debe validar robot completo', () => {
    const completePart: Part = {
      id: 'test',
      name: 'Test',
      category: 'head',
      rarity: 'common',
      cost: 50,
      stats: { health: 10, attack: 5, speed: 5 },
      color: '#808080',
      description: 'Test'
    }

    const completeRobot: Robot = {
      id: 'test',
      name: 'Complete',
      head: completePart,
      body: completePart,
      limbs: completePart,
      weapon: completePart,
      totalStats: { health: 0, attack: 0, speed: 0 },
      budget: 1000,
      spentBudget: 200
    }

    expect(isRobotComplete(completeRobot)).toBe(true)
  })

  it('debe identificar robot incompleto', () => {
    const incompletePart: Part = {
      id: 'test',
      name: 'Test',
      category: 'head',
      rarity: 'common',
      cost: 50,
      stats: { health: 10, attack: 5, speed: 5 },
      color: '#808080',
      description: 'Test'
    }

    const incompleteRobot: Robot = {
      id: 'test',
      name: 'Incomplete',
      head: incompletePart,
      body: incompletePart,
      limbs: null,
      weapon: null,
      totalStats: { health: 0, attack: 0, speed: 0 },
      budget: 1000,
      spentBudget: 100
    }

    expect(isRobotComplete(incompleteRobot)).toBe(false)
  })
})

describe('Rareza de Piezas', () => {
  it('debe obtener color de rareza correctamente', () => {
    expect(getRarityColor('common')).toBe('#808080')
    expect(getRarityColor('uncommon')).toBe('#00AA00')
    expect(getRarityColor('rare')).toBe('#0055FF')
    expect(getRarityColor('epic')).toBe('#AA00FF')
    expect(getRarityColor('legendary')).toBe('#FFAA00')
  })

  it('debe retornar color por defecto para rareza desconocida', () => {
    expect(getRarityColor('unknown')).toBe('#808080')
  })
})
