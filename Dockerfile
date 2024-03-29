#FROM gcr.io/distroless/nodejs:18
#COPY ./app/dist /opt/app/
#
#CMD ["/opt/app/index.js", "/var/lib/fritzbox-to-mqtt-gw/config.json"]

FROM node:18.12-alpine
COPY app/dist /opt/app/
WORKDIR /opt/app/

CMD ["node", "index.js", "/var/lib/fritzbox-to-mqtt-gw/config.json"]
