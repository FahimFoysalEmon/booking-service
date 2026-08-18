# Booking Service

Spring Boot API for shop appointment booking (BarBook-style).

## Stack

- Java / Spring Boot
- PostgreSQL
- JWT auth

## Roles

- `CUSTOMER` — browse shops, book slots, see own bookings
- `SHOP_OWNER` — manage shop, services, availability, see shop bookings
- `ADMIN` — seeded user only (no admin APIs yet)

## Auth

Base URL: `http://localhost:8080`

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/api/v1/auth/register` | Public | Register as CUSTOMER or SHOP_OWNER |
| POST | `/api/v1/auth/login` | Public | Login, returns JWT |
| GET | `/api/v1/auth/me` | JWT | Current user (stub) |

Private routes need header: `Authorization: Bearer <token>`

---

## Customer APIs

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| GET | `/api/v1/public/shops` | Public | List active shops |
| GET | `/api/v1/public/shops/{shopId}/services` | Public | List active services |
| GET | `/api/v1/public/shops/{shopId}/services/{serviceId}/slots` | Public | Free slots (`startTime`, `endTime`) |
| POST | `/api/v1/private/booking/create` | Customer JWT | Create a booking |
| GET | `/api/v1/private/booking/me` | Customer JWT | My bookings |

**Flow:** register/login → shops → services → slots → book → my bookings

---

## Shop Owner APIs

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/api/v1/private/shop/create` | Owner JWT | Create shop (one per owner) |
| GET | `/api/v1/private/shop/me` | Owner JWT | Get my shop |
| POST | `/api/v1/private/service/create` | Owner JWT | Create service |
| GET | `/api/v1/private/service/me` | Owner JWT | List my services |
| PUT | `/api/v1/private/service/update/{serviceId}` | Owner JWT | Update service |
| PATCH | `/api/v1/private/service/disable/{serviceId}` | Owner JWT | Disable service |
| POST | `/api/v1/private/availability/create` | Owner JWT | Open availability window |
| GET | `/api/v1/private/availability/me` | Owner JWT | List availability |
| PATCH | `/api/v1/private/availability/close/{availabilityId}` | Owner JWT | Close window |
| GET | `/api/v1/private/booking/shop` | Owner JWT | Bookings for my shop |

**Flow:** register/login → create shop → services → availability → view bookings

---

## Admin

- Admin user is seeded on startup from `app.admin.*` in `application.properties`
- Can log in via `/api/v1/auth/login`
- No dedicated admin management APIs yet
