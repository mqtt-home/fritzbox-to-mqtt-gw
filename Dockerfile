FROM gcr.io/distroless/nodejs:18
COPY ./app/dist /opt/app/

CMD ["/opt/app/index.js", "/var/lib/fritzbox-to-mqtt-gw/config.json"]
