# Integración de datos del frontend en el backend

## ¿Qué se ha incluido?

- El archivo `parts.json` del frontend se ha copiado y unificado en el backend (`back-mecha/src/main/resources/parts.json`).
- Ahora el backend sirve el catálogo de partes del robot exactamente igual que el frontend, permitiendo que el frontend consuma los datos desde la API.

## ¿Cómo se utiliza?

### Backend
- El endpoint `/api/parts` devuelve el contenido de `parts.json`.
- Puedes añadir o modificar partes en el archivo JSON y el frontend las recibirá automáticamente al hacer peticiones.

### Frontend
- El frontend debe hacer peticiones HTTP (por ejemplo, usando Axios) a `http://localhost:8080/api/parts` para obtener las partes.
- Ejemplo:

```typescript
import axios from 'axios'
const api = axios.create({ baseURL: 'http://localhost:8080/api' })
async function getParts() {
  const response = await api.get('/parts')
  return response.data
}
```

## Ventajas
- Los datos de partes están centralizados en el backend.
- El frontend siempre consume la versión actualizada desde la API.
- Puedes ampliar el JSON o la lógica del backend para nuevas funcionalidades.

## Recomendaciones
- Si añades nuevas partes en el backend, actualiza el archivo `parts.json`.
- Si el frontend necesita otros datos (robots, batallas), puedes seguir el mismo patrón.
