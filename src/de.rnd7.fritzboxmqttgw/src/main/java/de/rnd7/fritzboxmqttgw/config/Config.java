package de.rnd7.fritzboxmqttgw.config;

import java.time.Duration;

public class Config {
	private String mqttBroker;
	private String mqttUsername = "";
	private String mqttPassword = "";

	private Duration pollingInterval;
	private String fullMessageTopic;
	private String fritzboxHost;
	private String fritzboxUsername;
	private String fritzboxPassword;

	public void setMqttBroker(final String mqttBroker) {
		this.mqttBroker = mqttBroker;
	}

	public String getMqttBroker() {
		return this.mqttBroker;
	}

	public void setUsername(final String username) {
		this.mqttUsername = username;
	}

	public String getUsername() {
		return this.mqttUsername;
	}

	public void setPassword(final String password) {
		this.mqttPassword = password;
	}

	public String getPassword() {
		return this.mqttPassword;
	}

	public void setPollingInterval(final Duration pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	public Duration getPollingInterval() {
		return this.pollingInterval;
	}

	public void setFullMessageTopic(final String fullMessageTopic) {
		this.fullMessageTopic = fullMessageTopic;
	}

	public String getFullMessageTopic() {
		return this.fullMessageTopic;
	}

	public void setFritzboxHost(final String fritzboxHost) {
		this.fritzboxHost = fritzboxHost;
	}

	public String getFritzboxHost() {
		return this.fritzboxHost;
	}

	public void setFritzboxUsername(String fritzboxUsername) {
		this.fritzboxUsername = fritzboxUsername;
	}
	
	public String getFritzboxUsername() {
		return fritzboxUsername;
	}
	
	public void setFritzboxPassword(final String fritzboxPassword) {
		this.fritzboxPassword = fritzboxPassword;
	}

	public String getFritzboxPassword() {
		return this.fritzboxPassword;
	}

}
