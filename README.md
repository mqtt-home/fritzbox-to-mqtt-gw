# fritzbox-to-mqtt-gw

Convert the FritzBox connection data 
for `dsl` to mqtt messages.

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