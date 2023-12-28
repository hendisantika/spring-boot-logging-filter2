# spring-boot-logging-filter2
Logging Request and Response Body In Spring Boot using Filter

Spring Boot API gateway

APIs are a common way of communication between applications. In the case of microservice architecture, there will be a
number of services and the client has to know the hostnames of all underlying applications to invoke them.

To simplify this communication, we prefer a component between client and server to manage all API requests called API
Gateway. Additionally, we can have other features which include:

1. Security — Authentication, authorization
2. Routing — routing, request/response manipulation, circuit breaker
3. Observability — metric aggregation, logging, tracing
4.

Architectural benefits of API Gateway:

1. Reduced complexity
2. Centralized control of policies
3. Simplified troubleshooting

There are many types of implementations available for API Gateway which include — Spring Cloud Gateway, Zuul API
Gateway, APIGee, EAG (Enterprise API Gateway)

In this article, we will see how to implement the Spring Cloud API gateway, filter incoming requests, manipulate
requests/responses, and handle authentication.

* Service Registry — The application that keeps track of the available instances of each microservice in a project.
* API Gateway — receives incoming requests, performs authentication (if enabled) and forwards requests to actual
  microservice. On getting the response, return it to the consumer.
* Authentication server — The application that takes care of authentication
* First and Second Microservices — Two normal internal applications with different functionalities.

All the applications on startup register themselves into Service Registry. Below are the steps that occur upon receiving
any API request:

The consumer calls any application via the API gateway.

1. API gateway will check if the incoming URL needs authentication. If yes, it calls the Authentication server to
   validate it.
2. If it is a valid token, it forwards the requests to the corresponding application after applying filters.
3. If it is an invalid token, respond to the consumer as unauthorized.
4. Once it receives a response from an internal microservice, it returns it back to the consumer after applying filters.
5. The filters in Gateway can include operations like logging or manipulating/customizing the request/response details.

### Image Screen shots

Eureka Server

![img.png](eureka/img.png "Eureka Server")
