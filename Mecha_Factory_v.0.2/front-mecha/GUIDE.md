# 📖 Guía de Uso - Mecha-Factory Arena

## 🎮 Cómo Jugar

### Página de Inicio
- Bienvenida a Mecha-Factory Arena
- Explicación de las características principales
- Botón para comenzar en el taller

### El Taller (The Garage)

#### Panel de Control
1. **Nombre del Robot**: Ingresa un nombre único para tu robot (3-50 caracteres)
2. **Presupuesto**: 
   - Total: 1000 créditos
   - Gastado: Suma de precios de piezas
   - Disponible: Lo que queda por gastar

#### Construcción del Robot
1. Selecciona **Cabeza** (Head) - 3 opciones
2. Selecciona **Cuerpo** (Body) - 3 opciones
3. Selecciona **Extremidades** (Limbs) - 3 opciones
4. Selecciona **Arma** (Weapon) - 3 opciones

#### Sistema de Rareza
- 🔷 **Común** (Common): Básico, económico
- 🔶 **Infrecuente** (Uncommon): Mejor balance
- 🔵 **Raro** (Rare): Más potente
- 💜 **Épico** (Epic): Muy poderoso

#### Estadísticas

Cada pieza contribuye a tres estadísticas principales:

**❤️ Salud (Health)**
- Puntos de vida totales del robot
- Al llegar a 0, pierdes la batalla
- Influenciada principalmente por Head y Body

**⚔️ Ataque (Attack)**
- Daño base que inflige cada turno
- Multiplica por un factor el daño calculado
- Influenciada principalmente por Weapon

**⚡ Velocidad (Speed)**
- Aumenta probabilidad de golpe crítico
- Velocidad = Speed/100 = Probabilidad crítico
- Influenciada principalmente por Limbs

#### Previsualización en Tiempo Real
- Tu robot se muestra con todas las partes seleccionadas
- Los íconos verdes indican partes seleccionadas
- Los íconos rojos indican partes faltantes

#### Guardado
- **Guardar y Continuar**: Guarda tu robot y ve a la Arena
  - Solo disponible si el robot está completo
  - Solo disponible si tienes presupuesto disponible
- **Reiniciar**: Borra todo y comienza de nuevo

### La Arena (The Arena)

#### Inicio de Batalla
Al llegar a la Arena, automáticamente comienza una batalla contra la IA.

#### Interfaz de Combate

**Panel de Combate**:
- Barra de salud de tu robot (verde)
- Barra de salud de la IA (roja)
- Estadísticas de ambos lados

**Registro de Batalla**:
- Historial de todas las acciones
- Acciones del jugador en verde
- Acciones de la IA en rojo
- Muestra daño infligido

#### Acciones Disponibles

**⚔️ ATACAR**
- Inflige daño basado en tu estadística de Ataque
- Daño = tu Ataque + modificador aleatorio - defensa enemiga
- Puedes obtener golpe crítico (1.5x daño)
- Solo disponible en tu turno

**💊 CURARSE**
- Recuperas salud basado en tu Ataque * 0.5 + 10
- No puedes curar más allá de tu salud máxima
- Solo disponible en tu turno

**Indicador de Turno**:
- 🎮 TU TURNO: Espera a que selecciones una acción
- 🤖 TURNO DE LA IA: Espera a que la IA ataque/se cure

#### Estrategia de la IA

La IA sigue este algoritmo:
1. Si su salud es < 30% de su máximo → Se cura
2. Si tu salud es < 30 → Ataca agresivamente
3. En otros casos → Decide aleatoriamente

#### Fin de Batalla

**¡Victoria!** 🏆
- Tu robot ha derrotado a la IA
- Se muestra el número de turnos jugados
- Se muestra el daño total infligido
- Opciones:
  - 🔄 Reintentar: Nueva batalla con el mismo robot
  - 🏗️ Volver al Taller: Modificar robot
  - 🏠 Ir a Inicio: Volver a la página de inicio

