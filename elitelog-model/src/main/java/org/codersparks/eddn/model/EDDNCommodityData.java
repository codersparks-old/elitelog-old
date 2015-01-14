package org.codersparks.eddn.model;

import org.codersparks.elitelog.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EDDNCommodityData {

	private static Logger logger = LoggerFactory.getLogger(EDDNCommodityData.class);
	
	private EDDNCommodityDataHeader header;
	private String $schemaRef;
	private EDDNCommodityDataMessage message;

	public EDDNCommodityDataHeader getHeader() {
		return header;
	}

	public void setHeader(EDDNCommodityDataHeader header) {
		this.header = header;
	}

	public String get$schemaRef() {
		return $schemaRef;
	}

	public void set$schemaRef(String $schemaRef) {
		this.$schemaRef = $schemaRef;
	}

	public EDDNCommodityDataMessage getMessage() {
		return message;
	}

	public void setMessage(EDDNCommodityDataMessage message) {
		this.message = message;
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
