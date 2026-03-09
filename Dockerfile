FROM gradle:8.11.0-jdk21-alpine AS builder
ADD --chown=gradle . /app
WORKDIR /app
RUN gradle build -x test -x generateJooq

FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080
EXPOSE 8081
WORKDIR /app
ARG PROFILE
ENV SPRING_PROFILES_ACTIVE=$PROFILE
COPY --from=builder /app/build/libs/*.jar /app/
ENTRYPOINT ["java","-jar","/app/app.jar"]