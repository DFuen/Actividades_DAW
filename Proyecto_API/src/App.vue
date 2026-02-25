<script setup lang="ts">
import { RouterView, RouterLink } from 'vue-router'
import { computed } from 'vue'
import { useGameStore } from './stores/gameStore'

const gameStore = useGameStore()

const robotName = computed(() => gameStore.robot.name || 'Tu Robot')
</script>

<template>
  <div class="app">
    <nav class="navbar">
      <div class="navbar__container">
        <router-link to="/" class="navbar__brand">
          🌌 Mecha Frontier
        </router-link>

        <div class="navbar__links">
          <router-link to="/" class="navbar__link">Mundo Abierto</router-link>
          <router-link to="/garage" class="navbar__link">Taller</router-link>
          <router-link to="/arena" class="navbar__link" :class="{ disabled: !gameStore.isRobotComplete }">
            Simulador
          </router-link>
        </div>

        <div class="navbar__robot-info" v-if="gameStore.robot.name">
          <span class="robot-badge">{{ robotName }}</span>
        </div>
      </div>
    </nav>

    <main class="main-content">
      <router-view />
    </main>

    <footer class="footer">
      <p>&copy; 2026 Mecha-Factory Arena. Proyecto Académico de Desarrollo Web con Vue.js</p>
    </footer>
  </div>
</template>

<style scoped>
.app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: var(--color-dark-bg);
  color: var(--color-text-primary);
}

.navbar {
  background: linear-gradient(135deg, #1a252f 0%, #2c3e50 100%);
  border-bottom: 3px solid #3498db;
  padding: 1rem 0;
  position: sticky;
  top: 0;
  z-index: 1000;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
}

.navbar__container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 2rem;
}

.navbar__brand {
  font-size: 1.5rem;
  font-weight: 700;
  color: #f39c12;
  text-decoration: none;
  transition: color 0.3s ease;
  white-space: nowrap;
}

.navbar__brand:hover {
  color: #e67e22;
}

.navbar__links {
  display: flex;
  gap: 2rem;
  flex: 1;
}

.navbar__link {
  font-size: 0.95rem;
  font-weight: 600;
  color: #bdc3c7;
  text-decoration: none;
  transition: all 0.3s ease;
  position: relative;
}

.navbar__link:hover:not(.disabled) {
  color: #3498db;
}

.navbar__link.router-link-active {
  color: #2ecc71;
}

.navbar__link.router-link-active::after {
  content: '';
  position: absolute;
  bottom: -0.5rem;
  left: 0;
  right: 0;
  height: 2px;
  background-color: #2ecc71;
}

.navbar__link.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.navbar__robot-info {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.robot-badge {
  background-color: #34495e;
  border: 1px solid #3498db;
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  font-size: 0.85rem;
  font-weight: 600;
  color: #f1c40f;
}

.main-content {
  flex: 1;
}

.footer {
  background-color: #1a252f;
  border-top: 1px solid #3498db;
  padding: 2rem 1rem;
  text-align: center;
  color: #95a5a6;
  font-size: 0.9rem;
}

.footer p {
  margin: 0;
}

@media (max-width: 768px) {
  .navbar__container {
    flex-direction: column;
    gap: 1rem;
  }

  .navbar__links {
    gap: 1rem;
    width: 100%;
    justify-content: space-around;
  }

  .navbar__robot-info {
    width: 100%;
    justify-content: center;
  }
}
</style>
