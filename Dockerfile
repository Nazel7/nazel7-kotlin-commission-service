FROM openjdk:14-jdk-alpine
ADD pom.xml ./
ADD target/commission-service-deployment.jar commission-service-deployment.jar
ENTRYPOINT ["java", "-jar", "commission-service-deployment.jar"]
VOLUME /dev/student
EXPOSE 8086
