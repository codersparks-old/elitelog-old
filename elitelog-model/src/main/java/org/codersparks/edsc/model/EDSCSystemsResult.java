package org.codersparks.edsc.model;

import java.util.Date;
import java.util.List;

import org.codersparks.elitelog.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EDSCSystemsResult {
	
	private static Logger logger = LoggerFactory.getLogger(EDSCSystemsResult.class);

	@JsonProperty("ver")
	private float version;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date date;
	
	private List<EDSCSystem> systems;

	public float getVersion() {
		return version;
	}

	public void setVersion(float version) {
		this.version = version;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<EDSCSystem> getSystems() {
		return systems;
	}

	public void setSystems(List<EDSCSystem> systems) {
		this.systems = systems;
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
