# JobTrackr - Spring Backend



## Tech Stack

### Backend
- **Framework**: Spring Boot 3.x
- **Language**: Java 17
- **Database**: PostgreSQL
- **Security**: Spring Security, JWT
- **API Documentation**: OpenAPI 3.0 (Swagger)
- **Build Tool**: Maven

## Project Structure
```
backend/
├── src/main/java/com/jobtracker/backend/
│   ├── config/          # Configuration classes
│   ├── controller/      # REST controllers
│   ├── dto/             # Data Transfer Objects
│   ├── entity/          # JPA entities
│   ├── repository/      # Data access layer
│   └── service/         # Business logic
└── src/main/resources/  # Configuration files
```

## API Endpoints

### Users
- `GET /api/users` - Get all users (ADMIN only)
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user



## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6.3 or higher
- PostgreSQL 13 or higher

### Installation
1. Clone the repository
2. Configure database connection in `application.properties`
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`

## API Documentation
Access the Swagger UI at: `http://localhost:8080/swagger-ui.html`

## Security
- All endpoints (except `/api/auth/**`) require authentication
- JWT token should be included in the `Authorization` header
- Password encryption using BCrypt
- CSRF protection enabled

## Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License
This project is licensed under the MIT License - see the LICENSE file for details.
