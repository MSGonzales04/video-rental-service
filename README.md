# 🎬 video-rental-service

[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Database](https://img.shields.io/badge/Database-MS%20SQL%20Server-blue.svg)](https://www.microsoft.com/sql-server)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> **Back-End API Service for Video Rental System**
>
> A robust RESTful microservice built with **Spring Boot** and **Spring Data JPA** handling core customer identity management, rental processing, duplicate validation, and centralized exception management.

---

## 📋 Table of Contents

- [Architecture & Tech Stack](#-architecture--tech-stack)
- [Centralized API Standard](#-centralized-api-standard)
  - [Success Payload Schema](#success-payload-schema)
  - [Error Payload Schema](#error-payload-schema)
- [REST API Endpoints](#-rest-api-endpoints)
  - [1. Create Customer](#1-create-customer)
  - [2. Update Customer](#2-update-customer)
- [Configuration & Setup](#-configuration--setup)

---

## 🛠 Architecture & Tech Stack

| Component | Technology | Description |
| :--- | :--- | :--- |
| **Language** | Java 17+ | Core programming language |
| **Framework** | Spring Boot 3.x | Application framework & REST controllers |
| **Persistence** | Spring Data JPA / Hibernate 6+ | Data access and object-relational mapping |
| **Database** | MS SQL Server | Relational persistence store |
| **DTO Utility** | Lombok / Jackson | Boilerplate reduction & custom JSON serialization |

---

## 🌐 Centralized API Standard

All endpoints follow uniform response conventions for seamless frontend integration and predictable exception handling.

### Success Payload Schema

Successful operations return HTTP `200 OK` or `201 Created` wrapped inside a standardized `ApiResponse` envelope:

```json
{
  "message": "Customer successfully created",
  "customer": {
    "firstName": "Juan",
    "lastName": "Doe",
    "middleInitial": "A",
    "birthDate": "1995-08-20",
    "createdDate": "2026-10-07T21:19:37.083"
  }
}
```

### Error Payload Schema

Handled and unhandled exceptions are caught by a `@RestControllerAdvice` global handler:

```json
{
  "response": {
    "error": "BAD_REQUEST",
    "status": 400,
    "message": "Customer does not exist",
    "datetime": "2026-10-07T20:56:16.189209"
  }
}
```

---

## 🚀 REST API Endpoints

### 1. Create Customer

Registers a new customer profile after validating unique identity constraints.

- **URL:** `/customer/createCustomer`
- **Method:** `POST`

#### Headers

| Header | Value | Required | Description |
| :--- | :--- | :--- | :--- |
| `Content-Type` | `application/json` | **Yes** | Payload format |

#### Request Body
```json
{
  "firstName": "Juan",
  "lastName": "Doe",
  "middleInitial": "A",
  "birthDate": "1995-08-20"
}
```

<details>
<summary><b>▶ Sample Success Response (200 OK)</b></summary>

```json
{
  "message": "Customer successfully created",
  "customer": {
    "firstName": "Juan",
    "lastName": "Doe",
    "middleInitial": "A",
    "birthDate": "1995-08-20",
    "createdDate": "2026-10-07T21:19:37.083"
  }
}
```
</details>

<details>
<summary><b>▶ Sample Error Response (409 Conflict)</b></summary>

```json
{
  "response": {
    "error": "CONFLICT",
    "status": 409,
    "message": "Customer already existing",
    "datetime": "2026-10-07T21:00:12.123456"
  }
}
```
</details>

---

### 2. Update Customer

Modifies profile details of an existing customer using their unique public user ID.

- **URL:** `/customer/updateCustomer`
- **Method:** `POST`

#### Headers

| Header | Value | Required | Description |
| :--- | :--- | :--- | :--- |
| `Content-Type` | `application/json` | **Yes** | Payload format |
| `public_user_id` | `String` | **Yes** | Unique public customer identifier |

#### Request Body
```json
{
  "firstName": "Juan",
  "lastName": "Doe Updated",
  "middleInitial": "B",
  "birthDate": "1995-08-20"
}
```

<details>
<summary><b>▶ Sample Success Response (200 OK)</b></summary>

```json
{
  "message": "Customer successfully updated",
  "customer": {
    "firstName": "Juan",
    "lastName": "Doe Updated",
    "middleInitial": "B",
    "birthDate": "1995-08-20",
    "createdDate": "2026-10-07T21:19:37.083"
  }
}
```
</details>

<details>
<summary><b>▶ Sample Error Response (400 Bad Request)</b></summary>

```json
{
  "response": {
    "error": "BAD_REQUEST",
    "status": 400,
    "message": "Customer does not exist",
    "datetime": "2026-10-07T21:05:44.987654"
  }
}
```
</details>

<details>
<summary><b>▶ Sample Error Response (409 Conflict)</b></summary>

```json
{
  "response": {
    "error": "CONFLICT",
    "status": 409,
    "message": "Another customer already exists with the given details",
    "datetime": "2026-10-07T21:10:11.654321"
  }
}
```
</details>

---

## ⚙️ Configuration & Setup

> [!IMPORTANT]
> **MSSQL Dialect Setup**: To prevent query syntax errors (`Incorrect syntax near 'limit'`), explicitly configure the SQL Server dialect in your application properties.

Configure your `src/main/resources/application.properties`:

```properties
# DataSource Configuration
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=video_rental_db;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=YourStrongPassword123

# JPA & Hibernate Dialect
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Disable Spring Boot 3 RFC 7807 problem details to use custom exception handler format
spring.mvc.problemdetails.enabled=false
```

---

*Maintained for Video Rental System API Services.*
