from Utils import crud, models, database
from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session
from dto.create_user_dto import CreateUserDTO
from dto.login_user_dto import LoginDTO

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
def create_user(create_user_dto: CreateUserDTO, db: Session = Depends(get_db)):
    db_user = crud.get_user_by_username(db, create_user_dto.username)
    if db_user:
        raise HTTPException(status_code=400, detail="Username already registered")
    return crud.create_user(db, create_user_dto.username, create_user_dto.password)

@app.get("/users/{user_id}")
def read_user(user_id: int, db: Session = Depends(get_db)):
    db_user = crud.get_user(db, user_id)
    if db_user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return db_user

@app.post("/login")
def login(login_dto: LoginDTO, db: Session = Depends(get_db)):
    user = crud.get_user_by_username(db, login_dto.username)
    if user is None or not crud.verify_password(login_dto.password, user.password):
        raise HTTPException(status_code=401, detail="Invalid credentials")
    return {"success": True, "message": "Login successful"}

