# timeseries_assignment

## Tech Stack
- Next.js
- Java Spring Boot
- PostgreSQL
- Docker

## How to Run
To run the application, execute the following command in the root directory:
```bash
docker-compose up
```

## Solution
### Important Files

#### Frontend
| Filename | Description |
|----------|-------------|
| [data-render.tsx](https://github.com/YoussefMorcos/take_home_assignment_timeseries/blob/main/frontend/src/app/data-render.tsx) | The UI |
| [api.ts](https://github.com/YoussefMorcos/take_home_assignment_timeseries/blob/main/frontend/src/app/api.ts) | The API |
| [page.tsx](https://github.com/YoussefMorcos/take_home_assignment_timeseries/blob/main/frontend/src/app/page.tsx) | Main Page, data fetching |

#### Backend
| Filename | Description |
|----------|-------------|
| [TSController.java](https://github.com/YoussefMorcos/take_home_assignment_timeseries/blob/main/backend/src/main/java/com/example/backend/TSController.java) | REST controller defining endpoints |
| [TSService.java](https://github.com/YoussefMorcos/take_home_assignment_timeseries/blob/main/backend/src/main/java/com/example/backend/TSService.java) | Service layer handling logic for data processing |
| [TSEntity.java](https://github.com/YoussefMorcos/take_home_assignment_timeseries/blob/main/backend/src/main/java/com/example/backend/TSEntity.java) | JPA entity representing the data model for the database |
| [TSRepository.java](https://github.com/YoussefMorcos/take_home_assignment_timeseries/blob/main/backend/src/main/java/com/example/backend/TSRepository.java) | Spring Data repository interface for database operations on the data |


#### Database
| Filename | Description |
|----------|-------------|
| [script.sql](https://github.com/YoussefMorcos/take_home_assignment_timeseries/blob/main/db/script.sql) | SQL script to initialize the PostgreSQL database schema |

## Assumptions Made
- Files might have the same name uploaded --> concat a number next to the original filename to represent that its a copy, escpecially if the data is exported from a source that uses the same filename everytime assumed (similar to operating systems)
- Files should have a valid ts column or it gets rejected
- Missing values or incorrect data type like strings for measures, skips adding a record for that measure 
- if a header is missing, data won't be added as there is no clear definition to add it under which measure name in the db
- Timestamp is assumed to have a specific format