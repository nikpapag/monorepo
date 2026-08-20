# App1 - Python - TeamA (Resource Intensive)

## Description
Machine learning service with resource-intensive operations including model training and data processing.

## Team
TeamA

## Resource Profile
- **Type**: Large (L)
- **CPU**: High
- **Memory**: High
- **Reason**: ML model training and large dataset processing

## Technologies
- Python 3.x
- Flask
- NumPy, Pandas
- scikit-learn

## Endpoints
- `GET /` - Service information
- `GET /health` - Health check
- `POST /train` - Train ML model (resource intensive)
- `POST /process` - Process large datasets

## Running Locally
```bash
pip install -r requirements.txt
python app.py
```

## Running Tests
```bash
pytest
```

## CI/CD
Uses guild-recommended Python pipeline with:
- Large resource allocation
- Extended timeout for ML operations
- Code coverage reporting
