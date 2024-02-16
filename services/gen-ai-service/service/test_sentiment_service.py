from fastapi.testclient import TestClient
from service.sentiment_service import app  # Replace 'your_fastapi_app_file' with the name of your FastAPI app file

client = TestClient(app)

def test_analyze_sentiment_positive_rescue():
    response = client.post("/analyze/", json={
        "id": 1,
        "name": "Positive Incident",
        "location": "Test Location",
        "emergencyType": "Rescue",
        "emergencySeverity": "High",
        "generalDescription": "Rescue teams arrived quickly and provided help.",
        "timeReceived": "2024-02-15T10:00:00"
    })
    assert response.status_code == 200
    data = response.json()
    assert data['sentiment_score'] > 0  # Expecting a positive score due to the presence of "rescue"

def test_analyze_sentiment_negative_flood():
    response = client.post("/analyze/", json={
        "id": 2,
        "name": "Negative Incident",
        "location": "Test Location 2",
        "emergencyType": "Flood",
        "emergencySeverity": "Severe",
        "generalDescription": "The flood caused devastating damage to the area.",
        "timeReceived": "2024-02-15T11:00:00"
    })
    assert response.status_code == 200
    data = response.json()
    assert data['sentiment_score'] < 0  # Expecting a negative score due to the presence of "flood"

def test_analyze_sentiment_neutral():
    response = client.post("/analyze/", json={
        "id": 3,
        "name": "Neutral Incident",
        "location": "Test Location 3",
        "emergencyType": "Drill",
        "emergencySeverity": "None",
        "generalDescription": "This is a scheduled drill, no actual emergency.",
        "timeReceived": "2024-02-15T12:00:00"
    })
    assert response.status_code == 200
    data = response.json()
    assert data['sentiment_score'] == 0  # Expecting a neutral score due to lack of keywords from lexicon
