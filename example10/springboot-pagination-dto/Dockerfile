FROM eclipse-temurin:21-jdk-ubi9-minimal AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x ./mvnw && ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean package -Dmaven.test.skip

FROM eclipse-temurin:21-jre-ubi9-minimal AS layered
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/${JAR_FILE} /app/app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:21-jre-ubi9-minimal
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY --from=layered /app/dependencies/ ./
COPY --from=layered /app/spring-boot-loader/ ./
COPY --from=layered /app/snapshot-dependencies/ ./
COPY --from=layered /app/application/ ./

ENV DATABASE_ADDR=postgresql
ENV DATABASE_NAME=springbootapp
ENV DATABASE_USER=postgres
ENV DATABASE_PASSWORD=postgres

RUN useradd ucup
USER ucup

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]