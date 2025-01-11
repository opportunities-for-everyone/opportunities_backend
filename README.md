# API Documentation

## Authentication & Authorization
### Registration Flow
**Endpoint:** `POST /auth/registration`  
**Access:** Public  
**Consumes:** `multipart/form-data`

**Flow:**
1. User submits registration data with avatar image
2. System validates email uniqueness
3. Creates new User entity
4. Encodes password for security
5. Uploads avatar via ImageService
6. Assigns appropriate roles (ADMIN/EDITOR)
7. Saves user to database
8. Returns UserResponseDto with user details

### Login Flow
**Endpoint:** `POST /auth/login`  
**Access:** Public

**Flow:**
1. User submits credentials (email/password)
2. System authenticates credentials
3. Generates authentication token
4. Returns UserLoginResponseDto with token

## News Management

### Public Endpoints

#### Get All News
**Endpoint:** `GET /public/news`  
**Access:** Public

**Flow:**
1. Accepts pagination parameters
2. Retrieves paginated list of news articles
3. Returns Page<NewsResponseDto>

#### Get News by ID
**Endpoint:** `GET /public/news/{id}`  
**Access:** Public

**Flow:**
1. Validates news ID exists
2. Retrieves specific news article
3. Returns NewsResponseDto

### Admin Endpoints

#### Create News
**Endpoint:** `POST /admin/news`  
**Access:** ADMIN role  
**Consumes:** `multipart/form-data`

**Flow:**
1. Receives news content and cover image
2. Validates input data
3. Uploads cover image via ImageService
4. Creates new News entity
5. Sets creation timestamp and author
6. Saves to database
7. Returns NewsResponseDto

#### Update News Content
**Endpoint:** `PUT /admin/news/{id}`  
**Access:** EDITOR role

**Flow:**
1. Validates news exists
2. Updates title and content
3. Saves changes
4. Returns updated NewsResponseDto

#### Update News Image
**Endpoint:** `PUT /admin/news/image/{id}`  
**Access:** EDITOR role  
**Consumes:** `multipart/form-data`

**Flow:**
1. Validates news exists
2. Uploads new image via ImageService
3. Updates news with new image URL
4. Returns updated NewsResponseDto

#### Delete News
**Endpoint:** `DELETE /admin/news/{id}`  
**Access:** EDITOR role

**Flow:**
1. Validates news exists
2. Deletes news article
3. Returns 204 No Content

## Partner Management

### Public Endpoints

#### Get Partners List
**Endpoint:** `GET /public/partners`  
**Access:** Public

**Flow:**
1. Accepts pagination parameters
2. Returns paginated list of partner general info

#### Get Partner Details
**Endpoint:** `GET /public/partners/{id}`  
**Access:** Public

**Flow:**
1. Validates partner exists
2. Returns detailed partner information

#### Submit Partner Application
**Endpoint:** `POST /public/partners`  
**Access:** Public  
**Consumes:** `multipart/form-data`

**Flow:**
1. Receives partner details and logo
2. Uploads logo via ImageService
3. Creates Partner entity with PENDING status
4. Creates associated Director entity
5. Saves to database

### Admin Endpoints

#### Get Pending Partners
**Endpoint:** `GET /admin/partners`  
**Access:** ADMIN role

**Flow:**
1. Retrieves paginated list of pending partner applications
2. Returns Page<PartnerAllInfoDto>

#### Update Partner Status
**Endpoint:** `PATCH /admin/partners/{id}`  
**Access:** ADMIN role

**Flow:**
1. Validates partner exists
2. Updates partner status
3. Returns updated PartnerAllInfoDto

#### Add Partner Directly
**Endpoint:** `POST /admin/partners`  
**Access:** ADMIN role  
**Consumes:** `multipart/form-data`

**Flow:**
1. Receives partner details and logo
2. Uploads logo via ImageService
3. Creates Partner entity with ACTIVE status
4. Creates associated Director entity
5. Saves to database
6. Returns PartnerAllInfoDto

## Project Management

### Public Endpoints

#### Get Project by ID
**Endpoint:** `GET /public/projects/{id}`  
**Access:** Public

