# Match Parser

Spring Boot application for importing, parsing and storing match results from PractiScore-like HTML pages.

## Project Overview

**Match Parser** is an educational client-server Java application developed with Spring Boot.
The project demonstrates the implementation of a REST-based backend, a relational database model, HTML parsing with Jsoup, a simple Thymeleaf web interface, Swagger API documentation, and Docker-based deployment.

The application allows a user to submit a URL, create an import job, fetch and save an HTML snapshot, parse match data and results, and store them in the database.

The application simulates a real-world use case of processing competition results from external web sources.

## Main Features

- REST API for import jobs, matches and results
- CRUD operations for main entities
- DTO usage between layers
- Relational data model with connected tables
- HTML parsing using Jsoup
- Snapshot-based import processing
- Demo import modes for stable testing
- Graceful handling of failed imports
- Thymeleaf web interface
- Swagger/OpenAPI documentation
- Docker and Docker Compose support

## Technology Stack

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Thymeleaf
- H2 Database
- Jsoup
- Lombok
- Spring Validation
- springdoc OpenAPI / Swagger
- Docker / Docker Compose

## Project Structure

```text
src/main/java/com/example/match_parser
├── config
├── controller
├── dto
├── entity
├── exception
├── parser
├── repository
└── service
```

## Main Entities

### ImportJob

Represents an import request and its processing status.

Main fields: `id`, `sourceUrl`, `status`, `requestedAt`, `finishedAt`, `errorMessage`

### Match

Represents a parsed match.

Main fields: `id`, `externalId`, `matchName`, `matchDate`, `sourceUrl`

### DivisionResult

Represents a parsed result row for a division.

Main fields: `id`, `divisionName`, `placeValue`, `competitorName`, `memberNumber`, `pointsValue`, `percentValue`, `timeValue`

## Data Relationships

- One `ImportJob` can be linked to one `Match`
- One `Match` can contain many `DivisionResult` records

## Import Processing Flow

1. User submits a source URL
2. Application creates an `ImportJob`
3. HTML document is fetched (or loaded from demo source)
4. HTML snapshot is saved into local storage
5. Parser extracts match data and result rows
6. Parsed data is saved into the database
7. Import status is updated to `SUCCESS` or `FAILED`

## Demo Test Scenarios

The application supports several import scenarios for demonstration.

These scenarios are designed to demonstrate both successful and failure cases of the import process.

### 1. Simple demo import

Use:

```
demo://sample
```

Expected result: import status `SUCCESS`, sample match is created, sample result rows are stored.

### 2. Realistic demo import

Use:

```
demo://realistic
```

Expected result: import status `SUCCESS`, realistic PractiScore-like HTML structure is parsed, match and results are stored in the database.

### 3. Failure scenario

Use a real or inaccessible PractiScore URL, for example:

```
https://practiscore.com/results/html/test
```

Expected result: import status `FAILED`, error message is stored in `ImportJob`.

## Running Locally

### 1. Start the application

```bash
./mvnw spring-boot:run
```

### 2. Open in browser

| Page | URL |
|------|-----|
| Home page | http://localhost:8080/ |
| Imports page | http://localhost:8080/imports |
| Matches page | http://localhost:8080/matches |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| H2 Console | http://localhost:8080/h2-console |

### 3. H2 Console settings

| Parameter | Value |
|-----------|-------|
| JDBC URL | `jdbc:h2:mem:testdb` |
| Username | `sa` |
| Password | *(empty)* |

## Running with Docker

### Build and start

```bash
docker compose up --build
```

### Open

| Page | URL |
|------|-----|
| Home page | http://localhost:8080/ |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |

## Snapshot Storage

This approach demonstrates a two-phase import pipeline:

1. Fetch and persist raw HTML snapshot
2. Parse stored snapshot into structured data

This improves reliability and allows debugging and reprocessing.

## REST API Endpoints

### Import Jobs

| Method | Endpoint |
|--------|----------|
| `POST` | `/api/import-jobs` |
| `POST` | `/api/import-jobs/{id}/process` |
| `GET` | `/api/import-jobs` |
| `GET` | `/api/import-jobs/{id}` |
| `DELETE` | `/api/import-jobs/{id}` |

### Matches

| Method | Endpoint |
|--------|----------|
| `POST` | `/api/matches` |
| `GET` | `/api/matches` |
| `GET` | `/api/matches/{id}` |
| `PUT` | `/api/matches/{id}` |
| `DELETE` | `/api/matches/{id}` |

### Results

| Method | Endpoint |
|--------|----------|
| `POST` | `/api/results` |
| `GET` | `/api/results` |
| `GET` | `/api/results/{id}` |
| `GET` | `/api/results/match/{matchId}` |
| `PUT` | `/api/results/{id}` |
| `DELETE` | `/api/results/{id}` |

## Web UI Pages

| Path | Description |
|------|-------------|
| `/` | Main page with import form and quick test scenarios |
| `/imports` | Import history |
| `/matches` | Saved matches |
| `/matches/{id}` | Match details and parsed results |

## Notes

- H2 is used as an in-memory database for simplicity.
- Database records are reset when the application restarts.
- Snapshot HTML files are stored separately in `import-storage`.
- Real PractiScore pages may return `403 Forbidden` due to access restrictions, therefore demo HTML sources are used for consistent testing.

## Possible Future Improvements

- Switch from H2 to PostgreSQL
- Support parsing multiple divisions from one match
- Add file upload for local HTML parsing
- Add automated tests
- Improve UI styling and filtering
- Persist database data in Docker using PostgreSQL

## Author

Yaroslav Tsyhanok  
Educational project for Software Engineering studies