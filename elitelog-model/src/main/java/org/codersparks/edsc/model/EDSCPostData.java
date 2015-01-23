package org.codersparks.edsc.model;

import org.codersparks.elitelog.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)	
public class EDSCPostData {
	
	private static Logger logger = LoggerFactory.getLogger(EDSCPostData.class);

	private int ver = 2;
	private boolean test = false;
	private int outputMode = 1;
	private EDSCFilter filter = null;
	public int getVer() {
		return ver;
	}
	public void setVer(int ver) {
		this.ver = ver;
	}
	public boolean isTest() {
		return test;
	}
	public void setTest(boolean test) {
		this.test = test;
	}
	public int getOutputMode() {
		return outputMode;
	}
	public void setOutputMode(int outputMode) {
		this.outputMode = outputMode;
	}
	public EDSCFilter getFilter() {
		return filter;
	}
	public void setFilter(EDSCFilter filter) {
		this.filter = filter;
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
