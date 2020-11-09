FROM openjdk:8-jdk-alpine
RUN apk add --update netcat-openbsd && rm -rf /var/cache/apk/*
COPY docker/wait-for /wait-for
RUN chmod +x /wait-for
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
VOLUME /app
COPY docker/voting-session-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8010
ENV JAVA_OPTS=""