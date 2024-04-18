# Use an official Python runtime as a parent image
FROM python:3.9

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY ./ /app
RUN mv ./src/main/python/auth ./
RUN rm -r ./src

COPY ./requirements.txt /app

# Copy private and public keys for JWT tokens
COPY ./src/main/python/auth/private_key.pem /app/private_key.pem
COPY ./src/main/python/auth/public_key.pem /app/public_key.pem

# Install any needed packages specified in requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Expose the port that FastAPI will run on
EXPOSE 80

# Command to run your application
CMD ["uvicorn", "auth.main:app", "--host", "0.0.0.0", "--port", "80"]
