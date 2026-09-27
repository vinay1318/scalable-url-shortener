## API

| Method | Endpoint                 | Request            | Response               | Description                        |
|--------|--------------------------|--------------------|------------------------|------------------------------------|
| POST   | /shorten                 | Long URL           | Short code + short URL | Creates a new short URL            |
| GET    | /{shortCode}             | Short code in path | 302 redirect           | Redirects user to the original URL |
| GET    | /analytics/{shortCode}   | Short code in path | Analytics data         | Returns click statistics           |
| GET    | /health                  | None               | OK                     | Verifies service is running        |


# API Contracts

## 1. Create Short URL

### Endpoint

POST /shorten

### Purpose

Creates a short URL from a long URL.

### Request

Headers:

Content-Type: application/json

Request Body:

{
"longUrl": "https://www.example.com/some/very/long/url"
}

### Request Validation

- longUrl is required.
- longUrl must be a valid URL.
- Empty or malformed URLs should be rejected.

### Success Response

Status: 201 Created

{
"shortCode": "aB12",
"shortUrl": "https://short.ly/aB12"
}

### Error Responses

400 Bad Request
- Invalid or missing long URL.

500 Internal Server Error
- Unexpected server error.


---

## 2. Redirect to Original URL

### Endpoint

GET /{shortCode}

### Purpose

Resolves a short code and redirects the client to the original long URL.

### Request

Path Parameter:

shortCode = aB12

Example:

GET /aB12

Request Body:

None.

### Processing

1. Look up the short code.
2. Check whether the short URL exists.
3. Check whether the short URL has expired.
4. Record the analytics event.
5. Return an HTTP 302 redirect.

### Success Response

Status: 302 Found

Location: https://www.example.com/some/very/long/url

### Error Responses

404 Not Found
- Short code does not exist.

410 Gone
- Short URL has expired.

500 Internal Server Error
- Unexpected server error.


---

## 3. Get Analytics

### Endpoint

GET /analytics/{shortCode}

### Purpose

Retrieves analytics information for a short URL.

### Request

Path Parameter:

shortCode = aB12

Example:

GET /analytics/aB12

Request Body:

None.

### Success Response

Status: 200 OK

{
"shortCode": "aB12",
"clickCount": 1520
}

### Initial Analytics

The initial version will track:

- Total click count.

Future versions may track:

- Clicks over time.
- Device type.
- Browser.
- Country/region.
- Referrer.

### Error Responses

404 Not Found
- Short code does not exist.

500 Internal Server Error
- Unexpected server error.


---

## 4. Health Check

### Endpoint

GET /health

### Purpose

Checks whether the application is running.

### Request

Request Body:

None.

### Success Response

Status: 200 OK

OK

### Error Responses

500 Internal Server Error
- Application health check failed.


---

# Common Error Response

All APIs should use a consistent error response structure.

### Error Response Format

{
"timestamp": "2026-09-12T10:30:00Z",
"status": 404,
"error": "Not Found",
"message": "Short URL not found",
"path": "/aB12"
}


# Common HTTP Status Codes

200 OK
- Request completed successfully.

201 Created
- Resource successfully created.

302 Found
- Temporary redirect to the original URL.

400 Bad Request
- Invalid request data.

404 Not Found
- Requested resource does not exist.

410 Gone
- Short URL has expired.

500 Internal Server Error
- Unexpected server-side error.


# API Summary

| Method | Endpoint               | Purpose                  | Success Status |
|--------|------------------------|--------------------------|----------------|
| POST   | /shorten               | Create short URL         | 201 Created    |
| GET    | /{shortCode}           | Redirect to original URL | 302 Found      |
| GET    | /analytics/{shortCode} | Retrieve analytics       | 200 OK         |
| GET    | /health                | Check application health | 200 OK         |

# Reserved Paths

The following paths are reserved and cannot be generated as short codes:

- /health
- /analytics
- /shorten



### Short Code Strategy

**Strategy chosen:**
We use a **database-generated unique ID + Base62 encoding** to generate short codes. The database ID guarantees uniqueness, while Base62 converts the numeric ID into a compact, URL-friendly representation.

**Collision handling:**
Since each database ID is unique and Base62 provides a deterministic one-to-one encoding, different IDs cannot produce the same short code. Therefore, no separate collision-resolution mechanism is required.

**Reasoning:**
This approach is simple, deterministic, efficient, and scalable. It avoids the collision checks required by random or hash-based approaches while producing significantly shorter URLs than exposing the numeric database ID directly.
