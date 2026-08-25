from flask import Flask, jsonify, request
import numpy as np
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
import os
import time
from datetime import datetime

app = Flask(__name__)

# Application metrics
app_metrics = {
    'request_count': 0,
    'train_count': 0,
    'process_count': 0,
    'error_count': 0,
    'total_processing_time': 0.0,
    'start_time': datetime.utcnow().isoformat()
}

# Simulated ML model training (resource-intensive operation)
def train_model():
    """Train a machine learning model - resource intensive"""
    # Generate synthetic data
    X = np.random.rand(10000, 20)
    y = np.random.randint(0, 2, 10000)

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

    model = RandomForestClassifier(n_estimators=100)
    model.fit(X_train, y_train)

    score = model.score(X_test, y_test)
    return score

@app.route('/')
def home():
    app_metrics['request_count'] += 1
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
    app_metrics['request_count'] += 1
    start = time.time()
    try:
        app_metrics['train_count'] += 1
        accuracy = train_model()
        elapsed = time.time() - start
        app_metrics['total_processing_time'] += elapsed
        return jsonify({
            'status': 'success',
            'accuracy': accuracy,
            'processing_time': elapsed,
            'message': 'Model trained successfully'
        }), 200
    except Exception as e:
        app_metrics['error_count'] += 1
        return jsonify({
            'status': 'error',
            'message': str(e)
        }), 500

@app.route('/process', methods=['POST'])
def process_data():
    """Process data - resource intensive"""
    app_metrics['request_count'] += 1
    app_metrics['process_count'] += 1
    start = time.time()

    data = request.get_json()
    size = data.get('size', 1000)

    # Generate and process large dataset
    df = pd.DataFrame(np.random.randn(size, 10))
    elapsed = time.time() - start
    app_metrics['total_processing_time'] += elapsed

    result = {
        'mean': df.mean().tolist(),
        'std': df.std().tolist(),
        'shape': df.shape,
        'processing_time': elapsed
    }

    return jsonify(result), 200

@app.route('/metrics')
def metrics():
    """Application metrics endpoint"""
    uptime_seconds = (datetime.utcnow() - datetime.fromisoformat(app_metrics['start_time'])).total_seconds()

    return jsonify({
        'service': 'app1-python-teamA',
        'metrics': {
            'requests': {
                'total': app_metrics['request_count'],
                'train': app_metrics['train_count'],
                'process': app_metrics['process_count'],
                'errors': app_metrics['error_count']
            },
            'performance': {
                'total_processing_time_seconds': round(app_metrics['total_processing_time'], 2),
                'avg_processing_time_seconds': round(
                    app_metrics['total_processing_time'] / max(app_metrics['train_count'] + app_metrics['process_count'], 1),
                    2
                )
            },
            'uptime': {
                'start_time': app_metrics['start_time'],
                'uptime_seconds': round(uptime_seconds, 2)
            }
        }
    }), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
