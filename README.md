# SpringTools Application

[![Main Workflow](https://github.com/gustavotiengo/spring-boot-tools-and-patterns/actions/workflows/main.yml/badge.svg)](https://github.com/gustavotiengo/springtools/actions/workflows/main.yml)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

[Spring Boot](http://projects.spring.io/spring-boot/) sample app implemented using industry standard tools and best practices regarding REST APIs, Docker, Cache, Code Coverage, Build Tools and more...

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Gradle 8.5](https://gradle.org/) (Recommended use of gradle wrapper)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.gt.springtools.SpringToolsApplication` class from your IDE.
Alternatively you can use the [Spring Boot Gradle plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins.html#build-tool-plugins.gradle) like so:

```shell
./gradlew build
./gradlew bootRun
```

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.