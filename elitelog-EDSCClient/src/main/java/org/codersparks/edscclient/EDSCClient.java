package org.codersparks.edscclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class EDSCClient {
	
	private static Logger logger = LoggerFactory.getLogger(EDSCClient.class);
	
	private RestTemplate restTemplate = new RestTemplate();
	
	private final static String getSystemsUrl = "http://edstarcoordinator.com/api.asmx/GetSystems";
	
	public String getSystems(boolean test, int outputMode, EDSCFilter filter) {
		
		EDSCPostData query = new EDSCPostData();
		query.setTest(test);
		query.setOutputMode(outputMode);
		query.setFilter(filter);
		
		logger.debug("Query object: " + query);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<String> response = restTemplate.postForEntity(getSystemsUrl, new EDSCPostDataWrapper(query), String.class);
		
		return response.getBody();
		
	}

}
