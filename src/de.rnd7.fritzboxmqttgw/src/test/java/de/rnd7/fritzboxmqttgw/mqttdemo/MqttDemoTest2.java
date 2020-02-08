package de.rnd7.fritzboxmqttgw.mqttdemo;

import java.nio.charset.StandardCharsets;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.Test;

public class MqttDemoTest2 {
	@Test
	public void testName() throws Exception {
		try (final MqttClient client = connect()) {
			for (int i = 0; i < 3; i++) {
				publish(client, "de/rnd7/mqtt-analyzer/dishwasher/000123456789", "{\"phase\":\"DRYING\",\"remainingDurationMinutes\":38,\"timeCompleted\":\"15:47\",\"remainingDuration\":\"0:38\",\"phaseId\":1799,\"state\":\"RUNNING\"}");
				publish(client, "de/rnd7/mqtt-analyzer/dishwasher/000123456789/full", "{\"ident\":{\"xkmIdentLabel\":{\"releaseVersion\":\"03.59\",\"techType\":\"EK037\"},\"deviceIdentLabel\":{\"fabNumber\":\"000123456789\",\"swids\":[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\",\"7\",\"8\",\"9\",\"10\",\"11\"],\"fabIndex\":\"64\",\"matNumber\":\"10999999\",\"techType\":\"G7560\"},\"type\":{\"value_raw\":7,\"key_localized\":\"Devicetype\",\"value_localized\":\"Dishwasher\"},\"deviceName\":\"\"},\"state\":{\"programType\":{\"value_raw\":0,\"key_localized\":\"Program type\",\"value_localized\":\"Operation mode\"},\"signalInfo\":false,\"ProgramID\":{\"value_raw\":6,\"key_localized\":\"Program Id\",\"value_localized\":\"\"},\"dryingStep\":{\"value_raw\":null,\"key_localized\":\"Drying level\",\"value_localized\":\"\"},\"remainingTime\":[0,38],\"signalFailure\":false,\"targetTemperature\":[{\"unit\":\"Celsius\",\"value_raw\":-32768,\"value_localized\":null},{\"unit\":\"Celsius\",\"value_raw\":-32768,\"value_localized\":null},{\"unit\":\"Celsius\",\"value_raw\":-32768,\"value_localized\":null}],\"light\":2,\"ventilationStep\":{\"value_raw\":null,\"key_localized\":\"Power Level\",\"value_localized\":\"\"},\"remoteEnable\":{\"fullRemoteControl\":true,\"smartGrid\":false},\"plateStep\":[],\"temperature\":[{\"unit\":\"Celsius\",\"value_raw\":-32768,\"value_localized\":null},{\"unit\":\"Celsius\",\"value_raw\":-32768,\"value_localized\":null},{\"unit\":\"Celsius\",\"value_raw\":-32768,\"value_localized\":null}],\"signalDoor\":false,\"spinningSpeed\":{\"unit\":\"rpm\",\"value_raw\":null,\"key_localized\":\"Spinning Speed\",\"value_localized\":null},\"startTime\":[0,0],\"programPhase\":{\"value_raw\":1799,\"key_localized\":\"Phase\",\"value_localized\":\"Drying\"},\"status\":{\"value_raw\":5,\"key_localized\":\"State\",\"value_localized\":\"In use\"},\"elapsedTime\":[0,0]}}");
				publish(client, "de/rnd7/mqtt-analyzer/dishwasher/frontdoor", "{\"battery\":91,\"voltage\":2985,\"contact\":true,\"linkquality\":65}");
				
				for (int ii = 0; ii < 20; ii ++) {
					publish(client, "de/rnd7/mqtt-analyzer/dishwasher/sensors/water", "{\"temperature\":50.5}");
					publish(client, "de/rnd7/mqtt-analyzer/dishwasher/sensors/air/in", "{\"temperature\":21.5625}");
					publish(client, "de/rnd7/mqtt-analyzer/dishwasher/sensors/air/out", "{\"temperature\":23.1875}");
					publish(client, "de/rnd7/mqtt-analyzer/dishwasher/sensors/bathroom/temperature", "{\"battery\":97,\"voltage\":2995,\"temperature\":22.58,\"humidity\":37.17,\"pressure\":962,\"linkquality\":31}");
				}
			}
			client.disconnect();
		}
	}

	private void publish(final MqttClient client, String topic, String msg) throws MqttException, MqttPersistenceException {
		MqttMessage message = new MqttMessage();
		message.setRetained(true);
		message.setPayload(msg.getBytes(StandardCharsets.UTF_8));
		client.publish(topic, message);
	}

	private MqttClient connect() throws MqttException, MqttSecurityException {
		final MqttClient result = new MqttClient("tcp://test.mosquitto.org", "mqttanalyzer", new MemoryPersistence());
		final MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		result.connect(connOpts);
		return result;
	}
}
