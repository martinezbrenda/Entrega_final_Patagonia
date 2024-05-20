# Bootcamp Patagonia

## 1. Introducción

### Objetivo del Proyecto
Implementar dos microservicios, donde cada uno de ellos pueda realizar las operaciones CRUD. A su vez, uno de los microservicios debe consumir información generada por el otro y utilizarla a nivel lógico.
Para realizar lo pedido buscamos simular una gestión real de vuelos de una aerolinea desde la api de vuelos.

## 2. Funcionalidades de las APIs

### 2.1. API de Vuelos
- **Creación de Vuelos**
- **Modificación de Vuelos**
- **Eliminación de Vuelos**
- **Consulta de Vuelos**
- **Agregado de Pasajeros a un Vuelo**
- **Agregado de Tripulantes a un Vuelo**
- **Agregado de Compañías a un Vuelo**

### 2.2. API de Personas
- **Creación de Pasajeros y Tripulantes**
- **Modificación de Información Personal**
- **Eliminación de Registros**
- **Consulta de Personas**

### 2.3. API de Compañías Aéreas
- **Creación de Compañías**
- **Modificación de Información de la Compañía**
- **Eliminación de Compañías**
- **Consulta de Compañías**

## 3. Comunicación entre Microservicios

### Arquitectura de Microservicios
Se escogió el siguiente tipo de arquitectura:
Servicio A --- Feign Client ---> Eureka Server ----> Servicio B

[![](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/brenda-martinez-772132255/)

