import { ref, computed } from 'vue'
import type { Part } from '../types'
import { robotService } from '../services/robotService'

/**
 * Composable para cargar partes disponibles
 */
export function useParts() {
  const parts = ref<Part[]>([])
  const loading = ref(false)
  const error = ref<string | null>(null)

  const loadParts = async () => {
    loading.value = true
    error.value = null
    try {
      parts.value = await robotService.getParts()
    } catch (err) {
      error.value = 'Error cargando piezas'
      console.error(err)
    } finally {
      loading.value = false
    }
  }

  const getPartsByCategory = (category: string) => {
    return parts.value.filter(p => p.category === category)
  }

  return {
    parts,
    loading,
    error,
    loadParts,
    getPartsByCategory
  }
}

/**
 * Composable para estadísticas del robot
 */
export function useRobotStats(head: Part | null, body: Part | null, limbs: Part | null, weapon: Part | null) {
  const totalStats = computed(() => {
    const stats = { health: 0, attack: 0, speed: 0 }
    const parts = [head, body, limbs, weapon].filter((p): p is Part => p !== null)

    parts.forEach(part => {
      stats.health += part.stats.health
      stats.attack += part.stats.attack
      stats.speed += part.stats.speed
    })

    stats.health = Math.max(10, stats.health)
    stats.attack = Math.max(1, stats.attack)
    stats.speed = Math.max(1, Math.abs(stats.speed))

    return stats
  })

  return { totalStats }
}
