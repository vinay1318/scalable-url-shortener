# Functional Requirements

1. **Shorten URL**

Description: The system shall allow users to shorten a long URL into a unique, compact URL.

Details:

  Accept a valid long URL as input

  Generate a unique short code

  Return a shortened URL in the response

  Support optional custom alias (future enhancement)

  Ensure no collisions for generated short codes

  Example
    Input:  https://www.example.com/very/long/url
    Output: https://sho.rt/abc123

**2. Redirect Behavior**

Description: When a user accesses a shortened URL, the system shall redirect them to the original long URL.

Details:

   Perform HTTP 301 (Permanent Redirect) or 302 (Temporary Redirect)

   Redirect must be fast (low latency)

   If the short URL does not exist, return 404 Not Found

   If the short URL is expired, return 410 Gone

**3. Analytics Tracking**

Description: The system shall capture basic analytics for each shortened URL.

Minimum Metrics:

  Total number of redirects (click count)

  Timestamp of access

  Future Enhancements

  Client IP (masked)

  User-Agent

  Geo-location (country-level)

  Analytics collection must not impact redirect latency.

**4. Expiration Support**

Description:The system shall support URL expiration.

Details:

  Allow URLs to have an optional expiration time

  Expired URLs must not redirect

  Accessing an expired URL should return 410 Gone

  Default behavior: URLs do not expire unless specified

**5. Error Handling**

Description: The system shall handle invalid scenarios gracefully.

Cases:

   Invalid URL format → 400 Bad Request

   Non-existent short URL → 404 Not Found

   Expired short URL → 410 Gone