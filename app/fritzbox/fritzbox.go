package fritzbox

import (
	"encoding/json"
	"fmt"
	"github.com/mqtt-home/fritzbox-to-mqtt-gw/config"
	"github.com/philipparndt/go-logger"
	"github.com/sberk42/fritzbox_exporter/fritzbox_upnp"
)

func JsonFromDataMap(dataMap map[string]interface{}) string {
	// Marshal the map into a JSON byte slice
	jsonBytes, err := json.Marshal(dataMap)
	if err != nil {
		// Handle the error (optional: return an empty string or an error message)
		fmt.Println("Error marshaling JSON:", err)
		return ""
	}

	// Convert the byte slice to a string and return it
	return string(jsonBytes)
}

func mapEnumValue(mapping []config.ValueMapping, key string, value interface{}) interface{} {
	for _, msgValue := range mapping {
		if key == msgValue.Name {
			for mapKey, mapValue := range msgValue.MapEnum {
				if value == mapKey {
					value = mapValue
					break
				} else if mapKey == "__default" {
					value = mapValue
				}
			}
		}
	}
	return value
}

func LoadData(cfg config.Config) map[string]interface{} {

	services, err := fritzbox_upnp.LoadServices(
		"http://"+cfg.Fritzbox.Host+":49000",
		cfg.Fritzbox.Username,
		cfg.Fritzbox.Password,
		false)
	if err != nil {
		logger.Error("Failed loading services", err)
		return nil
	}

	dataMap := make(map[string]interface{})

	for _, service := range services.Services {
		logger.Debug("Service: ", service.ServiceType)
		for _, action := range service.Actions {
			logger.Debug("    Action: ", action.Name)
		}

		for _, msg := range cfg.Fritzbox.Message {
			if msg.Service == service.ServiceType {
				for _, action := range service.Actions {
					if action.Name == msg.Action {
						actionArg := fritzbox_upnp.ActionArgument{
							Name: "",
						}
						result, err := action.Call(&actionArg)
						if err == nil {
							for key, value := range result {
								dataMap[key] = mapEnumValue(msg.Values, key, value)
							}
						} else {
							logger.Error("Error calling action", err)
						}
					}
				}
			}
		}
	}

	return dataMap
}
