package de.rnd7.speedportmqttgw.config;

import java.time.Duration;

public class Config {
	private String mqttBroker;

	private Duration pollingInterval;
	private String fullMessageTopic;
	private String speedportUrl;
	private String speedportPassword;

	public void setMqttBroker(final String mqttBroker) {
		this.mqttBroker = mqttBroker;
	}

	public String getMqttBroker() {
		return this.mqttBroker;
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

	public void setSpeedportUrl(final String speedportUrl) {
		this.speedportUrl = speedportUrl;
	}

	public String getSpeedportUrl() {
		return this.speedportUrl;
	}

	public void setSpeedportPassword(final String speedportPassword) {
		this.speedportPassword = speedportPassword;
	}

	public String getSpeedportPassword() {
		return this.speedportPassword;
	}

}
