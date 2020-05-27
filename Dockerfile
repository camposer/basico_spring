FROM openjdk
RUN mkdir -p /usr/src/apps
COPY target/nomina-0.0.1-SNAPSHOT.jar /usr/src/apps/
WORKDIR /usr/src/apps
CMD ["java", "-jar", "nomina-0.0.1-SNAPSHOT.jar"]
