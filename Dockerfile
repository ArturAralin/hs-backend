FROM openjdk:buster
WORKDIR "/app"
COPY target/uberjar/backend-standalone.jar /app
COPY .lein-env /app
CMD ["java", "-jar", "/app/backend-standalone.jar"]