**Flow:**
1. Validates project exists
2. Returns ProjectResponseDto

#### Get All Projects
**Endpoint:** `GET /public/projects/all`  
**Access:** Public

**Flow:**
1. Accepts pagination parameters
2. Returns paginated list of all projects

#### Get Active Projects
**Endpoint:** `GET /public/projects/active`  
**Access:** Public

**Flow:**
1. Accepts pagination parameters
2. Returns paginated list of active projects

#### Get Successful Projects
**Endpoint:** `GET /public/projects/successful`  
**Access:** Public

**Flow:**
1. Accepts pagination parameters
2. Returns paginated list of completed successful projects

#### Generate Project Donation Payment
**Endpoint:** `POST /public/projects/{projectId}/donate`  
**Access:** Public

**Flow:**
1. Validates project exists and is active
2. Creates payment form via PaymentGenerationService
3. Returns HTML payment form

### Admin Endpoints

#### Create Project
**Endpoint:** `POST /admin/projects`  
**Access:** ADMIN role  
**Consumes:** `multipart/form-data`

**Flow:**
1. Receives project details and image
2. Validates input data
3. Uploads project image
4. Creates Project entity
5. Saves to database
6. Returns ProjectResponseDto

#### Update Project Status
**Endpoint:** `PATCH /admin/projects/{id}/status`  
**Access:** ADMIN role

**Flow:**
1. Validates project exists
2. Updates project status
3. Returns updated ProjectResponseDto

#### Get Project Donations
**Endpoint:** `GET /admin/projects/{id}/donations`  
**Access:** ADMIN role

**Flow:**
1. Validates project exists
2. Returns paginated list of project donations

## Donation Management

### Public Endpoints

#### Generate General Donation Form
**Endpoint:** `POST /public/donations`  
**Access:** Public

**Flow:**
1. Receives donation details
2. Generates payment form via PaymentGenerationService
3. Returns HTML payment form

### Admin Endpoints

#### Get General Donations
**Endpoint:** `GET /admin/donations`  
**Access:** ADMIN role

**Flow:**
1. Accepts pagination parameters
2. Returns paginated list of general donations

## Volunteer Management

### Public Endpoints

#### Submit Volunteer Application
**Endpoint:** `POST /public/volunteers`  
**Access:** Public  
**Consumes:** `multipart/form-data`

**Flow:**
1. Receives volunteer details and avatar
2. Validates input data
3. Uploads avatar via ImageService
4. Creates Volunteer entity with PENDING status
5. Saves to database
6. Returns VolunteerResponseDto

#### Get Active Volunteers
**Endpoint:** `GET /public/volunteers/active`  
**Access:** Public

**Flow:**
1. Accepts pagination parameters
2. Returns paginated list of active volunteers

#### Get Pending Volunteers
**Endpoint:** `GET /public/volunteers/pending`  
**Access:** Public

**Flow:**
1. Accepts pagination parameters
2. Returns paginated list of pending volunteer applications

#### Get Rejected Volunteers
**Endpoint:** `GET /public/volunteers/rejected`  
**Access:** Public

**Flow:**
1. Accepts pagination parameters
2. Returns paginated list of rejected volunteer applications

### Admin Endpoints

#### Add Volunteer Directly
**Endpoint:** `POST /admin/volunteers`  
**Access:** ADMIN role  
**Consumes:** `multipart/form-data`

**Flow:**
1. Receives volunteer details and avatar
2. Uploads avatar via ImageService
3. Creates Volunteer entity with ACTIVE status
4. Saves to database
5. Returns VolunteerResponseDto

#### Update Volunteer Status
**Endpoint:** `PATCH /admin/volunteers/{id}`  
**Access:** ADMIN role

**Flow:**
1. Validates volunteer exists
2. Updates volunteer status
3. Returns updated VolunteerResponseDto

## Payment Processing

### Payment Callback
**Endpoint:** `POST /public/payment/callback`  
**Access:** Public (Hidden - for payment system use)

**Flow:**
1. Receives payment data and signature
2. Validates payment signature
3. Parses payment data
4. Verifies payment status
5. Routes to appropriate donation processor (Project/General)
6. Updates relevant entities
7. Handles any errors with logging
