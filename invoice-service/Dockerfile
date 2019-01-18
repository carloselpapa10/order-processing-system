FROM java:8
MAINTAINER c.avendano10@gmail.com
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar","invoice-service.jar"]
COPY target/invoice-service.jar .