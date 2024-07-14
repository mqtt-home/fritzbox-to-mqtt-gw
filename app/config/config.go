package config

import (
	"encoding/json"
	"github.com/philipparndt/go-logger"
	"github.com/philipparndt/mqtt-gateway/config"
	"os"
)

type ValueMapping struct {
	Name    string                 `json:"name"`
	MapEnum map[string]interface{} `json:"mapEnum,omitempty"`
}
type Fritzbox struct {
	PollingInterval int    `json:"polling-interval"`
	Host            string `json:"host"`
	Username        string `json:"username"`
	Password        string `json:"password"`
	BoxType         string `json:"box-type"`
	Message         []struct {
		Service string         `json:"service"`
		Action  string         `json:"action"`
		Values  []ValueMapping `json:"values"`
	} `json:"message"`
}

type Port struct {
	Name string `json:"name"`
	Port string `json:"port"`
}

type Config struct {
	MQTT     config.MQTTConfig `json:"mqtt"`
	Fritzbox Fritzbox          `json:"fritzbox"`
	LogLevel string            `json:"loglevel,omitempty"`
}

func LoadConfig(file string) (Config, error) {
	data, err := os.ReadFile(file)
	if err != nil {
		logger.Error("Error reading config file", err)
		return Config{}, err
	}

	data = config.ReplaceEnvVariables(data)

	// Create a Config object
	var cfg Config

	// Unmarshal the JSON data into the Config object
	err = json.Unmarshal(data, &cfg)
	if err != nil {
		logger.Error("Unmarshalling JSON:", err)
		return Config{}, err
	}

	if cfg.LogLevel == "" {
		cfg.LogLevel = "info"
	}

	return cfg, nil
}
