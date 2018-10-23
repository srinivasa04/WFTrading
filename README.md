# WFTrading
Dummy Fx platform 
Fx Trading platform details
=================================
Api Doucmentation url:-
http://localhost:8081/swagger-ui.html

Technologies Used for development :
===========
 Java 8,
 Spring Boot 2.x,
 Spring REST API,
 Spring Data JPA,
 Lombok, 
 JUnit with Mockito, 
 Swagger2 for Api Documentation, 
 H2(in memory database),
 Slf4j for logging, 
 Maven as build tool.
 
 Build & deploy :-
 - Inside the project folder use the command  "mvn spring-boot:run"

Technical:-
================================
Security(Authentication & authorization) is not considered for the lean solution.
Added unit test cases with enought coverage.
Logging is implemented wherever needed.
Handled exceptions.

Functional Requirements(implmentation/assumptions):-
=================================================
1) Trade Order application allows only order with currency(GBP/USD).
2) Order Id is unique key and generated at runtime.
3) Added field to maintain order STATUS, like : PENDING,CANCELLED,EXECUTED. New orders are created with PENDING status.
4) Only pending orders are allowed to cancel. For cancelled orders, we are updating only status to CANCELLED.
5) Assuming the current price is 1.2100.
6) For requirement 5 in the given spec., retrieved all matching orders(ASK) for orders(BID).
7) For requirement 4 in the given spec., retrieved all orders(ASK) not matching with orders(BID) and with current price.


