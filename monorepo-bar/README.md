# Monorepo Bar

This monorepo contains 3 applications using **Custom CI/CD Pipelines** (not guild-recommended).

## Team Structure
- **TeamA**: Owns app5, app7
- **TeamB**: Owns app6

## Applications

### App5 - Python - TeamA (Lightweight)
- **Location**: `app5-python-teamA/`
- **Type**: Lightweight API Service
- **Resource Profile**: Small (S)
  - CPU: 250m-500m
  - Memory: 256Mi-512Mi
- **Pipeline**: Custom Python Pipeline
- **Tech Stack**: Python 3.11, Flask, Gunicorn

### App6 - Java - TeamB (Lightweight)
- **Location**: `app6-java-teamB/`
- **Type**: Product Catalog Service
- **Resource Profile**: Small (S)
  - CPU: 250m-500m
  - Memory: 256Mi-512Mi
- **Pipeline**: Custom Java Pipeline
- **Tech Stack**: Java 17, Spring Boot 3.2, Maven

### App7 - Java - TeamA (Resource Intensive)
- **Location**: `app7-java-teamA/`
- **Type**: Analytics Service
- **Resource Profile**: Large (L)
  - CPU: 1-2 cores
  - Memory: 1-2 GB
- **Pipeline**: Custom Java Pipeline
- **Tech Stack**: Java 17, Spring Boot 3.2, Spring Data JPA, Maven

## Custom CI/CD Pipelines

Unlike monorepo-foo, this repository uses **custom-built pipelines** tailored to specific application needs:

### Custom Python Pipeline (App5)
- Custom Python environment setup
- Lint checking with pylint
- Unit tests with coverage threshold (70%)
- Custom build steps
- Flexible resource allocation

### Custom Java Pipeline (App6)
- Maven validation
- Separate compilation and test stages
- JaCoCo coverage with 60% threshold
- Custom packaging steps
- Optimized resource usage

### Custom Java Pipeline (App7)
- Extended resource allocation for analytics
- Performance testing
- Separate compilation, test, and coverage stages
- JaCoCo coverage with 65% threshold
- Custom build optimization

## Resource Requirements Summary

| Application | Team | Language | Profile | CPU Request | CPU Limit | Memory Request | Memory Limit |
|------------|------|----------|---------|-------------|-----------|----------------|--------------|
| app5 | TeamA | Python | Small | 250m | 500m | 256Mi | 512Mi |
| app6 | TeamB | Java | Small | 250m | 500m | 256Mi | 512Mi |
| app7 | TeamA | Java | Large | 1000m | 2000m | 1Gi | 2Gi |

## Development

### Prerequisites
- Python 3.11+
- Java 17+
- Maven 3.9+
- Docker
- Kubernetes (for deployment)

### Running Applications Locally

**Python app:**
```bash
cd app5-python-teamA
pip install -r requirements.txt
python app.py
```

**Java apps:**
```bash
cd app6-java-teamB  # or app7-java-teamA
mvn spring-boot:run
```

### Running Tests

**Python:**
```bash
cd app5-python-teamA
pytest --cov=. --cov-report=term
```

**Java:**
```bash
cd app6-java-teamB  # or app7-java-teamA
mvn test jacoco:report
```

## CI/CD

Each application has its own **custom** Harness CI/CD pipeline configuration in the `.harness/` directory.

### Custom Pipeline Features
- **Flexibility**: Pipelines are tailored to specific app requirements
- **Custom Steps**: Applications can define their own build and test steps
- **Resource Optimization**: Each pipeline has optimized resource allocation
- **Coverage Thresholds**: Different coverage requirements per app

### Pipeline Comparison with Monorepo Foo

| Feature | Monorepo Foo (Guild) | Monorepo Bar (Custom) |
|---------|---------------------|----------------------|
| Pipeline Type | Standardized | Custom |
| Flexibility | Low | High |
| Maintenance | Centralized | Per-app |
| Customization | Limited | Extensive |
| Coverage Thresholds | Standard | Varied (60-70%) |

## Deployment

Kubernetes manifests are located in each application's `k8s/` directory.

```bash
kubectl apply -f app5-python-teamA/k8s/deployment.yaml
kubectl apply -f app6-java-teamB/k8s/deployment.yaml
kubectl apply -f app7-java-teamA/k8s/deployment.yaml
```

## Monitoring and Health Checks

All applications expose health endpoints:
- Python apps: `GET /health`
- Java apps: `GET /actuator/health`

## Team Ownership

| Team | Applications | Responsibilities |
|------|--------------|------------------|
| TeamA | app5, app7 | Lightweight API, analytics processing |
| TeamB | app6 | Product catalog service |

## Why Custom Pipelines?

This monorepo uses custom pipelines instead of guild-recommended ones for several reasons:

1. **Specific Requirements**: Applications have unique build and test requirements
2. **Optimization**: Resource allocation is fine-tuned per application
3. **Flexibility**: Teams can modify pipelines without affecting guild standards
4. **Experimentation**: Testing new CI/CD patterns before proposing to guild
5. **Coverage Variance**: Different coverage thresholds based on app criticality

## Migration Path

Applications in this repo may eventually migrate to guild-recommended pipelines once patterns are validated and standardized.

## Support

For questions or issues:
- TeamA: Contact TeamA leads (app5, app7)
- TeamB: Contact TeamB leads (app6)
