# Opportunities Platform - Back-end API
## Short Description
The Opportunities Platform is a comprehensive system for managing assistance projects, volunteers, and partners with a complete set of administrative and public APIs. Built on Java 17 and Spring Boot 3.3.4, this platform provides secure management of donations, news, documents, and users through a role-based authentication system. The system uses MySQL for data storage, includes Telegram integration, supports project management, and provides robust endpoints for both administrative and public usage, all documented with OpenAPI and secured with JWT authentication.

***

# 🛠️ Technologies Used

## Programming Language
* **Java 17:** The version of Java used for building and running the application, offering features like enhanced pattern matching, records, and improved performance.

## Backend Framework
* **Spring Boot 3.3.4:** Main framework for building the backend API with embedded Tomcat server support.

## Database
* **MySQL:** Relational database management system for storing application data.
* **H2:** In-memory database used for testing purposes.
* **Liquibase:** Database version control and schema management tool.

## Security
* **Spring Security:** Provides security services, including authentication and authorization, for the application.
* **JWT (JSON Web Tokens):** For secure user authentication using tokens.

## ORM and Persistence
* **Spring Data JPA:** Simplifies data access and integration with the database through JPA.
* **Hibernate Validator:** Used for validating Java beans based on constraints.

## Code Quality & Static Analysis
* **Checkstyle:** Ensures code quality by enforcing a set of coding standards.
* **Lombok:** Reduces boilerplate code with annotations like @Getter, @Setter, and @ToString.
* **MapStruct:** Simplifies object mapping between Java beans.

* **JUnit 5:** Framework for writing and running tests.
* **TestContainers:** Provides lightweight, disposable containers for integration testing.
* **Spring Security Test:** Tools for testing security aspects in Spring applications.
## Documentation
* **Springdoc OpenAPI:** Automatically generates OpenAPI documentation for RESTful APIs.
## Build & Packaging
* **Maven:** Dependency management and build tool.
* **Spring Boot Maven Plugin:** For packaging the application into an executable JAR file.
## Docker
* **Docker Compose:** Manages multi-container Docker applications, useful for local development and testing.

***

# Administrative API Documentation

This document provides detailed information about the administrative endpoints available in the Opportunities project API. These endpoints are designed for administrators to manage various aspects of the system.

