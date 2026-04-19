# MoveMate

MoveMate is a web platform designed to help users discover, book, and manage nearby sports activities.  
The project was developed as part of the **ISST** course and follows a **full-stack architecture** with a **Spring Boot backend** and a **React + Vite frontend**.

## Features

### For users
- Register and log in securely
- Browse and search activities
- View activity details
- Discover nearby activities on a map
- Book and cancel reservations
- Manage personal profile and bookings

### For organizers
- Register as an organizer
- Create new activities
- Edit or cancel existing activities
- View and manage their own activities
- Manage organizer profile

### For administrators
- Review pending activities
- Approve or reject activities
- Delete activities when necessary

## Tech Stack

### Frontend
- React
- Vite
- React Router
- Axios
- Google Maps API

### Backend
- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT authentication
- Spring Validation
- Spring Mail

### Database
- H2 database
- PostgreSQL support included in the project configuration

### Testing
- JUnit
- Selenium
- WebDriverManager

## Project Structure

```text
movemate/
├── movemate-back/     # Spring Boot backend
└── movemate-front/    # React frontend
```

### Backend structure
```text
movemate-back/
├── src/main/java/.../controllers
├── src/main/java/.../services
├── src/main/java/.../repositories
├── src/main/java/.../models
├── src/main/java/.../DTO
├── src/main/java/.../config
├── src/main/resources
├── data/
├── pom.xml
└── mvnw
```

### Frontend structure
```text
movemate-front/
├── src/components
├── src/pages
├── src/assets
├── src/styles_components
├── src/styles_pages
├── package.json
└── vite.config.js
```

## Main Functionalities

- **JWT-based authentication**
- **Role-based access control** for users, organizers, and administrators
- **Activity creation and management**
- **Reservation system**
- **Profile management**
- **Nearby activity search using Google Maps**
- **Image support for activities**
- **Admin moderation workflow**
- **Automated tests with Selenium**

## Backend API Overview

The backend exposes endpoints for:

- `/login` → authentication
- `/registro` → user and organizer registration
- `/usuarios` → user profile and reservations
- `/organizadores` → organizer profile and activity management
- `/actividad` → activity browsing, filtering, details, and nearby search
- `/admin` → moderation and administration

## Requirements

Before running the project, make sure you have:

- **Java 21**
- **Maven** or the provided Maven Wrapper
- **Node.js** and **npm**
- A working local database setup if you want to customize persistence
- Google Maps support configured properly if you want to use map-related features

## Running the Project

## 1. Clone the repository
```bash
git clone https://github.com/your-username/movemate.git
cd movemate
```

## 2. Run the backend
Go to the backend folder:

```bash
cd movemate-back
```

Run the Spring Boot application:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The backend is configured to run on:

```text
http://localhost:8080
```

## 3. Run the frontend
Open another terminal and go to the frontend folder:

```bash
cd movemate-front
npm install
npm run dev
```

The frontend will usually be available at:

```text
http://localhost:5173
```

## Database Notes

The project includes H2 database files inside:

```text
movemate-back/data/
```

The backend configuration is set up to use an H2 database connection.  
If you want to switch to PostgreSQL or another database, you can update the configuration in:

```text
movemate-back/src/main/resources/application.properties
```

## Testing

Backend and UI testing support is included in the project.

### Run backend tests
```bash
cd movemate-back
./mvnw test
```

### Selenium tests
Selenium-based tests are included in the backend test directory and can be adapted depending on your local environment and browser driver setup.

## Security Notes

Before deploying this project to production, it is strongly recommended to:

- Move sensitive configuration to environment variables
- Remove hardcoded secrets and API keys from the source code
- Use a production-ready database
- Configure proper CORS rules
- Add stronger validation and error handling
- Secure file uploads and external integrations

## Future Improvements

- Deploy the application to a cloud platform
- Use PostgreSQL as the default production database
- Add Docker support for full deployment
- Improve responsive UI design
- Add notifications and payment integration
- Improve test coverage
- Introduce CI/CD pipelines

## Authors

- **Diego Galán Baquero**
- **Aitana Cuadra Cano**

## Academic Context

This project was developed for the **ISST** course as a full-stack application focused on activity discovery, reservation management, and role-based interaction between users, organizers, and administrators.
