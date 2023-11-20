from Utils import crud, models, database
from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session
#from . import crud, models, database

app = FastAPI()

# Dependency to get the database session
def get_db():
    db = database.SessionLocal()
    try:
        yield db
    finally:
        db.close()

@app.post("/users/")
def create_user(username: str, password: str, db: Session = Depends(get_db)):
    db_user = crud.get_user_by_username(db, username)
    if db_user:
        raise HTTPException(status_code=400, detail="Username already registered")
    return crud.create_user(db, username, password)

@app.get("/users/{user_id}")
def read_user(user_id: int, db: Session = Depends(get_db)):
    db_user = crud.get_user(db, user_id)
    if db_user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return db_user

