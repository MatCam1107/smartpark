# 🅿️ SmartPark — Sistema de Gestión de Estacionamientos Inteligentes

Backend desarrollado bajo una arquitectura de **microservicios con Spring Boot**, diseñado para administrar estacionamientos inteligentes mediante la gestión de usuarios, vehículos, reservas, pagos, ubicaciones, revisiones y notificaciones.

Proyecto desarrollado para la asignatura **DSY1103 - Desarrollo FullStack I** de **Duoc UC**.

---

# 📌 Contexto del proyecto

SmartPark es una plataforma que permite administrar de forma eficiente el uso de estacionamientos inteligentes.

La solución fue desarrollada utilizando una arquitectura de microservicios, permitiendo desacoplar cada funcionalidad del sistema en servicios independientes que se comunican mediante REST y son administrados a través de un servidor Eureka.

El sistema implementa autenticación, administración de usuarios, vehículos, reservas, pagos, revisiones, ubicaciones, notificaciones y funciones administrativas, todo centralizado mediante un API Gateway.

---

# 👥 Integrantes

- Nicolás Rojas Barría
- Elvira Andrea San Martín Aguilera

---

# 🧱 Arquitectura del sistema

El proyecto implementa una arquitectura basada en **Spring Cloud**, utilizando:

- Eureka Server para descubrimiento de servicios.
- API Gateway para centralizar todas las solicitudes.
- Comunicación REST entre microservicios.
- Persistencia mediante MySQL.
- Documentación mediante Swagger/OpenAPI.

```
                    Cliente
                       │
                       ▼
              ┌────────────────┐
              │  API Gateway   │
              │    (8080)      │
              └────────┬───────┘
                       │
                lb://Microservicios
                       │
                       ▼
              ┌────────────────┐
              │ Eureka Server  │
              │    (8761)      │
              └────────┬───────┘
                       │
 ┌──────────────┬─────────────┬──────────────┬──────────────┐
 ▼              ▼             ▼              ▼
Auth        Usuario      Reserva       Estacionamiento
 ▼              ▼             ▼              ▼
Pago      Vehículo     Ubicación     Notificación
                ▼
             Revisión
                ▼
             Administración
```

---

# 📦 Microservicios implementados

| Microservicio | Puerto | Función |
|---------------|--------|---------|
| Eureka Server | 8761 | Registro y descubrimiento de servicios |
| API Gateway | 8080 | Punto único de acceso |
| ms-auth | 8093 | Autenticación |
| ms-usuario | 8087 | Gestión de usuarios |
| ms-vehiculo | 8094 | Gestión de vehículos |
| ms-estacionamiento | 8086 | Gestión de estacionamientos |
| ms-reserva | 8089 | Gestión de reservas |
| ms-pago | 8088 | Gestión de pagos |
| ms-ubicacion | 8085 | Gestión de ubicaciones |
| ms-notificacion | 8090 | Gestión de notificaciones |
| ms-revision | 8091 | Gestión de revisiones |
| ms-admin | 8092 | Administración del sistema |

---

# 🔄 Comunicación entre microservicios

Todos los microservicios se registran automáticamente en **Eureka Server**.

El **API Gateway** consulta Eureka para descubrir dinámicamente los servicios disponibles y enrutar las solicitudes mediante balanceo de carga utilizando direcciones del tipo:

```
lb://MS-USUARIO
lb://MS-AUTH
lb://MS-RESERVA
```

La comunicación entre microservicios se realiza mediante **WebClient**, evitando dependencias directas entre ellos.

---

# 🛠 Tecnologías utilizadas

- Java 21
- Spring Boot 4
- Spring Cloud
- Spring Cloud Gateway
- Spring Cloud Netflix Eureka
- Spring Data JPA
- MySQL
- WebClient
- Swagger / OpenAPI
- Docker
- Docker Compose
- Maven
- Lombok
- JUnit 5
- Mockito

---

# ▶️ Ejecución local

## Requisitos

- Java 21
- Maven
- MySQL
- Docker (opcional)

## Orden de ejecución

1. Eureka Server
2. API Gateway
3. Microservicios

Ejemplo:

```bash
cd eureka-server
./mvnw spring-boot:run

cd api-gateway
./mvnw spring-boot:run

cd ms-usuario
./mvnw spring-boot:run
```

Dashboard Eureka:

```
http://localhost:8761
```

---

# ☁️ Despliegue remoto

Para demostrar el funcionamiento de la arquitectura distribuida, parte del proyecto fue desplegado utilizando **Railway**.

Servicios desplegados:

- Eureka Server
- API Gateway
- MySQL
- ms-auth
- ms-usuario

Debido a las limitaciones del plan gratuito de Railway, el resto de los microservicios mantiene la misma configuración y puede desplegarse utilizando el mismo procedimiento.

---

# 🧪 Pruebas unitarias

Cada microservicio incorpora pruebas unitarias utilizando:

- JUnit 5
- Mockito

Las pruebas verifican:

- Servicios
- Controladores
- Lógica de negocio
- Mock de repositorios

Ejecución:

```bash
./mvnw test
```

---

# 📖 Documentación Swagger

Cada microservicio posee documentación OpenAPI.

Swagger local:

```
http://localhost:PUERTO/doc/swagger-ui.html
```

Ejemplo:

```
http://localhost:8085/doc/swagger-ui.html
```

---

# 🐳 Docker

El proyecto incorpora:

- Dockerfile por microservicio.
- docker-compose.yml.

Levantar servicios:

```bash
docker compose up
```

Reconstrucción:

```bash
docker compose up --build
```

Detener:

```bash
docker compose down
```

---

# 📂 Estructura del proyecto

```
smartpark/

api-gateway/

eureka-server/

ms-auth/

ms-usuario/

ms-vehiculo/

ms-estacionamiento/

ms-reserva/

ms-pago/

ms-ubicacion/

ms-notificacion/

ms-revision/

ms-admin/

docker-compose.yml

README.md
```

Todos los microservicios implementan la estructura:

```
Controller
↓
Service
↓
Repository
↓
Entity
```

además de:

- DTO
- Config
- Exception Handler
- SwaggerConfig
- DataInitializer

---

# 📋 Gestión del proyecto

Herramienta utilizada:

- Trello *(agregar enlace)*

---

# 🔗 Repositorio GitHub

Repositorio oficial:

**https://github.com/MatCam1107/smartpark**

---

# 📄 Proyecto académico

Proyecto desarrollado para la asignatura **DSY1103 - Desarrollo FullStack I** de **Duoc UC** utilizando una arquitectura de microservicios basada en Spring Boot y Spring Cloud.