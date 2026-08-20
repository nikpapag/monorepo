# Monorepo Bar - Architecture

## Overview
Monorepo Bar contains 3 microservices following **custom CI/CD patterns** with varying resource requirements. This repository demonstrates flexibility in pipeline design compared to standardized guild approaches.

## Architecture Diagram
```
┌─────────────────────────────────────────────────────────┐
│               Monorepo Bar                               │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  ┌──────────────────┐     ┌──────────────────┐         │
│  │  App5 (TeamA)    │     │  App6 (TeamB)    │         │
│  │  Python API      │     │  Java Products   │         │
│  │  Resource: Small │     │  Resource: Small │         │
│  │  Pipeline:Custom │     │  Pipeline:Custom │         │
│  └──────────────────┘     └──────────────────┘         │
│                                                          │
│         ┌──────────────────────────┐                    │
│         │  App7 (TeamA)            │                    │
│         │  Java Analytics          │                    │
│         │  Resource: Large         │                    │
│         │  Pipeline: Custom        │                    │
│         └──────────────────────────┘                    │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

## Application Patterns

### Resource-Intensive Application (App7)
- **Pattern**: Large resource allocation with custom pipeline
- **Use Cases**: Complex analytics, data aggregation, intensive computations
- **Characteristics**:
  - High CPU and memory requirements
  - Custom performance testing
  - Extended build times
  - Tailored coverage thresholds (65%)

### Lightweight Applications (App5, App6)
- **Pattern**: Small resource allocation with optimized custom pipelines
- **Use Cases**: Simple APIs, CRUD operations
- **Characteristics**:
  - Minimal resource footprint
  - Fast build and test cycles
  - Optimized for quick deployments
  - Flexible coverage thresholds (60-70%)

## CI/CD Architecture

### Custom Pipeline Philosophy
Unlike monorepo-foo's standardized approach, monorepo-bar embraces custom pipelines:

1. **App5 Custom Python Pipeline**
   - Custom linting configuration
   - Flexible dependency management
   - 70% coverage threshold
   - Optimized for lightweight operations

2. **App6 Custom Java Pipeline**
   - Separated build stages
   - Custom Maven goals
   - 60% coverage threshold
   - Optimized resource usage

3. **App7 Custom Java Pipeline**
   - Resource-intensive build configuration
   - Performance testing integration
   - 65% coverage threshold
   - Extended timeout configurations

### Pipeline Customization Benefits
- **Per-App Optimization**: Each pipeline is tuned for its specific workload
- **Team Autonomy**: Teams can modify pipelines without guild approval
- **Experimentation**: Try new patterns before standardization
- **Resource Efficiency**: Fine-grained resource allocation

## Deployment Strategy

### Resource Allocation Strategy
- **Large App (App7)**: Higher resources with longer startup times
- **Small Apps (App5, App6)**: Minimal resources with fast startup

### Health Check Strategy
- Custom health check intervals per application
- Adaptive probe configurations based on app complexity
- Startup probes for resource-intensive apps

## Team Boundaries

### TeamA
- Owns: App5, App7
- Focus: API Services and Analytics
- Tech: Python (Flask), Java (Spring Boot)
- Pipeline Strategy: Custom optimization for diverse workloads

### TeamB
- Owns: App6
- Focus: Product Catalog Management
- Tech: Java (Spring Boot)
- Pipeline Strategy: Custom lightweight build

## Custom vs. Guild-Recommended

### When to Use Custom Pipelines
- Unique build requirements
- Experimental patterns
- Performance-critical applications
- Team-specific tooling needs

### When to Use Guild Pipelines
- Standard CRUD applications
- Consistent team practices
- Reduced maintenance overhead
- Compliance requirements

## Monitoring and Observability

### Metrics
- Application-specific metrics
- Build and deployment metrics
- Resource utilization tracking
- Custom alerting per app

### Logging
- Structured logging with custom formats
- Application-specific log levels
- Centralized aggregation

## Security

### Container Security
- Minimal attack surface
- Regular vulnerability scanning
- Non-root execution
- Custom security scanning in pipelines

### Pipeline Security
- Secret management
- Access controls
- Audit logging
- Custom security gates

## Migration Considerations

### Path to Guild Pipelines
Applications may migrate to guild-recommended pipelines when:
- Patterns are validated and proven
- Benefits of standardization outweigh flexibility needs
- Guild adopts custom patterns as standards
- Team capacity for maintenance decreases

## Future Improvements
- Automated pipeline optimization
- A/B testing of pipeline configurations
- Cross-monorepo pipeline sharing
- Performance benchmarking
