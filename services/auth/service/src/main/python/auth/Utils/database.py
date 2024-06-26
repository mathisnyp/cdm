import os
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from .models import Base
from sqlalchemy.orm import sessionmaker

def init():
    DATABASE_URL = os.getenv("database-url")

    engine = create_engine(DATABASE_URL)

    Base.metadata.create_all(bind=engine)

    SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
    return SessionLocal()