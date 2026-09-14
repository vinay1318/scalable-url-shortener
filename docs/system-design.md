### Data Consistency Expectations

**Consistency guarantees:**
URL mappings require strong consistency. Once a URL is successfully created, subsequent requests must be able to retrieve the mapping. Analytics data can use eventual consistency because a small delay in reporting click metrics is acceptable.

**Read-after-write behavior:**
After `POST /shorten` returns a successful response, a subsequent `GET /{shortCode}` must be able to retrieve the newly created URL without requiring a delay.

**Cache vs DB consistency:**
MySQL is the authoritative source of truth, while Redis is used as a performance cache. Redis may temporarily contain stale or missing data, but it must not override the authoritative state in MySQL. Cache entries will be invalidated or refreshed when the underlying data changes, with TTL providing an additional safety mechanism.
