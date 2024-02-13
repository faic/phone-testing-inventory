Phone Booking API 

REST API for a phone rental system providing inventory management, booking capabilities, and phone status tracking.  



Java 18  
H2 database  
Gradle  
Spring Boot  
Spring Data JPA  
Flyway

It's initially created with 2 users with emails:  
bob@phonetesting.org  
alice@phonetesting.org  

The API utilizes standard HTTP status codes (400 range for client errors, 500 range for server-side)  
Endpoints

1. GET /api/phones

Purpose: Lists all phones within the system . 

**Success Response**

* **Status Code:** 200 OK

* **Content-Type:** application/json An array of phone objects.
  Object structure  
```json
{
  "id": 1,
  "spec": {
    "manufacturer": "Samsung",
    "model": "Galaxy S9"
  },
  "os": {
    "os": "ANDROID",
    "version": "5.0"
  },
  "available": true
}
```

2. GET /api/phones/available

(Similar Structure, emphasize difference in response filtering available items)

3. GET /api/phones/{phoneId}  
Purpose:   Retrieves details of a specific phone by its unique identifier. Includes booking information if the phone is currently booked.

**Success Response**

* **Status Code:** 200 OK

* **Content-Type:** application/json

**Example Response**

```json
{
  "phone": {
    "id": 1,
    "spec": {
      "manufacturer": "Samsung",
      "model": "Galaxy S9"
    },
    "os": {
      "os": "ANDROID",
      "version": "5.0"
    },
    "available": false
  },
  "activeBooking": {
    "id": 2,
    "user": {
      "name": "Bob Smith",
      "email": "bob@phonetesting.org"
    },
    "bookedAt": "2024-02-13T19:38:24.922Z"
  }
}
```

4. POST /api/phones/book

Initiates the booking process for a phone specified by its ID.  
Requires a JSON request body containing the user's email.  

Request Body

* **Content-Type:** application/json  

**Example Request**

```json
{
  "phoneId": 1,
  "userEmail": "bob@phonetesting.org"
}
```
**Success Response**

* **Status Code:** 200 OK

* **Content-Type:** application/json

**Example Response**

```json
{
  "id": 2,
  "spec": {
    "manufacturer": "Samsung",
    "model": "Galaxy S9"
  },
  "user": {
    "name": "Bob Smith",
    "email": "bob@phonetesting.org"
  },
  "bookedAt": "2024-02-13T19:38:24.922Z",
  "returnedAt": null
}
```

5. POST /api/phones/book/spec

Initiates the booking process for a phone specified by phone specification.
It tries to book first available phone that matches specification.  
The response format is equal to POST /api/phones/book endpoint.  

Request Body

* **Content-Type:** application/json

**Example Request**

```json
{
  "manufacturer": "Samsung",
  "model": "Galaxy S9",
  "userEmail": "bob@phonetesting.org"
}
```

6. PUT /return/{bookingId}
Marks a booked phone as returned, using the provided 'bookingId'.
**Success Response**
* **Status Code:** 200 OK


