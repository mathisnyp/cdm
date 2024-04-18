from pathlib import Path
import jwt
from cryptography.x509 import load_pem_x509_certificate
from cryptography.hazmat.primitives import serialization
import sys

jwt_instance = jwt.JWT()

def decode_and_validate_token(access_token):
    public_key_text = Path('/app/public_key.pem').read_text()
    public_key = load_pem_x509_certificate(public_key_text.encode('utf-8')).public_key()
    public_key = public_key.public_bytes(encoding=serialization.Encoding.PEM, format=serialization.PublicFormat.SubjectPublicKeyInfo)
    public_key_jwk = jwt.jwk_from_pem(public_key)

    return jwt_instance.decode(access_token,key=public_key_jwk,algorithms=["RS256"], do_time_check=False)