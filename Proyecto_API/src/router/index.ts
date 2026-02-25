import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import { useGameStore } from '../stores/gameStore'

import HomeView from '../views/HomeView.vue'
import GarageView from '../views/GarageView.vue'
import ArenaView from '../views/ArenaView.vue'
import StatsView from '../views/StatsView.vue'
import NotFoundView from '../views/NotFoundView.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Home',
    component: HomeView,
    meta: { title: 'Inicio | Mecha-Factory Arena' }
  },
  {
    path: '/garage',
    name: 'Garage',
    component: GarageView,
    meta: { title: 'El Taller | Mecha-Factory Arena' }
  },
  {
    path: '/arena',
    name: 'Arena',
    component: ArenaView,
    meta: {
      title: 'La Arena | Mecha-Factory Arena',
      requiresRobot: true
    }
  },
  {
    path: '/stats',
    name: 'Stats',
    component: StatsView,
    meta: { title: 'Estadísticas | Mecha-Factory Arena' }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFoundView,
    meta: { title: '404 | Mecha-Factory Arena' }
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

/**
 * Navigation Guard: Validar requisitos de ruta
 */
router.beforeEach((to) => {
  const gameStore = useGameStore()

  // Verificar si la ruta requiere un robot construido
  if (to.meta.requiresRobot && !gameStore.isRobotComplete) {
    // Redirigir al taller si el robot no está completo
    return '/garage'
  }
})

/**
 * Navigation Guard: Actualizar título de página
 */
router.afterEach((to) => {
  document.title = (to.meta.title as string) || 'Mecha-Factory Arena'
})

export default router
