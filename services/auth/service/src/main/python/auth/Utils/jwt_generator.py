from datetime import datetime, timedelta
from pathlib import Path
import jwt
from cryptography.hazmat.primitives import serialization
import sys

jwt_instance = jwt.JWT()

def generate_jwt(sub, aud):
    now = datetime.utcnow()
    payload = {
        "iss": "http://localhost:8000",
        "sub": sub,
        "aud": aud,
        "iat": now.timestamp(),
        "exp": (now + timedelta(hours=24)).timestamp(),
        "scope": "openid"
    }

    with open("/app/private_key.pem", 'rb') as pem_file:
        private_key = jwt.jwk_from_pem(pem_file.read())

    return jwt_instance.encode(payload=payload, key=private_key, alg="RS256")