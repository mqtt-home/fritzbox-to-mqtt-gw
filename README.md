# fritzbox-to-mqtt-gw

[![mqtt-smarthome](https://img.shields.io/badge/mqtt-smarthome-blue.svg)](https://github.com/mqtt-smarthome/mqtt-smarthome)

Convert the FritzBox tr-064 data to MQTT messages.

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
    "box-type": "dsl",
    "message": [
      {
        "service": "WANDSLLinkConfig:1",
        "action": "GetStatistics",
        "values": [
          { "name": "NewATMCRCErrors" },
          { "name": "NewATMTransmittedBlocks" },
          { "name": "NewATMReceivedBlocks" },
          { "name": "NewAAL5CRCErrors" }
        ]
      },
      {
        "service": "WANCommonInterfaceConfig:1",
        "action": "GetTotalBytesSent",
        "values": [
          { "name": "NewTotalBytesSent" }
        ]
      },
      {
        "service": "WANCommonInterfaceConfig:1",
        "action": "GetTotalBytesReceived",
        "values": [
          { "name": "NewTotalBytesReceived" }
        ]
      },
      {
        "service": "WANPPPConnection:1",
        "action": "GetInfo",
        "values": [
          { "name": "NewConnectionStatus", "mapEnum": { "Connected": 1, "__default": 0 } },
          { "name": "NewExternalIPAddress" },
          { "name": "NewUptime" }
        ]
      },
      {
        "service": "WANDSLInterfaceConfig:1",
        "action": "GetInfo",
        "values": [
          { "name": "NewDownstreamMaxRate" },
          { "name": "NewUpstreamMaxRate" },
          { "name": "NewDownstreamCurrRate" },
          { "name": "NewUpstreamCurrRate" }
        ]
      },
      {
        "service": "WANCommonInterfaceConfig:1",
        "action": "GetCommonLinkProperties",
        "values": [
          { "name": "NewLayer1DownstreamMaxBitRate" },
          { "name": "NewLayer1UpstreamMaxBitRate" },
          { "name": "NewPhysicalLinkStatus", "mapEnum": { "Up": 1, "__default": 0 } }
        ]
      },
      {
        "service": "LANEthernetInterfaceConfig:1",
        "action": "GetStatistics",
        "values": [
          { "name": "NewBytesReceived" },
          { "name": "NewBytesSent" },
          { "name": "NewPacketsReceived" },
          { "name": "NewPacketsSent" }
        ]
      }
    ]
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

# Upgrade to 4.x

The configuration has been changed and the message format is much more flexible now. 
You can now specify the properties of the message that will be sent.

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
