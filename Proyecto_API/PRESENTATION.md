# 🎓 Instrucciones de Presentación

## Preparación para la Presentación Final (27 de Febrero, 2026)

Este documento contiene todo lo necesario para presentar exitosamente el proyecto "Mecha-Factory Arena" ante la clase.

---

## 📝 Estructura de la Presentación (30 minutos)

### Distribución de Tiempo
- **Introducción**: 2 minutos
- **Demo del Taller**: 8 minutos
- **Demo de la Arena**: 8 minutos
- **Arquitectura Técnica**: 7 minutos
- **Preguntas**: 5 minutos

---

## 1️⃣ INTRODUCCIÓN (2 minutos)

### Guión
"Bienvenidos a Mecha-Factory Arena, un proyecto web completo desarrollado con Vue 3.

En esta aplicación pueden construir robots personalizados seleccionando diferentes componentes, gestionar un presupuesto de 1000 créditos, y luego enfrentarse a una IA en combates por turnos.

Este proyecto cubre el 100% del temario de Desarrollo Web con Vue.js, incluyendo componentes, estado global, routing, TypeScript y testing."

### Diapositiva Sugerida
```
╔════════════════════════════════════════╗
║     🤖 MECHA-FACTORY ARENA             ║
║                                        ║
║  Single Page Application con Vue 3     ║
║  + TypeScript + Pinia + Vue Router     ║
║                                        ║
║  Construcción de Robots │ Combate IA   ║
╚════════════════════════════════════════╝
```

---

## 2️⃣ DEMO DEL TALLER (8 minutos)

### Paso a Paso

**1. Página de Inicio (1 minuto)**
```
Mostrar:
- Presentación bonita
- Las 3 características principales
- Botón "Comienza a Construir"
```

**2. Entrar al Taller (1 minuto)**
```
Mostrar:
- Panel de control con presupuesto
- Previsualización del robot a la izquierda
- Selector de piezas a la derecha
- Explicar las 4 categorías
```

**3. Construir un Robot (5 minutos)**
```
PASO 1: Nombrar el robot
  - "Mi Mecha Destructor"
  - Mostrar validación

PASO 2: Seleccionar Cabeza
  - Hacer clic en "Tactical Head" (Raro)
  - Mostrar:
    * Tarjeta resaltada en azul
    * Estadísticas añadidas
    * Presupuesto actualizado
    * Preview actualizado

PASO 3: Seleccionar Cuerpo
  - Hacer clic en "Heavy Armor" (Raro)
  - Mostrar cambios en tiempo real

PASO 4: Seleccionar Extremidades
  - Hacer clic en "Powered Limbs" (Raro)
  - Mostrar cálculo de velocidad total

PASO 5: Seleccionar Arma
  - Hacer clic en "Plasma Rifle" (Épico)
  - Mostrar ahora está disponible el botón "Guardar"

CONCLUSIÓN:
  - Robot completamente construido
  - Mostrar estadísticas finales
  - Presupuesto disponible
```

**Palabras Clave a Mencionar**:
- "Presupuesto limitado de 1000 créditos"
- "Cada pieza afecta las estadísticas"
- "Actualización en tiempo real"
- "Diseño responsivo"

---

## 3️⃣ DEMO DE LA ARENA (8 minutos)

### Paso a Paso

**1. Entrar a la Arena (1 minuto)**
```
Mostrar:
- Transición desde el Taller
- Interface de batalla
- Tu robot vs IA
```

**2. Explorar HUD de Batalla (2 minutos)**
```
Mostrar y explicar:
- Barra de salud del jugador (verde)
- Barra de salud de la IA (roja)
- Indicador de turno
- Registro de batalla
- Botones: ATACAR y CURARSE
```

**3. Realizar Batalla (5 minutos)**
```
TURNO 1:
  - Clic en ATACAR
  - Mostrar: "¡Ataque normal! Infligiste 18 de daño"
  - Barra de IA se reduce
  - Cambio a turno IA
  - IA ataca con efecto pequeño
  - Barra de jugador se reduce

TURNO 2-3:
  - Hacer 2-3 ataques más
  - En el medio, mostrar CURARSE
  - Explicar que la IA también se cura

DURANTE LA BATALLA:
  - Mencionar: "La IA toma decisiones inteligentes"
  - Mencionar: "Sistema de golpes críticos"
  - Mostrar registro actualizado en tiempo real

FINAL DE BATALLA:
  - Esperar victoria
  - Mostrar pantalla de resultados
  - Mostrar estadísticas (turnos, daño total)
  - Botones para reintentar
```

**Palabras Clave a Mencionar**:
- "Combate por turnos"
- "IA adaptativa"
- "Cálculo de daño dinámico"
- "Sistema de golpes críticos"
- "Interfaz reactiva"

---

## 4️⃣ ARQUITECTURA TÉCNICA (7 minutos)

