package de.rnd7.fritzboxmqttgw.fritzbox;

import java.io.IOException;

import org.json.JSONObject;

import de.mapoll.javaAVMTR064.Action;
import de.mapoll.javaAVMTR064.FritzConnection;
import de.mapoll.javaAVMTR064.Response;
import de.mapoll.javaAVMTR064.Service;


public class Fritzbox {

	private String host;
	private String username;
	private String password;

	public Fritzbox(final String host, final String username, final String password) {
		this.host = host;
		this.username = username;
		this.password = password;
	}

	public JSONObject downloadInfo() throws IOException {

		try {
			FritzConnection connection = new FritzConnection(host, username, password);
			connection.init();
			
			JSONObject result = new JSONObject();
			linkConfig(connection, result);
			ethernetInterfaceConfig(connection, result);
			wanInterfaceConfig(connection, result);
			
			return result;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	private void linkConfig(FritzConnection connection, JSONObject result) throws IOException, NoSuchFieldException {
		Response response = get(connection, "WANDSLLinkConfig:1", "GetStatistics");
		result.put("NewATMCRCErrors", response.getValueAsInteger("NewATMCRCErrors"));
		result.put("NewATMTransmittedBlocks", response.getValueAsInteger("NewATMTransmittedBlocks"));
		result.put("NewATMReceivedBlocks", response.getValueAsInteger("NewATMReceivedBlocks"));
		result.put("NewAAL5CRCErrors", response.getValueAsInteger("NewAAL5CRCErrors"));
	}
	
	private void ethernetInterfaceConfig(FritzConnection connection, JSONObject result) throws IOException, NoSuchFieldException {
		Response response = get(connection, "LANEthernetInterfaceConfig:1", "GetStatistics");
		result.put("NewBytesReceived", response.getValueAsInteger("NewBytesReceived"));
		result.put("NewBytesSent", response.getValueAsInteger("NewBytesSent"));
		result.put("NewPacketsReceived", response.getValueAsInteger("NewPacketsReceived"));
		result.put("NewPacketsSent", response.getValueAsInteger("NewPacketsSent"));
	}
	
	private void wanInterfaceConfig(FritzConnection connection, JSONObject result) throws IOException, NoSuchFieldException {
		Response response = get(connection, "WANCommonInterfaceConfig:1", "GetCommonLinkProperties");
		result.put("NewLayer1DownstreamMaxBitRate", response.getValueAsInteger("NewLayer1DownstreamMaxBitRate"));
		result.put("NewLayer1UpstreamMaxBitRate", response.getValueAsInteger("NewLayer1UpstreamMaxBitRate"));
		result.put("NewPhysicalLinkStatus", response.getValueAsString("NewPhysicalLinkStatus").equalsIgnoreCase("up") ? 1 : 0);
	}
	
	private Response get(FritzConnection connection, String serviceName, String actionName) throws UnsupportedOperationException, IOException {
		Service service = connection.getService(serviceName);
		Action action = service.getAction(actionName);
		return action.execute();
	}

}
