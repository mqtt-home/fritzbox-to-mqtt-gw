package de.rnd7.speedportmqttgw.mqtt;

import org.json.JSONObject;

public class Message {
	private final String topic;
	private final JSONObject data;

	public Message(final String topic, final JSONObject data) {
		this.topic = topic;
		this.data = data;
	}

	public String getTopic() {
		return this.topic;
	}

	public JSONObject getData() {
		return this.data;
	}
}
