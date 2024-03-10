FROM openjdk:11
EXPOSE 8080
COPY target/project.jar project.jar
ENTRYPOINT ["java","-jar","/project.jar"]