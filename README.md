# DeployTracker

DeployTracker is a Spring Boot REST API for recording and viewing application deployments. The first release provides a local, in-memory deployment history with validated request data and a layered application structure.

## First release scope

The current release supports:

- Creating a deployment record
- Listing all deployment records
- Retrieving a deployment by ID
- Validating required request fields
- Restricting environments and statuses to known enum values
- Automatically recording the deployment time
- Persisting data in an in-memory H2 database

This release implements the **Create** and **Read** portions of CRUD. Update and Delete operations are planned for a later release.

## Technology stack

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Jakarta Validation
- H2 Database
- Maven Wrapper
- JUnit 5

## Project structure

```text
src/
├── main/
│   ├── java/com/leon/deploytracker/
│   │   ├── DeploytrackerApplication.java
│   │   ├── controller/
│   │   │   └── DeploymentController.java
│   │   ├── dto/
│   │   │   └── CreateDeploymentRequest.java
│   │   ├── model/
│   │   │   ├── Deployment.java
│   │   │   ├── Environment.java
│   │   │   └── Status.java
│   │   ├── repository/
│   │   │   └── DeploymentRepository.java
│   │   └── service/
│   │       └── DeploymentService.java
│   └── resources/
│       └── application.yaml
└── test/
    └── java/com/leon/deploytracker/
        └── DeploytrackerApplicationTests.java
```

### Application layers

| Layer | Responsibility |
| --- | --- |
| Controller | Receives HTTP requests and returns HTTP responses |
| DTO | Defines and validates the data accepted by the API |
| Service | Applies application logic and coordinates persistence |
| Model | Defines the deployment entity and enum values |
| Repository | Provides database operations through Spring Data JPA |

The request flow is:

```text
HTTP request → Controller → DTO validation → Service → Repository → H2 database
```

## Deployment data

A deployment contains:

| Field | Type | Description |
| --- | --- | --- |
| `id` | Long | Database-generated identifier |
| `applicationName` | String | Name of the deployed application |
| `environment` | Environment | Target deployment environment |
| `version` | String | Deployed application version |
| `status` | Status | Current deployment status |
| `deployedAt` | LocalDateTime | Time assigned by the service when the record is created |

Supported environments:

- `DEVELOPMENT`
- `STAGING`
- `PRODUCTION`

Supported statuses:

- `PENDING`
- `IN_PROGRESS`
- `SUCCESS`
- `FAILED`

Enum values are stored by name instead of numeric position.

## Running locally

### Prerequisite

Install JDK 17 or newer. Maven does not need to be installed because the project includes the Maven Wrapper.

### Start the application

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
./mvnw.cmd spring-boot:run
```

The API starts at `http://localhost:8080`.

The H2 database is in memory, so deployment records are cleared whenever the application stops.

## API endpoints

| Method | Endpoint | Description | Successful response |
| --- | --- | --- | --- |
| `POST` | `/deployments` | Create a deployment | `201 Created` |
| `GET` | `/deployments` | List all deployments | `200 OK` |
| `GET` | `/deployments/{id}` | Retrieve a deployment by ID | `200 OK` or `404 Not Found` |

### Create a deployment

```bash
curl -X POST http://localhost:8080/deployments \
  -H "Content-Type: application/json" \
  -d '{
    "applicationName": "payment-service",
    "environment": "PRODUCTION",
    "version": "1.0.0",
    "status": "SUCCESS"
  }'
```

Example response:

```json
{
  "id": 1,
  "applicationName": "payment-service",
  "environment": "PRODUCTION",
  "version": "1.0.0",
  "status": "SUCCESS",
  "deployedAt": "2026-10-08T13:30:00"
}
```

`applicationName` and `version` cannot be blank. `environment` and `status` cannot be null and must match one of the supported enum values. Invalid requests return `400 Bad Request`.

### List deployments

```bash
curl http://localhost:8080/deployments
```

### Retrieve a deployment

```bash
curl http://localhost:8080/deployments/1
```

## Running tests

```bash
./mvnw test
```

The current automated test verifies that the Spring application context loads successfully.

## Next steps

- Add controller tests for all endpoints and validation cases
- Add Update and Delete endpoints to complete CRUD support
- Return consistent API error responses
- Add filtering, sorting, and pagination
- Add GitHub Actions continuous integration
- Replace the local in-memory database with PostgreSQL and Flyway migrations
