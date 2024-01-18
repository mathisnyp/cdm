from pydantic import BaseModel


class LoggedInSuccessfullyDTO(BaseModel):
    success: bool
    message: str
