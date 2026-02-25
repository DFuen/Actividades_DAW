<template>
  <div class="not-found-view">
    <div class="not-found-view__container">
      <div class="not-found-view__content">
        <h1 class="not-found-view__code">404</h1>
        <h2 class="not-found-view__title">Página No Encontrada</h2>
        <p class="not-found-view__message">
          Lo sentimos, la página que buscas no existe en nuestra arena.
        </p>

        <router-link to="/" class="btn btn--primary">
          🏠 Volver a Inicio
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { RouterLink } from 'vue-router'
</script>

<style scoped>
.not-found-view {
  min-height: 100vh;
  background: linear-gradient(135deg, #0f1419 0%, #1a252f 100%);
  color: #ecf0f1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem 1rem;
}

.not-found-view__container {
  text-align: center;
}

.not-found-view__content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}

.not-found-view__code {
  margin: 0;
  font-size: 8rem;
  font-weight: 900;
  background: linear-gradient(135deg, #e74c3c, #c0392b);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.not-found-view__title {
  margin: 0;
  font-size: 2rem;
  font-weight: 700;
  color: #f39c12;
}

.not-found-view__message {
  margin: 1rem 0;
  font-size: 1.1rem;
  color: #bdc3c7;
}

.btn {
  margin-top: 1rem;
  padding: 1rem 2rem;
  font-size: 1rem;
  font-weight: 700;
  text-decoration: none;
  border-radius: 0.5rem;
  transition: all 0.3s ease;
  text-transform: uppercase;
  display: inline-block;
  cursor: pointer;
  border: none;
}

.btn--primary {
  background-color: #3498db;
  color: white;
}

.btn--primary:hover {
  background-color: #2980b9;
  transform: translateY(-4px);
  box-shadow: 0 8px 16px rgba(52, 152, 219, 0.4);
}
</style>
