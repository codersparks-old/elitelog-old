package org.codersparks.edscclient;

import java.util.ArrayList;
import java.util.List;

import org.codersparks.edsc.model.EDSCFilter;
import org.codersparks.edsc.model.EDSCPostData;
import org.codersparks.edsc.model.EDSCPostDataWrapper;
import org.codersparks.edsc.model.EDSCSystem;
import org.codersparks.edsc.model.EDSCSystemsResultWrapper;
import org.codersparks.edsc.model.EDSCFilter.EDSCFilterBuilder;
import org.codersparks.edsc.model.EDSCFilter.FilterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class EDSCClient {

	private static Logger logger = LoggerFactory.getLogger(EDSCClient.class);

	private RestTemplate restTemplate = new RestTemplate();

	private final static String getSystemsUrl = "http://edstarcoordinator.com/api.asmx/GetSystems";

	public EDSCSystemsResultWrapper getSystems(boolean test, int outputMode,
			EDSCFilter filter) {

		EDSCPostData query = new EDSCPostData();
		query.setTest(test);
		query.setOutputMode(outputMode);
		query.setFilter(filter);

		logger.debug("Query object: " + query);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		EDSCSystemsResultWrapper response = restTemplate.postForObject(
				getSystemsUrl, new EDSCPostDataWrapper(query),
				EDSCSystemsResultWrapper.class);

		return response;

	}

	public List<String> getSystemsWithinSphere(float radius, float originX,
			float originY, float originZ) throws FilterException {

		EDSCFilterBuilder filterBuilder = new EDSCFilter.EDSCFilterBuilder();

		filterBuilder.cr(5).date("1970-1-1 00:00:00")
				.coordsphere(radius, originX, originY, originZ);

		EDSCPostData query = new EDSCPostData();
		query.setTest(false);
		query.setOutputMode(1);
		query.setFilter(filterBuilder.build());

		logger.debug("GetSystemsWithinSphere query: " + query);

		EDSCSystemsResultWrapper response = restTemplate.postForObject(
				getSystemsUrl, new EDSCPostDataWrapper(query),
				EDSCSystemsResultWrapper.class);

		List<String> systems = new ArrayList<String>();

		for (EDSCSystem system : response.getResult().getSystems()) {
			systems.add(system.getName());
		}

		return systems;
	}


	
	public EDSCSystem getSystemsByName(String name) throws FilterException {

		EDSCFilterBuilder filterBuilder = new EDSCFilter.EDSCFilterBuilder();

		filterBuilder.cr(5).date("1970-1-1 00:00:00").systemname(name);

		EDSCPostData query = new EDSCPostData();
		query.setTest(false);
		query.setOutputMode(2);
		query.setFilter(filterBuilder.build());

		logger.debug("GetSystemsWithinSphere query: " + query);

		EDSCSystemsResultWrapper response = restTemplate.postForObject(
				getSystemsUrl, new EDSCPostDataWrapper(query),
				EDSCSystemsResultWrapper.class);

		
		for (EDSCSystem system : response.getResult().getSystems()) {
			if(system.getName().toLowerCase().equals(name.toLowerCase())) {
				return system;
			}
		}

		return null;
	}

}
