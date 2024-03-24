FROM maven:3.8-openjdk-17 as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN mvn package

FROM openjdk:17
COPY --from=build /usr/app/target/BikeCatalog-0.0.1-SNAPSHOT.jar /app/BikeCatalog-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/BikeCatalog-0.0.1-SNAPSHOT.jar"]