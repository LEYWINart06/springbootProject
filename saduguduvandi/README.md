# SADUGUDU VANDI — Simple Vehicle Rental Website

A small, beginner-level vehicle rental project built with **Java 17, Spring Boot, Spring MVC, Spring Data JPA, SQLite, Thymeleaf, HTML5, CSS3 and minimal JavaScript**.

## Flow

Home → Register/Login → View Vehicles → View Details → Book Vehicle → My Bookings

## Tech Stack

- Java 17
- Spring Boot 3.3.4
- Spring MVC + Thymeleaf (server-rendered pages)
- Spring Data JPA + SQLite (via `sqlite-jdbc` and `hibernate-community-dialects`)
- Spring Security + BCrypt
- Minimal vanilla JavaScript (mobile nav toggle + live price calculation)
- Maven

## How to Run (IntelliJ IDEA)

1. Open IntelliJ IDEA → **Open** → select the `saduguduvandi` folder (the one containing `pom.xml`).
2. Let Maven download dependencies (first time may take a minute).
3. Open `SaduguduVandiApplication.java` and click the green ▶ run button.
4. The app starts on **http://localhost:8080**
5. A SQLite file `saduguduvandi.db` is created automatically in the project root the first time you run it, and 9 sample vehicles (3 bikes, 3 cars, 3 vans) are seeded automatically.

## How to Run (Command Line)

```bash
mvn spring-boot:run
```

or build a jar and run it:

```bash
mvn clean package
java -jar target/saduguduvandi-1.0.0.jar
```

## Using the Website

1. Go to `http://localhost:8080` — this is the Home page.
2. Click **Register** and create an account (name, email, password).
3. **Login** with your new account.
4. Browse **Vehicles**, search by name, or filter by category (Bike / Car / Van).
5. Click **View Details** to see a vehicle, or **Book Now** to book directly.
6. On the booking page, pick a **Pickup Date** and **Return Date** — the total price updates live.
7. Confirm the booking — you'll be redirected to **My Bookings**.
8. From **My Bookings** you can view all your bookings and **Cancel** any that are still `CONFIRMED`.

## REST APIs (for Postman testing)

A ready-made Postman collection is included: `SaduguduVandi.postman_collection.json` — import it directly into Postman.

| Method | Endpoint | Description |
|--------|----------|--------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login (creates a session cookie — enable Postman's cookie jar) |
| GET | `/api/vehicles` | List all vehicles (supports `?category=` and `?search=`) |
| GET | `/api/vehicles/{id}` | Get one vehicle |
| POST | `/api/bookings` | Create a booking (must be logged in) |
| GET | `/api/bookings/my` | Get the logged-in user's bookings |
| PUT | `/api/bookings/{id}/cancel` | Cancel one of your bookings |

**Important:** Since bookings require authentication, call `POST /api/auth/login` first in Postman with cookies enabled, then the following requests in the same Postman session will be authenticated automatically.

### Example: Register
```json
POST /api/auth/register
{
  "name": "Test User",
  "email": "test@example.com",
  "password": "password123"
}
```

### Example: Login
```json
POST /api/auth/login
{
  "email": "test@example.com",
  "password": "password123"
}
```

### Example: Create Booking
```json
POST /api/bookings
{
  "vehicleId": 1,
  "pickupDate": "2026-08-10",
  "returnDate": "2026-08-12"
}
```

## Database (SQLite)

Only 3 tables, created automatically by Hibernate:

- **users** — id, name, email, password (BCrypt hashed)
- **vehicles** — id, name, category, price_per_day, image_url
- **bookings** — id, user_id, vehicle_id, pickup_date, return_date, total_price, status

## Notes

- CSRF protection is disabled in `SecurityConfig` to keep the project simple and easy to test with Postman — this is fine for a student/learning project but should be re-enabled for production use.
- Vehicle images use placeholder photo URLs (picsum.photos) so the project works out of the box without needing to source real vehicle photos.
- Double-booking is prevented: booking a vehicle for overlapping dates with an existing `CONFIRMED` booking is rejected.

## Project Structure

```
com.saduguduvandi
├── controller   (Thymeleaf page controller + REST API controllers)
├── service      (AuthService, VehicleService, BookingService)
├── repository   (Spring Data JPA repositories)
├── entity       (User, Vehicle, Booking, BookingStatus)
├── security     (SecurityConfig, CustomUserDetailsService)
├── config       (DataSeeder, ApiExceptionHandler)
└── dto          (RegisterRequest, LoginRequest, BookingRequest)
```
