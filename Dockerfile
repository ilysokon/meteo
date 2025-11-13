FROM openjdk:17-jdk

# Copy local code to the container image.
WORKDIR /meteo
COPY . .

# Build a release artifact.
RUN ./mvnw package -DskipTests

# Run the web service on container startup.
CMD ["java", "-jar", "/meteo/target/meteo-0.1.jar"]