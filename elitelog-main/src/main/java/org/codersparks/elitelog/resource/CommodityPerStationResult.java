package org.codersparks.elitelog.resource;

import java.util.Date;
import java.util.List;

import org.codersparks.elitelog.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Object to store the results of the aggregation used to find Commodity prices per station.
 * @author Mark
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommodityPerStationResult {
	
	private static Logger logger = LoggerFactory.getLogger(CommodityPerStationResult.class);
	
	public String name;
	public List<StationMarketDetail> stationMarketDetails;
	

	/**
	 * A class that will hold the pricess for each of the stations
	 * @author Mark
	 *
	 */
	public class StationMarketDetail {
		private String system;
		private String station;
		private int buy;
		private String supplyLevel;
		private int sell;
		private String demandLevel;
		private Date created;
		public String getSystem() {
			return system;
		}
		public void setSystem(String system) {
			this.system = system;
		}
		public String getStation() {
			return station;
		}
		public void setStation(String station) {
			this.station = station;
		}
		public int getBuy() {
			return buy;
		}
		public void setBuy(int buy) {
			this.buy = buy;
		}
		public String getSupplyLevel() {
			return supplyLevel;
		}
		public void setSupplyLevel(String supplyLevel) {
			this.supplyLevel = supplyLevel;
		}
		public int getSell() {
			return sell;
		}
		public void setSell(int sell) {
			this.sell = sell;
		}
		public String getDemandLevel() {
			return demandLevel;
		}
		public void setDemandLevel(String demandLevel) {
			this.demandLevel = demandLevel;
		}
		public Date getCreated() {
			return created;
		}
		public void setCreated(Date created) {
			this.created = created;
		}
		
		
	}
	
	@Override
	public String toString() {
		
		try {
			return JSONUtils.convertToJson(this);
		} catch (Exception e) {
			logger.error("Exception caught trying to convert object to JSON", e);
			return this.toString();
		}
	}
	
}
