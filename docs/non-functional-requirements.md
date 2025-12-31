# Non Functional Requirements

1️⃣ **Performance Requirements**: The system should respond quickly to ensure a smooth user experience.

  Redirect latency

      P95 latency ≤ 50 ms

      P99 latency ≤ 100 ms

  URL creation latency

      P95 latency ≤ 200 ms

  Redirect operation should be read-optimized and faster than write operations.


2️⃣ **Availability & Reliability Requirements**: The service should be highly available and resilient to failures.

  Availability target: 99.9% uptime (excluding planned maintenance)

  Fault tolerance:
      Service should tolerate Instance failures,Cache node failures,Graceful degradation
                
  In case of analytics subsystem failure, redirect must still work

  Data durability: Short URL mappings must not be lost after creation.



3️⃣ **Scalability Requirements**:The system should scale to support growing traffic with minimal changes.

   Traffic assumptions:

      Read (redirect) traffic ≫ Write (URL creation)

      Expected ratio: 100:1 (reads:writes)

   Horizontal scalability:

      Application servers should scale horizontally behind a load balancer.

   Storage scalability:

      URL mappings must support:

          Millions to billions of records

   Analytics scalability: 
      
      Click logging should be asynchronous and non-blocking.


   Rationale:
   Redirect traffic grows faster than URL creation. The system must scale reads efficiently without impacting latency.

4️⃣ **Consistency Expectations**

  Read consistency: Eventual consistency is acceptable for analytics.

  Strong consistency: Required for URL creation and retrieval.



5️⃣ **Security & Abuse Protection** (Non-Functional)

   Rate limiting on URL creation APIs

   Protection against brute-force scanning of short URLs

   HTTPS enforced for all endpoints