# API REST with Spring Boot 2 and Java 11

![](https://img.shields.io/badge/Java11-orange.svg)
![](https://img.shields.io/badge/Spring%20Boot-2.7.5-green.svg)

This is a sample Java / Maven / Spring Boot (version 2.7.5) application that can be used as a starter for creating an API Rest with elementary CRUD operations.

## Content of repository
- Connection to a database in `H2` stored in memory
- `maven` project with `SpringBoot`
- Uses `JPA` for database maintenance
- Documentation with `Swagger` offering a user interface with `Swagger-ui`
- Tests with `Junit` and `MockMVC`
- `TrackExecutionTime` annotation is created to control the execution time of some services
- Using a `HandlerException` to handle and customize exceptions
- Some requests are cached with `Spring Cache`

## How to run:
Compile and prepare jar:
```
mvn clean install
```
Run application:
```
mvn spring-boot:run
```
Base path API Rest: http://localhost:8080


## REST Services ##

  - GET /api/v1/personas 
  - GET /api/v1/personas/{id} 
  - DELETE /api/v1/personas/{id}
  - POST /api/v1/personas
  - PUT /api/v1/personas/{id} 
  - POST /api/v1/personas/{id1}/padre/{id2}
  - GET /api/v1/relaciones/{id1}/{id2}

    
## Docker ##
Command to execute the container:
```
docker build -t personas .
```
Command to execute the container:
```
run -p 8080:8080 personas
```
## Documentación API ##
http://localhost:8080/v2/api-docs

## Swagger UI ##
http://localhost:8080/swagger-ui.html