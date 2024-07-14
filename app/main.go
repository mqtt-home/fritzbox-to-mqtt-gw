package main

import (
	"encoding/json"
	"github.com/mqtt-home/fritzbox-to-mqtt-gw/config"
	"github.com/mqtt-home/fritzbox-to-mqtt-gw/fritzbox"
	"github.com/mqtt-home/fritzbox-to-mqtt-gw/mqtt"
	"github.com/philipparndt/go-logger"
	config2 "github.com/philipparndt/mqtt-gateway/config"
	mqtt2 "github.com/philipparndt/mqtt-gateway/mqtt"
	"os"
	"os/signal"
	"syscall"
	"time"
)

func PublishJSON(cfg config2.MQTTConfig, data any) {
	jsonData, err := json.Marshal(data)
	if err != nil {
		logger.Error("Error marshaling to JSON", err)
	} else {
		mqtt2.PublishAbsolute(cfg.Topic, string(jsonData), cfg.Retain)
	}
}

func mainLoop(cfg config.Config) {
	for {
		dataMap := fritzbox.LoadData(cfg)
		logger.Info("DataMap: ", fritzbox.JsonFromDataMap(dataMap))
		PublishJSON(cfg.MQTT, dataMap)

		time.Sleep(time.Duration(cfg.Fritzbox.PollingInterval) * time.Second)
	}
}

func main() {
	if len(os.Args) < 2 {
		logger.Error("No config file specified")
		os.Exit(1)
	}

	configFile := os.Args[1]
	logger.Info("Config file", configFile)
	cfg, err := config.LoadConfig(configFile)
	if err != nil {
		logger.Error("Failed loading config", err)
		return
	}

	logger.SetLevel(cfg.LogLevel)
	mqtt.Start(cfg.MQTT)

	go mainLoop(cfg)

	logger.Info("Application is now ready. Press Ctrl+C to quit.")

	quitChannel := make(chan os.Signal, 1)
	signal.Notify(quitChannel, syscall.SIGINT, syscall.SIGTERM)
	<-quitChannel

	logger.Info("Received quit signal")
}
