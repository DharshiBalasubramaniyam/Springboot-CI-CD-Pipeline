# Spring Boot CI/CD Pipeline

<p align="center">
  <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Boot-green?style=for-the-badge">
  <img alt="Static Badge" src="https://img.shields.io/badge/GitHub%20Actions-purple?style=for-the-badge">
  <img alt="Static Badge" src="https://img.shields.io/badge/Docker-blue?style=for-the-badge">
  <img alt="Static Badge" src="https://img.shields.io/badge/AWS-red?style=for-the-badge">
</p>

## 🌟 Description

This project demonstrates a complete CI/CD pipeline for a Spring Boot application using GitHub Actions, Docker, and AWS. 

The pipeline automates the build, test, Dockerization, and deployment processes to ensure consistent and reliable delivery of the application.

The main features of this project include:

1. **Spring Boot Application**: A sample Spring Boot application that serves as the base for demonstrating the CI/CD pipeline.
   
2. **Dockerization**: The application is packaged into a Docker container to ensure it runs consistently across different environments.
   
3. **Continuous Integration with GitHub Actions**: Automated build, test, and Dockerization of the Spring Boot application using GitHub Actions. Every push to the main branch triggers the CI pipeline.
   
4. **AWS Deployment**

## 🌟 Dockerization

Docker is a tool designed to make it easy to build, test, deploy and run applications by using containers.

Containers are lightweight, self-contained units that package an application and all its dependencies together.

Each Docker container runs in its own isolated environment, which means that the dependencies of one application won't interfere with another.

Docker ensures that our application runs the same way on any machine. This eliminates the `it works on my machine` problem.

### Docker architecture components

1. **Docker Client**: The Docker client is the way that users interact with Docker by using commands. The client sends these commands to the Docker daemon.

2. **Docker Daemon**: The Docker daemon runs on the host machine. It listens for Docker API requests and manages Docker objects such as images, containers, networks, and volumes.

3. **Docker Images**: The Docker images are read-only templates that contain the application and all the dependencies needed to run it. Images are used to create Docker containers. They can be created from a Dockerfile or pulled from a Docker registry.

4. **Docker Containers**: The Containers are runnable instances of Docker images. They contain everything needed to run the application, including the code, runtime, libraries, and settings.

5. **Docker Registries**: The Docker registries store Docker images. The default registry is Docker Hub.

6. **Dockerfile**: The Dockerfile is a text file that contains a set of instructions on how to build a Docker image.

### Docker workflow

1. Create a Dockerfile.
```
# Specify the base image for your Docker image
FROM openjdk:17-jdk-slim  # Replace with your desired JDK version

# Expose the application port
EXPOSE 8080               # Replace with your application's exposed port

# Add the JAR file to the container
COPY target/yourapp.jar yourapp.jar  # Replace with your actual JAR name

# Run the JAR file
ENTRYPOINT ["java","-jar","/yourapp.jar"]
```

2. Build Docker image.
```
docker build -t yourusername/yourapp .
```

3. Login to Dockerhub.
```
docker login -u yourusername -p yourpassword
```

4. Push the image to Dockerhub.
```
docker push yourusername/yourapp:yourtagname
```

5. Pull and run the image. (By others)
```
docker pull yourusername/yourapp:yourtagname
docker run -p 8080:8080 yourusername/yourapp:yourtagname
```

### Automate Dockerization with GitHub Actions

- Traditionally, if we're using Docker without automation tools like GitHub Actions, we would indeed need to manually build our application, create a Docker image, and push it to a Docker registry (like Docker Hub) every time we make changes to our code.

- GitHub Actions automates this process.

- With GitHub Actions, we can define workflows in YAML files that specify what actions to take when certain events happen (e.g., pushing to a specific branch).

- By this way, we can automate tasks like building our application, running tests, building Docker images, pushing them to Docker Hub, and even deploying to a server or cloud platform.

- Automating Tasks with GitHub Actions saves time, reduces manual effort and ensures that every code change triggers a consistent build and deployment process.

- The following example workflow demonstrates how to build a docker image and push it to the DockerHub.

```yaml
name: CI/CD Pipeline  # Name of the CI/CD Pipeline

on:
  push:
    branches: [ "main" ]  # Trigger on push events to the main branch
  pull_request:
    branches: [ "main" ]  # Trigger on pull requests to the main branch

jobs:
  build:
    runs-on: ubuntu-latest  # Runs on the latest version of Ubuntu | Default operating system environment for workflows unless explicitly specified.
    
    steps:
    - name: Checkout code  # Step to checkout the code from the repository | Fetch the source code repository into the runner machine where our workflow is executing. 
      uses: actions/checkout@v4
      
    - name: Set up JDK 17 # Step to set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: maven
        
    - name: Build with Maven # Step to build the project with Maven
      run: mvn clean install

    - name: Login to DockerHub # Step to login to DockerHub
      run: docker login -u ${{secrets.DOCKER_USERNAME}} -p ${{secrets.DOCKER_PASSWORD}}

    - name: Build Docker image # Step to build the Docker image
      run: docker build -t ${{secrets.DOCKER_USERNAME}}/${{secrets.REPOSITORY_NAME}} .

    - name: Push the image to DockerHub # Step to push the Docker image to DockerHub
      run: docker push ${{secrets.DOCKER_USERNAME}}/${{secrets.REPOSITORY_NAME}}:${{secrets.TAG_NAME}}
```


