# JaxStarter4 - Teacher Management Application

Welcome to the repository of JaxStarter4, an application designed for managing teacher information!

## Overview

JaxStarter4 is an application that allows you to manage teacher information. This application is developed as part of an educational program and is distributed for learning purposes.

## Features

- Utilizes Hibernate for Database Operations: JaxStarter4 uses Hibernate for performing database operations, making it easy to manage teacher data.

- Data Validation with Hibernate Validator: The application incorporates Hibernate Validator to ensure the integrity of the data being processed.

- RESTful API: JaxStarter4 provides a RESTful API for managing teacher information, allowing easy integration with other applications.

- Jersey and Jackson Dependencies: The application utilizes the Jersey framework for building RESTful APIs and the Jackson library for JSON processing.

## Repository Structure

### DTO (Data Transfer Objects)

- `TeacherInsertDTO.java`: This class represents an object used for inserting and creating new teachers.
- `TeacherReadOnlyDTO.java`: This class provides read-only data for teachers.
- `UserDTO.java`: This class is used for managing user information.

### Model

- `Teacher.java`: This class represents the data model for teachers.

### REST Controllers

- `HelloRestController.java`: This controller provides a simple "Hello World!" endpoint for checking the system's functionality.
- `TeacherRestController.java`: This controller handles teacher management operations.

### Main Application

- `StarterApplication.java`: This is the main application class for JaxStarter4.

## Installation and Execution

1. Install the required dependencies and the Maven environment.

2. Download the source code of the application from the repository.

3. Run the application using the command `mvn spring-boot:run`.

4. Use Postman or any other REST API client to interact with the API endpoints.

## License

This application is distributed under the MIT License provided by GitHub.

## Contact

For any questions, comments, or issues, feel free to contact us at dimthoma@aueb.gr.
