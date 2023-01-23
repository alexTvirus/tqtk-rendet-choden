#
# Build stage
#
FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:11-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
COPY --from=build /user.properties user.properties
# ENV PORT=8080 
EXPOSE 8081
ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-jar","demo.jar"]
