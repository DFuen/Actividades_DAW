# 👥 Guía de Departamentos - Metodología Empresarial

Este documento explica cómo el proyecto está dividido para trabajar como una empresa real con 3 departamentos especializados.

## 🏢 Estructura Organizacional

```
┌─────────────────────────────────────────┐
│     Junta Directiva (Proyecto General)  │
├──────────────┬──────────────┬───────────┤
│              │              │           │
▼              ▼              ▼           ▼
UI/UX      Core Logic    Architecture  QA/Testing
```

## 📋 Departamento de COMUNICACIÓN (UI/UX)

### 🎯 Responsabilidad
Identidad visual, experiencia de usuario y biblioteca de componentes gráficos.

### 🛠️ Tecnologías Clave
- CSS Avanzado
- Animaciones
- Slots
- Diseño Responsivo
- Variables CSS

### 📁 Archivos de Responsabilidad

```
src/
├── assets/
│   └── styles/
│       └── global.css              ← PRINCIPAL
├── components/
│   ├── parts/
│   │   ├── PartCard.vue            ← PRINCIPAL
│   │   └── RobotPreview.vue        ← PRINCIPAL
│   ├── ui/
│   │   ├── Button.vue              ← PRINCIPAL
│   │   ├── StatBar.vue             ← PRINCIPAL
│   │   └── Spinner.vue             ← PRINCIPAL
│   └── arena/
│       └── BattleHUD.vue           ← PRINCIPAL
├── views/
│   ├── HomeView.vue                ← PRINCIPAL
│   ├── GarageView.vue              ← SECUNDARIO
│   ├── ArenaView.vue               ← SECUNDARIO
│   └── NotFoundView.vue            ← PRINCIPAL
└── App.vue                         ← SECUNDARIO
```

### 🎨 Tareas Principales

**Fase 1: Maquetación Base**
- [ ] Componentes PartCard y RobotPreview
- [ ] Estilos iniciales
- [ ] Diseño responsive

**Fase 2: Efectos Visuales**
- [ ] Estilos dinámicos por rareza
- [ ] Efectos hover
- [ ] Animaciones de transición

**Fase 3: Componentes UI**
- [ ] Button con variantes
- [ ] StatBar animada
- [ ] Spinner de carga

**Fase 4: Vistas**
- [ ] HomeView: Landing page
- [ ] GarageView: Layout general
- [ ] ArenaView: HUD de batalla
- [ ] NotFoundView: Error page

**Fase 5: Pulido**
- [ ] Responsive design
- [ ] Iconografía final
- [ ] Tipografías y detalles

### 🎯 Criterios de Éxito

✅ Interfaz visualmente coherente
✅ Animaciones suaves y sin lag
✅ Funciona en mobile, tablet, desktop
✅ Accesibilidad básica (contraste, tamaño)
✅ Código CSS organizado y reutilizable

### 💻 Comandos Útiles

```bash
# Desarrollo con hot reload
npm run dev

# Revisar sin cambios
npm run build

# Ver en producción
npm run preview
```

---

## 🔧 Departamento de DESARROLLO (Core Logic)

### 🎯 Responsabilidad
Lógica de negocio, algoritmos de combate, validación de formularios y cálculos matemáticos.

### 🛠️ Tecnologías Clave
- Reactividad avanzada
- Watchers
- Eventos (Emits)
- Manipulación de Arrays/Objetos
- TypeScript

### 📁 Archivos de Responsabilidad

```
src/
├── components/
│   ├── parts/
│   │   ├── PartCard.vue            ← PRINCIPAL
│   │   └── RobotPreview.vue        ← SECUNDARIO
│   ├── ui/
│   │   ├── Button.vue              ← PRINCIPAL
│   │   ├── StatBar.vue             ← PRINCIPAL
│   │   └── Spinner.vue             ← SECUNDARIO
│   └── arena/
│       └── BattleHUD.vue           ← SECUNDARIO
├── composables/
│   └── index.ts                    ← PRINCIPAL
├── utils/
│   └── calculations.ts             ← PRINCIPAL
├── views/
│   ├── GarageView.vue              ← PRINCIPAL
│   └── ArenaView.vue               ← PRINCIPAL
└── stores/
    └── gameStore.ts                ← SECUNDARIO
```

