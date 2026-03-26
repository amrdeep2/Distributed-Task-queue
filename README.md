A distributed task queue system built using Spring Boot, Redis, and PostgreSQL.
This project demonstrates asynchronous background job processing using a producer-consumer architecture.

ARCHITECTURE
API Service → Creates tasks and pushes them to Redis
Worker Service → Consumes tasks and processes them
Redis → Acts as the message broker (queue)
PostgreSQL → Stores task data and status

FEATURES
Asynchronous task processing
Producer-consumer architecture
Multiple task types:
EMAIL (simulated)
REPORT (file generation)
NOTIFICATION (simulated)
Task lifecycle tracking:
PENDING → PROCESSING → DONE / FAILED
Persistent storage using PostgreSQL
Redis-based queue system

TECHSTACK
Java(spring boot)
Redis
PostgresSQL
Docker

Future Enhancements
Dead-letter queue
retry mechanism
Priority-Based Mechanism
Web page (React page)
Cloud Deployment
