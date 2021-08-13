FROM adoptopenjdk/openjdk11

RUN adduser --system --group spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} bc-clients-service-0.1.jar
ENTRYPOINT ["java", "-jar", "/bc-clients-service-0.1.jar"]