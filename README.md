# E-Signature Backend

A comprehensive electronic signature backend service built with Spring Boot that provides a complete digital document signing workflow.

## 🚀 Features

- **Document Management**: Upload, organize, and manage documents in folders
- **Digital Signatures**: Create and manage digital signatures with audit trails
- **Signature Requests**: Send signature requests to multiple recipients
- **Form Fields**: Dynamic form field creation and assignment
- **Templates**: Reusable document templates for common workflows
- **Notifications**: Real-time notifications for signature status updates
- **Activity Logging**: Comprehensive audit trail for all system activities
- **Document Versioning**: Track document changes and versions
- **Annotations**: Add comments and annotations to documents
- **Shared Documents**: Share documents with specific users or groups

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.5.3
- **Java Version**: 21
- **Database**: MySQL
- **Caching**: Redis
- **Messaging**: Apache Kafka
- **Build Tool**: Maven
- **ORM**: Spring Data JPA
- **Development Tools**: Spring Boot DevTools, Lombok

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- Java 21 or higher
- Maven 3.6+ (or use the included Maven wrapper)
- Docker and Docker Compose (for running dependencies)
- MySQL 8.0+ (or use Docker)
- Redis (or use Docker)
- Apache Kafka (optional, for messaging features)

## 🔧 Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd E-Signature
```

### 2. Start Dependencies with Docker

The project includes a `compose.yaml` file to quickly start MySQL and Redis:

```bash
docker-compose up -d
```

This will start:
- MySQL on port 3306 (database: `mydatabase`, user: `myuser`, password: `secret`)
- Redis on port 6379

### 3. Configure Application Properties

Update the configuration files based on your environment:

#### Development Environment (`src/main/resources/application-dev.properties`)
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/mydatabase
spring.datasource.username=myuser
spring.datasource.password=secret

# Redis Configuration
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.cache.type=redis

# Kafka Configuration (if using)
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=esignature-group
```

#### Production Environment (`src/main/resources/application-prod.properties`)
Configure production-specific settings as needed.

### 4. Build and Run

#### Using Maven Wrapper (Recommended)

**Windows:**
```cmd
.\mvnw.cmd spring-boot:run
```

**Unix/Linux/macOS:**
```bash
./mvnw spring-boot:run
```

#### Using Installed Maven
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/backend/esignature/
│   │   ├── ESignatureApplication.java      # Main application class
│   │   ├── cache/                          # Caching configurations
│   │   ├── config/                         # Application configurations
│   │   ├── controllers/                    # REST API controllers
│   │   ├── dto/                           # Data Transfer Objects
│   │   │   ├── requests/                   # Request DTOs
│   │   │   └── responses/                  # Response DTOs
│   │   ├── entities/                       # JPA entities
│   │   ├── enums/                         # Enumeration classes
│   │   ├── exceptions/                     # Custom exceptions
│   │   ├── mapper/                        # Entity-DTO mappers
│   │   ├── repositories/                   # Data access layer
│   │   ├── services/                       # Business logic layer
│   │   └── utils/                         # Utility classes
│   └── resources/
│       ├── application.properties          # Main configuration
│       ├── application-dev.properties      # Development configuration
│       ├── application-prod.properties     # Production configuration
│       ├── static/                        # Static resources
│       └── templates/                     # Template files
└── test/                                  # Test classes
```

## 🗃️ Database Entities

The application manages the following core entities:

- **Users**: System users and signature recipients
- **Documents**: Electronic documents for signing
- **DocumentPages**: Individual pages of documents
- **DocumentVersions**: Version control for documents
- **DocumentFolders**: Document organization
- **Folders**: Document storage folders
- **SignatureRequests**: Signature workflow requests
- **SignatureRecipients**: Recipients of signature requests
- **DigitalSignatures**: Digital signature data
- **SignatureAuditTrail**: Signature process audit logs
- **FormFields**: Dynamic form elements
- **AssignedFields**: Field assignments to recipients
- **Templates**: Reusable document templates
- **RequestTemplates**: Template-based requests
- **Annotations**: Document annotations and comments
- **Comments**: User comments on documents
- **SharedDocuments**: Document sharing management
- **RequestNotifications**: Notification management
- **ActivityLogs**: System activity tracking

## 🔌 API Endpoints

The application provides RESTful APIs for:

- Document management (upload, download, organize)
- Signature request workflows
- Digital signature creation and verification
- User management
- Template management
- Notification handling
- Activity tracking

*Note: Detailed API documentation will be available once the controllers are implemented.*

## 🏗️ Development

### Running Tests

```bash
./mvnw test
```

### Building for Production

```bash
./mvnw clean package
```

The JAR file will be created in the `target/` directory.

### Code Style

The project uses:
- **Lombok** for reducing boilerplate code
- **Spring Boot DevTools** for development productivity
- **JPA** for database interactions
- **Maven** for dependency management

## 🐳 Docker Support

The project includes Docker Compose support for easy dependency management. You can extend this to containerize the entire application:

```yaml
# Add to compose.yaml for the application
app:
  build: .
  ports:
    - "8080:8080"
  depends_on:
    - mysql
    - redis
  environment:
    - SPRING_PROFILES_ACTIVE=prod
```

## 📝 Configuration Profiles

The application supports multiple profiles:

- **dev**: Development environment (default)
- **prod**: Production environment
- **test**: Testing environment

Switch profiles by setting:
```properties
spring.profiles.active=prod
```

## 🔐 Security Considerations

- Configure proper database credentials
- Set up SSL/TLS for production
- Implement authentication and authorization
- Secure API endpoints
- Configure proper CORS settings
- Set up audit logging

## 📊 Monitoring & Logging

The application includes:
- Spring Boot Actuator (when enabled)
- Comprehensive activity logging
- Audit trail for all signature operations
- Error tracking and reporting

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Check the documentation
- Review the application logs

## 🚧 Development Status

This project is currently in development. Core features being implemented:
- [ ] API Controllers
- [ ] Service Layer Implementation
- [ ] Entity Relationships
- [ ] Authentication & Authorization
- [ ] File Upload/Download
- [ ] Digital Signature Processing
- [ ] Notification System
- [ ] Template Management

---

**Happy Coding! 🎉**
