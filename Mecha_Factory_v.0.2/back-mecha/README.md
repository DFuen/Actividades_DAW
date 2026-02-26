# Mecha Factory Backend v0.2

Este proyecto es un backend realizado con Spring Boot (Java 21, Maven, JAR) para la gestión de robots, partes y batallas en la aplicación Mecha Factory.

## Estructura

- `pom.xml`: Configuración Maven y dependencias.
- `src/main/java/com/mechafactory/mecha/MechaFactoryApplication.java`: Clase principal para arrancar el backend.
- `src/main/java/com/mechafactory/mecha/controller/RobotController.java`: Endpoints REST para robots.
- `src/main/java/com/mechafactory/mecha/controller/PartController.java`: Endpoints REST para partes.
- `src/main/java/com/mechafactory/mecha/controller/BattleController.java`: Endpoints REST para batallas.
- `src/main/resources/application.properties`: Configuración del servidor.

## Endpoints principales

### Robots
- `GET /api/robots` — Lista todos los robots
- `POST /api/robots` — Crea un robot
- `GET /api/robots/{id}` — Obtiene un robot por ID

### Partes
- `GET /api/parts` — Lista todas las partes
- `POST /api/parts` — Crea una parte
- `GET /api/parts/{id}` — Obtiene una parte por ID

### Batallas
- `GET /api/battles` — Lista todas las batallas
- `POST /api/battles` — Crea una batalla
- `GET /api/battles/{id}` — Obtiene una batalla por ID

## Funcionamiento

- El backend arranca en el puerto 8080.
- Los datos se almacenan en memoria (listas), ideales para pruebas y desarrollo.
- Puedes consumir los endpoints desde el frontend o herramientas como Postman.

## Compilar y ejecutar

1. Instala Java 21 y Maven.
2. Ejecuta:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. Accede a los endpoints en `http://localhost:8080/api/robots`, `http://localhost:8080/api/parts`, `http://localhost:8080/api/battles`.

## Personalización

Puedes ampliar la lógica, añadir persistencia (base de datos), seguridad, o nuevas funcionalidades según las necesidades del proyecto.
