from flask import Flask, jsonify, request
import numpy as np
import pandas as pd
import time
import os

app = Flask(__name__)

def process_large_batch(batch_size=10000):
    """Simulate resource-intensive batch processing"""
    data = np.random.randn(batch_size, 50)
    df = pd.DataFrame(data)

    # Simulate complex calculations
    result = {
        'correlation_matrix': df.corr().values.tolist()[:5][:5],
        'describe': df.describe().to_dict(),
        'processed_rows': len(df)
    }
    return result

@app.route('/')
def home():
    return jsonify({
        'service': 'app4-python-teamB',
        'team': 'TeamB',
        'status': 'running',
        'type': 'resource-intensive-batch-processor',
        'version': os.getenv('APP_VERSION', '1.0.0')
    })

@app.route('/health')
def health():
    return jsonify({'status': 'healthy'}), 200

@app.route('/batch/process', methods=['POST'])
def batch_process():
    """Process large batches - resource intensive"""
    try:
        data = request.get_json() or {}
        batch_size = data.get('batch_size', 10000)

        start_time = time.time()
        result = process_large_batch(batch_size)
        processing_time = time.time() - start_time

        return jsonify({
            'status': 'success',
            'processing_time': processing_time,
            'result': result
        }), 200
    except Exception as e:
        return jsonify({
            'status': 'error',
            'message': str(e)
        }), 500

@app.route('/aggregate', methods=['POST'])
def aggregate_data():
    """Aggregate large datasets"""
    data = request.get_json()
    size = data.get('size', 5000)

    df = pd.DataFrame({
        'category': np.random.choice(['A', 'B', 'C', 'D'], size),
        'value': np.random.randn(size),
        'timestamp': pd.date_range('2024-01-01', periods=size, freq='1min')
    })

    aggregated = df.groupby('category').agg({
        'value': ['mean', 'std', 'min', 'max', 'count']
    }).to_dict()

    return jsonify({
        'aggregated_data': str(aggregated),
        'total_rows': size
    }), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
