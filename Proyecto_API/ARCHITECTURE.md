# Arquitectura del Proyecto Mecha-Factory Arena

## 🏗️ Visión General

El proyecto está diseñado siguiendo patrones modernos de Vue 3 con una arquitectura limpia y escalable.

## 📦 Capas de la Aplicación

### 1. **Capa de Presentación (Presentation Layer)**

**Ubicación**: `src/views/` y `src/components/`

Los componentes están organizados jerárquicamente:

- **Views** (`views/`): Componentes de página completa correspondientes a rutas
  - `HomeView.vue`: Página de inicio
  - `GarageView.vue`: Interfaz de construcción de robots
  - `ArenaView.vue`: Interfaz de combate
  - `NotFoundView.vue`: Página 404

- **Components** (`components/`): Componentes reutilizables
  - `parts/`: Componentes relacionados con piezas de robots
  - `ui/`: Componentes UI genéricos (Button, StatBar, Spinner)
  - `arena/`: Componentes específicos de combate

### 2. **Capa de Lógica de Negocio (Business Logic Layer)**

**Ubicación**: `src/utils/` y `src/composables/`

- **Calculations** (`utils/calculations.ts`):
  - Cálculo de estadísticas
  - Cálculo de daño en combate
  - Validaciones de robot
  - Obtención de colores por rareza

- **Composables** (`composables/index.ts`):
  - `useParts()`: Manejo de carga de piezas
  - `useRobotStats()`: Cálculo reactivo de estadísticas

### 3. **Capa de Estado Global (State Management)**

**Ubicación**: `src/stores/`

**Pinia Store** (`stores/gameStore.ts`):
```typescript
- robot: Objeto del robot construido
- battle: Estado actual de la batalla
- aiStats: Estadísticas de la IA
```

**Acciones principales**:
- `setPart()`: Asignar pieza al robot
- `startBattle()`: Inicializar batalla
- `playerAttack()`: Acción de ataque del jugador
- `playerHeal()`: Acción de curación
- `aiTurn()`: Lógica del turno de la IA

### 4. **Capa de Servicios (Services Layer)**

**Ubicación**: `src/services/`

**Robot Service** (`robotService.ts`):
- Carga de piezas desde JSON
- Persistencia en localStorage
- Simulación de peticiones API

### 5. **Capa de Datos (Data Layer)**

**Ubicación**: `src/data/`

- `parts.json`: Base de datos de piezas disponibles

### 6. **Capa de Enrutamiento (Routing Layer)**

**Ubicación**: `src/router/`

**Router** (`router/index.ts`):
- Configuración de rutas
- Navigation Guards para proteger rutas
- Redirección automática a Garage si robot no está completo

## 🔄 Flujo de Datos

### Flujo de Construcción del Robot (Garage)

```
Usuario interactúa con PartCard
    ↓
selectPart() en GarageView
    ↓
gameStore.setPart()
    ↓
updateRobotStats()
    ↓
Componentes se actualizan vía reactividad (computed)
    ↓
RobotPreview muestra cambios en tiempo real
```

### Flujo de Combate (Arena)

```
gameStore.startBattle()
    ↓
playerAttack() o playerHeal()
    ↓
Cálculo de daño (calculateDamage)
    ↓
Actualizar estado batalla
    ↓
aiTurn() después de delay
    ↓
BattleHUD muestra cambios
    ↓
Si es victoria/derrota → mostrar resultado
```

## 🧩 Patrones Utilizados

### 1. **Composition API**

Todos los componentes usan `<script setup>`:
```vue
<script setup lang="ts">
const state = ref(0)
const computed_value = computed(() => state.value * 2)
</script>
```

### 2. **Reactividad Automática**

Los componentes se actualizan automáticamente cuando:
- Se modifica el estado de Pinia
- Se actualizan los computed properties
- Se modifican las propiedades reactivas

### 3. **Props y Emits**

Comunicación entre componentes padres e hijos:
```typescript
// Hijo
const props = defineProps<{ part: Part; isSelected: boolean }>()
defineEmits<{ select: [] }>()

// Padre
@select="selectPart"
```

### 4. **Slots Implícitos**

Los componentes UI utilizan slots para flexibilidad:
```vue
<Button>
  Contenido personalizable
</Button>
```

## 🎯 Principios de Arquitectura

### SOLID

