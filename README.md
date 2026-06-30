# 🅿️ SmartPark — Sistema de Estacionamientos Inteligentes

Plataforma backend basada en **microservicios** para la gestión de estacionamientos: ubicaciones, reservas, pagos, vehículos, usuarios, notificaciones y revisiones.

Proyecto desarrollado para la asignatura **DSY1103 - Desarrollo FullStack I** (Duoc UC).

---

## 🧱 Arquitectura

El sistema sigue una arquitectura de **microservicios independientes** que se comunican vía REST y se registran en un **servidor Eureka** (descubrimiento de servicios). Un **API Gateway** centraliza el acceso y enruta las peticiones hacia cada microservicio usando balanceo de carga.

```
                    ┌─────────────────┐
   Cliente  ──────▶ │   API Gateway   │  (puerto 8080)
                    └────────┬────────┘
                             │ enruta con lb://
                             ▼
                    ┌─────────────────┐
                    │  Eureka Server  │  (puerto 8761)
                    │  (registro)     │
                    └────────┬────────┘
                             │ todos los ms se registran aquí
        ┌────────────────────┼────────────────────┐
        ▼                    ▼                     ▼
   ms-ubicacion        ms-estacionamiento      ms-reserva   ... etc
```

### Microservicios y puertos

| Módulo               | Puerto | Descripción                                    |
|----------------------|--------|------------------------------------------------|
| `eureka-server`      | 8761   | Servidor de registro y descubrimiento          |
| `api-gateway`        | 8080   | Puerta de entrada única (enrutamiento)          |
| `ms-ubicacion`       | 8085   | Ubicaciones geográficas                         |
| `ms-estacionamiento` | 8086   | Estacionamientos disponibles                    |
| `ms-usuario`         | 8087   | Usuarios de la plataforma                       |
| `ms-pago`            | 8088   | Pagos y tipos de pago                           |
| `ms-reserva`         | 8089   | Reservas de estacionamiento                     |
| `ms-notificacion`    | 8090   | Notificaciones a usuarios                       |
| `ms-revision`        | 8091   | Reseñas y calificaciones                        |
| `ms-admin`           | 8092   | Acciones administrativas                        |
| `ms-auth`            | 8093   | Registro e inicio de sesión                     |
| `ms-vehiculo`        | 8094   | Vehículos de los usuarios                       |

---

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 4.0.6** / **Spring Cloud 2025.1.1**
- **Spring Data JPA** + **MySQL**
- **Spring Cloud Gateway** (API Gateway)
- **Spring Cloud Netflix Eureka** (descubrimiento de servicios)
- **WebClient** (comunicación entre microservicios)
- **JUnit 5 + Mockito** (pruebas unitarias)
- **springdoc-openapi / Swagger UI** (documentación)
- **Docker / Docker Compose** (despliegue)
- **Lombok** (reducción de boilerplate)

---

## ▶️ Cómo ejecutar (local)

> Requisitos: Java 21, Maven (incluido vía `mvnw`) y MySQL corriendo en `localhost:3306`.

**El orden importa:** primero Eureka, luego los microservicios.

```bash
# 1. Levantar el servidor Eureka
cd eureka-server
./mvnw spring-boot:run

# 2. En otra terminal, levantar el API Gateway
cd api-gateway
./mvnw spring-boot:run

# 3. Levantar los microservicios que necesites (cada uno en su terminal)
cd ms-ubicacion
./mvnw spring-boot:run
```

Una vez arriba, el dashboard de Eureka muestra los servicios registrados:
👉 **http://localhost:8761**

> 💡 Con 8 GB de RAM se recomienda levantar solo los microservicios que vayas a probar, no los 11 a la vez.

---

## 🧪 Pruebas unitarias

Cada microservicio incluye pruebas unitarias con **JUnit 5 + Mockito** que validan la lógica de negocio mockeando los repositorios (no requieren base de datos).

Ejecutar las pruebas de un microservicio:

```bash
cd ms-usuario
./mvnw test
```

- **Pruebas de servicio** (`*ServiceTest`): usan `@ExtendWith(MockitoExtension.class)`, mockean el repositorio con `@Mock` e inyectan el servicio con `@InjectMocks`.
- **Pruebas de controlador** (`*ControllerTest`): usan `@WebMvcTest` con `MockMvc` y reemplazan el servicio con `@MockitoBean`.

---

## 📖 Documentación (Swagger)

Con un microservicio levantado, su documentación interactiva está en:

```
http://localhost:<puerto>/doc/swagger-ui.html
```

Ejemplo: http://localhost:8085/doc/swagger-ui.html (ms-ubicacion)

---

## 🐳 Despliegue con Docker

El proyecto incluye un `Dockerfile` por módulo y un `docker-compose.yml` en la raíz.

Levantar la base de datos MySQL + un microservicio (ejemplo, ms-ubicacion):

```bash
docker compose up mysql ms-ubicacion
```

Para reconstruir tras cambios en el código:

```bash
docker compose up --build mysql ms-ubicacion
```

Detener todo:

```bash
docker compose down
```

> El MySQL del contenedor se expone en el puerto **3307** (para no chocar con un MySQL/XAMPP local en 3306).

---

## 📂 Estructura de un microservicio

```
ms-ejemplo/
├── src/main/java/com/smartpark/ms_ejemplo/
│   ├── controller/   → endpoints REST
│   ├── service/      → lógica de negocio
│   ├── repository/   → acceso a datos (JPA)
│   ├── model/        → entidades
│   ├── dto/          → objetos de transferencia (Request/Response)
│   ├── config/       → SwaggerConfig, DataInitializer
│   └── exception/    → GlobalExceptionHandler
├── src/test/java/... → pruebas unitarias
├── src/main/resources/application.yml
├── Dockerfile
└── pom.xml
```

Patrón usado: **Controller → Service → Repository** con **DTOs** para entrada/salida.

---

## 👥 Equipo y gestión

- **Repositorio GitHub:** https://github.com/NicoRojasBarria/smartpark
- **Gestión de tareas:** Trello

---

_Proyecto académico — Duoc UC, 2025._