## Table of Contents
- [User Management](#user-management)
- [Allowed Emails Management](#allowed-emails-management)
- [News Management](#news-management)
- [Documents Management](#documents-management)
- [Donation Management](#donation-management)
- [Partners Management](#partners-management)
- [Project Management](#project-management)
- [Volunteer Management](#volunteer-management)
- [Telegram Integration](#telegram-integration)

## User Management

**Base URL**: `/admin/users`

| Endpoint | Method | Description | Required Role | Response Codes |
|----------|--------|-------------|--------------|----------------|
| `/{id}` | DELETE | Permanently removes a user account from the system | SUPER_ADMIN | 204 (Success), 404 (Not Found) |
| `/` | PUT | Updates basic information for a user account | SUPER_ADMIN, ADMIN, EDITOR | 200 (Success), 400 (Bad Request), 404 (Not Found) |
| `/image` | PUT | Updates the avatar/profile image for a user account | SUPER_ADMIN, ADMIN, EDITOR | 200 (Success), 400 (Bad Request), 404 (Not Found) |

## Allowed Emails Management

**Base URL**: `/admin/allowed_emails`

| Endpoint | Method | Description | Required Role | Response Codes |
|----------|--------|-------------|--------------|----------------|
| `/` | POST | Adds an email to the allowed email list for registration | SUPER_ADMIN | 201 (Created), 400 (Bad Request), 403 (Forbidden) |
| `/` | GET | Retrieves a paginated list of all allowed emails | SUPER_ADMIN | 200 (Success), 403 (Forbidden) |
| `/{id}` | DELETE | Deletes an allowed email from the list by its ID | SUPER_ADMIN | 200 (Success), 404 (Not Found), 403 (Forbidden) |

## News Management

**Base URL**: `/admin/news`

| Endpoint | Method | Description | Required Role | Response Codes |
|----------|--------|-------------|--------------|----------------|
| `/` | POST | Creates a new news article with title, content, and cover image | SUPER_ADMIN, ADMIN, EDITOR | 200 (Success), 400 (Bad Request), 403 (Forbidden) |
| `/{id}` | PUT | Updates title and content of an existing news article | SUPER_ADMIN, ADMIN, EDITOR | 200 (Success), 400 (Bad Request), 403 (Forbidden), 404 (Not Found) |
| `/image/{id}` | PUT | Updates the cover image of an existing news article | SUPER_ADMIN, ADMIN, EDITOR | 200 (Success), 400 (Bad Request), 403 (Forbidden), 404 (Not Found) |
| `/{id}` | DELETE | Deletes an existing news article by its ID | SUPER_ADMIN, ADMIN, EDITOR | 204 (No Content), 403 (Forbidden), 404 (Not Found) |

## Documents Management

**Base URL**: `/admin/documents`

| Endpoint | Method | Description | Required Role | Response Codes |
|----------|--------|-------------|--------------|----------------|
| `/` | POST | Adding a new document URL to the system | SUPER_ADMIN | 201 (Created), 400 (Bad Request), 403 (Forbidden) |
| `/{id}` | DELETE | Delete an existing document by its ID | SUPER_ADMIN | 204 (No Content), 403 (Forbidden), 404 (Not Found) |

## Donation Management

**Base URL**: `/admin/donations`

| Endpoint | Method | Description | Required Role | Response Codes |
|----------|--------|-------------|--------------|----------------|
| `/` | GET | Retrieves a paginated list of general donations | SUPER_ADMIN, ADMIN | 200 (Success), 403 (Forbidden) |

## Partners Management

**Base URL**: `/admin/partners`

| Endpoint | Method | Description | Required Role | Response Codes |
|----------|--------|-------------|--------------|----------------|
| `/` | GET | Fetches a paginated list of partners whose applications are pending approval | SUPER_ADMIN, ADMIN | 200 (Success) |
| `/{id}` | PATCH | Updates the status of a specific partner by ID | SUPER_ADMIN, ADMIN | 200 (Success), 404 (Not Found), 400 (Bad Request) |
| `/` | POST | Creates and adds a new partner to the system | SUPER_ADMIN, ADMIN | 201 (Created), 400 (Bad Request) |
| `/all` | GET | Fetches a paginated list of all partners | SUPER_ADMIN, ADMIN, EDITOR | 200 (Success) |

## Project Management

**Base URL**: `/admin/projects`

| Endpoint | Method | Description | Required Role | Response Codes |
|----------|--------|-------------|--------------|----------------|
| `/` | POST | Creates a new project based on the provided request | SUPER_ADMIN, ADMIN, EDITOR | 201 (Created), 400 (Bad Request) |
| `/{id}/status` | PATCH | Updates the status of the project with the given ID | SUPER_ADMIN, ADMIN, EDITOR | 200 (Success), 404 (Not Found), 400 (Bad Request) |
| `/{id}/donations` | GET | Retrieves a paginated list of donations for a specific project | SUPER_ADMIN, ADMIN | 200 (Success), 404 (Not Found), 403 (Forbidden) |

## Volunteer Management

**Base URL**: `/admin/volunteers`

| Endpoint | Method | Description | Required Role | Response Codes |
|----------|--------|-------------|--------------|----------------|
| `/` | POST | Allows administrators to directly add a new volunteer to the system | SUPER_ADMIN, ADMIN | 201 (Created), 400 (Bad Request), 403 (Forbidden) |
| `/{id}` | PATCH | Allows administrators to update the status of an existing volunteer | SUPER_ADMIN, ADMIN, EDITOR | 200 (Success), 400 (Bad Request), 403 (Forbidden), 404 (Not Found) |

## Telegram Integration

**Base URL**: `/admin/telegram`

| Endpoint | Method | Description | Required Role | Response Codes |
|----------|--------|-------------|--------------|----------------|
| `/notifyMe` | GET | Allows administrators to get a link to the Telegram bot for receiving notifications about website operations | SUPER_ADMIN, ADMIN, EDITOR | 200 (Success), 401 (Unauthorized) |

---

## Role Hierarchy

The API uses a role-based access control system with the following hierarchy:

1. **SUPER_ADMIN**: Has complete access to all administrative functions
2. **ADMIN**: Has access to most administrative functions except the most sensitive ones
3. **EDITOR**: Has limited administrative access focused on content management

All administrative endpoints require authentication and proper authorization based on the user's role.

***

# Public API Documentation

## Table of Contents
- [Authentication](#authentication)
- [Documents Management](#documents-management)
- [Donation Management](#donation-management)
- [News Management](#news-management)
- [Partners Management](#partners-management)
- [Project Management](#project-management)
- [User Management](#user-management)
- [Volunteer Management](#volunteer-management)

## Authentication

**Base URL**: `/auth`

| Endpoint | Method | Description | Request Format | Response Codes |
|----------|--------|-------------|--------------|----------------|
| `/registration` | POST | Registers a new team member with the provided information | multipart/form-data | 201 (Created), 400 (Bad Request) |
| `/login` | POST | Authenticates a team member using their credentials | JSON | 200 (Success), 401 (Unauthorized) |

## Documents Management

**Base URL**: `/public/documents`

| Endpoint | Method | Description | Parameters | Response Codes |
|----------|--------|-------------|------------|----------------|
| `/reports` | GET | Retrieves a paginated list of all report documents available for public viewing | Pageable | 200 (Success) |
| `/foundings` | GET | Retrieves a paginated list of all founding documents available for public viewing | Pageable | 200 (Success) |

## Donation Management

**Base URL**: `/public/donations`

| Endpoint | Method | Description | Request Format | Response Codes |
|----------|--------|-------------|--------------|----------------|
| `/` | POST | Generates a payment form for donation based on the provided details | JSON | 200 (Success), 400 (Bad Request) |

## News Management

**Base URL**: `/public/news`

| Endpoint | Method | Description | Parameters | Response Codes |
|----------|--------|-------------|------------|----------------|
| `/` | GET | Retrieves a paginated list of all news articles available for public viewing | Pageable | 200 (Success) |
| `/{id}` | GET | Retrieves a specific news article by its ID | Path variable: id | 200 (Success), 404 (Not Found) |

## Partners Management

**Base URL**: `/public/partners`

| Endpoint | Method | Description | Parameters/Format | Response Codes |
|----------|--------|-------------|-------------------|----------------|
| `/` | GET | Fetches a paginated list of general partner information | Pageable | 200 (Success) |
| `/{id}` | GET | Fetches detailed information about a specific partner by ID | Path variable: id | 200 (Success), 404 (Not Found) |
| `/` | POST | Submits a new partner application | multipart/form-data | 201 (Created), 400 (Bad Request) |

## Project Management

**Base URL**: `/public/projects`

| Endpoint | Method | Description | Parameters | Response Codes |
|----------|--------|-------------|------------|----------------|
| `/{id}` | GET | Retrieves a single project by its unique identifier | Path variable: id | 200 (Success), 404 (Not Found) |
| `/all` | GET | Retrieves a paginated list of all projects | Pageable | 200 (Success) |
| `/active` | GET | Retrieves a paginated list of projects that are currently active | Pageable | 200 (Success) |
| `/successful` | GET | Retrieves a paginated list of projects that have been successfully completed | Pageable | 200 (Success) |
| `/{projectId}/donate` | POST | Generates a payment link for a specific project based on donation details | Path variable: projectId, JSON body | 200 (Success), 400 (Bad Request), 404 (Not Found), 500 (Server Error) |

## User Management

**Base URL**: `/public/users`

| Endpoint | Method | Description | Parameters | Response Codes |
|----------|--------|-------------|------------|----------------|
| `/` | GET | Retrieves a paginated list of team members with general information | Pageable | 200 (Success), 400 (Bad Request) |

## Volunteer Management

**Base URL**: `/public/volunteers`

| Endpoint | Method | Description | Parameters/Format | Response Codes |
|----------|--------|-------------|-------------------|----------------|
| `/` | POST | Creates a new volunteer application with the provided information | multipart/form-data | 201 (Created), 400 (Bad Request) |
| `/active` | GET | Returns a paginated list of all active volunteers | Pageable | 200 (Success) |
| `/pending` | GET | Returns a paginated list of all pending volunteer applications | Pageable | 200 (Success) |
| `/rejected` | GET | Returns a paginated list of all rejected volunteer applications | Pageable | 200 (Success) |
