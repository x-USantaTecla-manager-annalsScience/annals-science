FROM maven as builder
WORKDIR /annals-science
COPY . .
RUN mvn package spring-boot:repackage

FROM openjdk:11.0.8-slim-buster
COPY --from="builder" /annals-science/target/ .
EXPOSE 8080
CMD java -jar -Dspring.profiles.active=${profile} *.jar --db-host=${DB_HOST} --secretToken=${SECRET_TOKEN} --spring.datasource.password=${DB_PASS} --spring.jpa.hibernate.ddl-auto=${DDL_AUTO}