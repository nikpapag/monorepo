# App2 - Java - TeamB (Lightweight)

## Description
Lightweight Java REST API service for basic data processing operations.

## Team
TeamB

## Resource Profile
- **Type**: Small (S)
- **CPU**: 250m - 500m
- **Memory**: 256Mi - 512Mi
- **Reason**: Simple REST API with minimal processing

## Technologies
- Java 17
- Spring Boot 3.2
- Maven
- JaCoCo (code coverage)

## Endpoints
- `GET /api/` - Service information
- `GET /api/health` - Health check
- `POST /api/process` - Process data
- `GET /api/calculate?a=10&b=20` - Perform calculations

## Running Locally
```bash
mvn spring-boot:run
```

## Running Tests
```bash
mvn test jacoco:report
```

## Building
```bash
mvn clean package
```

## CI/CD
Uses guild-recommended Java pipeline with:
- Small resource allocation
- Maven build and test
- JaCoCo code coverage
