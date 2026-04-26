# Transport Management API

A RESTful API for managing buses, routes, and seat bookings, built with Spring Boot and secured with JWT authentication.

## Tech Stack

- Java 17
- Spring Boot 4.0.2
- Spring Security (JWT / Stateless)
- Spring Data JPA + Hibernate
- MySQL 8
- Lombok

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL 8 running locally

### Database Setup

Create the database in MySQL:

```sql
CREATE DATABASE transport_db;
```

### Configuration

Copy `.env.example` to `.env` and fill in your values:

```bash
cp .env.example .env
```

| Variable | Default | Description |
|---|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/transport_db` | JDBC connection URL |
| `DB_USERNAME` | `root` | MySQL username |
| `DB_PASSWORD` | _(your password)_ | MySQL password |
| `JWT_SECRET` | _(32+ char string)_ | Secret used to sign JWT tokens |

> Tables are auto-created on first run (`ddl-auto=update`).

### Run

```bash
./mvnw spring-boot:run
```

The server starts on **http://localhost:8080**.

---

## Authentication

All endpoints except `/api/auth/register` and `/api/auth/login` require a JWT token.

Pass the token in every request header:

```
Authorization: Bearer <token>
```

Tokens expire after **1 hour**.

### Roles

| Role | Capabilities |
|---|---|
| `USER` | View buses & routes, book seats, view own bookings |
| `ADMIN` | All USER capabilities + create/delete buses & routes, register new admins |

> The first ADMIN must be inserted directly into the database. After that, existing admins can use `POST /api/auth/register-admin` to create more.

```sql
INSERT INTO users (username, email, password, role)
VALUES ('admin', 'admin@example.com', '<bcrypt_hash>', 'ADMIN');
```

---

## API Endpoints

### Auth — `/api/auth`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/register` | Public | Register a new user (role forced to USER) |
| POST | `/login` | Public | Returns JWT token |
| POST | `/register-admin` | ADMIN | Register a new admin |

**Register / Register-Admin — request body:**
```json
{
  "username": "john",
  "email": "john@example.com",
  "password": "secret123"
}
```

**Login — query params:**
```
POST /api/auth/login?username=john&password=secret123
```

**Login — response:**
```
eyJhbGciOiJIUzI1NiJ9...
```

---

### Buses — `/api/buses`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/buses` | USER, ADMIN | List all buses |
| POST | `/api/buses` | ADMIN | Create a bus |
| DELETE | `/api/buses/{id}` | ADMIN | Delete a bus |

**Create bus — request body:**
```json
{
  "busNumber": "NB-1234",
  "capacity": 50,
  "type": "Express"
}
```

---

### Routes — `/api/routes`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/routes` | USER, ADMIN | List all routes |
| POST | `/api/routes` | ADMIN | Create a route |
| DELETE | `/api/routes/{id}` | ADMIN | Delete a route |

**Create route — request body:**
```json
{
  "origin": "Colombo",
  "destination": "Kandy",
  "departureTime": "2026-05-01T08:00:00"
}
```

---

### Bookings — `/api/bookings`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/api/bookings/{busId}?seatNumber=5` | USER, ADMIN | Book a seat on a bus |
| GET | `/api/bookings/my` | USER, ADMIN | Get current user's bookings |

> Attempting to book an already-taken seat returns `409 Conflict`.

---

## Error Responses

| Status | Situation |
|---|---|
| 400 | Validation failed (missing/invalid fields) |
| 401 | Missing or invalid JWT token |
| 403 | Authenticated but insufficient role |
| 404 | Resource not found |
| 409 | Seat already booked |

**Validation error (400):**
```json
{
  "busNumber": "Bus number is required",
  "capacity": "Capacity must be at least 1"
}
```

**Not found error (404):**
```json
{
  "timestamp": "2026-05-01T10:00:00",
  "status": 404,
  "error": "Bus not found with id: 99"
}
```

---

## Project Structure

```
src/main/java/com/transport/transport_api/
├── config/
│   ├── SecurityBeansConfig.java     # BCryptPasswordEncoder bean
│   └── SecurityConfig.java          # Filter chain, permit rules
├── controller/
│   ├── AuthController.java
│   ├── BusController.java
│   ├── BookingController.java
│   └── RouteController.java
├── entity/
│   ├── AppUser.java
│   ├── Bus.java
│   ├── Booking.java
│   └── Route.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── SeatAlreadyBookedException.java
├── repository/
│   ├── AppUserRepository.java
│   ├── BusRepository.java
│   ├── BookingRepository.java
│   └── RouteRepository.java
├── security/
│   ├── JwtUtil.java
│   └── JwtAuthenticationFilter.java
└── service/
    ├── AuthService.java
    ├── BusService.java
    ├── BookingService.java
    └── RouteService.java
```
