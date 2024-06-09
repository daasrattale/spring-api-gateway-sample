# Spring Microservices with Zuul, Eureka, Ribbon, and JWT

![api-gateway](https://github.com/daasrattale/spring-zuul-eureka-ribbon-jwt/assets/44347757/9c7cad80-afb8-47b9-a04d-9bae5320eaa6)


## Overview

This project demonstrates a microservices architecture using Spring Boot. It integrates the following technologies:
- **Zuul**: API Gateway
- **Eureka**: Service Discovery
- **Ribbon**: Client-side Load Balancer
- **JWT**: JSON Web Tokens for secure authentication

## Features

- **API Gateway**: Routing and filtering requests to various microservices using Zuul.
- **Service Discovery**: Dynamic discovery of microservices using Eureka.
- **Load Balancing**: Distributing requests among instances of microservices with Ribbon.
- **Authentication**: Secure access to services with JWT.

## Components

### Zuul Gateway
Handles routing of requests to the appropriate microservice based on predefined rules.

### Eureka Server
Acts as the service registry where all microservices register themselves for discovery.

### Eureka Client
Microservices that register with the Eureka Server to make themselves discoverable.

### Ribbon
Provides client-side load balancing to distribute requests across multiple instances of a service.

### JWT Authentication
Ensures secure access to the microservices by validating JWT tokens.

## Prerequisites

- Java 8 or higher
- Maven
- Spring Boot

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements

This project is inspired by various tutorials and examples in the Spring community.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## Contact

For any queries, please contact the repository owner at [sa.elattar.ad@gmail.com].

