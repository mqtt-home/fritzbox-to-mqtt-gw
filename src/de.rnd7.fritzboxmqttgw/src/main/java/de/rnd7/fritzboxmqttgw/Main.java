package de.rnd7.fritzboxmqttgw;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.rnd7.fritzboxmqttgw.config.ConfigFritzbox;
import de.rnd7.fritzboxmqttgw.config.ConfigMqtt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;

import de.rnd7.fritzboxmqttgw.config.Config;
import de.rnd7.fritzboxmqttgw.config.ConfigParser;
import de.rnd7.fritzboxmqttgw.mqtt.GwMqttClient;
import de.rnd7.fritzboxmqttgw.mqtt.Message;
import de.rnd7.fritzboxmqttgw.fritzbox.Fritzbox;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	private final Config config;

	private final EventBus eventBus = new EventBus();

	@SuppressWarnings("squid:S2189")
	public Main(final Config config) {
		ConfigMqtt configMqtt = config.getMqtt();

		this.config = config;
		this.eventBus.register(new GwMqttClient(configMqtt));

		try {
			final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(this::exec, 0, configMqtt.getPollingInterval().getSeconds(), TimeUnit.SECONDS);

			while (true) {
				this.sleep();
			}
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void exec() {
		try {
			ConfigFritzbox configFritzbox = config.getFritzbox();

			final Fritzbox fritzbox = new Fritzbox(configFritzbox.getHost(),
					configFritzbox.getUsername(),
					configFritzbox.getPassword());
			this.eventBus.post(new Message("dsl", fritzbox.downloadInfo()));
		} catch (final IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	private void sleep() {
		try {
			Thread.sleep(100);
		} catch (final InterruptedException e) {
			LOGGER.debug(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
	}

	public static void main(final String[] args) {
		if (args.length != 1) {
			LOGGER.error("Expected configuration file as argument");
			return;
		}

		try {
			new Main(ConfigParser.parse(new File(args[0])));
		} catch (final IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
