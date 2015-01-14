package org.codersparks.java_eddn_client_test;

import java.io.IOException;

import org.codersparks.eddn.model.EDDNCommodityData;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App2 {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		
		String s = "{'header': {'softwareVersion': '0.3.8', 'gatewayTimestamp': '2015-01-11T11:04:11.942525', 'softwareName': 'EliteOCR', 'uploaderID': 'EO066268bc'}, '$schemaRef': 'http://schemas.elite-markets.net/eddn/commodity/1', 'message': {'buyPrice': 0, 'timestamp': '2015-01-11T18:54:56+00:00', 'stationStock': 0, 'systemName': 'Poqomana', 'stationName': 'Cori Terminal', 'demand': 1892, 'sellPrice': 1640, 'itemName': 'Coffee'}}";
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		EDDNCommodityData data = mapper.readValue(s.getBytes(), EDDNCommodityData.class);
		
		System.out.println(data);

	}

}
