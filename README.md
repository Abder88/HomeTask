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
3. **Set token on application.properties**
4. **Build app with mvn clean package (tests will run using MySQL Testcontainers)**
5. **Run docker-compose up --build**
6. **Go to swagger http://localhost:8080/swagger-ui/index.html**
7. **Enjoy creating references to your repositories (or fake ones) and checking their status**

## Personal Comments 
It was amazing to work on this task over the weekend. I spent around 4–5 hours fine-tuning the initial solution to match the requirements. I'm not usually a test person (I often delegate such tasks), but I learned a lot of new things. Thank you for taking the time to review my work.