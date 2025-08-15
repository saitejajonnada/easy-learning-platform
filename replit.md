# Easy Learning API

## Overview

The Easy Learning API is a Spring Boot-based educational platform that provides a comprehensive learning management system. It offers user authentication, course management, lesson tracking, enrollment handling, and quiz functionality. The system is designed as a RESTful API with JWT-based authentication and uses PostgreSQL for data persistence with Flyway for database migrations.

## User Preferences

Preferred communication style: Simple, everyday language.

## System Architecture

### Backend Framework
- **Spring Boot**: Core framework providing dependency injection, auto-configuration, and embedded server capabilities
- **Spring Security**: Handles authentication and authorization with JWT token-based security
- **Spring Data JDBC**: Uses JdbcTemplate for database operations, providing fine-grained control over SQL queries

### Authentication & Authorization
- **JWT (JSON Web Tokens)**: Stateless authentication mechanism for API security
- **JwtUtil**: Custom utility class for token generation, validation, and claims extraction
- **SecurityConfig**: Centralized security configuration for endpoint protection and CORS handling

### Data Access Layer
- **DAO Pattern**: Repository classes (UserDao, CourseDao, etc.) encapsulate database operations
- **JdbcTemplate**: Direct SQL execution for performance and control over database interactions
- **Flyway**: Database migration tool for version-controlled schema changes

### API Design
- **RESTful Architecture**: Standard HTTP methods and status codes for resource manipulation
- **DTO Pattern**: Data Transfer Objects for request/response serialization and validation
- **Layered Architecture**: Clear separation between controllers, services, and data access layers

### Database Schema
- **Users**: Authentication and profile management
- **Courses**: Course metadata and content organization
- **Lessons**: Individual learning units within courses
- **Enrollments**: User-course relationship tracking
- **Quizzes & Questions**: Assessment functionality with question-answer management

### Build & Deployment
- **Gradle**: Build automation and dependency management
- **Replit Deployment**: Configured for cloud deployment with environment variable support
- **Docker**: Optional containerization for local development

## External Dependencies

### Database
- **PostgreSQL**: Primary relational database for data persistence
- **Flyway**: Database migration and version control

### Spring Ecosystem
- **Spring Boot Starter Web**: REST API development
- **Spring Boot Starter Security**: Authentication and authorization
- **Spring Boot Starter JDBC**: Database connectivity and operations
- **Spring Boot Starter Test**: Testing framework integration

### Authentication
- **JWT Libraries**: JSON Web Token implementation for stateless authentication

### Development Tools
- **Gradle**: Build system and dependency management
- **Docker**: Optional containerization for local PostgreSQL instance

### Deployment Platform
- **Replit**: Cloud-based development and hosting environment
- **Environment Variables**: Configuration management for database credentials and secrets