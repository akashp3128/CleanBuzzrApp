# Buzzr - Barber Appointment Application

This repository contains a cleaned-up version of the Buzzr application, which is a barber appointment management system. The application consists of a frontend Android app and a Spring Boot backend.

## Project Structure

### Backend (Spring Boot)
- Located in `/Backend/SpringFile/Buzzr/`
- Java-based REST API
- Uses Spring Boot for the server
- Contains controllers, repositories, and service layers for various entities:
  - Admin
  - Appointment
  - Barber
  - BarberProfile
  - Client
  - Person
  - Review

### Frontend (Android)
- Located in `/Frontend/BuzzrFrontend/`
- Android application written in Java
- Follows MVVM architecture with Data, Model, and UI layers
- UI components for:
  - Login/Registration
  - Admin Dashboard
  - Barber Dashboard
  - Client Dashboard
  - Barber Profile
  - Map View

### Documentation
- Located in `/Documents/`
- Contains API documentation and architectural diagrams
- Includes block diagrams and API explanations

## Building and Running

### Backend
1. Navigate to the Backend directory: `cd Backend/SpringFile/Buzzr`
2. Build with Maven: `./mvnw clean install`
3. Run the application: `./mvnw spring-boot:run`

### Frontend
1. Navigate to the Frontend directory: `cd Frontend/BuzzrFrontend`
2. Open the project in Android Studio
3. Build and run on an emulator or physical device

## Technologies Used
- Spring Boot
- Java
- Android SDK
- Gradle
- Maven 