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
    "message": [
      {
        "service": "urn:dslforum-org:service:WANCommonInterfaceConfig:1",
        "action": "GetTotalBytesSent",
        "values": [
          { "name": "TotalBytesSent" }
        ]
      },
      {
        "service": "urn:dslforum-org:service:WANCommonInterfaceConfig:1",
        "action": "GetTotalBytesReceived",
        "values": [
          { "name": "TotalBytesReceived" }
        ]
      },
      {
        "service": "urn:dslforum-org:service:WANIPConnection:1",
        "action": "GetInfo",
        "values": [
          { "name": "Uptime" },
          { "name": "ConnectionStatus", "mapEnum": { "Connected": 1, "__default": 0 } }
        ]
      },
      {
        "service": "urn:dslforum-org:service:Hosts:1",
        "action": "GetSpecificHostEntry",
        "args": [
          { "name": "NewMACAddress", "value": "12:34:56:78:9A:BC" }
        ],
        "alias": "host1"
      }
    ]
  }
}
```

## Example message

```json
{
  "ConnectionStatus": 0,
  "ConnectionTrigger": "AlwaysOn",
  "ConnectionType": "IP_Routed",
  "DNSEnabled": true,
  "DNSOverrideAllowed": true,
  "DNSServers": "xx:xx:xx:xx:xx, ...",
  "Enable": true,
  "ExternalIPAddress": "xx.xx.xx.xx",
  "LastConnectionError": "ERROR_NONE",
  "MACAddress": "xx:xx:xx:xx:xx",
  "NATEnabled": true,
  "Name": "internet",
  "PossibleConnectionTypes": "IP_Routed, IP_Bridged",
  "RSIPAvailable": false,
  "RouteProtocolRx": "Off",
  "TotalBytesReceived": 812730036094,
  "TotalBytesSent": 164014571233,
  "Uptime": 3801160,
  "host1.Active": true,
  "host1.AddressSource": "DHCP",
  "host1.HostName": "PC-xx-xx-xx-xx",
  "host1.IPAddress": "xx.xx.xx.xx",
  "host1.InterfaceType": "Ethernet",
  "host1.LeaseTimeRemaining": 0
}
```

# Upgrade to 5.x

5.x is a major upgrade
- reimplemented using go for lower memory footprint
- configuration has changed slightly

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
