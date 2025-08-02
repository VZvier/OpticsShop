FROM eclipse-temurin:19-jdk-alpine
COPY  src/main/resources/com/vzv/shop/    src/main/resources/com/vzv/shop/
COPY target/*.jar GlassesShop.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "GlassesShop.jar"]