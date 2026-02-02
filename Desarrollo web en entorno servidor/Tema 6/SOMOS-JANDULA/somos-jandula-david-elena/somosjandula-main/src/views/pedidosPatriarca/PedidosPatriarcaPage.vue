<template>
  <ion-page>
    <ion-header :translucent="true">
      <ion-toolbar color="primary">
        <ion-buttons slot="start">
          <ion-back-button default-href="/"></ion-back-button>
        </ion-buttons>
        <ion-title>Pedidos Patriarca</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Pedidos Patriarca</ion-title>
        </ion-toolbar>
      </ion-header>

      <div v-if="loading" class="ion-padding">
        <ion-spinner></ion-spinner>
        <p>Cargando aplicación...</p>
      </div>

      <iframe
        v-else
        :src="pedidosPatriarcaUrl"
        style="width: 100%; height: 100%; border: none;"
        title="Pedidos Patriarca"
      ></iframe>
    </ion-content>
  </ion-page>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonButtons,
  IonBackButton,
  IonSpinner,
} from '@ionic/vue';

const loading = ref(true);
const pedidosPatriarcaUrl = ref('');

onMounted(() => {
  // Configura la URL base según el entorno
  const baseUrl = import.meta.env.VITE_PEDIDOS_PATRIARCA_URL || 'http://localhost:8080';
  pedidosPatriarcaUrl.value = baseUrl;
  loading.value = false;
});
</script>

<style scoped>
ion-content {
  --padding-start: 0;
  --padding-end: 0;
  --padding-top: 0;
  --padding-bottom: 0;
}
</style>
