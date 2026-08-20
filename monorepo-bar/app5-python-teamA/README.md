# App5 - Python - TeamA (Lightweight)

## Description
Lightweight Flask API service with minimal resource requirements and custom CI/CD pipeline.

## Team
TeamA

## Resource Profile
- **Type**: Small (S)
- **CPU**: 250m - 500m
- **Memory**: 256Mi - 512Mi
- **Reason**: Simple REST API with minimal processing

## Technologies
- Python 3.11
- Flask
- Gunicorn
- Pytest

## Endpoints
- `GET /` - Service information
- `GET /health` - Health check
- `POST /api/greet` - Greeting endpoint
- `GET /api/status` - Service status

## Running Locally
```bash
pip install -r requirements.txt
python app.py
```

## Running Tests
```bash
pytest --cov=. --cov-report=term
```

## CI/CD
Uses **custom pipeline** (not guild-recommended) with:
- Custom build steps
- Lint checking
- Coverage threshold enforcement (70%)
- Small resource allocation
