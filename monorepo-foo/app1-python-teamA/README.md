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
- `POST /predict` - Make predictions using trained model
- `GET /model/info` - Get current model information
- `POST /process` - Process large datasets

### Prediction Workflow
1. Train the model: `POST /train`
2. Check model status: `GET /model/info`
3. Make predictions: `POST /predict` with JSON body:
   ```json
   {
     "features": [0.1, 0.2, ..., 0.9]  // Array of 20 float values
   }
   ```

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
