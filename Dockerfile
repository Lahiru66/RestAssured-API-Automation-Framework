# Use a base image with Java
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container (replace this with your actual JAR file name)
COPY ./target/RestAssuredAssignment-1.0-SNAPSHOT.jar /app/RestAssuredAssignment-1.0-SNAPSHOT.jar

# Run the application
CMD ["java", "-jar", "/app/RestAssuredAssignment-1.0-SNAPSHOT.jar"]


