Sure, here's an updated README file with installation instructions using Gradle:

# Webstore Application

## About The Project

The Webstore Application is an e-commerce platform where individual customers can buy products, and the owner can manage the webstore via the application. The application is built using Java programming language, Spring Boot framework for the backend, and React for the frontend. The project also uses Docker for CI/CD.

## Features

The Webstore Application comes with the following features:

- User authentication and authorization
- Search, filter, and browse products
- Add, edit, and delete products
- View and manage orders
- Cart management

## Installation

To use this application, you need to have the following software installed on your system:

- Java Development Kit (JDK) version 11 or later
- Gradle build tool version 7.0 or later
- Docker

Follow the steps below to install and run the application:

1. Clone the repository to your local machine using the command `git clone https://github.com/ssbwz/WebShopSpringBootReact.git`.
2. Navigate to the `backend` directory.
3. Build the application using Gradle by running the command `gradle build` in your terminal.
4. Start the application by running the command `gradle bootRun`.
5. Navigate to the `frontend` directory.
6. Install dependencies by running the command `npm install`.
7. Start the frontend server by running the command `npm start`.
8. Open your web browser and go to `http://localhost:3000` to access the application.

## Contributing

If you would like to contribute to the development of this application, you are welcome to submit a pull request.

## License

This application is released under the MIT License. You can find a copy of the license in the `LICENSE` file.
