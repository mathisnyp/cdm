from fastapi.openapi.utils import get_openapi
from main import app
import json

#from fastapi import FastAPI

# with open('./openapi.json', 'w') as f:
#     json.dump(get_openapi(
#         title=app.title,
#         version=app.version,
#         openapi_version=app.openapi_version,
#         description=app.description,
#         routes=app.routes,
#         #openapi_prefix=app.openapi_prefix,
#     ), f)



i = get_openapi(title=app.title,version=app.version,openapi_version=app.openapi_version,description=app.description,routes=app.routes)
print(i.get("paths"))


    
