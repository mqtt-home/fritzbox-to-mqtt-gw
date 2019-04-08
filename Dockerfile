FROM openjdk:8-jdk-alpine

LABEL maintainer="Philipp Arndt <2f.mail@gmx.de>"
LABEL version="1.0"
LABEL description="speedport to mqtt gateway"


ENV LANG en_US.UTF-8
ENV TERM xterm

WORKDIR /opt/speedport-to-mqtt-gw

RUN apk update --no-cache && apk add --no-cache maven

COPY src /opt/speedport-to-mqtt-gw

RUN mvn install assembly:single
RUN cp ./de.rnd7.speedportmqttgw/target/speedport-to-mqtt-gw.jar ./speedport-to-mqtt-gw.jar

CMD java -jar speedport-to-mqtt-gw.jar /var/lib/speedport-to-mqtt-gw/config.json
