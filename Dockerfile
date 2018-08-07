FROM java:8  
COPY ./target/Rest-0.0.1-SNAPSHOT.jar /Rest-0.0.1-SNAPSHOT.jar
WORKDIR /  
EXPOSE 8080
CMD ["java", "-jar", "Rest-0.0.1-SNAPSHOT.jar"]