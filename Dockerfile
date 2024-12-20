# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/Crix-0.0.1-SNAPSHOT.jar app.jar

# Expose the default port for Spring Boot (8080)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
