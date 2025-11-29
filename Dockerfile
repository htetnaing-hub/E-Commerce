FROM eclipse-temurin:19-jdk
COPY target/shop_management-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]