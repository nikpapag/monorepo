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

    # Validate input (None means no JSON body, {} is valid)
    if data is None:
        return jsonify({
            'error': 'Request body is required',
            'code': 'MISSING_BODY'
        }), 400

    name = data.get('name', 'Guest')

    # Validate name length
    if len(name) > 100:
        return jsonify({
            'error': 'Name too long (max 100 characters)',
            'code': 'NAME_TOO_LONG'
        }), 400

    # Validate name characters
    if not name.replace(' ', '').isalnum():
        return jsonify({
            'error': 'Name can only contain letters, numbers, and spaces',
            'code': 'INVALID_NAME'
        }), 400

    return jsonify({
        'message': f'Hello, {name}!',
        'timestamp': time.time(),
        'validated': True
    }), 200

@app.route('/api/status')
def status():
    return jsonify({
        'service': 'app5-python-teamA',
        'uptime': 'healthy',
        'features': ['input-validation'],
        'endpoints': [
            'GET /',
            'GET /health',
            'POST /api/greet',
            'GET /api/status'
        ],
        'validation_rules': {
            'name': {
                'max_length': 100,
                'allowed_chars': 'alphanumeric and spaces'
            }
        }
    }), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
