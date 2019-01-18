FROM java:8
MAINTAINER c.avendano10@gmail.com
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar","orderview-service.jar"]
COPY target/orderview-service.jar .