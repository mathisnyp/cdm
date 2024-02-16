from pydantic import BaseModel

class SampleDTO(BaseModel):
    attribute_one: str
    attribute_two: str
