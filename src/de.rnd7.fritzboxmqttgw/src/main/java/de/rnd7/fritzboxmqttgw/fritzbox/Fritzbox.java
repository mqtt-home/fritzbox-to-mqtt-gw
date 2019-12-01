package de.rnd7.fritzboxmqttgw.fritzbox;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
			
//			debugPrintServices(connection);
			
			JSONObject result = new JSONObject();
			wanDSLIfConfig(connection, result);
			linkConfig(connection, result);
			ethernetInterfaceConfig(connection, result);
			wanInterfaceConfig(connection, result);
			
			return result;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	private void wanDSLIfConfig(FritzConnection connection, JSONObject result) throws IOException, NoSuchFieldException {
		Response response = get(connection, "WANDSLInterfaceConfig:1", "GetInfo");

		result.put("NewDownstreamMaxRate", response.getValueAsLong("NewDownstreamMaxRate"));
		result.put("NewUpstreamMaxRate", response.getValueAsLong("NewUpstreamMaxRate"));
		result.put("NewDownstreamCurrRate", response.getValueAsLong("NewDownstreamCurrRate"));
		result.put("NewUpstreamCurrRate", response.getValueAsLong("NewUpstreamCurrRate"));
	}
	
	
	private void linkConfig(FritzConnection connection, JSONObject result) throws IOException, NoSuchFieldException {
		Response response = get(connection, "WANDSLLinkConfig:1", "GetStatistics");
		result.put("NewATMCRCErrors", response.getValueAsLong("NewATMCRCErrors"));
		result.put("NewATMTransmittedBlocks", response.getValueAsLong("NewATMTransmittedBlocks"));
		result.put("NewATMReceivedBlocks", response.getValueAsLong("NewATMReceivedBlocks"));
		result.put("NewAAL5CRCErrors", response.getValueAsLong("NewAAL5CRCErrors"));
		
		response = get(connection, "WANDSLLinkConfig:1", "GetInfo");
		response = get(connection, "WANDSLLinkConfig:1", "GetDSLLinkInfo");
	}
	
	private void ethernetInterfaceConfig(FritzConnection connection, JSONObject result) throws IOException, NoSuchFieldException {
		Response response = get(connection, "LANEthernetInterfaceConfig:1", "GetStatistics");
		result.put("NewBytesReceived", response.getValueAsLong("NewBytesReceived"));
		result.put("NewBytesSent", response.getValueAsLong("NewBytesSent"));
		result.put("NewPacketsReceived", response.getValueAsLong("NewPacketsReceived"));
		result.put("NewPacketsSent", response.getValueAsLong("NewPacketsSent"));
	}
	
	private void wanInterfaceConfig(FritzConnection connection, JSONObject result) throws IOException, NoSuchFieldException {
		Response response = get(connection, "WANCommonInterfaceConfig:1", "GetCommonLinkProperties");
		result.put("NewLayer1DownstreamMaxBitRate", response.getValueAsLong("NewLayer1DownstreamMaxBitRate"));
		result.put("NewLayer1UpstreamMaxBitRate", response.getValueAsLong("NewLayer1UpstreamMaxBitRate"));
		result.put("NewPhysicalLinkStatus", response.getValueAsString("NewPhysicalLinkStatus").equalsIgnoreCase("up") ? 1 : 0);
		
		response = get(connection, "WANCommonInterfaceConfig:1", "GetTotalBytesSent");
		result.put("NewTotalBytesSent", response.getValueAsLong("NewTotalBytesSent"));
		
		response = get(connection, "WANCommonInterfaceConfig:1", "GetTotalBytesReceived");
		result.put("NewTotalBytesReceived", response.getValueAsLong("NewTotalBytesReceived"));
		
		response = get(connection, "WANPPPConnection:1", "GetInfo");
		result.put("NewConnectionStatus", response.getValueAsString("NewConnectionStatus").equalsIgnoreCase("connected") ? 1 : 0);
		result.put("NewUptime", response.getValueAsLong("NewUptime"));
		result.put("NewExternalIPAddress", response.getValueAsString("NewExternalIPAddress"));
		
	}
	
	private Response get(FritzConnection connection, String serviceName, String actionName) throws IOException {
		Service service = connection.getService(serviceName);
//		debugPrintActions(serviceName, service);
		
		Action action = service.getAction(actionName);
		return action.execute();
	}

//	private void debugPrintServices(FritzConnection connection) {
//		connection.getServices().entrySet()
//		.stream()
//		.sorted(Comparator.comparing(Entry::getKey))
//		.forEach(x -> System.out.println(x.getKey() + " " + x.getValue()));
//	}
//
//	private void debugPrintActions(String serviceName, Service service) {
//		System.out.println(serviceName);
//		service.getActions().entrySet()
//		.stream()
//		.sorted(Comparator.comparing(Entry::getKey))
//		.forEach(x -> System.out.println("   " + x.getKey() + " " + x.getValue()));
//	}

}
