# Demo-spring-boot-microservices

This repository contains examples of how to build a Java microservices architecture with Spring Boot, Spring Cloud, and Netflix Eureka.

# Prerequisites:
[JDK 1.8+](https://www.oracle.com/java/technologies/downloads/)  and [maven 3.6.3](https://maven.apache.org/docs/3.6.3/release-notes.html)

# IDEA

These projects included in the microservices example describe how to start an application from scratch and then develop it to become a complete microservices environment.


Usage
========

The best way to run the sample applications is with IDEs like IntelliJ IDEA or STS.

Instalation
============

To install this example, run the following commands:

git clone https://github.com/surajjaiswal11/Demo-spring-boot-microservices.git

 Architecture
================
Our sample microservices-based system consists of the following modules:

**gateway** - a module  or  API gateway that proxies all the micro-services

**cloud-config** - a module that uses Spring Cloud Config Server for running configuration server in the native mode. The configuration files are placed on the classpath.

**eureka** - a module that depending on the example it uses Spring Cloud Netflix Eureka or Spring Cloud Netlix Alibaba Nacos as an embedded discovery server.

**user-service** - a module containing the first of our sample microservices that allows to perform CRUD operation.It communicates with department-service.

**department-service** - a module containing the second of our sample microservices that allows to perform CRUD operation

**common-data** -  a module contaning common entity, repo and constants files.

Navigate to http://localhost:8080/swagger-ui.html (department-service) and  http://localhost:8080/swagger-ui.html (user-service) in your browser to check everything is working correctly and needed request body. You can change the default port in the application.properties file
