## Components

### 1. **GitHub REST API**
   - The system interacts with the GitHub REST API to fetch repository data.

### 2. **GHRepositoryCrawler**
   - A service responsible for fetching repository data from the GitHub API.
   - It is scheduled to run periodically (using cron) to fetch updates from GitHub.

### 3. **ObservedRepo Service**
   - This service processes observed repositories data.

### 4. **Mysql DB**
   - A Mysql database is used to store repository information fetched from GitHub.

### 6. **REST Controllers**
   - RESTful endpoints for creating, updating, deleting, and retrieving repository data.
   - The controllers handle HTTP requests and provide the necessary responses.

## Technology Stack

- **Backend:** Java (Spring Boot)
- **Database:** Mysql
- **ORM:** JPA/Hibernate
- **Scheduler:** Cron Jobs
- **API:** GitHub REST API

## Setup

1. **Clone the repository:**
2. **Create a token from github:**
3. **set token on application.properties**
4. **build app with mvn clean package**
5. **docker-compose up**
6. **go to http://localhost:8080/swagger-ui/index.html**
7. **use  Post to create repositories**
8. **after 5 minutes you can check list of repositories and their status**