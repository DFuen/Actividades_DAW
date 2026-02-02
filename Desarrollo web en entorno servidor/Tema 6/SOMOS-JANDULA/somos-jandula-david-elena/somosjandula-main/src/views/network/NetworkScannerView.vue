<template>
  <div class="network-scanner">
    <div class="placeholder-content">
      <div class="construction-icon">🚧</div>
      <h1>🌐 Escaneo de Redes</h1>
      <p class="placeholder-text">Aquí iría la visualización general de las redes</p>
      <p class="construction-label">En construcción</p>
    </div>
  </div>
</template>

<script setup lang="ts">
// Página de prueba - sin funcionalidad implementada
</script>

<style scoped>
.network-scanner {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.placeholder-content {
  text-align: center;
  background: white;
  padding: 60px 40px;
  border-radius: 20px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
  max-width: 600px;
}construction-icon {
  font-size: 5em;
  margin-bottom: 20px;
  animation: swing 2s ease-in-out infinite;
}

@keyframes swing {
  0%, 100% { transform: rotate(-10deg); }
  50% { transform: rotate(10deg); }
}

.placeholder-content h1 {
  font-size: 2.5em;
  color: #667eea;
  margin-bottom: 20px;
}

.placeholder-text {
  font-size: 1.2em;
  color: #666;
  margin: 0 0 15px 0;
}

.construction-label {
  font-size: 1.1em;
  color: #f39c12;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 2px;
}

@media (max-width: 768px) {
  .placeholder-content {
    padding: 40px 30px;
  }

  .placeholder-content h1 {
    font-size: 2em;
  }

  .placeholder-text {
    font-size: 1em;
  }
}
</style>

