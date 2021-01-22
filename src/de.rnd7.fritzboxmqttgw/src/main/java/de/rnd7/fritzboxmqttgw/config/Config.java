package de.rnd7.fritzboxmqttgw.config;

import de.rnd7.mqttgateway.config.ConfigMqtt;


public class Config {
    private ConfigMqtt mqtt;
    private ConfigFritzbox fritzbox;

    public ConfigMqtt getMqtt() {
        return mqtt;
    }

    public ConfigFritzbox getFritzbox() {
        return fritzbox;
    }
}
