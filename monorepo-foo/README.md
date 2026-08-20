# Monorepo Foo

This monorepo contains 4 applications using **Guild Recommended Pipelines** for Python and Java.

## Team Structure
- **TeamA**: Owns app1
- **TeamB**: Owns app2, app3, app4

## Applications

### App1 - Python - TeamA (Resource Intensive)
- **Location**: `app1-python-teamA/`
- **Type**: Machine Learning Service
- **Resource Profile**: Large (L)
  - CPU: 1-2 cores
  - Memory: 2-4 GB
- **Pipeline**: Guild Recommended Python Pipeline
- **Tech Stack**: Python 3.11, Flask, NumPy, Pandas, scikit-learn

### App2 - Java - TeamB (Lightweight)
- **Location**: `app2-java-teamB/`
- **Type**: REST API Service
- **Resource Profile**: Small (S)
  - CPU: 250m-500m
  - Memory: 256Mi-512Mi
- **Pipeline**: Guild Recommended Java Pipeline
- **Tech Stack**: Java 17, Spring Boot 3.2, Maven

### App3 - Java - TeamB (Lightweight)
- **Location**: `app3-java-teamB/`
- **Type**: User Management Service
- **Resource Profile**: Small (S)
  - CPU: 250m-500m
  - Memory: 256Mi-512Mi
- **Pipeline**: Guild Recommended Java Pipeline
- **Tech Stack**: Java 17, Spring Boot 3.2, Maven

### App4 - Python - TeamB (Resource Intensive)
- **Location**: `app4-python-teamB/`
- **Type**: Batch Processing Service
- **Resource Profile**: Large (L)
  - CPU: 1.5-3 cores
  - Memory: 2-4 GB
- **Pipeline**: Guild Recommended Python Pipeline
- **Tech Stack**: Python 3.11, Flask, NumPy, Pandas, Celery

## Guild Recommended Pipelines

This monorepo uses standardized CI/CD pipelines recommended by the engineering guild:

### Python Pipeline
- Dependency installation
- Unit tests with pytest
- Code coverage reporting (pytest-cov)
- Coverage thresholds
- Build artifacts

### Java Pipeline
- Maven compilation
- Unit tests
- JaCoCo code coverage
- Package generation
- Artifact publishing

## Resource Requirements Summary

| Application | Team | Language | Profile | CPU Request | CPU Limit | Memory Request | Memory Limit |
|------------|------|----------|---------|-------------|-----------|----------------|--------------|
| app1 | TeamA | Python | Large | 1000m | 2000m | 2Gi | 4Gi |
| app2 | TeamB | Java | Small | 250m | 500m | 256Mi | 512Mi |
| app3 | TeamB | Java | Small | 250m | 500m | 256Mi | 512Mi |
| app4 | TeamB | Python | Large | 1500m | 3000m | 2Gi | 4Gi |

## Development

### Prerequisites
- Python 3.11+
- Java 17+
- Maven 3.9+
- Docker
- Kubernetes (for deployment)

### Running Applications Locally

**Python apps:**
```bash
cd app1-python-teamA  # or app4-python-teamB
pip install -r requirements.txt
python app.py
```

**Java apps:**
```bash
cd app2-java-teamB  # or app3-java-teamB
mvn spring-boot:run
```

### Running Tests

**Python:**
```bash
pytest --cov=. --cov-report=term
```

**Java:**
```bash
mvn test jacoco:report
```

## CI/CD

Each application has its own Harness CI/CD pipeline configuration in the `.harness/` directory.

### Pipeline Triggers
- Push to main branch
- Pull request creation
- Manual trigger

### Pipeline Stages
1. Code checkout
2. Dependency installation
3. Unit tests
4. Code coverage
5. Build/Package
6. Docker image build (optional)
7. Deployment (optional)

## Deployment

Kubernetes manifests are located in each application's `k8s/` directory.

```bash
kubectl apply -f app1-python-teamA/k8s/deployment.yaml
kubectl apply -f app2-java-teamB/k8s/deployment.yaml
kubectl apply -f app3-java-teamB/k8s/deployment.yaml
kubectl apply -f app4-python-teamB/k8s/deployment.yaml
```

## Monitoring and Health Checks

All applications expose health endpoints:
- Python apps: `GET /health`
- Java apps: `GET /actuator/health`

## Team Ownership

| Team | Applications | Responsibilities |
|------|--------------|------------------|
| TeamA | app1 | ML model training, data science operations |
| TeamB | app2, app3, app4 | API services, user management, batch processing |

## Support

For questions or issues:
- TeamA: Contact TeamA leads
- TeamB: Contact TeamB leads
