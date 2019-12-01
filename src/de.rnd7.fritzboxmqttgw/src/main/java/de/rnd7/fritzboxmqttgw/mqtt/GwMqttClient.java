package de.rnd7.fritzboxmqttgw.mqtt;

import java.util.Optional;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.Subscribe;

import de.rnd7.fritzboxmqttgw.config.Config;

public class GwMqttClient {
	private static final int QOS = 2;
	private static final String CLIENTID = "fritzbox-mqtt-gw";

	private static final Logger LOGGER = LoggerFactory.getLogger(GwMqttClient.class);

	private final MemoryPersistence persistence = new MemoryPersistence();
	private final Object mutex = new Object();
	private final Config config;

	private Optional<MqttClient> client;

	public GwMqttClient(final Config config) {
		this.config = config;
		this.client = this.connect();
	}

	private Optional<MqttClient> connect() {
		try {
			LOGGER.info("Connecting MQTT client");
			final MqttClient result = new MqttClient(this.config.getMqttBroker(), CLIENTID, this.persistence);
			final MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);

			this.config.getMqttCredentials().ifPresent(credentials -> {
				connOpts.setUserName(credentials.getUsername());
				connOpts.setPassword(credentials.getPassword().toCharArray());
			});

			result.connect(connOpts);

			return Optional.of(result);
		} catch (final MqttException e) {
			LOGGER.error(e.getMessage(), e);
			return Optional.empty();
		}
	}

	private void publish(final String topic, final String value) {
		synchronized (this.mutex) {
			if (!this.client.filter(MqttClient::isConnected).isPresent()) {
				this.client = this.connect();
			}

			this.client.ifPresent(mqttClient -> {
				try {
					final MqttMessage message = new MqttMessage(value.getBytes());
					message.setQos(QOS);
					mqttClient.publish(topic, message);
				} catch (final MqttException e) {
					LOGGER.error(e.getMessage(), e);
				}
			});
		}
	}

	@Subscribe
	public void publish(final Message message) {
		final String topic = this.config.getFullMessageTopic() + "/" + message.getTopic();
		final String valueString = message.getData().toString();

		LOGGER.info("{} = {}", topic, valueString);

		this.publish(topic, valueString);
	}

}
