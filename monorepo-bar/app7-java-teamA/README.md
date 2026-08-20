# App7 - Java - TeamA (Resource Intensive)

## Description
Resource-intensive analytics service performing complex data processing and aggregation operations.

## Team
TeamA

## Resource Profile
- **Type**: Large (L)
- **CPU**: 1-2 cores
- **Memory**: 1-2 GB
- **Reason**: Complex analytics processing, large dataset operations, intensive computations

## Technologies
- Java 17
- Spring Boot 3.2
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- JaCoCo (code coverage)

## Endpoints
- `GET /api/analytics/` - Service information
- `GET /api/analytics/health` - Health check
- `POST /api/analytics/process` - Process large datasets
- `POST /api/analytics/aggregate` - Aggregate data
- `GET /api/analytics/compute/{iterations}` - Intensive computation

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
- Extended resource allocation
- Separate compilation and test stages
- Performance testing
- Coverage threshold enforcement (65%)
- Large resource allocation for intensive operations
