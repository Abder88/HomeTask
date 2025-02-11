## Applied Best Practices
### 1. **Command Query Responsibility Segregation (CQRS)**
Command Query Responsibility Segregation (CQRS) is a pattern that separates the write (command) and read (query) models of an application, allowing for optimized processing paths for each. This separation can improve scalability, performance, and maintainability, especially in event-driven and microservices architectures.
ID Strategy: Combining UUID and INT
For a balanced approach, combine both UUID for external references and INT for internal use.
Use UUID when you need global uniqueness, security, or a distributed architecture.
Use INT (AUTO_INCREMENT) for better performance and simplicity in small-scale applications.
### 2. **Delegating Date Management to JPA**
Use @MappedSuperclass to delegate date management to JPA, allowing automatic timestamping:
### 3. **Query Optimization**
Use a QueryResult object to fetch only the necessary metadata instead of retrieving full entity attributes, reducing memory usage and transaction overhead.
Avoid holding an entity for the entire transaction unless necessary.
### 4. **Global Exception Handling**
Implement a global exception handler to personalize error messages and catch exceptions gracefully.
### 5. **REST API Best Practices**
Never expose entities in REST APIs—always use DTOs and map them using a tool like ModelMapper.
Optimize database interactions:
Avoid fetching entire entities if not needed—use references where possible.
Disable Open Session in View (spring.jpa.open-in-view=false) to prevent unnecessary persistence context retention.
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
