FROM gradle:8.6.0-jdk17-alpine AS builder
ADD --chown=gradle . /app
WORKDIR /app
RUN gradle build --no-daemon -x test -x generateJooq

FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
EXPOSE 8081
WORKDIR /app
ENV SPRING_PROFILES_ACTIVE=default
COPY --from=builder /app/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]