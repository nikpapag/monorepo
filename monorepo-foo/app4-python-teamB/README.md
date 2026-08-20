# App4 - Python - TeamB (Resource Intensive)

## Description
Batch processing service with resource-intensive data aggregation and analysis operations.

## Team
TeamB

## Resource Profile
- **Type**: Large (L)
- **CPU**: 1.5-3 cores
- **Memory**: 2-4 GB
- **Reason**: Large batch processing and data aggregation

## Technologies
- Python 3.11
- Flask
- NumPy, Pandas
- Celery (for async tasks)

## Endpoints
- `GET /` - Service information
- `GET /health` - Health check
- `POST /batch/process` - Process large batches
- `POST /aggregate` - Aggregate large datasets

## Running Locally
```bash
pip install -r requirements.txt
python app.py
```

## Running Tests
```bash
pytest --cov=. --cov-report=term
```
