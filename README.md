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
