# Observatoire Citoyen du Climat – Web Application

This repository contains the web application for the **Observatoire Citoyen du Climat** project. It is developed as part of a Bachelor's thesis at HEIG-VD.

The application provides the database and the backend REST API used by the mobile application and a web administration portal for managing users and measurements.

You can find more about the mobile application [here](https://github.com/Observatoire-du-Climat/Mobile).

## Context

This application was developed as part of a Bachelor's thesis at HEIG-VD in collaboration with the **Parc naturel régional Jura vaudois**.

The objective of the project is to provide a digital platform allowing citizen observers to collect and submit climate-related observations.

## Features

### REST API

The application exposes a REST API used by the mobile application to:

- Register and authenticate users
- Retrieve and update user information
- Submit climate measurements
- Retrieve measurement history
- Update and delete climate measurements

All the information about the endpoint and their responses can be found here : **link to doc**.

### Administration Portal

The web administration portal allows administrators to:

- View, search and validate registered users 
- View and manage measurements
- Send push notifications to mobile application users
- Export collected data to Excel files

## Technologies

The backend is built using:

- Java 21 
- Quarkus 
- Jakarta 
- Hibernate ORM 
- PostgreSQL 
- Qute 
- Apache POI 
- Firebase Cloud Messaging 
- Maven 
- Docker

## Project Structure

The application follows this structure :

`src/main/java/ch/heigvd`

- `dto` – Data Transfer Objects used between application layers
- `entity` – JPA entities representing the application's data model
- `resource/api` – REST API endpoints used by the mobile application
- `resource/admin` – Web resources for the administration portal
- `service` – Application business logic
- `firebase` - Configuration and methods about Firebase Cloud Messaging 

## Requirements

To run the application locally, you need:

* Java 21
* Maven
* PostgreSQL

The whole application can be run using Docker Compose as well.

## Configuration

Firebase credentials are required to send push notifications. The path to the Firebase service account credentials must be provided through the following environment variable:

```
FIREBASE_CREDENTIALS_PATH=/path/to/firebase-service-account.json
```

## Running the Application

### Development Mode

The application can be started in development mode with:

```
./mvnw quarkus:dev
```

The application is then available at:

```
http://localhost:8080
```

The administration portal is available at:

```
http://localhost:8080/admin
```

## Running Tests

Run all tests with:

```
./mvnw test
```

## Docker

The application can be packaged with:

```
./mvnw package
```

A Docker image can then be built using the Dockerfile provided by the project.

For example:

```
docker build -f src/main/docker/Dockerfile.jvm -t observatoire-climat-web .
```

The application can also be deployed alongside a PostgreSQL database using Docker Compose.

## CI/CD

A CI/CD pipeline is configured using GitHub Actions.

The pipeline:
- Runs automated tests for pull requests 
- Runs automated tests when changes are pushed to the main branch 
- Builds the application 
- Creates a Docker image 
- Publishes the Docker image to the GitHub Container Registry

The Docker image can then be pulled and deployed on the production server.