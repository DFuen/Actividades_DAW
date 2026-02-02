# Reaktor Redes - Network Scanner

API REST de Spring Boot para escaneo de redes locales y detección de dispositivos activos.

## Características

- 🔍 **Escaneo de redes**: Detecta dispositivos activos en la red local
- 🌐 **Información de interfaces**: Lista todas las interfaces de red del sistema
- 📍 **Ping a hosts**: Verifica conectividad con hosts específicos
- 🔌 **Escaneo de puertos**: Detecta puertos abiertos en dispositivos
- ⚡ **Alto rendimiento**: Escaneo paralelo con pool de threads

## Requisitos

- Java 17+
- Maven 3.6+

## Instalación y Ejecución

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La API estará disponible en: `http://localhost:8084`

## Endpoints

### 1. Obtener interfaces de red
```http
GET /api/network/interfaces
```

**Respuesta:**
```json
[
  {
    "name": "eth0",
    "displayName": "Ethernet",
    "addresses": "192.168.1.100"
  }
]
```

### 2. Información de red local
```http
GET /api/network/local-info
```

**Respuesta:**
```json
{
  "localIp": "192.168.1.100",
  "hostname": "PC-LOCAL",
  "networkPrefix": "192.168.1."
}
```

### 3. Escanear red
```http
GET /api/network/scan?networkPrefix=192.168.1.
```

**Respuesta:**
```json
[
  {
    "ip": "192.168.1.1",
    "hostname": "router.local",
    "reachable": true
  },
  {
    "ip": "192.168.1.50",
    "hostname": "192.168.1.50",
    "reachable": true
  }
]
```

### 4. Ping a un host
```http
GET /api/network/ping?host=192.168.1.1
```

**Respuesta:**
```json
{
  "host": "192.168.1.1",
  "reachable": true,
  "responseTime": 15,
  "hostname": "router.local"
}
```

### 5. Escanear puertos
```http
POST /api/network/scan-ports
Content-Type: application/json

{
  "host": "192.168.1.1",
  "ports": [80, 443, 8080, 22, 3306]
}
```

**Respuesta:**
```json
{
  "host": "192.168.1.1",
  "openPorts": [80, 443],
  "totalScanned": 5
}
```

## Configuración

El archivo `application.properties` permite configurar:

- **Puerto del servidor**: `server.port=8084`
- **Nivel de logging**: `logging.level.Reaktor_redes_main=DEBUG`

## Arquitectura

```
Reaktor_Redes/
├── controller/
│   └── NetworkScannerController.java  # Endpoints REST
├── service/
│   └── NetworkScannerService.java     # Lógica de escaneo
└── ReaktorRedesMainApplication.java   # Clase principal
```

## Tecnologías

- Spring Boot 4.0.2
- Java 17
- Lombok
- Maven

## Notas de Seguridad

⚠️ **Importante**: Este servicio debe ejecutarse en redes de confianza. El escaneo de redes sin autorización puede violar políticas de seguridad.

## Licencia

Proyecto educativo del IES Jándula
