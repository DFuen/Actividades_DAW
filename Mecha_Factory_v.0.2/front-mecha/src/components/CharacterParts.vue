<template>
  <div>
    <div class="zones">
      <!-- Botones ocultos para head, limbs, body, weapon -->
    </div>
    <div v-if="loading">Cargando partes...</div>
    <div v-if="error" class="error">{{ error }}</div>
    <div v-if="selectedZone && parts[selectedZone]">
      <h3>Partes de {{ selectedZone }}</h3>
      <ul>
        <li v-for="(part, idx) in parts[selectedZone]" :key="idx">{{ part.name || part }}</li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { fetchParts } from '../services/partService';
import type { PartsResponse } from '../services/partService';

const parts = ref<PartsResponse>({ head: [], limbs: [], body: [], weapon: [] });
const loading = ref(false);
const error = ref('');
const selectedZone = ref<'head' | 'limbs' | 'body' | 'weapon' | null>(null);

const zones = ['head', 'limbs', 'body', 'weapon'] as const;

onMounted(async () => {
  loading.value = true;
  error.value = '';
  try {
    parts.value = await fetchParts();
  } catch (e: any) {
    error.value = e.message || 'Error desconocido';
  } finally {
    loading.value = false;
  }
});

function selectZone(zone: typeof zones[number]) {
  selectedZone.value = zone;
}
</script>

<style scoped>
.zones button {
  margin: 0 0.5rem;
}
.error {
  color: red;
}
</style>
