package de.rnd7.fritzboxmqttgw.config;

import java.time.Duration;
import java.util.Optional;

public class Config {
	private String mqttBroker;

	private Duration pollingInterval;
	private String fullMessageTopic;
	private String fritzboxHost;
	private String fritzboxUsername;
	private String fritzboxPassword;
	
	private Optional<MqttCredentials> mqttCredentials = Optional.empty();
	
	public static class MqttCredentials {
		private String username = "";
		private String password = "";
		
		public void setUsername(String username) {
			this.username = username;
		}
		
		public String getUsername() {
			return username;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}
		
		public String getPassword() {
			return password;
		}

	}
	
	public void setMqttBroker(final String mqttBroker) {
		this.mqttBroker = mqttBroker;
	}

	public String getMqttBroker() {
		return this.mqttBroker;
	}

	public Optional<MqttCredentials> getMqttCredentials() {
		return mqttCredentials;
	}
	
	public MqttCredentials initMqttCredentials() {
		MqttCredentials result = new MqttCredentials();
		mqttCredentials = Optional.of(result); 
		return result;
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
