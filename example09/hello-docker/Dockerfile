FROM eclipse-temurin:21-jdk-ubi9-minimal AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x ./mvnw && ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install && java -Djarmode=layertools -jar target/*.jar extract

FROM eclipse-temurin:21-jre-ubi9-minimal
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

RUN useradd ucup
USER ucup
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]