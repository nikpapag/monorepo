from flask import Flask, jsonify, request
import os
import time
import logging
from datetime import datetime
import json

app = Flask(__name__)

# Configure structured logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Request log storage
request_log = []

@app.before_request
def log_request():
    """Log all incoming requests"""
    log_entry = {
        'timestamp': datetime.utcnow().isoformat(),
        'method': request.method,
        'path': request.path,
        'remote_addr': request.remote_addr
    }
    request_log.append(log_entry)

    # Keep only last 100 requests
    if len(request_log) > 100:
        request_log.pop(0)

    logger.info(f"{request.method} {request.path} from {request.remote_addr}")

@app.route('/')
def home():
    return jsonify({
        'service': 'app5-python-teamA',
        'team': 'TeamA',
        'status': 'running',
        'type': 'lightweight-api',
        'version': os.getenv('APP_VERSION', '1.0.0'),
        'pipeline': 'custom'
    })

@app.route('/health')
def health():
    return jsonify({'status': 'healthy'}), 200

@app.route('/api/greet', methods=['POST'])
def greet():
    data = request.get_json()
    name = data.get('name', 'Guest')

    logger.info(f"Greeting request for: {name}")

    return jsonify({
        'message': f'Hello, {name}!',
        'timestamp': time.time()
    }), 200

@app.route('/api/status')
def status():
    return jsonify({
        'service': 'app5-python-teamA',
        'uptime': 'healthy',
        'endpoints': [
            'GET /',
            'GET /health',
            'POST /api/greet',
            'GET /api/status',
            'GET /api/logs'
        ]
    }), 200

@app.route('/api/logs')
def get_logs():
    """Return recent request logs"""
    return jsonify({
        'service': 'app5-python-teamA',
        'total_requests': len(request_log),
        'logs': request_log[-20:]  # Last 20 requests
    }), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
