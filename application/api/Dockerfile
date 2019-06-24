FROM openjdk:8-jdk-alpine as build-api
WORKDIR /workspace/app/api
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B

COPY src src
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/api/target/dependency
COPY --from=build-api ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build-api ${DEPENDENCY}/META-INF app/META-INF
COPY --from=build-api ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n", "-cp",  "app:app/lib/*", "fr.takima.cdb.CdbApiApplication"]