# Dockerized Microservices with API Gateway

A microservices architecture project demonstrating API Gateway pattern using Spring Boot and Docker containerization.

## Architecture Overview

This project consists of four main services orchestrated through Docker Compose:

```
┌─────────────────┐
│   API Gateway   │  (Port 8080)
│    (Spring Gateway)
└────────┬────────┘
         │
    ┌────┴────┬────────────┬─────────────┐
    │         │            │             │
┌───▼────┐ ┌──▼─────┐ ┌───▼────────┐   │
│ Item   │ │ Order  │ │  Payment   │   │
│Service │ │Service │ │  Service   │   │
│ :8081  │ │ :8082  │ │   :8083    │   │
└────────┘ └────────┘ └────────────┘   │
```

## Services

### API Gateway (Port 8080)

- Central entry point for all API requests
- Routes requests to appropriate microservices
- Built with Spring Cloud Gateway

**Routes:**

- `/items/**` → Item Service (8081)
- `/orders/**` → Order Service (8082)
- `/payments/**` → Payment Service (8083)

### Item Service (Port 8081)

- Manages item-related operations
- Handles inventory and product information

### Order Service (Port 8082)

- Processes customer orders
- Manages order lifecycle

### Payment Service (Port 8083)

- Handles payment processing
- Manages payment transactions

## Technology Stack

- **Framework:** Spring Boot 4.0.3
- **Java Version:** 17
- **Build Tool:** Maven
- **API Gateway:** Spring Cloud Gateway
- **Containerization:** Docker
- **Orchestration:** Docker Compose
- **Base Image:** Eclipse Temurin 17 JDK Alpine

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker Desktop
- Docker Compose

## Project Structure

```
dockerized-microservices-api-gateway/
├── api-gateway/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── item-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── order-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── payment-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── docker-compose.yml
└── .gitignore
```

## Getting Started

### 1. Build All Services

Navigate to each service directory and build with Maven:

```bash
# Build Item Service
cd item-service
./mvnw clean package

# Build Order Service
cd ../order-service
./mvnw clean package

# Build Payment Service
cd ../payment-service
./mvnw clean package

# Build API Gateway
cd ../api-gateway
./mvnw clean package
```

Or build all at once from the root directory:

```bash
cd item-service && ./mvnw clean package && cd ..
cd order-service && ./mvnw clean package && cd ..
cd payment-service && ./mvnw clean package && cd ..
cd api-gateway && ./mvnw clean package && cd ..
```

### 2. Build Docker Images

From the project root directory:

```bash
docker-compose build
```

### 3. Start All Services

```bash
docker-compose up
```

To run in detached mode:

```bash
docker-compose up -d
```

### 4. Verify Services

Check if all containers are running:

```bash
docker-compose ps
```

## Usage

### Access Services Through API Gateway

All requests should go through the API Gateway on port 8080:

#### Items

```bash
curl http://localhost:8080/items
```

#### Orders

```bash
curl http://localhost:8080/orders
```

#### Payments

```bash
curl http://localhost:8080/payments
```

### Direct Service Access (for debugging)

You can also access services directly:

```bash
# Item Service
curl http://localhost:8081/items

# Order Service
curl http://localhost:8082/orders

# Payment Service
curl http://localhost:8083/payments
```

## Docker Commands

### Stop All Services

```bash
docker-compose down
```

### View Logs

```bash
# All services
docker-compose logs

# Specific service
docker-compose logs api-gateway
docker-compose logs item-service
```

### Restart Services

```bash
docker-compose restart
```

### Remove All Containers and Networks

```bash
docker-compose down -v
```

## Development

### Running Services Locally (without Docker)

Each service can be run independently:

```bash
cd <service-directory>
./mvnw spring-boot:run
```

### Hot Reload During Development

For development with auto-reload, use Spring Boot DevTools (if configured) or run services directly through your IDE.

## Network Configuration

All services communicate through a custom Docker bridge network named `microservices-net`. This allows service-to-service communication using service names as hostnames.

## Troubleshooting

### Port Already in Use

If you encounter port conflicts, ensure no other applications are using ports 8080-8083:

```bash
# Windows
netstat -ano | findstr :8080

# Linux/Mac
lsof -i :8080
```

### Container Build Failures

Ensure all services are built with Maven before building Docker images:

```bash
./mvnw clean package
```

### Service Communication Issues

Verify all services are on the same Docker network:

```bash
docker network inspect dockerized-microservices-api-gateway_microservices-net
```

## API Gateway Configuration

The API Gateway routes are configured in `api-gateway/src/main/resources/application.yml`:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: item-service
          uri: http://item-service:8081
          predicates:
            - Path=/items/**
        # ... other routes
```

## License

This project is for educational purposes.


## Acknowledgments

- Spring Boot Team for excellent microservices framework
- Docker for containerization platform
