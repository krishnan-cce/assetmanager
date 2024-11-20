FROM eclipse-temurin:17

LABEL mentainer ="krishnan@udyata.com"

WORKDIR /app
COPY target/linen-track-main.jar /app/springboot-docker.jar

ENTRYPOINT ["java","-jar","springboot-docker.jar"]
