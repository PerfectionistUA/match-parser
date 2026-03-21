FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

RUN mkdir -p /app/import-storage

EXPOSE 8080

CMD ["java", "-jar", "target/match-parser-0.0.1-SNAPSHOT.jar"]