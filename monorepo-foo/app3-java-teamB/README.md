# App3 - Java - TeamB (Lightweight)

## Description
Lightweight user management REST API service with in-memory storage.

## Team
TeamB

## Resource Profile
- **Type**: Small (S)
- **CPU**: 250m - 500m
- **Memory**: 256Mi - 512Mi
- **Reason**: Simple CRUD API with minimal processing

## Technologies
- Java 17
- Spring Boot 3.2
- Maven
- JaCoCo (code coverage)

## Endpoints
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `GET /api/users/health` - Health check

## Running Locally
```bash
mvn spring-boot:run
```

## Running Tests
```bash
mvn test jacoco:report
```

## CI/CD
Uses guild-recommended Java pipeline with:
- Small resource allocation
- Maven build and test
- JaCoCo code coverage
