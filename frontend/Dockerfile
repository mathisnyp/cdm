FROM node:20.9.0-slim
LABEL authors="mathisno"


WORKDIR /build
COPY . .
RUN rm -rf node_modules
RUN npm install
RUN npx expo export -p web

WORKDIR /app
RUN npm install -g serve

RUN mv /build/dist ./dist
RUN rm -rf /build


EXPOSE 3000
CMD [ "npx", "serve", "dist", "--single" ]
