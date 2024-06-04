FROM openjdk:21-jdk
VOLUME /tmp
COPY target/*.jar MathEase.jar
ENTRYPOINT ["java","-jar","/MathEase.jar"]
EXPOSE 8080