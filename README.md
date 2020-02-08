# fritzbox-to-mqtt-gw

Convert the FritzBox connection data 
for `dsl` to mqtt messages.

## Example configuration

```json
{
  "mqtt": {
    "url": "tcp://192.168.2.2:1883",
    "client-id": "fritzbox-mqtt-gw",
    "username": "mqtt-username",
    "password": "mqtt-password",
    "retain": true,

    "message-interval": 60,
    "full-message-topic": "internet/connection"
  },

  "fritzbox": {
    "host": "192.168.2.1",
    "username": "fritzbox-username",
    "password": "fritzbox-password"
  }
}
```

## Example message

```json
{
  "NewTotalBytesSent": 2950666126,
  "NewDownstreamMaxRate": 280133,
  "NewATMCRCErrors": 0,
  "NewUpstreamMaxRate": 50012,
  "NewDownstreamCurrRate": 270157,
  "NewConnectionStatus": 1,
  "NewUptime": 5123,
  "NewUpstreamCurrRate": 48321,
  "NewAAL5CRCErrors": 0,
  "NewATMTransmittedBlocks": 1254889514,
  "NewExternalIPAddress": "XXX.XXX.XXX.XXX",
  "NewLayer1DownstreamMaxBitRate": 259117000,
  "NewBytesReceived": 0,
  "NewATMReceivedBlocks": 987654249,
  "NewBytesSent": 0,
  "NewLayer1UpstreamMaxBitRate": 50072000,
  "NewPhysicalLinkStatus": 1,
  "NewPacketsReceived": 0,
  "NewPacketsSent": 0,
  "NewTotalBytesReceived": 12345460744
}
```

# build

build the docker container using `build.sh`

# run

copy the `config-example.json` to `/production/config/config.json`
```
cd ./production
docker-compose up -d
```

This project is based on:
https://github.com/mirthas/FritzTR064

with patches from:
https://github.com/a-brandt/FritzTR064

Grafana Dashboard adapted from:
https://blog.butenostfreesen.de/2018/10/11/Fritz-Box-Monitoring-mit-Grafana-und-Raspberry/