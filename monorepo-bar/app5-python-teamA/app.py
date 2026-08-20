from flask import Flask, jsonify, request
import os
import time

app = Flask(__name__)

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
            'GET /api/status'
        ]
    }), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
