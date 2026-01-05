**The redirect service uses HTTP 302 to ensure real-time analytics collection and dynamic destination control.**

# ****## 📊 Success Metrics****

This section defines how the success of the URL Shortener service is measured.
All metrics are treated as **Service Level Objectives (SLOs)** , guide design, scalability, and reliability decisions.

---

### **1. Performance Metrics**

#### Redirect Latency
- **Definition:** Time taken to redirect a short URL to its original long URL.
- **Targets:**
    - p50 latency ≤ 50 ms
    - p95 latency ≤ 100 ms
    - p99 latency ≤ 200 ms
- **Measurement:**  
  Captured using Spring Boot Actuator (`http.server.requests`).
- **Reason:**  
  Redirect is the most user-facing operation and must be fast.

#### Throughput
- **Definition:** Number of redirect requests handled per second.
- **Target:**  
  Support 1,000 requests per second initially with horizontal scalability.
- **Measurement:**  
  Request count per second via Actuator metrics.
- **Reason:**  
  URL shorteners are read-heavy systems and can experience traffic spikes.

---

### **2. Reliability Metrics**

#### Availability
- **Definition:** Percentage of time the service is operational.
- **Target:**  
  99.9% availability.
- **Reason:**  
  Downtime breaks existing short links and impacts user trust.

#### Error Rate
- **Definition:** Percentage of failed redirect requests (4xx + 5xx).
- **Target:**  
  Redirect error rate < 0.1%.
- **Measurement:**  
  HTTP status code metrics via Actuator.
- **Reason:**  
  Redirect failures directly impact user experience.

---

### **3. Scalability Metrics**

#### Data Growth
- **Assumption:**  
  Up to 10 million new short URLs per year.
- **Reason:**  
  Ensures database and storage design can scale long-term.

#### Cache Efficiency
- **Definition:** Percentage of redirect requests served from cache.
- **Target:**  
  Cache hit ratio ≥ 90%.
- **Measurement:**  
  Redis cache hit and miss metrics.
- **Reason:**  
  Reduces database load and improves latency for frequently accessed URLs.

---

### **4. Correctness Metrics**

#### Redirect Accuracy
- **Definition:** Correct mapping between short URLs and long URLs.
- **Target:**  
  100% redirect correctness.
- **Reason:**  
  Incorrect redirects are unacceptable.

#### Expiry Handling
- **Definition:** Behavior of expired short URLs.
- **Target:**  
  Expired URLs must not redirect and should return 410 Gone error.
- **Reason:**  
  Ensures predictable and secure behavior.

---

### Monitoring and Validation
- Metrics are exposed using **Spring Boot Actuator**.
- Key endpoints:
    - `/actuator/health`
    - `/actuator/metrics`
- Percentile-based latency tracking is enabled for realistic performance analysis.


