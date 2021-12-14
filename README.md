# fritzbox-to-mqtt-gw

[![mqtt-smarthome](https://img.shields.io/badge/mqtt-smarthome-blue.svg)](https://github.com/mqtt-smarthome/mqtt-smarthome)

Convert the FritzBox connection data for `DSL` and `CABLE` to MQTT messages.

## Example configuration

```json
{
  "mqtt": {
    "url": "tcp://192.168.2.2:1883",
    "client-id": "fritzbox-mqtt-gw",
    "username": "mqtt-username",
    "password": "mqtt-password",
    "retain": true,

    "topic": "internet/connection"
  },

  "fritzbox": {
    "polling-interval": 60,
    "host": "192.168.2.1",
    "username": "fritzbox-username",
    "password": "fritzbox-password", 
    "enum-as-long": false
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
  "NewConnectionStatus": "Connected",
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
  "NewPhysicalLinkStatus": "Up",
  "NewPacketsReceived": 0,
  "NewPacketsSent": 0,
  "NewTotalBytesReceived": 12345460744
}
```

# Upgrade from 2.x to 3.x

The message format is changed for `NewConnectionStatus` and `NewPhysicalLinkStatus`.
When you like to restore the old behavior, you have to set `"enum-as-long": true` in the configuration.

# build

build the docker container using `build.sh`

# run

copy the `config-example.json` to `/production/config/config.json`

```
cd ./production
docker-compose up -d
```

Grafana Dashboard adapted from:
https://blog.butenostfreesen.de/2018/10/11/Fritz-Box-Monitoring-mit-Grafana-und-Raspberry/
