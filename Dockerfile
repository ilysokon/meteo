FROM openjdk:26-ea

# Copy local code to the container image.
WORKDIR /meteo
COPY . .

# Build a release artifact.
RUN ./mvnw package -DskipTests

# Run the web service on container startup.
CMD ["java", "-jar", "/meteo/bootstrap/target/bootstrap-0.1.jar"]