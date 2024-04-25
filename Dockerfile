#FROM gcr.io/distroless/nodejs:18
#COPY ./app/dist /opt/app/
#
#CMD ["/opt/app/index.js", "/var/lib/fritzbox-to-mqtt-gw/config.json"]

FROM node:18.12-alpine
COPY app /opt/app/
WORKDIR /opt/app/
RUN npm install npm-run-all --save-dev
RUN npm run build
WORKDIR /opt/app/dist/
RUN mkdir /var/lib/fritzbox-to-mqtt-gw

CMD ["node", "index.js", "/var/lib/fritzbox-to-mqtt-gw/config.json"]
