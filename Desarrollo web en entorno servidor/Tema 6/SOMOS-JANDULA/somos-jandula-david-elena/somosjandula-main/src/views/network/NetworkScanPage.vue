<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-menu-button></ion-menu-button>
        </ion-buttons>
        <ion-title>Escaneo de Redes</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content class="ion-padding">
      <ion-card>
        <ion-card-header>
          <ion-card-title>Herramienta de Escaneo de Red</ion-card-title>
        </ion-card-header>
        <ion-card-content>
          <p>Esta herramienta permite escanear dispositivos en la red local.</p>
          
          <ion-item>
            <ion-label position="stacked">Rango de IPs</ion-label>
            <ion-input v-model="ipRange" placeholder="192.168.1.0/24"></ion-input>
          </ion-item>

          <ion-button expand="block" @click="startScan" :disabled="scanning">
            <ion-icon slot="start" :name="scanning ? 'hourglass-outline' : 'search-outline'"></ion-icon>
            {{ scanning ? 'Escaneando...' : 'Iniciar Escaneo' }}
          </ion-button>

          <div v-if="scanning" class="scanning-progress">
            <ion-progress-bar type="indeterminate"></ion-progress-bar>
            <p>Escaneando la red, por favor espere...</p>
          </div>

          <div v-if="devices.length > 0" class="results">
            <h3>Dispositivos encontrados ({{ devices.length }})</h3>
            <ion-list>
              <ion-item v-for="device in devices" :key="device.ip">
                <ion-icon slot="start" :name="getDeviceIcon(device.type)" :color="device.online ? 'success' : 'medium'"></ion-icon>
                <ion-label>
                  <h2>{{ device.ip }}</h2>
                  <p>{{ device.hostname || 'Desconocido' }}</p>
                  <p v-if="device.mac">MAC: {{ device.mac }}</p>
                </ion-label>
                <ion-badge slot="end" :color="device.online ? 'success' : 'medium'">
                  {{ device.online ? 'Online' : 'Offline' }}
                </ion-badge>
              </ion-item>
            </ion-list>
          </div>

          <div v-if="!scanning && devices.length === 0 && scanCompleted">
            <ion-text color="medium">
              <p>No se encontraron dispositivos en el rango especificado.</p>
            </ion-text>
          </div>
        </ion-card-content>
      </ion-card>
    </ion-content>
  </ion-page>
</template>

<script setup>
import { ref } from 'vue';
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonButtons,
  IonMenuButton,
  IonTitle,
  IonContent,
  IonCard,
  IonCardHeader,
  IonCardTitle,
  IonCardContent,
  IonItem,
  IonLabel,
  IonInput,
  IonButton,
  IonIcon,
  IonProgressBar,
  IonList,
  IonBadge,
  IonText
} from '@ionic/vue';

const ipRange = ref('192.168.1.0/24');
const scanning = ref(false);
const scanCompleted = ref(false);
const devices = ref([]);

const startScan = async () => {
  scanning.value = true;
  scanCompleted.value = false;
  devices.value = [];

  // Simulación de escaneo (en producción, esto debería llamar a un endpoint del backend)
  await new Promise(resolve => setTimeout(resolve, 2000));

  // Datos de ejemplo
  devices.value = [
    { ip: '192.168.1.1', hostname: 'Router', mac: 'AA:BB:CC:DD:EE:01', online: true, type: 'router' },
    { ip: '192.168.1.10', hostname: 'PC-Profesor', mac: 'AA:BB:CC:DD:EE:02', online: true, type: 'computer' },
    { ip: '192.168.1.15', hostname: 'Impresora-Sala', mac: 'AA:BB:CC:DD:EE:03', online: true, type: 'printer' },
    { ip: '192.168.1.20', hostname: 'Proyector-Aula-1', mac: 'AA:BB:CC:DD:EE:04', online: true, type: 'projector' },
    { ip: '192.168.1.25', hostname: 'Switch-Principal', mac: 'AA:BB:CC:DD:EE:05', online: true, type: 'network' }
  ];

  scanning.value = false;
  scanCompleted.value = true;
};

const getDeviceIcon = (type) => {
  const icons = {
    router: 'wifi-outline',
    computer: 'desktop-outline',
    printer: 'print-outline',
    projector: 'videocam-outline',
    network: 'server-outline',
    default: 'hardware-chip-outline'
  };
  return icons[type] || icons.default;
};
</script>

<style scoped>
.scanning-progress {
  margin-top: 20px;
  text-align: center;
}

.results {
  margin-top: 20px;
}

.results h3 {
  margin-bottom: 10px;
  color: var(--ion-color-primary);
}
</style>
