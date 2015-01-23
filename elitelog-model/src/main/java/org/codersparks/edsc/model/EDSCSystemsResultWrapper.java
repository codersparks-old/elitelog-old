package org.codersparks.edsc.model;

import org.codersparks.elitelog.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Again due to some reason the EDSC does not like anonymous objects therefore we have to wrap it
 * @author codersparks
 *
 */
public class EDSCSystemsResultWrapper {
	
	private static Logger logger = LoggerFactory.getLogger(EDSCSystemsResultWrapper.class);
	
	@JsonProperty("d")
	private EDSCSystemsResult result;

	public EDSCSystemsResult getResult() {
		return result;
	}

	public void setResult(EDSCSystemsResult result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		try {
			return JSONUtils.convertToJson(this);
		} catch (Exception e) {
			logger.error("Cannot convert object to JSON string dropping back to default behaviour", e);
			return super.toString();
		}
	}
	

}
