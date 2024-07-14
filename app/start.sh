#!/usr/bin/env bash

PWD=$(pwd)
config="$PWD/../production/config/config.json"

go run . "$config"
