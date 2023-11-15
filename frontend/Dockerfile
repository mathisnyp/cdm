FROM node:20.9.0-slim
LABEL authors="mathisno"

WORKDIR /app
RUN npm install -g serve

COPY ./dist ./dist


EXPOSE 3000
CMD [ "npx", "serve", "dist", "--single" ]
