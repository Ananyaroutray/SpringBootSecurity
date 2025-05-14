FROM openjdk:17

WORKDIR /app

COPY ./target/CustomerSecurity_SpringBoot.jar /app

EXPOSE 8080

CMD ["java", "-jar", "CustomerSecurity_SpringBoot.jar"]