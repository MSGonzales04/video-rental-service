# video-rental-service

# Overview

video-rental-service serves as the core back-end API service for the Video Rental System. It manages essential business domain entities, customer registration workflows, user profile updates, and database persistence using Spring Boot, Spring Data JPA, and Microsoft SQL Server.

# Tech Stack & Prerequisites

Language: Java 17+

Framework: Spring Boot 3.x

Database: Microsoft SQL Server

OR Framework: Spring Data JPA / Hibernate

Build Tool: Maven / Gradle

# Centralized API Response Structure

# Success Response (ApiResponse)

All successful operational endpoints return data wrapped in the ApiResponse schema:

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


# Error Response Schema

Unhandled exceptions and domain validation errors are transformed into standard error structures:

{
  "response": {
    "error": "BAD_REQUEST",
    "status": 400,
    "message": "Customer does not exist",
    "datetime": "2026-10-07T20:56:16.189209"
  }
}


# Customer API Endpoints

# 1. Create Customer

Registers a new customer in the Video Rental System.

Mapping: POST /customer/createCustomer

Headers: Content-Type: application/json

Description: Validates whether a customer with the same First Name, Last Name, and Birth Date already exists in the system before persisting.

Sample Request Payload:

{
  "firstName": "Juan",
  "lastName": "Doe",
  "middleInitial": "A",
  "birthDate": "1995-08-20"
}


Sample Response (201 / 200 OK):

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


Error Responses:

409 Conflict:

{
  "response": {
    "error": "CONFLICT",
    "status": 409,
    "message": "Customer already existing",
    "datetime": "2026-10-07T21:00:12.123456"
  }
}


# 2. Update Customer Details

Updates an existing customer's basic information using their unique public user ID.

Mapping: POST /customer/updateCustomer

Headers:

Content-Type: application/json

public_user_id: <STRING_PUID> (Required)

Description: Fetches the customer record matching public_user_id. Verifies no duplicate records match the updated details before saving.

Sample Request Payload:

{
  "firstName": "Juan",
  "lastName": "Doe Updated",
  "middleInitial": "B",
  "birthDate": "1995-08-20"
}


Sample Response (200 OK):

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


Error Responses:

400 Bad Request (Not Found):

{
  "response": {
    "error": "BAD_REQUEST",
    "status": 400,
    "message": "Customer does not exist",
    "datetime": "2026-10-07T21:05:44.987654"
  }
}


409 Conflict:

{
  "response": {
    "error": "CONFLICT",
    "status": 409,
    "message": "Another customer already exists with the given details",
    "datetime": "2026-10-07T21:10:11.654321"
  }
}


# Configuration

Ensure your application.properties (or application.yml) file specifies the correct dialect for SQL Server integration:

spring.datasource.url=jdbc:sqlserver://<HOST>:<PORT>;databaseName=<DB_NAME>
spring.datasource.username=<USERNAME>
spring.datasource.password=<PASSWORD>

# Hibernate Dialect Configuration for MSSQL
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
