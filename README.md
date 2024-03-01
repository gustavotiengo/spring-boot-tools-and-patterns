# SpringTools Application

[![Main Workflow](https://github.com/gustavotiengo/spring-boot-tools-and-patterns/actions/workflows/main.yml/badge.svg)](https://github.com/gustavotiengo/springtools/actions/workflows/main.yml)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=gustavotiengo_springtools&metric=coverage)](https://sonarcloud.io/summary/new_code?id=gustavotiengo_springtools)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=gustavotiengo_springtools&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=gustavotiengo_springtools)

[Spring Boot](http://projects.spring.io/spring-boot/) sample app implemented using industry standard tools and best practices regarding REST APIs, 
Docker (and compose) for development, caching, logging, Spring exceptions handling, code coverage & quality gates, 
database migrations, system metrics and tracing, build tools and much more...

## 1. Requirements

For building and running the application you need:

- [JDK 17](https://openjdk.org/projects/jdk/17/)
- [Gradle 8.6](https://gradle.org/) (Recommended use of gradle wrapper)
- [Docker 25](https://docs.docker.com/engine/release-notes/25.0/)

## 2. Running the application locally

### 2.1 Database and Cache dependencies
This sample app depends on running database and cache servers. To provide such resources before execute the Spring 
application, we must run a **PostgreSQL Server** on port **5432** and a **Redis Server** on port **6379**. The fastest way to
run both dependencies is using **Docker Compose** like so:
```shell
docker-compose up postgres
docker-compose up redis
```
Both resources are accessible (exposed) outside Docker internal network, that way our app will be able to reach the 
database and cache server by pointing to `localhost:{port}`

You can find more details on postgres and redis configurations on the
_compose.yaml_ file. 

#### 2.1.1 Database Migrations
It's also mandatory to run **Flyway** database migrations to build the schema structure on **Postgres**. If you choose 
**Docker Compose**, it's going to be a very fast and simple step: 
```shell
docker-compose up flyway
```

### 2.2 Running the Spring application
There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method
in the `com.gt.springtools.SpringToolsApplication` class from your IDE. Alternatively you can use the 
[Spring Boot Gradle plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins.html#build-tool-plugins.gradle) like so:
```shell
./gradlew build
./gradlew bootRun
```
After running the `build` and `bootRun` Gradle tasks you should be able to access the app on _http://localhost:8080/_

If you prefer to run everything using containers, you should use Docker Compose to run the Spring app within a 
local container. To do that, just run:
```shell
docker-compose up app
```
- For debbuging code purposes it's preferable using _Intellij_ to execute `bootRun` task on debug mode.
- You could install _Docker_ plugin for _Intellij_ for easier running and monitoring containers, whether using _compose_ 
  or not.
## 3. REST API
### 3.1 Available Endpoints
To-Do
### 3.2 Swagger
To-Do
## 4. Metrics, Monitoring & Tracing
To-Do
## 5. Quality: Code Coverage & Quality Gates
To-Do
## 6. On the way for _production_
To-Do
### 6.1 Application Profiles
To-Do
### 6.2 GitHub Actions Workflow
To-Do
## 7. Copyright
Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.