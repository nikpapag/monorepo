from flask import Flask, jsonify, request
import numpy as np
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
import os
from datetime import datetime

app = Flask(__name__)

# Global model cache
_model_cache = {
    'model': None,
    'version': None,
    'trained_at': None
}

# Simulated ML model training (resource-intensive operation)
def train_model():
    """Train a machine learning model - resource intensive"""
    # Generate synthetic data
    X = np.random.rand(10000, 20)
    y = np.random.randint(0, 2, 10000)

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    model = RandomForestClassifier(n_estimators=100, random_state=42)
    model.fit(X_train, y_train)

    score = model.score(X_test, y_test)

    # Cache the trained model
    version = datetime.utcnow().strftime('%Y%m%d_%H%M%S')
    _model_cache['model'] = model
    _model_cache['version'] = version
    _model_cache['trained_at'] = datetime.utcnow().isoformat()

    return score, model, version

@app.route('/')
def home():
    return jsonify({
        'service': 'app1-python-teamA',
        'team': 'TeamA',
        'status': 'running',
        'type': 'resource-intensive',
        'version': os.getenv('APP_VERSION', '1.0.0')
    })

@app.route('/health')
def health():
    return jsonify({'status': 'healthy'}), 200

@app.route('/train', methods=['POST'])
def train():
    """Resource-intensive ML training endpoint"""
    try:
        accuracy, model, version = train_model()
        return jsonify({
            'status': 'success',
            'accuracy': float(accuracy),
            'model_version': version,
            'trained_at': _model_cache['trained_at'],
            'message': 'Model trained successfully'
        }), 200
    except Exception as e:
        return jsonify({
            'status': 'error',
            'message': str(e)
        }), 500

@app.route('/predict', methods=['POST'])
def predict():
    """Make predictions using the trained model"""
    if _model_cache['model'] is None:
        return jsonify({
            'status': 'error',
            'message': 'No trained model available. Please train a model first.'
        }), 400

    try:
        data = request.get_json()
        if not data or 'features' not in data:
            return jsonify({
                'status': 'error',
                'message': 'Missing features in request body'
            }), 400

        features = np.array(data['features'])
        if features.ndim == 1:
            features = features.reshape(1, -1)

        if features.shape[1] != 20:
            return jsonify({
                'status': 'error',
                'message': f'Expected 20 features, got {features.shape[1]}'
            }), 400

        predictions = _model_cache['model'].predict(features)
        probabilities = _model_cache['model'].predict_proba(features)

        return jsonify({
            'status': 'success',
            'predictions': predictions.tolist(),
            'probabilities': probabilities.tolist(),
            'model_version': _model_cache['version'],
            'num_samples': len(predictions)
        }), 200
    except Exception as e:
        return jsonify({
            'status': 'error',
            'message': str(e)
        }), 500

@app.route('/model/info', methods=['GET'])
def model_info():
    """Get information about the current model"""
    if _model_cache['model'] is None:
        return jsonify({
            'status': 'no_model',
            'message': 'No model trained yet'
        }), 200

    return jsonify({
        'status': 'ready',
        'model_version': _model_cache['version'],
        'trained_at': _model_cache['trained_at'],
        'model_type': 'RandomForestClassifier',
        'n_estimators': _model_cache['model'].n_estimators,
        'n_features': _model_cache['model'].n_features_in_
    }), 200

@app.route('/process', methods=['POST'])
def process_data():
    """Process data - resource intensive"""
    data = request.get_json()
    size = data.get('size', 1000)

    # Generate and process large dataset
    df = pd.DataFrame(np.random.randn(size, 10))
    result = {
        'mean': df.mean().tolist(),
        'std': df.std().tolist(),
        'shape': df.shape
    }

    return jsonify(result), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
