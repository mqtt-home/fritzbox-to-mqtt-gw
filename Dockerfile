FROM openjdk:8-jdk-alpine

LABEL maintainer="Philipp Arndt <2f.mail@gmx.de>"
LABEL version="1.0"
LABEL description="fritzbox to mqtt gateway"


ENV LANG en_US.UTF-8
ENV TERM xterm

WORKDIR /opt/fritzbox-to-mqtt-gw

RUN apk update --no-cache && apk add --no-cache maven

COPY src /opt/fritzbox-to-mqtt-gw

RUN mvn install assembly:single
RUN cp ./de.rnd7.fritzboxmqttgw/target/fritzbox-to-mqtt-gw.jar ./fritzbox-to-mqtt-gw.jar

CMD java -jar fritzbox-to-mqtt-gw.jar /var/lib/fritzbox-to-mqtt-gw/config.json
