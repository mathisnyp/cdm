from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from .models import Base
from sqlalchemy.orm import sessionmaker

DATABASE_URL = "postgresql://postgres:password@localhost:5432/AuthenticationService"

engine = create_engine(DATABASE_URL)

Base.metadata.create_all(bind=engine)

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)