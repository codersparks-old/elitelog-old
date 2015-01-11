package org.codersparks.elitelog.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String convertToJson(Object o) throws Exception {
		
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new Exception("Cannot convert object of type: " + o.getClass().getCanonicalName() + " to json String", e);
		}
	}
}
