# Monorepo Demo - Multi-Repository Setup

This repository demonstrates a realistic enterprise monorepo structure with two separate monorepos showcasing different CI/CD approaches.

## Repository Structure

```
.
├── monorepo-foo/          # Guild Recommended Pipelines
│   ├── app1-python-teamA/ # Resource Intensive (L)
│   ├── app2-java-teamB/   # Lightweight (S)
│   ├── app3-java-teamB/   # Lightweight (S)
│   └── app4-python-teamB/ # Resource Intensive (L)
│
└── monorepo-bar/          # Custom Pipelines
    ├── app5-python-teamA/ # Lightweight (S)
    ├── app6-java-teamB/   # Lightweight (S)
    └── app7-java-teamA/   # Resource Intensive (L)
```

## Overview

### Monorepo Foo
- **Pipeline Type**: Guild Recommended
- **Applications**: 4
- **Teams**: TeamA (1 app), TeamB (3 apps)
- **Resource Mix**: 2 Large, 2 Small
- **Tech Stack**: Python, Java (Spring Boot)

### Monorepo Bar
- **Pipeline Type**: Custom
- **Applications**: 3
- **Teams**: TeamA (2 apps), TeamB (1 app)
- **Resource Mix**: 1 Large, 2 Small
- **Tech Stack**: Python, Java (Spring Boot)

## Teams

### TeamA
- **Monorepo Foo**: app1 (Python ML - Large)
- **Monorepo Bar**: app5 (Python API - Small), app7 (Java Analytics - Large)
- **Focus**: Machine Learning, Analytics, API Services

### TeamB
- **Monorepo Foo**: app2 (Java API - Small), app3 (Java Users - Small), app4 (Python Batch - Large)
- **Monorepo Bar**: app6 (Java Products - Small)
- **Focus**: Application Services, User Management, Product Catalog

## Resource Requirements Summary

| Monorepo | App | Team | Lang | Profile | CPU | Memory |
|----------|-----|------|------|---------|-----|--------|
| Foo | app1 | A | Python | L | 1-2 cores | 2-4 GB |
| Foo | app2 | B | Java | S | 250-500m | 256-512 Mi |
| Foo | app3 | B | Java | S | 250-500m | 256-512 Mi |
| Foo | app4 | B | Python | L | 1.5-3 cores | 2-4 GB |
| Bar | app5 | A | Python | S | 250-500m | 256-512 Mi |
| Bar | app6 | B | Java | S | 250-500m | 256-512 Mi |
| Bar | app7 | A | Java | L | 1-2 cores | 1-2 GB |

## Pipeline Comparison

### Guild Recommended (Monorepo Foo)
**Benefits:**
- Standardized across organization
- Reduced maintenance overhead
- Consistent practices
- Easier onboarding

**Limitations:**
- Less flexibility
- May not fit all use cases
- Slower to adopt new patterns

### Custom (Monorepo Bar)
**Benefits:**
- Maximum flexibility
- Optimized per application
- Team autonomy
- Experimentation-friendly

**Limitations:**
- Higher maintenance
- Potential inconsistency
- More complex governance

## Technology Stack

### Python Applications
- **Framework**: Flask
- **Testing**: pytest, pytest-cov
- **Server**: Gunicorn
- **ML Libraries**: NumPy, Pandas, scikit-learn (where applicable)

### Java Applications
- **Framework**: Spring Boot 3.2
- **Build Tool**: Maven
- **Testing**: JUnit, Spring Test
- **Coverage**: JaCoCo

## Getting Started

### Prerequisites
```bash
# Python
python 3.11+
pip

# Java
java 17+
maven 3.9+

# Container & Orchestration
docker
kubernetes (kubectl)
```

### Running Applications

#### Python Apps
```bash
cd monorepo-foo/app1-python-teamA  # or any Python app
pip install -r requirements.txt
python app.py
```

#### Java Apps
```bash
cd monorepo-foo/app2-java-teamB  # or any Java app
mvn spring-boot:run
```

### Running Tests

#### Python
```bash
pytest --cov=. --cov-report=term
```

#### Java
```bash
mvn test jacoco:report
```

## CI/CD

### Harness Pipelines
Each application has its own Harness CI/CD pipeline:
- **Location**: `.harness/pipeline.yaml` (guild) or `.harness/custom-pipeline.yaml` (custom)
- **Trigger**: Push to main, PR creation, manual
- **Stages**: Build, test, coverage, package

### Monorepo-Level Pipelines
Both monorepos have aggregate pipelines that run all apps in parallel:
- `monorepo-foo/.harness/monorepo-pipeline.yaml`
- `monorepo-bar/.harness/monorepo-pipeline.yaml`

## Deployment

All applications include Kubernetes manifests in `k8s/deployment.yaml`:

```bash
# Deploy entire monorepo
kubectl apply -f monorepo-foo/app1-python-teamA/k8s/
kubectl apply -f monorepo-foo/app2-java-teamB/k8s/
# ... etc

# Or deploy individual apps
kubectl apply -f monorepo-bar/app7-java-teamA/k8s/deployment.yaml
```

## Monitoring

All applications expose health endpoints:
- **Python**: `GET /health`
- **Java**: `GET /actuator/health`

## Documentation

### Detailed Documentation
- [Monorepo Foo README](monorepo-foo/README.md)
- [Monorepo Foo Architecture](monorepo-foo/ARCHITECTURE.md)
- [Monorepo Bar README](monorepo-bar/README.md)
- [Monorepo Bar Architecture](monorepo-bar/ARCHITECTURE.md)

### Application READMEs
Each application has its own README with specific details:
- Running instructions
- API endpoints
- Testing guidelines
- Resource requirements
- CI/CD configuration

## Use Cases

This demo illustrates:
1. **Multi-monorepo strategy**: Separate monorepos for different pipeline approaches
2. **Resource heterogeneity**: Mix of lightweight and resource-intensive workloads
3. **Team organization**: Multiple teams sharing infrastructure
4. **Pipeline flexibility**: Guild-recommended vs. custom pipelines
5. **Polyglot services**: Python and Java coexisting
6. **Realistic CI/CD**: Unit tests, coverage, containerization

## Best Practices Demonstrated

- Clear team ownership boundaries
- Resource requirements documentation
- Health check implementation
- Comprehensive testing
- Code coverage enforcement
- Container optimization (multi-stage builds)
- Infrastructure as code (Kubernetes manifests)
- CI/CD as code (Harness pipelines)

## Future Enhancements

- Integration tests
- End-to-end testing
- Service mesh integration
- Observability stack (Prometheus, Grafana)
- GitOps deployment
- Policy as code
- Automated rollback strategies

## Support

For questions or issues:
- **Monorepo Foo**: See [monorepo-foo/README.md](monorepo-foo/README.md)
- **Monorepo Bar**: See [monorepo-bar/README.md](monorepo-bar/README.md)
