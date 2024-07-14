#!/bin/bash
cd "$(dirname "$0")/app"

docker build -t pharndt/fritzboxmqtt .
