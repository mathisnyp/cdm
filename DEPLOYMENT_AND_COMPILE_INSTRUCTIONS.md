# Compile Instructions

## Prerequisites

Make sure you have the following installed:

- [Node.js](https://nodejs.org/en/download/) (Version 20.9.0)
- [Gradle](https://gradle.org/install/) (Version 8.2)
- [Java](https://openjdk.org/projects/jdk/17/) (JDK 17)
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Python](https://www.python.org/downloads/) (Version 3.9)
- [Android SDK](https://developer.android.com/about/versions/14/setup-sdk)
- [IOS SDK](https://developer.apple.com/ios/)

## Compile Steps

(Depending on your system, especially the frontend build and export can take a good while)

1. To compile the backend run ./gradlew build
2. To install the frontend dependencies, run npm install in the frontend directory
3. To export the frontend for all platforms npx expo export in the frontend directory
4. To build all docker images, run docker-compose build in the root directory

# Deployment Instructions

## Local Deployment

1. To deploy the frontend, change the api gateway url (api-gateway.project-unified.eu) in the frontend to localhost:8090
2. To deploy the backend, run docker-compose up in the root directory

## Cloud Deploy

### Prerequisites

- Two subdomains
  - One for the frontend
  - One for the api-gateway
- A git repository with our code specifically the helm charts in infrastructure/helm
- A kubernetes cluster
  - Access to a docker registry
  - [ArgoCD](https://argo-cd.readthedocs.io/en/stable/) installed and configured on the cluster
- [kubectl](https://kubernetes.io/docs/reference/kubectl/) installed on your system

### Steps

1. Publish the images to the docker registry using docker-compose push
2. Adjust the images used in the helm charts to the ones published to your repository
3. Adjust the values in the helm charts to the new frontend and api-gateway urls
4. Adjust the values for ingres in api-gateway-ingress.yaml and frontend-ingress.yaml to your domain
5. Update the ingres configuration using these files
6. Use the ArgoCD UI or CLI https://argo-cd.readthedocs.io/en/stable/getting_started/ to configure apps for all of our
   services
   1. Use your git repository as the source of the helm charts
   2. Configure the applications according to your namespace
   3. Make sure automatic synchronization is enabled to allow ArgoCD to regularly pull new changes to your helm charts

## App deployment

### Requisites

Since we never published our app to the app stores, the Expo Go app is needed for Android and iOS.

### Steps

1. Open the Expo Go app
2. Run ./expo run in the frontend directory
3. Scan the QR code in your terminal with the Expo Go app
   (Make sure to configure the URLs in the Frontend to point to either your laptops api-gateway exposed on port 8090 or
   the api-gateway you deployed in the cloud)
