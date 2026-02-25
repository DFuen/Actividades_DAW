# 🚀 Instrucciones de Desarrollo

## Comenzar el Proyecto

### Requisitos Previos
- Node.js v16 o superior
- npm o yarn
- Git (opcional)

### Setup Inicial

```bash
# 1. Navegar a la carpeta del proyecto
cd c:\Users\DAM\Desktop\ffififik

# 2. Instalar dependencias
npm install

# 3. Iniciar servidor de desarrollo
npm run dev

# 4. Abrir en navegador
# La consola mostrará: http://localhost:5174/
```

## Modo de Desarrollo

### Servidor en Vivo
```bash
npm run dev
```
- Hot Module Replacement (HMR) automático
- Los cambios se ven instantáneamente
- Recompilación rápida con Vite

### Compilación
```bash
npm run build
```
- TypeScript compilation
- Vite bundling
- Salida en `dist/`

### Preview de Producción
```bash
npm run preview
```
- Sirve la carpeta `dist/` localmente
- Para verificar antes de desplegar

## Testing

### Ejecutar Tests
```bash
npm run test
```
- Ejecuta Vitest
- Modo watch activo
- Reexecuta on change

### UI de Tests
```bash
npm run test:ui
```
- Interfaz visual de Vitest
- Mejor para debugging
- Accesible en navegador

### Coverage
```bash
npm run test -- --coverage
```
- Muestra % de cobertura
- Identifica código no testeado

## Estructura del Código

### Convenciones de Nombres

**Componentes**:
```
PascalCase: PartCard.vue, RobotPreview.vue
```

**Funciones**:
```
camelCase: calculateDamage(), selectPart()
```

**Constantes**:
```
UPPER_SNAKE_CASE: MAX_BUDGET = 1000
```

**Variables**:
```
camelCase: robot, totalStats, isFinished
```

### Organización de Carpetas

```
src/
├── assets/            # Recursos estáticos
├── components/        # Componentes reutilizables
├── composables/       # Composables compartidos
├── data/             # Datos JSON
├── router/           # Configuración de rutas
├── services/         # Servicios y APIs
├── stores/           # Stores de Pinia
├── types/            # Tipos TypeScript
├── utils/            # Funciones de utilidad
├── views/            # Componentes de página
├── App.vue           # Componente raíz
└── main.ts           # Punto de entrada
```

## Git Workflow (Recomendado)

### Branches por Departamento

```bash
# Comunicación (UI/UX)
git checkout -b feature/ui-components
git checkout -b fix/responsive-design

# Desarrollo (Core Logic)
git checkout -b feature/battle-system
git checkout -b feature/robot-calculations

# Sistemas (Architecture)
git checkout -b feature/pinia-store
git checkout -b feature/router-guards
```

### Commit Messages

```bash
# Formato: [TIPO] Descripción breve

# Tipos comunes:
# feat: Nueva funcionalidad
# fix: Corrección de bug
# style: Cambios de estilos
# refactor: Reorganización de código
# test: Agregar/modificar tests
# docs: Cambios en documentación
# chore: Tareas de mantenimiento

# Ejemplos:
git commit -m "feat: agregar animación a PartCard"
git commit -m "fix: corregir cálculo de daño crítico"
git commit -m "style: mejorar estilos globales"
```

## Debugging

### Consola del Navegador (F12)

```javascript
// En la consola puedes acceder a:

// Estado global de Pinia
// Primero importa el store
import { useGameStore } from './stores/gameStore'
const store = useGameStore()
console.log(store.robot)
console.log(store.battle)

// Modificar estado en tiempo real
store.robot.name = "Nuevo Nombre"
store.setPart('head', partObject)
```

### Vue DevTools

1. Instalar extensión Vue DevTools para Chrome/Firefox
2. Abre DevTools (F12)
3. Pestaña "Vue"
4. Inspecciona componentes y estado

### TypeScript Warnings

Si ves warnings de TypeScript:

