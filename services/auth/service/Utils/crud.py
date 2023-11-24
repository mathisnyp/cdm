from passlib.context import CryptContext
from sqlalchemy.orm import Session
from .models import User
pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto") # TODO: move to utils/__init__.py

def get_user(db: Session, user_id: int):
    return db.query(User).filter(User.id == user_id).first()

def get_user_by_username(db: Session, username: str):
    return db.query(User).filter(User.username == username).first()

def create_user(db: Session, username: str, password: str):
    hashed_password = pwd_context.hash(password)
    db_user = User(username=username, password=hashed_password)
    print(f"User ID before commit: {db_user.id}")
    db.add(db_user)
    db.commit()
    db.refresh(db_user)
    print(f"User ID after commit: {db_user.id}")
    return db_user

def verify_password(plain_password: str, hashed_password: str) -> bool:
    return pwd_context.verify(plain_password, hashed_password)