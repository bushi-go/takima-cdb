FROM node:10.16.0-alpine as build-ui
WORKDIR /workspace/app/ui
COPY ui/package*.json .
RUN npm i

COPY ui/src src
COPY ui/tsconfig.*.json .
COPY ui/tsconfig.json .
COPY ui/angular.json .
COPY ui/e2e e2e

RUN ng build --prod

FROM openjdk:8-jdk-alpine as build-api
WORKDIR /workspace/app/api
COPY api/mvnw .
COPY api/.mvn .mvn
COPY api/pom.xml .
RUN api/./mvnw dependency:go-offline -B

COPY api/src src
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/api/target/dependency
ARG UI=/workspace/app/ui/dist
COPY --from=build-api ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build-api ${DEPENDENCY}/META-INF app/META-INF
COPY --from=build-api ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build-ui  ${UI} /app/static
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n", "-cp",  "app:app/lib/*", "fr.takima.cdb.CdbApiApplication"]