- **S**ingle Responsibility: Cada componente y función tiene una responsabilidad clara
- **O**pen/Closed: Componentes extensibles sin modificación
- **L**iskov Substitution: Componentes intercambiables
- **I**nterface Segregation: Interfaces pequeñas y específicas
- **D**ependency Inversion: Inyección de dependencias a través de props

### DRY (Don't Repeat Yourself)

- Composables para lógica compartida
- Utilidades centralizadas
- Componentes reutilizables

### KISS (Keep It Simple, Stupid)

- Componentes enfocados y simples
- Funciones pequeñas y específicas
- Nombres descriptivos

## 🔐 Seguridad de Tipos

### TypeScript Strict Mode

```typescript
// Tipos completamente definidos
interface Part { ... }
interface Robot { ... }
interface BattleState { ... }
```

### Props Tipadas

Todos los componentes definen props con tipos explícitos:
```typescript
interface Props {
  part: Part
  isSelected?: boolean
}

defineProps<Props>()
```

## 🚀 Optimizaciones

### Code Splitting

Vue Router automáticamente crea chunks para cada ruta:
```
dist/assets/
├── index-main.js (shared)
├── HomeView-chunk.js
├── GarageView-chunk.js
└── ArenaView-chunk.js
```

### Lazy Loading

Las vistas se cargan bajo demanda mediante Vue Router.

### Computed Properties

Se cachean automáticamente y solo se recalculan cuando cambian dependencias:
```typescript
const remainingBudget = computed(() => robot.budget - robot.spentBudget)
```

## 📊 Flujos de Estado Complejos

### Batalla Multi-turno

```
battleLog: BattleLog[]
  ↓
Cada acción crea una entrada en el log
  ↓
BattleHUD renderiza el log automáticamente
  ↓
Se agrega nueva entrada y se actualiza en tiempo real
```

### Persistencia de Datos

```
gameStore (en memoria)
    ↓
gameStore.saveRobot()
    ↓
robotService.saveRobot()
    ↓
localStorage.setItem()
```

## 🧪 Testing Strategy

### Unit Tests

```typescript
describe('Cálculos de Daño', () => {
  it('debe calcular daño correctamente', () => {
    const damage = calculateDamage(attacker, defender)
    expect(damage).toBeGreaterThan(0)
  })
})
```

Cubiertos:
- Funciones de utilidad (`calculations.ts`)
- Lógica de validación
- Cálculos matemáticos

### Integration Testing

Se recomienda agregar:
- E2E tests con Cypress/Playwright
- Tests de componentes con Vue Test Utils

## 🌍 Variables de Entorno

```
.env                      # Defaults
.env.development         # Desarrollo
.env.production          # Producción
```

## 🎨 Sistema de Estilos

### CSS Variables Globales

```css
:root {
  --color-primary: #3498db;
  --color-success: #2ecc71;
  --color-danger: #e74c3c;
}
```

### Estilos Scoped

Cada componente tiene sus propios estilos encapsulados:
```vue
<style scoped>
.component-specific { }
</style>
```

### Tema Oscuro

La aplicación usa un tema oscuro consistente implementado con:
- Variables CSS
- Paleta de colores predefinida
- Animaciones suaves

## 🔧 Herramientas de Desarrollo

- **Vite**: Build tool ultra-rápido
- **Vue Router**: Enrutamiento de SPA
- **Pinia**: State management
- **TypeScript**: Tipado fuerte
- **Vitest**: Testing unitario

## 📈 Escalabilidad

### Para agregar nuevas características:

1. **Nuevo tipo de pieza**: Agregar en `parts.json` y actualizar tipos
2. **Nueva acción de combate**: Agregar en `gameStore` y componente
3. **Nueva vista**: Crear `NewView.vue` y agregar en router
4. **Nueva utilidad**: Crear en `utils/` con tests asociados

## 🎓 Lecciones de Arquitectura

Este proyecto demuestra:

1. **Separación de preocupaciones**: UI, lógica, datos
2. **Reactividad avanzada**: Computed, watchers, reactive
3. **State management**: Patrón Pinia
4. **Enrutamiento dinámico**: Vue Router con guards
5. **Tipado seguro**: TypeScript avanzado
6. **Testing**: Vitest para funciones críticas
7. **Performance**: Code splitting y lazy loading
8. **UX**: Animaciones y feedback visual
