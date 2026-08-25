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
    assert data['validated'] == True

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
    assert len(data['endpoints']) == 4
    assert 'validation_rules' in data

def test_greet_no_body(client):
    response = client.post('/api/greet',
                          data=None,
                          content_type='application/json')
    assert response.status_code == 400
    data = json.loads(response.data)
    assert data['code'] == 'MISSING_BODY'

def test_greet_name_too_long(client):
    long_name = 'A' * 101
    response = client.post('/api/greet',
                          data=json.dumps({'name': long_name}),
                          content_type='application/json')
    assert response.status_code == 400
    data = json.loads(response.data)
    assert data['code'] == 'NAME_TOO_LONG'

def test_greet_invalid_characters(client):
    response = client.post('/api/greet',
                          data=json.dumps({'name': 'Alice@#$'}),
                          content_type='application/json')
    assert response.status_code == 400
    data = json.loads(response.data)
    assert data['code'] == 'INVALID_NAME'

def test_greet_valid_with_spaces(client):
    response = client.post('/api/greet',
                          data=json.dumps({'name': 'Alice Smith'}),
                          content_type='application/json')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['message'] == 'Hello, Alice Smith!'
