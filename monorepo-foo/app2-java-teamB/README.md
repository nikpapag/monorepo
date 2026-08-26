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

### Service Endpoints
- `GET /api/` - Service information
- `GET /api/health` - Health check
- `POST /api/process` - Process data (cached)
- `GET /api/calculate?a=10&b=20` - Perform calculations (cached)

### Cache Management Endpoints
- `GET /api/cache/stats` - Get cache statistics (hit rate, miss count, etc.)
- `DELETE /api/cache/clear` - Clear all caches
- `DELETE /api/cache/clear/{cacheName}` - Clear specific cache (calculations or dataProcessing)

## Features

### Caching Layer
This application now includes a high-performance caching layer using Spring Cache with Caffeine:
- **Automatic caching** of expensive calculations and data processing operations
- **Cache statistics** to monitor hit rates and performance
- **TTL-based expiration** (10 minutes)
- **Size-limited** caches (max 100 entries per cache)
- **Manual cache eviction** through management endpoints

### Cache Types
1. **calculations** - Caches mathematical operation results
2. **dataProcessing** - Caches text processing operations

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
