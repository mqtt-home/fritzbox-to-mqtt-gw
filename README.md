# speedport-to-mqtt-gw

Convert the Speedport Hybrid connection data 
for `lte` and `dsl` to mqtt topics.

# build

build the docker container using `build.sh`

# run

copy the `config-example.json` to `/production/config/config.json`
```
cd ./production
docker-compose up -d
```

Thanks to @melle for the [l33tport](https://github.com/melle/l33tport) script