### Diapositivas Sugeridas

**Diapositiva 1: Stack Tecnológico**
```
┌─────────────────────────────────┐
│        TECH STACK              │
├─────────────────────────────────┤
│ Frontend:                       │
│ • Vue 3 (Composition API)       │
│ • TypeScript Estricto           │
│ • Pinia (State Management)      │
│ • Vue Router (SPA)              │
│                                 │
│ Tooling:                        │
│ • Vite (Build)                  │
│ • Vitest (Testing)              │
│ • Axios (HTTP)                  │
└─────────────────────────────────┘
```

**Diapositiva 2: Estructura de Carpetas**
```
src/
├── components/          (Componentes reutilizables)
├── views/              (Páginas)
├── stores/             (Pinia - Estado global)
├── router/             (Vue Router)
├── services/           (APIs y servicios)
├── types/              (Tipos TypeScript)
├── utils/              (Funciones compartidas)
├── composables/        (Lógica reutilizable)
├── assets/             (Estilos globales)
└── data/               (JSON de piezas)
```

**Diapositiva 3: Flujo de Datos**
```
Usuario Interactúa
    ↓
Componente (Vue)
    ↓
Emisión de Evento (emit)
    ↓
Store (Pinia)
    ↓
Actualización de Estado
    ↓
Componentes se Actualizan (Reactividad)
    ↓
UI Refleja Cambios
```

**Diapositiva 4: Componentes**
```
Componentes Principales:
• PartCard: Tarjeta de pieza seleccionable
• RobotPreview: Previsualización del robot
• StatBar: Barra de estadísticas animada
• BattleHUD: Interface de combate
• Button, Spinner: Componentes UI reutilizables

Total: 13 componentes Vue
```

**Diapositiva 5: Funcionalidades Clave**
```
✅ Construcción modular de robots
✅ Gestión de presupuesto dinámico
✅ Cálculo de estadísticas en tiempo real
✅ Combate por turnos con IA
✅ Persistencia en localStorage
✅ Navegación protegida con Guards
✅ 100% tipado con TypeScript
```

**Diapositiva 6: Temario Cubierto**
```
Fundamentos Vue:
✅ Sintaxis de plantilla
✅ Directivas (v-if, v-for, v-on)
✅ Estilos scoped

Arquitectura:
✅ SFC (Single File Components)
✅ Props y Emits
✅ Composables

Reactividad:
✅ ref(), reactive(), computed()

Estado Global:
✅ Pinia Store

Routing:
✅ Vue Router con Guards

Tipado:
✅ TypeScript avanzado

Testing:
✅ Vitest
```

**Diapositiva 7: Métrica de Éxito**
```
Criterios de Evaluación:

📊 Funcionalidad (40/40)    ✅
   • Crear robot
   • Combatir IA
   • Persistencia

💻 Código (30/30)           ✅
   • Vue 3 + TypeScript
   • Estructura limpia
   • Tests

👥 Integración (20/20)      ✅
   • 3 departamentos
   • Comunicación clara
   • Sin conflictos

🎨 Estética (10/10)         ✅
   • Interfaz atractiva
   • Animaciones pulidas

TOTAL: 100/100
```

---

## 5️⃣ RESPUESTAS A PREGUNTAS ANTICIPADAS

### Pregunta 1: "¿Cómo manejas el estado global?"
**Respuesta**: 
"Utilizamos Pinia, que es la librería oficial de estado para Vue 3. Todos los datos del robot, batalla y IA se centralizan en un store, permitiendo que cualquier componente acceda y modifique el estado de forma reactiva sin necesidad de props drilling."

### Pregunta 2: "¿Cómo es la IA?"
**Respuesta**:
"La IA tiene un algoritmo adaptativo. Si su salud es baja, se cura. Si tu salud es crítica, ataca agresivamente. Si no, toma una decisión aleatoria. Todo esto está implementado en funciones calculadas que consideran el estado actual de la batalla."

### Pregunta 3: "¿Cómo validaste el sistema?"
**Respuesta**:
"Implementamos tests unitarios con Vitest que verifican los cálculos de daño, validaciones de robot, y sistema de rareza. También probamos manualmente toda la interfaz para asegurar una experiencia fluida."

### Pregunta 4: "¿Por qué usaste componentes modulares?"
**Respuesta**:
"La modularización permite reutilización de código, mantenimiento más fácil y escalabilidad. Por ejemplo, el componente StatBar se usa en el taller y en la arena sin necesidad de duplicar código."

### Pregunta 5: "¿Cómo persistes los datos?"
**Respuesta**:
"Utilizamos localStorage del navegador para guardar el robot construido. Cuando el usuario presiona 'Guardar y Continuar', los datos se almacenan localmente y se recuperan al volver."

