from fastapi import FastAPI, HTTPException, APIRouter
from pydantic import BaseModel, Field
from datetime import datetime
from textblob import TextBlob
from fastapi.responses import PlainTextResponse


router = APIRouter(prefix="/gen-ai-service")

# Define a Pydantic model that corresponds to the incident service's Report model
class IncidentReport(BaseModel):
    id: int = Field(default=None, alias='id')
    name: str = Field(default=None, alias='name')
    location: str = Field(default=None, alias='location')
    emergencyType: str = Field(default=None, alias='emergencyType')
    emergencySeverity: str = Field(default=None, alias='emergencySeverity')
    generalDescription: str = Field(default=None, alias='generalDescription')
    timeReceived: datetime = Field(default=None, alias='timeReceived')

    # Use Config to allow the use of the `Report` model's attribute names in Python's snake_case format
    class Config:
        allow_population_by_field_name = True
        schema_extra = {
            "example": {
                "id": 123,
                "name": "Incident Name",
                "location": "Location Description",
                "emergencyType": "Type of Emergency",
                "emergencySeverity": "Severity Level",
                "generalDescription": "Detailed description of the incident...",
                "timeReceived": "2024-02-15T10:00:00"
            }
        }

# Assume we have a dictionary where the keys are words/phrases and the values are their sentiment scores
disaster_lexicon = {
    "flood": -0.8,
    "rescue": 0.7,
    "evacuate": -0.5,
    "shelter": 0.5,
    "casualties": -1.0
}

def analyze_sentiment_with_lexicon(text):
    words = text.split()  # Simple word tokenization
    sentiment_score = 0
    words_found = 0

    for word in words:
        if word.lower() in disaster_lexicon:
            sentiment_score += disaster_lexicon[word.lower()]
            words_found += 1

    # Average sentiment score of found words
    if words_found > 0:
        return sentiment_score / words_found
    else:
        return 0  # Neutral sentiment if no specific words are found

# Define an endpoint for sentiment analysis
@router.post("/analyze/")
async def analyze_sentiment(report: IncidentReport):

    # Perform custom lexicon sentiment analysis
    try:
        sentiment_score = analyze_sentiment_with_lexicon(report.generalDescription)
        return {"sentiment_score": sentiment_score}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@router.get("/health", response_class=PlainTextResponse)
def healthcheck():
    return "200"

app = FastAPI()
app.include_router(router)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
