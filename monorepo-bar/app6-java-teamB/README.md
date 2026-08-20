# App6 - Java - TeamB (Lightweight)

## Description
Lightweight product catalog REST API service with custom CI/CD pipeline.

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
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{category}` - Get products by category
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/health` - Health check

## Running Locally
```bash
mvn spring-boot:run
```

## Running Tests
```bash
mvn test jacoco:report
```

## CI/CD
Uses **custom pipeline** (not guild-recommended) with:
- Custom build validation
- Separate compilation and test steps
- Coverage threshold enforcement (60%)
- Small resource allocation
