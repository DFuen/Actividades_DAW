# 🎮 Modo Minecraft - Mecha-Craft Arena

## 🌟 Descripción
El juego ahora funciona como un **tower defense estilo Minecraft** donde debes:
- ⛏️ **Minar recursos** de bloques del mapa
- 🏗️ **Construir estructuras defensivas** para proteger tu base
- 🔫 **Eliminar enemigos a distancia** con tu arma
- 🏰 **Defender tu base** de oleadas de enemigos

## 🎯 Objetivo
**¡Sobrevivir el mayor número de oleadas posible!** Los enemigos se mueven 1 cuadrado por turno hacia tu base. Si destruyen tu base, pierdes.

## ⌨️ Controles

### Movimiento
- **WASD** o **Flechas**: Mover el robot 1 casilla
- El jugador solo puede moverse a casillas de césped (no puede atravesar muros, torres, agua, etc.)

### Acciones
- **E**: Minar bloque adyacente (madera, piedra, hierro, oro)
- **Espacio**: Disparar al enemigo más cercano (rango 8 casillas)
- **Click en casilla de césped**: Construir estructura seleccionada

## 🧱 Tipos de Bloques

| Icono | Nombre | Descripción | Recurso |
|-------|--------|-------------|---------|
| 🟩 | Césped | Terreno básico donde puedes construir | - |
| 🟫 | Madera | Bloque minable (3 golpes) | +2 Madera |
| ⬜ | Piedra | Bloque minable (5 golpes) | +2 Piedra |
| ⬛ | Hierro | Bloque minable (7 golpes) | +1 Hierro |
| 🟨 | Oro | Bloque minable (10 golpes) | +1 Oro + 50 Créditos |
| 🟦 | Agua | Bloqueo natural, no atravesable | - |

## 🏗️ Estructuras Construibles

### 🏰 Base Principal (Ya colocada)
- **Costo**: 20 Madera, 15 Piedra
- **Vida**: 100 HP
- **Función**: ¡Si es destruida, pierdes!

### 🗼 Torre de Defensa
- **Costo**: 10 Madera, 10 Piedra, 5 Hierro
- **Vida**: 50 HP
- **Daño**: 15
- **Rango**: 5 casillas
- **Función**: Dispara automáticamente a enemigos cercanos

### 🧱 Muro Defensivo
- **Costo**: 5 Piedra
- **Vida**: 30 HP
- **Función**: Bloquea el paso de enemigos (pero los retrasa)

## 👾 Sistema de Enemigos

### Movimiento
- Los enemigos se mueven **1 cuadrado por turno**
- Se mueven hacia la base usando el camino más corto
- **NO pueden atravesar** muros ni agua
- Atacan la base cuando la alcanzan

### Oleadas
- Cada oleada tiene más enemigos y son más fuertes
- Oleada 1: 3 enemigos (20 HP, 5 daño)
- Oleada 2: 5 enemigos (30 HP, 7 daño)
- Oleada 3: 7 enemigos (40 HP, 9 daño)
- Y así sucesivamente...

## 🎯 Combate a Distancia

### Disparo del Jugador
- Presiona **Espacio** para disparar
- Rango: 8 casillas
- Daño: 20
- Dispara automáticamente al enemigo más cercano

### Torres Automáticas
- Disparan automáticamente cada ciertos intervalos
- Atacan al enemigo más cercano en su rango (5 casillas)
- Daño: 15 por disparo

## 📊 Sistema de Puntuación

- **+10 puntos** por cada estructura construida
- **+10 × Oleada** por cada enemigo eliminado
- **+25 créditos** por enemigo eliminado
- Los créditos se pueden usar en la tienda (futura expansión)

## 💡 Estrategias

### Inicio del Juego
1. Mina madera y piedra cerca de tu base
2. Construye muros formando un embudo hacia tu base
3. Coloca torres en posiciones estratégicas

### Defensa Avanzada
1. Crea un laberinto con muros para ralentizar enemigos
2. Coloca torres en las esquinas para máxima cobertura
3. Mantén recursos de sobra para reparar/reconstruir

### Recursos
- **Oro es valioso**: Además del oro, te da créditos
- **Hierro es escaso**: Úsalo solo en torres
- **Piedra es versátil**: Úsala para muros y torres
- **Madera es abundante**: Mina cerca de tu base

## 🎨 Interfaz

### Barra Superior
- **Inventario de Recursos**: Madera, Piedra, Hierro, Oro, Créditos
- **Estadísticas**: Oleada actual, Puntos, Vida de la Base

### Panel Lateral
- **Menú de Construcción**: Selecciona estructuras para construir
- **Controles**: Referencia rápida de teclas
- **Game Over**: Aparece cuando pierdes

## 🚀 Consejos Pro

1. **No esperes a la oleada**: Construye defensas durante el combate
2. **Torres en alto**: Colócalas alejadas de los enemigos
3. **Laberintos funcionan**: Los enemigos siguen el camino más corto
4. **Dispara constantemente**: Ayuda a tus torres eliminando enemigos
5. **Prioriza el hierro**: Las torres son tu mejor defensa

## 🔄 Reinicio
Si pierdes (base destruida), puedes:
- **Reintentar**: Botón en el panel de Game Over
- **Volver al Menú**: Router link al menú principal

## 🎮 Disfruta Jugando!
Este modo combina la estrategia de tower defense con la recolección de recursos estilo Minecraft. ¡Defiende tu base el mayor tiempo posible!

---
**Nota**: Los enemigos SIEMPRE se mueven 1 casilla por turno, nunca más. Esto hace que la estrategia de colocar muros y torres sea crucial para sobrevivir.
