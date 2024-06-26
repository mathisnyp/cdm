from auth.Utils import crud, database
from fastapi import FastAPI, Depends, HTTPException, APIRouter
from sqlalchemy.orm import Session
from auth.dto.create_user_dto import CreateUserDTO
from auth.dto.login_user_dto import LoginDTO
from auth.dto.user_dto import UserDTO
from auth.dto.jwt_dto import JWTDTO
from auth.dto.logged_in_successfully_dto import LoggedInSuccessfullyDTO
from auth.Utils.jwt_generator import generate_jwt
from fastapi.responses import PlainTextResponse

# from . import crud, models, database


router = APIRouter(prefix="/auth-service")


# @router.get("")
# @router.get("/")
# @app.get("/auth-service/")
# Dependency to get the database session
def get_db():
    db = database.init()
    try:
        yield db
    finally:
        db.close()

@router.post("/users/", response_model=UserDTO)
def create_user(create_user_dto: CreateUserDTO, db: Session = Depends(get_db)) -> UserDTO:
    db_user = crud.get_user_by_username(db, create_user_dto.username)
    if db_user:
        raise HTTPException(status_code=400, detail="Username already registered")
    db_user: User = crud.create_user(db, create_user_dto.username, create_user_dto.password)
    return UserDTO(id=db_user.id, password=db_user.password, username=db_user.username)


@router.get("/users/{user_id}", response_model=UserDTO)
def read_user(user_id: int, db: Session = Depends(get_db)) -> UserDTO:
    db_user = crud.get_user(db, user_id)
    if db_user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return UserDTO(id=db_user.id, password=db_user.password, username=db_user.username)


@router.post("/login", response_model=LoggedInSuccessfullyDTO)
def login(login_dto: LoginDTO, db: Session = Depends(get_db)) -> LoggedInSuccessfullyDTO:
    user = crud.get_user_by_username(db, login_dto.username)
    if user is None or not crud.verify_password(login_dto.password, user.password):
        raise HTTPException(status_code=401, detail="Invalid credentials")

    return LoggedInSuccessfullyDTO(success=True, message="Login successful")


@router.get("/health", response_class=PlainTextResponse)
def healthcheck():
    return "200"

@router.post("/get_jwt")
def get_jwt(login_dto: LoginDTO, db: Session = Depends(get_db)) -> JWTDTO:
    user = crud.get_user_by_username(db, login_dto.username)
    if user is None or not crud.verify_password(login_dto.password, user.password):
        raise HTTPException(status_code=401, detail="Invalid credentials")

    token = generate_jwt(user.id, "http://localhost:8000/api")

    return JWTDTO(token=token)


app = FastAPI()
app.include_router(router)
