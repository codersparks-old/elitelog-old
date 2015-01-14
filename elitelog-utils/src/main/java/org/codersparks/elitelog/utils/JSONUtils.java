package org.codersparks.elitelog.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

	private static ObjectMapper mapper = new ObjectMapper();

	public static String convertToJson(Object o) throws Exception {

		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new Exception("Cannot convert object of type: "
					+ o.getClass().getCanonicalName() + " to json String", e);
		}
	}

	public static <T> T fromJson(byte[] jsonSource, Class<T> type)
			throws Exception {

		return fromJson(jsonSource, type, false);
	}

	public static <T> T fromJson(byte[] jsonSource, Class<T> type,
			boolean allowSingleQuotes) throws Exception {
		try {
			mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,
					allowSingleQuotes);
			return mapper.readValue(jsonSource, type);
		} catch (Exception e) {
			throw new Exception("Cannot convert supplied json to type: "
					+ type.getCanonicalName(), e);
		}
	}
}