### 🧠 Tareas Principales

**Fase 1: Props y Emits**
- [ ] Configurar defineProps en componentes
- [ ] Implementar emits para selección
- [ ] Validar tipos

**Fase 2: Reactividad**
- [ ] Composable useParts()
- [ ] Composable useRobotStats()
- [ ] Computed properties

**Fase 3: Validaciones**
- [ ] Validar presupuesto
- [ ] Validar nombre robot
- [ ] Validar completitud

**Fase 4: Algoritmos de Combate**
- [ ] Cálculo de daño
- [ ] Sistema de crítico
- [ ] IA decision logic

**Fase 5: Integración**
- [ ] Conectar con store
- [ ] Tests unitarios
- [ ] Balanceo de dificultad

### 🎯 Criterios de Éxito

✅ Toda la lógica de negocio funciona
✅ Cálculos correctos y balanceados
✅ Validaciones robustas
✅ Tests unitarios ≥ 80% coverage
✅ Manejo correcto de edge cases

### 💻 Comandos Útiles

```bash
# Ejecutar tests
npm run test

# Ver tests en UI
npm run test:ui

# Ver coverage
npm run test -- --coverage
```

### 🔢 Especificaciones Matemáticas

**Cálculo de Daño**:
```
daño_base = attacker.attack + random(0, 10)
defensa = defender.health * 0.1
daño_final = max(1, daño_base - defensa)
daño_critico = daño_final * 1.5
```

**Probabilidad de Crítico**:
```
critico_chance = speed / 100
```

---

## 🏗️ Departamento de SISTEMAS (Architecture & Data)

### 🎯 Responsabilidad
Estructura del proyecto, gestión del estado global, enrutamiento, peticiones HTTP y configuración de entorno.

### 🛠️ Tecnologías Clave
- Pinia
- Vue Router
- Axios
- TypeScript
- Vitest

### 📁 Archivos de Responsabilidad

```
src/
├── data/
│   └── parts.json                  ← PRINCIPAL
├── types/
│   └── index.ts                    ← PRINCIPAL
├── services/
│   └── robotService.ts             ← PRINCIPAL
├── router/
│   └── index.ts                    ← PRINCIPAL
├── stores/
│   └── gameStore.ts                ← PRINCIPAL
├── main.ts                         ← PRINCIPAL
└── App.vue                         ← SECUNDARIO
```

### ⚙️ Tareas Principales

**Fase 1: Inicialización**
- [ ] Estructura carpetas
- [ ] Tipos TypeScript base
- [ ] Archivo package.json

**Fase 2: Datos**
- [ ] Estructura parts.json
- [ ] Schema de datos
- [ ] Validación de datos

**Fase 3: Store (Pinia)**
- [ ] Estado robot
- [ ] Estado batalla
- [ ] Acciones y getters

**Fase 4: Router**
- [ ] Rutas básicas
- [ ] Navigation Guards
- [ ] Protección de rutas

**Fase 5: Servicios**
- [ ] Robot service
- [ ] Persistencia localStorage
- [ ] Simulación API

### 🎯 Criterios de Éxito

✅ Arquitectura escalable
✅ Estado centralizado y consistente
✅ Rutas protegidas correctamente
✅ Persistencia funciona
✅ Tipos TypeScript estrictos

### 💻 Comandos Útiles

```bash
# Build de tipo TypeScript
npm run build

# Verificar tipos
npx vue-tsc --noEmit

# Ver arquitectura
npm run build && npm run preview
```

### 📊 Estructura de Datos

