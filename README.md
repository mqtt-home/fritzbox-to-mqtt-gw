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