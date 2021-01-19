# Build Jar
FROM maven:3.6.3-openjdk-11 AS builder

# Create a directory named build for sources
RUN mkdir -p /build
# Change working directory
WORKDIR /build
# Copy pom xml
COPY pom.xml /build
# Download all required dependencies into one layer
RUN mvn -B dependency:go-offline dependency:resolve-plugins
# Copy source code
COPY src /build/src
# Build application
RUN mvn package -DskipTests

# Build Image
#FROM openjdk:11.0.9-jdk
FROM adoptopenjdk/openjdk11:alpine

# Maintainer
MAINTAINER "Burak Koken"

VOLUME /tmp

ARG DEPENDENCY=target/dependency
COPY --from=builder /build/${DEPENDENCY}/BOOT-INF/lib         /app/lib
COPY --from=builder /build/${DEPENDENCY}/META-INF             /app/META-INF
COPY --from=builder /build/${DEPENDENCY}/BOOT-INF/classes     /app

# Run
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-cp", "app:app/lib/*","org.readingisgood.authmicroservice.AuthMicroserviceApplication"]