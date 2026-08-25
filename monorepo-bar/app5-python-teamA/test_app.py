import pytest
import json
from app import app

@pytest.fixture
def client():
    app.config['TESTING'] = True
    with app.test_client() as client:
        yield client

def test_home(client):
    response = client.get('/')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['service'] == 'app5-python-teamA'
    assert data['team'] == 'TeamA'
    assert data['pipeline'] == 'custom'

def test_health(client):
    response = client.get('/health')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['status'] == 'healthy'

def test_greet(client):
    response = client.post('/api/greet',
                          data=json.dumps({'name': 'Alice'}),
                          content_type='application/json')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['message'] == 'Hello, Alice!'
    assert 'timestamp' in data

def test_greet_default(client):
    response = client.post('/api/greet',
                          data=json.dumps({}),
                          content_type='application/json')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['message'] == 'Hello, Guest!'

def test_status(client):
    response = client.get('/api/status')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['service'] == 'app5-python-teamA'
    assert 'endpoints' in data
    assert len(data['endpoints']) == 5

def test_logs_endpoint(client):
    # Make some requests first
    client.get('/')
    client.get('/health')

    # Check logs
    response = client.get('/api/logs')
    assert response.status_code == 200
    data = json.loads(response.data)

    assert data['service'] == 'app5-python-teamA'
    assert 'total_requests' in data
    assert 'logs' in data
    assert data['total_requests'] > 0
    assert len(data['logs']) > 0
