# ManaArchivesDB Card Data Microservice
This Spring Boot microservice is responsible for maintaining and serving data in the ManaArchivesDB, a key part of the ManaVault project. The service automates the process of downloading, storing, and retrieving bulk Magic: The Gathering card data, ensuring that your card database is consistently up-to-date.

## Features
- Data Fetching: Automatically fetches the latest bulk card data file from a specified URL.
- Database Synchronization: Parses the downloaded JSON file and updates the PostgreSQL database with the most recent card information.
- Efficient Data Retrieval: Implements pagination and optimized query mechanisms to ensure quick and responsive access to card data.
- Microservice Design: Operates independently within the ManaArchivesDB, focusing solely on managing and serving card data.


## Technologies Used
- Java Spring Boot: Powers the RESTful API and handles backend operations.
  - Spring Data JPA: Simplifies database operations and entity management.
  - Spring Web: Facilitates the creation of RESTful endpoints.
  - Flyway: Manages database migrations and schema updates.
  - Lombok: Reduces boilerplate code and enhances code readability.
  - Jackson: Handles JSON parsing and serialization.
  - Spring Boot Actuator: Provides operational endpoints for monitoring and managing the application.
- PostgreSQL: Stores and manages Magic: The Gathering card data.
- Docker: Containerized for easy deployment and scalability.
- Postman: Used for API testing and validation.

How It Works
Download & Parse: The microservice downloads the bulk card data file in JSON format from a specified URL.
Database Update: Parses the JSON data and synchronizes the PostgreSQL database, ensuring all card information is current.
API Access: Provides RESTful endpoints for retrieving card data, with pagination to efficiently handle large datasets.