# Lógica de batalla en el backend

## ¿Qué se ha implementado?

- La lógica de batalla ahora está en el backend, en la clase `BattleService`.
- El endpoint `/api/battles/resolve` permite enviar los datos de los robots (player y enemy) y devuelve el resultado de la batalla.

## Ejemplo de uso

### Petición desde el frontend

```typescript
import axios from 'axios'
const api = axios.create({ baseURL: 'http://localhost:8080/api' })

async function resolveBattle(player, enemy) {
  const response = await api.post('/battles/resolve', { player, enemy })
  return response.data // { playerFinalHealth, enemyFinalHealth, winner }
}
```

### Payload de ejemplo

```json
{
  "player": {
    "stats": { "health": 50, "attack": 20 }
  },
  "enemy": {
    "stats": { "health": 40, "attack": 15 }
  }
}
```

### Respuesta

```json
{
  "playerFinalHealth": 35,
  "enemyFinalHealth": 20,
  "winner": "player"
}
```

## Ventajas
- Seguridad y control: la lógica no puede ser manipulada por el usuario.
- El frontend solo envía los datos y muestra el resultado.
- Puedes ampliar la lógica en el backend para batallas más complejas.
