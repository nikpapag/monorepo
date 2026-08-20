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
    assert data['service'] == 'app4-python-teamB'
    assert data['team'] == 'TeamB'

def test_health(client):
    """Test health endpoint"""
    response = client.get('/health')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['status'] == 'healthy'

def test_batch_process(client):
    """Test batch processing endpoint"""
    response = client.post('/batch/process',
                          data=json.dumps({'batch_size': 100}),
                          content_type='application/json')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['status'] == 'success'
    assert 'processing_time' in data
    assert data['result']['processed_rows'] == 100

def test_batch_process_default(client):
    """Test batch processing with default size"""
    response = client.post('/batch/process',
                          data=json.dumps({}),
                          content_type='application/json')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['status'] == 'success'

def test_aggregate_data(client):
    """Test data aggregation endpoint"""
    response = client.post('/aggregate',
                          data=json.dumps({'size': 100}),
                          content_type='application/json')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['total_rows'] == 100
    assert 'aggregated_data' in data