**Parts.json**:
```json
[
  {
    "id": "head_basic",
    "name": "Basic Head",
    "category": "head",
    "rarity": "common",
    "cost": 50,
    "stats": {
      "health": 10,
      "attack": 5,
      "speed": 5
    },
    "color": "#808080",
    "description": "Una cabeza robótica estándar."
  }
]
```

**Types**:
```typescript
interface Robot {
  id: string
  name: string
  head: Part | null
  body: Part | null
  limbs: Part | null
  weapon: Part | null
  totalStats: RobotStats
  budget: number
  spentBudget: number
}
```

---

## 🤝 Integración Entre Departamentos

### Comunicación UI ↔ Desarrollo

**Interfaces**:
- UI define componentes con props/emits
- Desarrollo consume y usa los emits
- Props contienen datos tipados

**Ejemplo**:
```vue
<!-- UI: PartCard.vue -->
<template>
  <div @click="$emit('select')">{{ part.name }}</div>
</template>

<!-- Desarrollo: GarageView.vue -->
<PartCard @select="selectPart(category, part)" />
```

### Desarrollo ↔ Sistemas

**Interfaces**:
- Desarrollo crea lógica usando tipos
- Sistemas proporciona store y servicios
- Desarrollo emite cambios al store

**Ejemplo**:
```typescript
// Sistemas: gameStore.ts
const robot = reactive<Robot>({ ... })
function setPart(category, part) { ... }

// Desarrollo: GarageView
const selectPart = (category, part) => {
  gameStore.setPart(category, part)
}
```

### Sistemas ↔ UI

**Interfaces**:
- Sistemas expone store con datos
- UI consume props y muestra datos
- UI emite eventos que disparan acciones

**Ejemplo**:
```vue
<!-- Sistemas: gameStore -->
robot.totalStats = computed(...)

<!-- UI: RobotPreview -->
<template>
  <div>{{ totalStats.health }}</div>
</template>
```

---

## 📅 Timeline de Integración

```
Semana 1: Cada depto trabaja independientemente
  UI: Componentes base
  Desarrollo: Composables y utilidades
  Sistemas: Estructura y tipos

Semana 2: Integración inicial
  UI + Desarrollo: Conectar eventos
  Desarrollo + Sistemas: Store integration
  UI + Sistemas: Props from store

Semana 3: Integración completa
  Todos trabajan juntos
  Testing de integración
  Debugging

Semana 4: Pulido
  Performance
  Bugfixes
  UX final
```

---

## 🎓 Puntos de Aprendizaje por Depto

### UI/UX
- Componentes Vue 3 con slots
- CSS avanzado y animaciones
- Diseño responsivo
- Accesibilidad web

### Desarrollo
- Composition API avanzada
- TypeScript en componentes
- Lógica de negocio
- Testing unitario

### Sistemas
- Pinia state management
- Vue Router avanzado
- TypeScript full-stack
- Arquitectura de aplicaciones

---

## ✅ Checklist de Completitud

### Por Departamento

**UI/UX**
- [ ] Todos los componentes funcionales
- [ ] Estilos responsive
- [ ] Animaciones suaves
- [ ] Sin errores en consola

**Desarrollo**
- [ ] Toda lógica implementada
- [ ] Validaciones correctas
- [ ] Tests ≥ 80%
- [ ] Balanceo correcto

**Sistemas**
- [ ] Routes protegidas
- [ ] Store centralizado
- [ ] Persistencia funciona
- [ ] TypeScript sin errores

### Global
- [ ] Build sin errores
- [ ] Funcionalidad completa
- [ ] Tests pasan
- [ ] Documentación actualizada

---

## 🚀 Despliegue Final

```bash
# Cada depto verifica su parte
npm run build
npm run test
npm run preview

# Integración final
npm run build

# Deploy
npm run build && upload dist/
```

¡Éxito en el proyecto! 🎉
