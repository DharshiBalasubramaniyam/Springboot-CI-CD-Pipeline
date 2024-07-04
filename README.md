# Spring Boot CI/CD Pipeline

<p align="center">
  <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Boot-green?style=for-the-badge">
  <img alt="Static Badge" src="https://img.shields.io/badge/GitHub%20Actions-purple?style=for-the-badge">
  <img alt="Static Badge" src="https://img.shields.io/badge/Docker-blue?style=for-the-badge">
  <img alt="Static Badge" src="https://img.shields.io/badge/amazon%20ecs-red?style=for-the-badge">
  <img alt="Static Badge" src="https://img.shields.io/badge/amazon%20ecr-red?style=for-the-badge">
  <img alt="Static Badge" src="https://img.shields.io/badge/aws%20fargate-red?style=for-the-badge">
  <img alt="Static Badge" src="https://img.shields.io/badge/amazon%20elb-red?style=for-the-badge">
  <img alt="Static Badge" src="https://img.shields.io/badge/mongodb%20atlas-darkgreen?style=for-the-badge">
</p>

- - -

## 🌟 Description

![ci-cd-diagram](https://github.com/DharshiBalasubramaniyam/Springboot-CI-CD-Pipeline/assets/139672976/5bb11561-eb98-4718-85fd-510c1275d5cb)

This project demonstrates a complete CI/CD pipeline for a Spring Boot CRUD application using GitHub Actions, Docker, and AWS. 

The pipeline automates the build, test, Dockerization, and deployment processes to ensure consistent and reliable delivery of the application.

The main features of this project include:

1. **Spring Boot Application**: A Inventory CRUD application that serves as the base for demonstrating the CI/CD pipeline.
   
2. **Dockerization**: The application is packaged into a Docker container and the Docker image is pushed to DockerHub for easy distribution.
   
3. **AWS Deployment**: Deployment utilizes Amazon ECR for Docker image storage, deploys on Amazon ECS service powered by AWS Fargate, and balances traffic with ELB.

4. **MongoDB Integration**: The MongoDB hosted on Mongo Atlas for scalable and reliable data storage.

5. **Continuous Integration with GitHub Actions**: Automated build, test, dockerization and deployment of the Spring Boot application using GitHub Actions. Every push to the main branch triggers the CI pipeline.

The frontend React application is also Dockerized and deployed using GitHub Actions, Docker, and AWS. Explore the frontend repository <a href="https://github.com/DharshiBalasubramaniyam/React-CI-CD-Pipeline">here</a>.

- - -

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

- - -

## 🌟 AWS Deployment

AWS stands for Amazon Web Services, a cloud infrastructure where we can host our application.

AWS offer IT services to the market in form of web services/cloud computing, which is about storing and access the data over the internet.

AWS offers a plethora of services across multiple categories.

For example: 

1. compute: Amazon EC2, AWS Lambda
2. containers: Amazon ECS, Amazon EKS
3. storage: Amazon S3
4. database: Amazon RDS, Amazon DynamoDB
   
and even more categories and services, making it a versatile platform for various IT needs.

Here as we are going to deploy a containerazed application we are going to look into containers category.

## Amazon ECS

- Amazon Elastic Container Service (ECS) is a **fully managed container orchestration service** provided by Amazon Web Services (AWS).

- A **fully managed container orchestration service** means that the service takes care of all the underlying infrastructure and complex operations (scheduling, scaling, load balancing, monitoring etc.) needed to deploy, manage, and scale containerized applications.

**Amazon ECS Components**

- **Docker image Registry**: The Docker registries store Docker images. We can use Amazon Elastic Container Registry (ECR), a fully managed container image registry service provided by AWS or else DockerHub.

- **Task Definitions**: A blueprint that describes how a Docker container should launch. It includes settings like the Docker image to use, CPU and memory requirements, and environment variables.

- **Tasks**: The instantiation of a task definition.

- **Container**: A task definition can include multiple container definitions, allowing us to define complex applications that consist of multiple interacting containers.
For example, we might have a web server container and a database container defined within the same task definition.

- **Services**: Allows to run and maintain a specified number of task instances simultaneously in a cluster. 

- **Cluster**: A logical grouping of tasks or services.

- **Elastic Load Balancing (ELB)**: Distributes incoming traffic across tasks in your services, ensuring scalability and reliability.

- **Launch Type**: Refers to how we want to deploy and manage your containers. AWS ECS supports two launch types: EC2 Launch Type, Fargate Launch Type. With EC2 we have to manage the EC2 instances(virtual servers) where our containers run. But with fargate, AWS manages the infrastructure for us automatically, we don’t need to provision or manage EC2 instances directly.

## ECS workflow 

1. Create an ECR repository to store your images.
2. Create an ECS task definition, an ECS cluster, and an ECS service.
3. Setup EC2 instances (Opt for the EC2 launch type).
4. Set Up Load Balancing (Optional).
5. Store your ECS task definition as a JSON file in your repository.
6. Create workflow file to automate CI/CD Integration with GitHubActions (check <a href="./.github/workflows/ci-cd.yml">ci-cd.yml</a> workflow file).
7. Access the endpoint of our deployed application in a browser. To access the endpoint of your deployed application in a browser.
   - To access the endpoint of your deployed application, use the DNS name of your Elastic Load Balancer (ELB) by copying it and pasting it into your web browser's address bar.
   - If you're not using a load balancer, access the application using the service's public IP address and the specified port number.
