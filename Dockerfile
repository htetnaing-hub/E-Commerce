# Stage 1: Build
FROM eclipse-temurin:19-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:19-jdk
WORKDIR /app
COPY --from=build /app/target/shop_management-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]