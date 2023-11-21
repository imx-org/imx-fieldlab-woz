FROM eclipse-temurin:21-jre-alpine

LABEL org.opencontainers.image.source=https://github.com/imx-org/imx-fieldlab-woz

RUN mkdir /opt/app

COPY target/imx-fieldlab-woz-*.jar /opt/app/app.jar
COPY config /opt/app/config

WORKDIR /opt/app

CMD ["java", "-jar", "app.jar"]