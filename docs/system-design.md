### Data Consistency Expectations

**Consistency guarantees:**
URL mappings require strong consistency. Once a URL is successfully created, subsequent requests must be able to retrieve the mapping. Analytics data can use eventual consistency because a small delay in reporting click metrics is acceptable.

**Read-after-write behavior:**
After `POST /shorten` returns a successful response, a subsequent `GET /{shortCode}` must be able to retrieve the newly created URL without requiring a delay.

**Cache vs DB consistency:**
MySQL is the authoritative source of truth, while Redis is used as a performance cache. Redis may temporarily contain stale or missing data, but it must not override the authoritative state in MySQL. Cache entries will be invalidated or refreshed when the underlying data changes, with TTL providing an additional safety mechanism.


### System Design

## High-Level Architecture

The URL shortener follows a cache-aside architecture where MySQL is the source of truth and Redis is used to reduce database load for frequently accessed short URLs.

```mermaid
flowchart TD

    Client[Client]

    API[Spring Boot API]

    Redis[(Redis Cache)]

    MySQL[(MySQL Database)]

    Destination[Long URL / Destination]

    Client -->|POST /shorten| API
    API -->|Store URL Mapping| MySQL
    API -->|Return Short URL| Client

    Client -->|GET /shortCode| API
    API -->|Cache Lookup| Redis

    Redis -->|Cache Hit| API
    Redis -->|Cache Miss| MySQL

    MySQL -->|URL Mapping| API
    API -->|Populate Cache| Redis

    API -->|302 Redirect| Destination


### Request Flow

## 1. Create Short URL

The client sends a request to create a short URL.


Client
   |
   | POST /shorten
   v
Spring Boot API
   |
   | Generate unique ID
   | Encode ID using Base62
   |
   | Store URL mapping
   v
MySQL
   |
   | URL successfully stored
   v
Spring Boot API
   |
   | Return short URL
   v
Client




## 2. Redirect Short URL

When a user clicks a short URL, the request is handled by the Spring Boot API.



Client
   |
   | GET /{shortCode}
   v
Spring Boot API
   |
   | Cache Lookup
   v
Redis
   |
   +----------------------+
   |                      |
Cache Hit              Cache Miss
   |                      |
   v                      v
Return URL              MySQL
                          |
                          | Retrieve URL Mapping
                          v
                        Redis
                          |
                          | Populate Cache
                          v
                    Spring Boot API
                          |
                          | HTTP 302 Redirect
                          v
                     Long URL
                          |
                          v
                     Destination