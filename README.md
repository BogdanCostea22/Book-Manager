# Book Manager

Book Manager is a Spring Boot application that provides a RESTful API for managing books. It integrates with the Open Library API and uses a PostgreSQL database for local storage.

## Prerequisites

- Docker
- Docker Compose
- Java 21 or later
- Maven (optional, if you want to run without Docker)

## Project Structure

- `src/main/kotlin/` - Kotlin source files
   - `controller/` - REST API controllers
   - `service/` - Business logic
   - `repository/` - Data access layer
   - `model/` - Data models
   - `client/` - External API client (Open Library)
- `src/test/kotlin/` - Test files
- `src/main/resources/` - Application properties and static resources
- `Dockerfile` - Docker configuration for the application
- `docker-compose.yml` - Docker Compose configuration

## Prerequisites

- Docker
- Docker Compose

## Running the Application

1. Clone the repository:
   ```
   git clone https://github.com/BogdanCostea22/Book-Manager.git
   cd Book-Manager
   ```

2. Build and run the application using Docker Compose:
   ```
   docker-compose up --build
   ```

   This command will:
   - Build the Spring Boot application
   - Start a PostgreSQL database
   - Run the application and connect it to the database

3. The application will be available at `http://localhost:8080`

## API Endpoints

- `GET /api/v1/books` - Retrieve all books
- `GET /api/v1/books/{id}` - Retrieve a specific book
- `POST /api/v1/books` - Create a new book
- `GET /api/v1/books/search?title={title}` - Search books by title

## Running Tests

To run the tests, use the following command:

```
./gradlew test
```

For integration tests:

```
./gradlew test
```

## Future Improvements

1. Introduce Coroutines:
   - Update the project to use Kotlin coroutines for asynchronous programming.
   - Modify service methods to be suspend functions.
   - Use `kotlinx-coroutines-reactor` to bridge with Spring's reactive types.

   Example:
   ```kotlin
   suspend fun getBook(id: String): Book = withContext(Dispatchers.IO) {
       bookRepository.findById(id).awaitSingle()
   }
   ```

2. Use Reactive Database Client:
   - Replace Spring Data JPA with Spring Data R2DBC for reactive database operations.
   - Update repositories to extend from `ReactiveCrudRepository`.
   - Modify service layer to work with reactive types (Flux/Mono).

   Example:
   ```kotlin
   interface BookRepository : ReactiveCrudRepository<Book, String> {
       fun findByTitleContaining(title: String): Flux<Book>
   }
   ```

3. Implement Caching:
   - Add caching for frequently accessed data to improve performance.

4. Enhance Error Handling:
   - Implement a global exception handler for consistent error responses.

5. Add API Documentation:
   - Integrate Swagger/OpenAPI for automated API documentation.

6. Implement Authentication and Authorization:
   - Add Spring Security to protect endpoints.

7. Containerize for Production:
   - Optimize the Dockerfile for a production environment.

8. Implement Logging:
   - Add comprehensive logging throughout the application.

9. Set Up CI/CD:
   - Configure GitHub Actions for continuous integration and deployment.
