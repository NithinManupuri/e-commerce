FROM openjdk17:alpine-jre
EXPOSE 8080
COPY target/project.jar project.jar
ENTRYPOINT ["java","-jar","/project.jar"]