### Pregunta 6: "¿Implementaste TypeScript desde el principio?"
**Respuesta**:
"Sí, TypeScript está configurado en modo estricto. Todos los tipos están explícitamente definidos: Robot, Part, BattleState, etc. Esto nos ayudó a detectar errores tempranamente."

### Pregunta 7: "¿Qué pasa si el usuario intenta ir a la Arena sin robot?"
**Respuesta**:
"Vue Router tiene un Navigation Guard que verifica si el robot está completo. Si no lo está, automáticamente redirige al taller. Es una capa de seguridad transparente."

### Pregunta 8: "¿Cómo fue el proceso de integración entre departamentos?"
**Respuesta**:
"Establecimos interfaces claras: UI proporciona componentes con props/emits, Desarrollo crea la lógica, Sistemas maneja el estado. Cada departamento trabajó de forma relativamente independiente, pero siempre respetando las interfaces acordadas."

---

## 📊 MATERIAL DE PRESENTACIÓN

### Recomendado Traer

1. **Laptop con el proyecto corriendo**
   ```bash
   npm run dev
   # Tener abierto en http://localhost:5174
   ```

2. **Diapositivas con imágenes del proyecto**
   - Screenshot de inicio
   - Screenshot del taller
   - Screenshot de batalla
   - Diagrama de arquitectura

3. **Documentación impresa o digital**
   - README.md
   - ARCHITECTURE.md
   - Checklist de características

4. **Código fuente disponible**
   - Para mostrar ejemplos
   - Para explicar implementación

---

## 🎤 CONSEJOS DE PRESENTACIÓN

### ✅ Hacer
- Hablar con claridad y velocidad moderada
- Hacer pausas entre explicaciones
- Mantener contacto visual con la audiencia
- Responder preguntas con confianza
- Mostrar el código cuando sea relevante
- Demostrar todo funcionando en vivo

### ❌ No Hacer
- Leer directamente de las diapositivas
- Hablar demasiado rápido
- Entrar en detalles técnicos muy profundos sin contexto
- Mostrar código sin explicar
- Perder el tiempo en funcionalidades secundarias

---

## ⏱️ CRONOGRAMA DE PRESENTACIÓN

```
00:00 - 02:00  │ Introducción
02:00 - 10:00  │ Demo del Taller
                ├─ Página Inicio
                ├─ Entrar al Taller
                ├─ Construir Robot
                └─ Guardar y Continuar
10:00 - 18:00  │ Demo de la Arena
                ├─ Entrar a la Arena
                ├─ Explorar Interface
                └─ Realizar Batalla
18:00 - 25:00  │ Arquitectura Técnica
                ├─ Stack Tecnológico
                ├─ Estructura
                ├─ Flujo de Datos
                ├─ Componentes
                ├─ Temario Cubierto
                └─ Logros
25:00 - 30:00  │ Preguntas y Respuestas
```

---

## 🎯 PUNTOS CLAVE A DESTACAR

1. **100% de Temario Cubierto**
   - Todos los temas del curso están implementados

2. **Metodología Empresarial**
   - 3 departamentos especializados
   - Arquitectura profesional

3. **Calidad de Código**
   - TypeScript estricto
   - Testing incluido
   - Sin errores de compilación

4. **Experiencia de Usuario**
   - Interfaz atractiva
   - Animaciones pulidas
   - Intuitivo y fácil de usar

5. **Escalabilidad**
   - Fácil agregar nuevas piezas
   - Fácil agregar nuevas acciones
   - Componentes reutilizables

---

## 📸 CAPTURAS RECOMENDADAS

### Página de Inicio
Mostrar: Diseño bonito, clara propuesta de valor

### Taller
Mostrar: Constructor de robot, actualizaciones en tiempo real

### Arena
Mostrar: Batalla completa con efectos visuales

### Código
Mostrar: 
- Componente Vue bien estructurado
- Store Pinia
- Tipos TypeScript

---

## 🎓 CONCLUSIÓN

Mencionar al finalizar:

"Este proyecto no solo cumple con los requisitos académicos, sino que demuestra una comprensión profunda de Vue 3 y arquitectura de aplicaciones modernas. La implementación incluye best practices profesionales, testing, documentación y una experiencia de usuario pulida.

Estamos orgullosos del resultado y esperamos que les haya gustado. ¡Gracias por la atención!"

---

## ✅ Checklist Final Antes de Presentar

- [ ] Proyecto compilado sin errores
- [ ] Servidor corriendo en http://localhost:5174
- [ ] Laptop conectada a proyector
- [ ] Diapositivas preparadas
- [ ] Documentación a mano
- [ ] He practicado la presentación
- [ ] He respondido mentalmente las preguntas
- [ ] Tengo ejemplos de código listo
- [ ] He probado todas las funciones
- [ ] Tengo respuestas para edge cases

---

**¡Buena suerte en la presentación!** 🚀

---

*Mecha-Factory Arena*
*Presentación Final - 27 de Febrero de 2026*
