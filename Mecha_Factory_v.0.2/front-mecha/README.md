# 🤖 Mecha-Factory Arena

Una Single Page Application (SPA) interactiva desarrollada con Vue 3, TypeScript, Pinia y Vue Router. El proyecto es una simulación educativa de un entorno de gestión y combate de robots personalizados.

## 📋 Descripción del Proyecto

"Mecha-Factory Arena" es un proyecto académico diseñado para el curso de "Desarrollo Web con Vue.js". La aplicación permite a los usuarios:

1. **Gestionar Recursos**: Administrar un presupuesto ficticio de 1000 créditos
2. **Construir Robots** (The Garage): Personalizar robots seleccionando componentes modulares
   - Cabeza (Head)
   - Cuerpo (Body)
   - Extremidades (Limbs)
   - Arma (Weapon)
3. **Combatir** (The Arena): Enfrentarse a una IA en un sistema de combate por turnos

Cada componente afecta las estadísticas del robot:
- **Salud (Health)**: Puntos de vida del robot
- **Ataque (Attack)**: Daño infligido por turno
- **Velocidad (Speed)**: Influye en probabilidades de crítico

## 🎯 Objetivos Pedagógicos

Este proyecto cubre 100% del temario del curso:

- ✅ **Fundamentos Vue 3**: Sintaxis de plantilla, directivas (v-if, v-for), estilos scoped
- ✅ **Componentes**: SFC (Single File Components) y comunicación Props/Emits
- ✅ **Reactividad**: Composition API (ref, reactive, computed, watch)
- ✅ **Estado Global**: Pinia para gestión centralizada
- ✅ **Enrutamiento**: Vue Router con Navigation Guards
- ✅ **Conectividad**: Axios para peticiones HTTP
- ✅ **TypeScript**: Tipado fuerte en toda la aplicación
- ✅ **Testing**: Unit tests con Vitest
- ✅ **Estilos Avanzados**: CSS personalizado, variables, animaciones

## 📁 Estructura del Proyecto

```
src/
├── assets/
│   └── styles/
│       └── global.css          # Estilos globales y variables CSS
├── components/
│   ├── parts/
│   │   ├── PartCard.vue        # Componente de tarjeta de pieza
│   │   └── RobotPreview.vue    # Previsualización del robot
│   ├── ui/
│   │   ├── Button.vue          # Componente de botón reutilizable
│   │   ├── StatBar.vue         # Barra de estadísticas animada
│   │   └── Spinner.vue         # Indicador de carga
│   └── arena/
│       └── BattleHUD.vue       # Interface de combate
├── composables/
│   └── index.ts                # Composables reutilizables
├── data/
│   └── parts.json              # Base de datos de piezas
├── router/
│   └── index.ts                # Configuración de Vue Router
├── services/
│   └── robotService.ts         # Servicios API y localStorage
├── stores/
│   └── gameStore.ts            # Pinia store centralizado
├── types/
│   └── index.ts                # Interfaces TypeScript
├── utils/
│   └── calculations.ts         # Funciones de cálculo
├── views/
│   ├── HomeView.vue            # Página de inicio
│   ├── GarageView.vue          # Taller de construcción de robots
│   ├── ArenaView.vue           # Arena de combate
│   └── NotFoundView.vue        # Página 404
├── App.vue                     # Componente raíz
└── main.ts                     # Punto de entrada

tests/
└── unit/
    └── calculations.test.ts    # Tests unitarios
```

## 🚀 Instalación y Ejecución

### Requisitos
- Node.js (v16+)
- npm o yarn

### Pasos

1. **Clonar o descargar el proyecto**
   ```bash
   cd mecha-factory-arena
   ```

2. **Instalar dependencias**
   ```bash
   npm install
   ```

3. **Iniciar servidor de desarrollo**
   ```bash
   npm run dev
   ```
   La aplicación estará disponible en `http://localhost:5173`

4. **Compilar para producción**
   ```bash
   npm run build
   ```

5. **Ejecutar tests**
   ```bash
   npm run test
   ```

6. **Ver resultados de tests**
   ```bash
   npm run test:ui
   ```

## 🛠️ Tecnologías Utilizadas

- **Vue 3**: Framework JavaScript progresivo
- **TypeScript**: Lenguaje tipado que compila a JavaScript
- **Vite**: Build tool rápido y moderno
- **Pinia**: Librería para gestión de estado
- **Vue Router**: Sistema de enrutamiento
- **Axios**: Cliente HTTP
- **Vitest**: Framework de testing unitario
- **CSS3**: Animaciones y variables personalizadas

## 📚 Características Principales

### The Garage (El Taller)
- Selección de componentes modulares
- Cálculo automático de estadísticas
- Sistema de presupuesto con límite
- Guardado local del robot construido
- Previsualización en tiempo real

### The Arena (La Arena)
- Combate por turnos contra IA
- Dos acciones disponibles: Atacar o Curarse
- Cálculo de daño basado en estadísticas
- Sistema de golpes críticos
- Registro de batalla detallado
- Persistencia de estado con localStorage

### Características de Calidad
- Navigation Guards para proteger rutas
- Validación de datos con TypeScript
- Tests unitarios para funciones críticas
- Interfaz responsiva
- Animaciones y efectos visuales

## 🎨 Diseño

La interfaz utiliza:
- **Paleta de colores**: Tema oscuro con acentos azules y dorados
- **Tipografía**: Segoe UI para mejor legibilidad
- **Animaciones**: Transiciones suaves y efectos hover
- **Responsividad**: Diseño adaptable a diferentes tamaños de pantalla

## 📊 Datos de Ejemplo

Las piezas disponibles incluyen:

**Cabezas** (Head):
- Basic Head (Común) - 50 Cr
- Armored Head (Infrecuente) - 100 Cr
- Tactical Head (Raro) - 150 Cr

**Cuerpos** (Body):
- Light Frame (Común) - 75 Cr
- Standard Chassis (Infrecuente) - 125 Cr
- Heavy Armor (Raro) - 200 Cr

**Extremidades** (Limbs):
- Agile Limbs (Infrecuente) - 100 Cr
- Standard Limbs (Común) - 60 Cr
- Powered Limbs (Raro) - 180 Cr

**Armas** (Weapon):
- Laser Cannon (Raro) - 200 Cr
- Plasma Rifle (Épico) - 300 Cr
- Missile Launcher (Infrecuente) - 120 Cr

## 🧪 Testing

Incluye tests unitarios para:
- Cálculo de daño en combate
- Estadísticas del robot
- Validación de completitud del robot
- Sistema de rareza de piezas

```bash
npm run test
```

## 📝 Notas de Desarrollo

- El estado global se gestiona completamente con Pinia
- Los datos se persisten en localStorage
- Las peticiones HTTP se simulan con archivos JSON
- TypeScript está configurado con modo estricto
- Todos los componentes usan `<script setup>` de Vue 3

## 👨‍💻 Autores

Proyecto académico desarrollado para el curso de Desarrollo Web con Vue.js (2026)

## 📄 Licencia

Este proyecto es con propósito educativo.
