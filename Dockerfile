FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -B -ntp -DskipTests package

# ====== Runtime stage ======
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV SPRING_PROFILES_ACTIVE=prod \
    JAVA_OPTS=""
# Tìm file jar duy nhất trong target và copy đặt tên app.jar
COPY --from=builder /workspace/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
