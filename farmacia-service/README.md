# Farmacia Service

Microservicio encargado de la gestión de medicamentos y recetas médicas del proyecto SmartHealth.

## Funcionalidades

- Registrar medicamentos
- Consultar medicamentos
- Registrar recetas médicas
- Consultar recetas
- Validar citas mediante Feign Client
- Publicar eventos mediante RabbitMQ
- Registro en Eureka Server

## Tecnologías

- Spring Boot
- Spring Data JPA
- MySQL
- OpenFeign
- Eureka Client
- RabbitMQ
- Lombok

## Endpoints

### Medicamentos

POST /api/medicamentos

GET /api/medicamentos

GET /api/medicamentos/{id}

### Recetas

POST /api/recetas

GET /api/recetas

## Autor

Dany Alzamora