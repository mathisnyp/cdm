from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from models import Base

DATABASE_URL = "sqlite:///./test.db"
engine = create_engine(DATABASE_URL)

def create_tables():
    Base.metadata.create_all(bind=engine)
    print(repr(Base.metadata.tables))

if __name__ == "__main__":
    create_tables()
