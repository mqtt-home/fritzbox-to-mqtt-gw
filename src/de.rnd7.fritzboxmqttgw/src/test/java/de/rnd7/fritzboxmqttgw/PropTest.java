package de.rnd7.fritzboxmqttgw;

import static org.junit.Assert.*;

import java.io.File;

import de.rnd7.fritzboxmqttgw.config.ConfigFritzbox;
import org.junit.Test;

import de.rnd7.fritzboxmqttgw.config.Config;
import de.rnd7.fritzboxmqttgw.config.ConfigParser;
import de.rnd7.fritzboxmqttgw.fritzbox.Fritzbox;

public class PropTest {
	@Test
	public void testName() throws Exception {
		Config config = ConfigParser.parse(new File("/Users/philipparndt/homeserver-services/config/fritzbox/config.json"));
		ConfigFritzbox fritzbox = config.getFritzbox();

		Fritzbox fb = new Fritzbox(fritzbox.getHost(), fritzbox.getUsername(), fritzbox.getPassword());
		fb.downloadInfo();
	}
}
