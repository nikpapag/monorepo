import pytest
import json
import numpy as np
from app import app, _model_cache

@pytest.fixture
def client():
    app.config['TESTING'] = True
    with app.test_client() as client:
        yield client

@pytest.fixture
def trained_client(client):
    """Fixture that provides a client with a trained model"""
    # Train the model
    client.post('/train')
    yield client
    # Cleanup
    _model_cache['model'] = None
    _model_cache['version'] = None
    _model_cache['trained_at'] = None

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
    assert 'model_version' in data
    assert 'trained_at' in data

    # Cleanup
    _model_cache['model'] = None

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

def test_predict_without_model(client):
    """Test prediction endpoint without trained model"""
    features = np.random.rand(20).tolist()
    response = client.post('/predict',
                          data=json.dumps({'features': features}),
                          content_type='application/json')
    assert response.status_code == 400
    data = json.loads(response.data)
    assert data['status'] == 'error'
    assert 'No trained model' in data['message']

def test_predict_with_model(trained_client):
    """Test prediction endpoint with trained model"""
    features = np.random.rand(20).tolist()
    response = trained_client.post('/predict',
                                   data=json.dumps({'features': features}),
                                   content_type='application/json')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['status'] == 'success'
    assert 'predictions' in data
    assert 'probabilities' in data
    assert 'model_version' in data
    assert data['num_samples'] == 1
    assert len(data['predictions']) == 1
    assert len(data['probabilities']) == 1

def test_predict_multiple_samples(trained_client):
    """Test prediction with multiple samples"""
    features = np.random.rand(5, 20).tolist()
    response = trained_client.post('/predict',
                                   data=json.dumps({'features': features}),
                                   content_type='application/json')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['status'] == 'success'
    assert data['num_samples'] == 5
    assert len(data['predictions']) == 5
    assert len(data['probabilities']) == 5

def test_predict_missing_features(trained_client):
    """Test prediction without features"""
    response = trained_client.post('/predict',
                                   data=json.dumps({}),
                                   content_type='application/json')
    assert response.status_code == 400
    data = json.loads(response.data)
    assert data['status'] == 'error'
    assert 'Missing features' in data['message']

def test_predict_wrong_feature_count(trained_client):
    """Test prediction with wrong number of features"""
    features = np.random.rand(10).tolist()  # Wrong: 10 instead of 20
    response = trained_client.post('/predict',
                                   data=json.dumps({'features': features}),
                                   content_type='application/json')
    assert response.status_code == 400
    data = json.loads(response.data)
    assert data['status'] == 'error'
    assert 'Expected 20 features' in data['message']

def test_model_info_without_model(client):
    """Test model info endpoint without trained model"""
    response = client.get('/model/info')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['status'] == 'no_model'

def test_model_info_with_model(trained_client):
    """Test model info endpoint with trained model"""
    response = trained_client.get('/model/info')
    assert response.status_code == 200
    data = json.loads(response.data)
    assert data['status'] == 'ready'
    assert 'model_version' in data
    assert 'trained_at' in data
    assert data['model_type'] == 'RandomForestClassifier'
    assert data['n_estimators'] == 100
    assert data['n_features'] == 20
