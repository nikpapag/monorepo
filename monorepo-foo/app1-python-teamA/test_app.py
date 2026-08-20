import pytest
import json
from app import app

@pytest.fixture
def client():
    app.config['TESTING'] = True
    with app.test_client() as client:
        yield client

def test_home(client):
    """Test home endpoint"""
    response = client.get('/')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['service'] == 'app1-python-teamA'
    assert data['team'] == 'TeamA'
    assert data['type'] == 'resource-intensive'

def test_health(client):
    """Test health endpoint"""
    response = client.get('/health')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['status'] == 'healthy'

def test_train(client):
    """Test ML training endpoint"""
    response = client.post('/train')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['status'] == 'success'
    assert 'accuracy' in data
    assert 0 <= data['accuracy'] <= 1

def test_process_data(client):
    """Test data processing endpoint"""
    response = client.post('/process',
                          data=json.dumps({'size': 100}),
                          content_type='application/json')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert 'mean' in data
    assert 'std' in data
    assert data['shape'] == [100, 10]

def test_process_data_default_size(client):
    """Test data processing with default size"""
    response = client.post('/process',
                          data=json.dumps({}),
                          content_type='application/json')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['shape'] == [1000, 10]
