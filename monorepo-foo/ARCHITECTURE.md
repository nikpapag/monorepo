# Monorepo Foo - Architecture

## Overview
Monorepo Foo contains 4 microservices following guild-recommended CI/CD patterns with varying resource requirements.

## Architecture Diagram
```
┌─────────────────────────────────────────────────────────┐
│               Monorepo Foo                               │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  ┌──────────────────┐     ┌──────────────────┐         │
│  │  App1 (TeamA)    │     │  App2 (TeamB)    │         │
│  │  Python ML       │     │  Java REST API   │         │
│  │  Resource: Large │     │  Resource: Small │         │
│  └──────────────────┘     └──────────────────┘         │
│                                                          │
│  ┌──────────────────┐     ┌──────────────────┐         │
│  │  App3 (TeamB)    │     │  App4 (TeamB)    │         │
│  │  Java Users      │     │  Python Batch    │         │
│  │  Resource: Small │     │  Resource: Large │         │
│  └──────────────────┘     └──────────────────┘         │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

## Application Patterns

### Resource-Intensive Applications (App1, App4)
- **Pattern**: Large resource allocation
- **Use Cases**: ML training, batch processing, data aggregation
- **Characteristics**:
  - High CPU usage
  - Large memory footprint
  - Extended processing times
  - Horizontal scaling with careful resource management

### Lightweight Applications (App2, App3)
- **Pattern**: Small resource allocation
- **Use Cases**: CRUD APIs, simple data processing
- **Characteristics**:
  - Low CPU usage
  - Small memory footprint
  - Fast response times
  - Easy horizontal scaling

## CI/CD Architecture

### Guild Recommended Pipelines
All applications use standardized pipelines with:

1. **Python Pipeline**
   - Dependency resolution
   - Static analysis
   - Unit tests
   - Coverage reporting
   - Artifact creation

2. **Java Pipeline**
   - Maven build
   - Compilation
   - Unit tests
   - JaCoCo coverage
   - Package generation

## Deployment Strategy

### Resource Allocation Strategy
- **Large Apps**: Higher replica count with resource quotas
- **Small Apps**: Multiple replicas with minimal resources

### Health Check Strategy
- Liveness probes: Detect crashed containers
- Readiness probes: Manage traffic routing
- Startup probes: Handle slow-starting apps (large resource apps)

## Team Boundaries

### TeamA
- Owns: App1
- Focus: Machine Learning and Data Science
- Tech: Python, ML libraries

### TeamB
- Owns: App2, App3, App4
- Focus: Application Services and Batch Processing
- Tech: Java (Spring Boot), Python (Flask)

## Monitoring and Observability

### Metrics
- Application-level metrics via actuator/health endpoints
- Resource utilization metrics (CPU, memory)
- Request/response metrics

### Logging
- Structured JSON logging
- Centralized log aggregation
- Log levels per environment

## Security

### Container Security
- Non-root user execution
- Minimal base images
- Regular security scans

### Network Security
- Service-to-service communication via ClusterIP
- Network policies for isolation
- TLS for external communication
