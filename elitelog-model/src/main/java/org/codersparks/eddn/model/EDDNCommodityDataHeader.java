package org.codersparks.eddn.model;

import java.util.Date;

import org.codersparks.elitelog.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EDDNCommodityDataHeader {

	private static Logger logger = LoggerFactory
			.getLogger(EDDNCommodityDataHeader.class);

	private String softwareVersion;
	private Date gatewayTimestamp;
	private String softwareName;
	private String uploaderID;

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public Date getGatewayTimestamp() {
		return gatewayTimestamp;
	}

	public void setGatewayTimestamp(Date gatewayTimestamp) {
		this.gatewayTimestamp = gatewayTimestamp;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getUploaderID() {
		return uploaderID;
	}

	public void setUploaderID(String uploaderID) {
		this.uploaderID = uploaderID;
	}

	@Override
	public String toString() {
		try {
			return JSONUtils.convertToJson(this);
		} catch (Exception e) {
			logger.error(
					"Cannot convert object to JSON string dropping back to default behaviour",
					e);
			return super.toString();
		}
	}

}
