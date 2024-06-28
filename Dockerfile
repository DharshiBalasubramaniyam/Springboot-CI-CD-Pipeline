FROM openjdk:17-jdk-slim
EXPOSE 8080
ADD target/springboot-ci-cd.jar springboot-ci-cd.jar
ENTRYPOINT ["java","-jar","/springboot-ci-cd.jar"]
