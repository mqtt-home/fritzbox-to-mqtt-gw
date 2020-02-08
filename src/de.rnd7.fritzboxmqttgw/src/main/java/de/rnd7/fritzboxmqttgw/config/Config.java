package de.rnd7.fritzboxmqttgw.config;

import java.time.Duration;
import java.util.Optional;

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
