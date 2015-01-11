package org.codersparks.elite.resource.pricePerStation;

import java.util.Date;

import org.codersparks.elite.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StationDetail {
	
	private static Logger logger = LoggerFactory.getLogger(StationDetail.class);
	
	private String system;
	private String supplyLevel;
	private String demandLevel;
	private float buy;
	private float sell;
	private Date created;

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSupplyLevel() {
		return supplyLevel;
	}

	public void setSupplyLevel(String supplyLevel) {
		this.supplyLevel = supplyLevel;
	}

	public String getDemandLevel() {
		return demandLevel;
	}

	public void setDemandLevel(String demandLevel) {
		this.demandLevel = demandLevel;
	}

	public float getBuy() {
		return buy;
	}

	public void setBuy(float buy) {
		this.buy = buy;
	}

	public float getSell() {
		return sell;
	}

	public void setSell(float sell) {
		this.sell = sell;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public String toString() {
		try {
			return JSONUtils.convertToJson(this);
		} catch (Exception e) {
			logger.error("Cannot convert to JSON, using super toString function", e);
			return super.toString();
		}
	}

}