**Derrota** 💀
- Tu robot ha sido derrotado
- Se muestra el número de turnos jugados
- Se muestra el daño total infligido
- Opciones:
  - 🔄 Reintentar: Nueva batalla con el mismo robot
  - 🏗️ Volver al Taller: Mejorar robot
  - 🏠 Ir a Inicio: Volver a la página de inicio

## 💡 Consejos de Estrategia

### Construcción del Robot

**Para Combatientes Equilibrados**:
- Head: Tactical (raro)
- Body: Standard (infrecuente)
- Limbs: Standard (común)
- Weapon: Laser Cannon (raro)
- Total: 550 créditos

**Para Combatientes Agresivos**:
- Head: Basic (común)
- Body: Light (común)
- Limbs: Powerful (raro)
- Weapon: Plasma Rifle (épico)
- Total: 605 créditos

**Para Defensores**:
- Head: Armored (infrecuente)
- Body: Heavy Armor (raro)
- Limbs: Agile (infrecuente)
- Weapon: Missile Launcher (infrecuente)
- Total: 520 créditos

### Durante el Combate

1. **Primeros Turnos**: Ataca para reducir la salud enemiga
2. **Estrategia Defensiva**: Cúrate cuando tengas < 50% de salud
3. **Golpe de Gracia**: Termina al enemigo cuando tiene baja vida
4. **Monitoreo**: Observa el patrón de la IA

## 🔧 Controles

### Navegación
- Haz clic en los enlaces de navegación en la barra superior
- Usa los botones "Continuar" o "Volver" en cada vista
- Presiona el navegador para volver atrás

### Selección
- Haz clic en una tarjeta de pieza para seleccionarla
- Haz clic nuevamente para deseleccionarla
- Las piezas seleccionadas se resaltan en azul

### Acciones
- Haz clic en los botones grandes para realizar acciones
- Los botones deshabilitados no pueden ser clicados
- Las transiciones de botones son suaves

## 📱 Compatibilidad

- **Computadoras**: Pantalla completa óptima
- **Tablets**: Interfaz adaptable
- **Teléfonos**: Versión responsiva (parcial)

## 🎯 Logros Personales

Aunque no están implementados en esta versión, aquí hay metas sugeridas:

- 🥇 Primera victoria: Gana tu primer combate
- 🥈 Campeón: Gana 5 combates seguidos
- 🥉 Ingeniero: Construye 10 robots diferentes
- 💎 Coleccionista: Usa todas las piezas al menos una vez
- ⚡ Relámpago: Gana una batalla en menos de 5 turnos
- 🛡️ Tanque: Gana una batalla terminando con > 80% salud

## ⚠️ Notas Importantes

1. **Presupuesto Limitado**: Solo puedes gastar máximo 1000 créditos
2. **Robot Completo**: Debes seleccionar todas las 4 partes
3. **Guardado Local**: Tus robots se guardan en el navegador
4. **Limpieza de Datos**: Si limpias el cache, perderás tus robots guardados
5. **Sin Conexión**: Funciona completamente sin internet

## 🐛 Solución de Problemas

**El botón "Guardar y Continuar" está deshabilitado**
- Asegúrate de haber seleccionado las 4 partes
- Verifica que el presupuesto disponible sea ≥ 0

**La barra de La Arena dice "deshabilitada"**
- Construye y guarda un robot en el Taller primero
- Regresa a El Taller y presiona "Guardar y Continuar"

**No veo mis cambios**
- Espera unos segundos para que se actualicen
- Recarga la página si algo se ve mal

**Perdí mi robot**
- Los robots se guardan en localStorage del navegador
- Limpiar datos o cambiar de navegador perderá los datos
- Construye uno nuevo

## 📞 Contacto y Reportes

Para reportar problemas:
1. Abre la consola del navegador (F12)
2. Busca mensajes de error
3. Reporta el error y los pasos para reproducirlo
