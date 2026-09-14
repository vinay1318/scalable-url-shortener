# Design Trade-offs

This document captures key architectural decisions made for the URL shortener and the trade-offs considered when selecting each approach.

---

## 1. Redis + MySQL vs MySQL Only

**Decision:** Use Redis as a cache in front of MySQL.

**Why:**
URL shorteners are typically read-heavy systems. Redirect requests can repeatedly access the same URL mappings. Redis provides low-latency in-memory lookups and reduces the number of read requests reaching MySQL.

**Trade-off:**
Using Redis introduces additional infrastructure cost and operational complexity. Cache consistency also needs to be managed through invalidation, refresh, and TTL strategies.

**Summary:**

* **Benefit:** Faster reads and reduced database load.
* **Trade-off:** Additional infrastructure and cache-consistency complexity.

---

## 2. 302 Redirect vs 301 Redirect

**Decision:** Use HTTP 302 (Found) for short URL redirects.

**Why:**
The short URL should remain under the application's control so that the system can perform analytics, expiration checks, security checks, and potentially change the destination URL.

A 301 redirect represents a permanent redirect and may be cached by browsers or intermediary caches. This can cause subsequent requests to bypass the application.

**Trade-off:**
A 302 redirect generally provides less permanent caching behavior than a 301 redirect, which can result in additional requests reaching the application.

**Summary:**

* **Benefit:** Keeps the redirect under application control.
* **Trade-off:** Potentially less caching efficiency than a permanent redirect.

---

## 3. Database ID + Base62 vs Random Base62

**Decision:** Use a database-generated unique ID and encode it using Base62.

**Why:**
The database provides a unique numeric ID, while Base62 converts that ID into a compact, URL-friendly short code. Since the database IDs are unique and Base62 encoding is deterministic, different IDs cannot produce the same short code.

With random Base62 generation, a collision is possible, requiring a database uniqueness check and retry mechanism.

**Trade-off:**
Sequential database IDs can make generated short codes predictable and potentially enumerable. Random Base62 codes provide better unpredictability but require collision detection and retry logic.

**Summary:**

* **Benefit:** Simple, deterministic, and collision-free short-code generation.
* **Trade-off:** Short codes can be predictable.
