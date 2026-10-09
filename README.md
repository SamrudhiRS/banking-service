# Banking Service

A REST API for a simple banking domain, built with **Java 17, Spring Boot 3, Spring Data JPA (Hibernate), Spring Security and OpenAPI/Swagger**.
It manages customers, accounts, credit/debit cards and transactions, and keeps account balances consistent when money moves.

## Features

- CRUD APIs for customers, accounts, credit cards, debit cards and users
- Deposit / withdraw transactions made with a credit or debit card, with automatic balance update
- Money handled with `BigDecimal` (no floating-point rounding errors)
- Transaction creation and balance update run in **one database transaction** (`@Transactional`): if a withdrawal fails for insufficient balance, nothing is saved
- HTTP Basic authentication with **BCrypt-hashed passwords**; only user registration and API docs are public
- Bean validation, centralised error handling (`@RestControllerAdvice` returning `ProblemDetail`)
- OpenAPI/Swagger documentation
- Unit tests (JUnit 5, Mockito) and controller tests (MockMvc)
- Runs with in-memory H2 by default, or MySQL via Docker Compose
- GitHub Actions CI pipeline

## Architecture

```
Controller  ->  Service  ->  DAO service  ->  Repository (Spring Data JPA)  ->  Database
   |              |
  DTO  <-  Mapper  ->  Entity
```

| Layer | Responsibility |
|-------|----------------|
| `controller` | HTTP endpoints, request validation |
| `service` | Business rules (e.g. balance checks) |
| `dao.service` / `repository` | Data access |
| `dto` / `mapper` | API models and conversion to/from entities |
| `exception` | Custom exceptions and global handler |
| `config` | Spring Security configuration |

ER diagrams: `entity_er.png`, `bank_db_er.png`, `project_flow_er.png`.

## Getting started

Requirements: Java 17+ and Maven 3.9+.

```bash
mvn spring-boot:run
```

The API starts on **http://localhost:9096**.

- Swagger UI: http://localhost:9096/swagger-ui/index.html
- H2 console (default profile only): http://localhost:9096/h2-console (JDBC URL `jdbc:h2:mem:bank_db`, user `sa`, empty password)

### Run with MySQL (Docker Compose)

```bash
docker compose up --build
```

This starts MySQL 8 and the application with the `mysql` profile; data is stored in a Docker volume.

### Run the tests

```bash
mvn test
```

## Using the API

1. Register a user (public endpoint):

```bash
curl -X POST http://localhost:9096/api/userService/v1/users \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123","active":true,"role":"ROLE_ADMIN"}'
```

2. Call any other endpoint with HTTP Basic auth:

```bash
curl -u admin:admin123 http://localhost:9096/api/accountService/v1/accounts
```

3. Create a transaction (use either `creditCardId` or `debitCardId`):

```bash
curl -u admin:admin123 -X POST http://localhost:9096/api/transactionService/v1/transactions \
  -H "Content-Type: application/json" \
  -d '{"transactionAmount": 250.00, "transactionType": "WITHDRAW", "debitCardId": 1}'
```

### Endpoints

| Resource | Base path |
|----------|-----------|
| Customers | `/api/customerService/v1/customers` |
| Accounts | `/api/accountService/v1/accounts` |
| Credit cards | `/api/creditCardService/v1/creditCards` |
| Debit cards | `/api/debitCardService/v1/debitCards` |
| Transactions | `/api/transactionService/v1/transactions` |
| Users | `/api/userService/v1/users` |

Full request/response schemas are available in Swagger UI.

## Design decisions

- **`BigDecimal` for money** - avoids rounding errors that `double` introduces.
- **`@Transactional` on transaction creation** - saving the transaction and updating the balance succeed or fail together.
- **BCrypt password hashing** - passwords are never stored or returned in plain text.
- **Secure by default** - every endpoint requires authentication except user registration, health check and API docs.

## Possible improvements

- JWT authentication and role-based access (`@PreAuthorize`)
- Pagination and filtering on list endpoints
- Database migrations with Flyway
- Integration tests with Testcontainers