```bash
# Verificar tipos
npx vue-tsc --noEmit

# Arreglar automáticamente (si es posible)
npx vue-tsc --noEmit --pretty
```

## Agregar Nuevas Funcionalidades

### Paso 1: Crear el Tipo
```typescript
// src/types/index.ts
export interface NewFeature {
  id: string
  name: string
}
```

### Paso 2: Crear la Utilidad
```typescript
// src/utils/newFeature.ts
export function calculateNewThing(data: NewFeature) {
  return data.name.length
}
```

### Paso 3: Agregar Test
```typescript
// tests/unit/newFeature.test.ts
describe('New Feature', () => {
  it('should work correctly', () => {
    expect(calculateNewThing(data)).toBe(expected)
  })
})
```

### Paso 4: Crear Componente
```vue
<!-- src/components/ui/NewComponent.vue -->
<template>
  <div>{{ feature.name }}</div>
</template>

<script setup lang="ts">
import type { NewFeature } from '../../types'

interface Props {
  feature: NewFeature
}

defineProps<Props>()
</script>

<style scoped>
div { }
</style>
```

### Paso 5: Integrar en Store (si es necesario)
```typescript
// src/stores/gameStore.ts
const features = reactive<NewFeature[]>([])

function addFeature(feature: NewFeature) {
  features.push(feature)
}
```

## Performance

### Analizar Bundle
```bash
npm run build
# Busca en dist/ el archivo más grande
```

### Lazy Loading de Vistas
```typescript
// Automático en Vue Router
const routes = [
  {
    path: '/view',
    component: () => import('./views/View.vue')
  }
]
```

### Computed vs Watchers
```typescript
// ✅ Usar computed cuando es posible
const result = computed(() => calculation)

// ⚠️ Usar watchers solo para side effects
watch(dependency, () => {
  // Side effect aquí
})
```

## Problemas Comunes

### "Port 5173 is already in use"
```bash
# Dedetiene el proceso
# O usa otro puerto
npm run dev -- --port 5175
```

### "Cannot find module"
```bash
# Limpia node_modules y reinstala
rm -r node_modules package-lock.json
npm install
```

### "TypeScript errors after changes"
```bash
# Reinicia el servidor
# Ctrl+C en terminal y npm run dev
```

### "Styles not appearing"
```vue
<!-- Asegúrate de usar scoped -->
<style scoped>
.component { }
</style>
```

### "Store state not updating"
```typescript
// ✅ Usa reactive para objetos
const state = reactive({ ... })

// ✅ Usa ref para primitivos
const count = ref(0)

// ⚠️ No olvides .value en script
count.value++
```

## Herramientas Recomendadas

### VS Code Extensions
- Vue - Official
- TypeScript Vue Plugin
- Prettier
- ESLint
- Vetur (opcional)

### Instalación
```
Ctrl+Shift+X (o Cmd+Shift+X en Mac)
Buscar y instalar las extensiones
```

## Workflow Recomendado

```bash
# 1. Crear rama
git checkout -b feature/mi-feature

# 2. Iniciar desarrollo
npm run dev

# 3. Hacer cambios
# 4. Ver cambios en navegador (hot reload automático)

# 5. Escribir tests
npm run test

# 6. Verificar compilación
npm run build

# 7. Commit
git add .
git commit -m "feat: descripción de cambios"

# 8. Push
git push origin feature/mi-feature

# 9. Merge en main (después de revisar)
git checkout main
git merge feature/mi-feature
```

## Documentación Adicional

- [Vue 3 Docs](https://vuejs.org)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)
- [Pinia](https://pinia.vuejs.org)
- [Vue Router](https://router.vuejs.org)
- [Vitest](https://vitest.dev)

## Soporte

Para dudas o problemas:
1. Revisa los archivos de documentación
2. Consulta la consola del navegador (F12)
3. Ejecuta tests para verificar lógica
4. Haz commit y crea issue si es persistente

¡Buen desarrollo! 🎉
