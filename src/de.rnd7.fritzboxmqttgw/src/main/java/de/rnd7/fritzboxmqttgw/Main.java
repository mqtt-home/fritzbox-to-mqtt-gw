package de.rnd7.fritzboxmqttgw;

import java.io.File;
import java.io.IOException;

import de.rnd7.mqttgateway.GwMqttClient;
import de.rnd7.mqttgateway.config.ConfigParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rnd7.fritzboxmqttgw.config.Config;
import de.rnd7.fritzboxmqttgw.fritzbox.FritzboxService;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private final Config config;

    @SuppressWarnings("squid:S2189")
    public Main(final Config config) {
        this.config = config;
        try {
            GwMqttClient.start(config.getMqtt()
                .setDefaultTopic("fritzbox")
            ).online();

            new FritzboxService(config.getFritzbox()).start();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static void main(final String[] args) {
        if (args.length != 1) {
            LOGGER.error("Expected configuration file as argument");
            return;
        }

        try {
            new Main(ConfigParser.parse(new File(args[0]), Config.class));
        } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
