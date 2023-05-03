# Challenge Login & Register 

This project implements a JWT-based authentication system for a user registration and login service. The system uses a MongoDB database to store the information of registered users. In addition, the application allows the authenticated user to get the count of users stored in the system. 

## Language and tools
    - Java 17
    - Gradle-Kotlin
    - Spring
    - MongoDB
    - Postman

## Installation
To install the project, follow the steps below:

- Download or clone the repository on your local machine.

- Open a terminal and navigate to the root folder of the project.

- Run the following command to compile and package the project:

        gradle build

- Run the following command to start the server:

        gradle bootRun

- The server will be available at http://localhost:8080

## API Endpoints

### Register
To register a user, send an HTTP POST request to the following URL:

        POST /api/auth/register

The request must include the following parameters:

- name: the user's name (string).
- email: the user's email (string).
- password: the user's password (string).

### Login
To authenticate a registered user, send an HTTP POST request to the following URL:

        POST /api/auth/login

The request must include the following parameters:

- email: the registered user's email (string).
- password: the password of the registered user (string).

### Count
To get the total users registered in the database, send an HTTP GET request to the following URL: 

        GET /api/users/count

