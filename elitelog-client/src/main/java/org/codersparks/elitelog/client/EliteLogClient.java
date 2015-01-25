package org.codersparks.elitelog.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.codersparks.elitelog.model.CommodityData;
import org.codersparks.elitelog.model.CurrentSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class EliteLogClient {
	
	private static Logger logger = LoggerFactory.getLogger(EliteLogClient.class);
	
	private final String elitelogBaseURL;
	private final String currentSystemURLString;
	private final String commoditiesURLString;
	
	private RestTemplate restTemplate;
	
	
	public EliteLogClient(String elitelogURL, RestTemplate restTemplate) {
		
		this.restTemplate = restTemplate;
		
		this.elitelogBaseURL = "http://" +elitelogURL;
		
		if(elitelogBaseURL.endsWith("/")) {
			currentSystemURLString = elitelogBaseURL + "api/currentSystems/";
			commoditiesURLString = elitelogBaseURL + "api/commodities/";
		} else {
			currentSystemURLString = elitelogBaseURL + "/api/currentSystems/";
			commoditiesURLString = elitelogBaseURL + "/api/commodities/";
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("CurrentSystemURL: " + currentSystemURLString);
			logger.debug("commoditiesURLL: " + commoditiesURLString);
		}
		
	}
	
	
	public void putCurrentSystem(CurrentSystem system) throws Exception {
		
		URI findByCommanderURL;
		URI currentSystemURL;
		
		try {
			// http://localhost:8080/api/currentSystems/byCommander?commanderName=sparkster
			findByCommanderURL = new URI(currentSystemURLString + "byCommander?commanderName=" + system.getCommanderName());
		} catch(URISyntaxException e) {
			logger.error("Exception caught constructing url:", e);
			throw new Exception(e);
		}
		
		logger.debug("Find by commander url: " + findByCommanderURL.toString());
		
		if(restTemplate == null) {
			logger.error("restTemplate is null");
		}
		ResponseEntity<CurrentSystem> commanderResponse = restTemplate.getForEntity(findByCommanderURL, CurrentSystem.class);
		
		if(commanderResponse.getBody() == null) {
			logger.debug("No commander found for: " + system.getCommanderName());
			try {
				currentSystemURL = new URI(currentSystemURLString);
				restTemplate.postForLocation(currentSystemURL, system);
			} catch(URISyntaxException e) {
				logger.error("Exception caught constructing url:", e);
				throw new Exception(e);
			}
			
		} else {
			// If we get a response then there already is a entry for that commander
			// So we need to get the id and put the response
			CurrentSystem prevSys = (CurrentSystem)commanderResponse.getBody();
			logger.debug("Commander found: " + prevSys);
			
			try {
				currentSystemURL = new URI(currentSystemURLString + "/" + prevSys.get_id());
				restTemplate.put(currentSystemURL, system);
			} catch(URISyntaxException e) {
				logger.error("Exception caught constructing url:", e);
				throw new Exception(e);
			}
		}
	}
	
	public void postCommodityData(CommodityData data) throws Exception {
		URI url;
		
		try {
			// http://localhost:8080/api/currentSystems/byCommander?commanderName=sparkster
			url = new URI(commoditiesURLString);
		} catch(URISyntaxException e) {
			logger.error("Exception caught constructing url:", e);
			throw new Exception(e);
		}
		
		restTemplate.postForLocation(url, data);
	}
	
	public List<String> getSystemsOfInterest() throws Exception {
		URI url;
		
		try {
			// http://localhost:8080/api/currentSystems/byCommander?commanderName=sparkster
			url = new URI(currentSystemURLString + "/systemsOfInterest");
		} catch(URISyntaxException e) {
			logger.error("Exception caught constructing url:", e);
			throw new Exception(e);
		}
		
		return Arrays.asList((restTemplate.getForEntity(url, String[].class)).getBody());
	}

}
