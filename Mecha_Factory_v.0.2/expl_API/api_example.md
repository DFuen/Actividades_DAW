# Ejemplo de uso de la API de partes

## Llamar a la API desde el frontend

Puedes usar Axios para hacer peticiones HTTP al backend Spring Boot. Por ejemplo, para obtener las partes del robot:

```typescript
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api', // Cambia el puerto si tu backend usa otro
  timeout: 5000
})

async function getParts() {
  try {
    const response = await api.get('/parts')
    return response.data // Array de partes
  } catch (error) {
    console.error('Error al obtener partes:', error)
    throw error
  }
}

// Uso en un componente Vue
onMounted(async () => {
  const parts = await getParts()
  console.log(parts)
})
```

## Respuesta de la API

La API devuelve un array de objetos JSON como este:

```json
[
  {
    "id": "head-1",
    "name": "Cabeza Básica",
    "category": "head",
    "rarity": "common",
    "cost": 100,
    "stats": { "health": 10, "attack": 0, "speed": 2 },
    "color": "#9ca3af",
    "description": "Cabeza estándar para robots principiantes."
  },
  ...
]
```

## Recomendaciones
- Asegúrate de que el backend esté corriendo en el puerto correcto.
- Usa la ruta `/api/parts` para obtener las partes.
- Puedes adaptar el ejemplo para otras rutas como `/api/robots` o `/api/battles`.
