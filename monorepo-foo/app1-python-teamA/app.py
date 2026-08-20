from flask import Flask, jsonify, request
import numpy as np
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
import os

app = Flask(__name__)

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
        accuracy = train_model()
        return jsonify({
            'status': 'success',
            'accuracy': accuracy,
            'message': 'Model trained successfully'
        }), 200
    except Exception as e:
        return jsonify({
            'status': 'error',
            'message': str(e)
        }), 500

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
