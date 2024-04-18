from pydantic import BaseModel

class JWTDTO(BaseModel):
    token: str