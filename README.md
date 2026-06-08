# Customer Account API

REST API for managing customer accounts and financial transactions.

The application allows:

* Creating customer accounts
* Retrieving existing accounts
* Creating financial transactions
* Automatic normalization of transaction amounts according to operation type
* Database versioning with Flyway
* API documentation with Swagger/OpenAPI
* Health monitoring with Spring Boot Actuator
* Containerized execution with Docker and Docker Compose

## Technologies

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Flyway
* Spring Validation
* Springdoc OpenAPI (Swagger)
* JUnit 5
* Mockito
* H2 (Test)
* Docker
* Docker Compose

## Project Structure

```text
src
└── main
    └── java
        ├── controllers
        ├── services
        ├── repositories
        ├── domain
        │   └── enums
        ├── dto
        ├── exceptions
        └── util
    └── resources
        └── db/migration
```

## Business Rules

### Accounts

Each account is uniquely identified by a document number.

Example:

```json
{
  "document_number": "12345678900"
}
```

### Transactions

Supported operation types:

| ID | Operation            |
| -- | -------------------- |
| 1  | PURCHASE             |
| 2  | INSTALLMENT PURCHASE |
| 3  | WITHDRAWAL           |
| 4  | PAYMENT              |

Transaction amount behavior:

| Operation            | Stored Amount |
| -------------------- | ------------- |
| PURCHASE             | Negative      |
| INSTALLMENT PURCHASE | Negative      |
| WITHDRAWAL           | Negative      |
| PAYMENT              | Positive      |

Examples:

Input:

```json
{
  "operation_type_id": 1,
  "amount": 100.00
}
```

Stored:

```json
{
  "amount": -100.00
}
```

Input:

```json
{
  "operation_type_id": 4,
  "amount": 100.00
}
```

Stored:

```json
{
  "amount": 100.00
}
```

## Database

The database schema is managed by Flyway migrations.

### Migration V1

Creates:

* accounts
* operation_types
* transactions

### Migration V2

Seeds operation types:

```text
1 PURCHASE
2 INSTALLMENT PURCHASE
3 WITHDRAWAL
4 PAYMENT
```

## Running with Docker

Open a terminal in the project root directory and run:

Build and start all services:

```bash
docker compose up --build
```

The command starts:

* PostgreSQL
* Customer Account API

After startup:

- Swagger UI: http://localhost:8080/swagger-ui.html
- Health Check: http://localhost:8080/actuator/health
```

Stop containers:

```bash
docker compose down
```

Remove containers and database volume:

```bash
docker compose down -v
```

## Running Locally

Open a terminal in the project root directory and do:

### Requirements

* Java 21
* Maven
* PostgreSQL

### Environment Variables

Create the following environment variables:

Linux / Mac:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/bank_db
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
```

Windows PowerShell:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/bank_db"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="postgres"
```

### Start PostgreSQL

Example:

```bash
docker run --name postgres-db \
-e POSTGRES_DB=bank_db \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-p 5432:5432 \
-d postgres:16
```

### Run Application

```bash
./mvnw spring-boot:run
```

or

```bash
./mvnw clean package
java -jar target/*.jar
```

After startup:

- Swagger UI: http://localhost:8080/swagger-ui.html
- Health Check: http://localhost:8080/actuator/health

## API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

All request and response payloads use snake_case.

Example:

```json
{
  "account_id": 1,
  "operation_type_id": 4,
  "amount": 123.45
}
```

> The Swagger documentation reflects the API contract using snake_case field names.

## Health Check

Health endpoint:

```text
GET /actuator/health
```

Example response:

```json
{
  "status": "UP"
}
```

## Main Endpoints

### Create Account

```http
POST /accounts
```

Request:

```json
{
  "document_number": "12345678900"
}
```

Response:

```json
{
  "account_id": 1,
  "document_number": "12345678900"
}
```

### Get Account

```http
GET /accounts/{accountId}
```

Response:

```json
{
  "account_id": 1,
  "document_number": "12345678900"
}
```

### Create Transaction

```http
POST /transactions
```

Request:

```json
{
  "account_id": 1,
  "operation_type_id": 4,
  "amount": 123.45
}
```

Response:

```json
{
  "transaction_id": 1,
  "account_id": 1,
  "operation_type_id": 4,
  "amount": 123.45
}
```

## Running Tests

Tests use an H2 in-memory database and don't require PostgreSQL.

Run all tests:

```bash
./mvnw test
```

Test coverage includes:

* Controllers
* Services
* Repositories
* Utility classes
* Validation scenarios
* Business rules
* Exception handling

## Architecture Decisions

- Separation between Controller, Service and Repository layers.
- DTO-based API contracts to avoid exposing persistence entities.
- Service contracts (interfaces) to support loose coupling and easier testing.
- Flyway migrations for database version control.
- Centralized exception handling for consistent error responses.
- H2 in-memory database for automated tests, ensuring fast and isolated test execution.
- Containerized execution using Docker and Docker Compose.
- Swagger/OpenAPI for API documentation.
