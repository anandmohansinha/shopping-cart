FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/*.jar shopping-cart.jar

CMD ["java","-jar","shopping-cart.jar"]