from Utils import crud, models, database
from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session
from dto.create_user_dto import CreateUserDTO
from dto.login_user_dto import LoginDTO
from dto.user_dto import UserDTO
from dto.logged_in_successfully_dto import LoggedInSuccessfullyDTO

# from . import crud, models, database

app = FastAPI()


class Gen:
    def __init__(self):
        self.gen_mode = False


gen = Gen()


# Dependency to get the database session
def get_db():
    print(gen.gen_mode)
    if gen.gen_mode:
        return None
    db = database.SessionLocal()
    try:
        yield db
    finally:
        db.close()


@app.post("/users/", response_model=UserDTO)
def create_user(create_user_dto: CreateUserDTO, db: Session = Depends(get_db)) -> UserDTO:
    db_user = crud.get_user_by_username(db, create_user_dto.username)
    if db_user:
        raise HTTPException(status_code=400, detail="Username already registered")
    db_user: User = crud.create_user(db, create_user_dto.username, create_user_dto.password)
    return UserDTO(id=db_user.id, password=db_user.password, username=db_user.username)


@app.get("/users/{user_id}", response_model=UserDTO)
def read_user(user_id: int, db: Session = Depends(get_db)) -> UserDTO:
    db_user = crud.get_user(db, user_id)
    if db_user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return UserDTO(id=db_user.id, password=db_user.password, username=db_user.username)


@app.post("/login", response_model=LoggedInSuccessfullyDTO)
def login(login_dto: LoginDTO, db: Session = Depends(get_db)) -> LoggedInSuccessfullyDTO:
    user = crud.get_user_by_username(db, login_dto.username)
    if user is None or not crud.verify_password(login_dto.password, user.password):
        raise HTTPException(status_code=401, detail="Invalid credentials")

    return LoggedInSuccessfullyDTO(success=True, message="Login successful")
