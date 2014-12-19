package org.codersparks.elite.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Markets")
public class Market {

	@Id
	private String id;
	
	@Indexed
	private String system;
	
	@Indexed
	private String station;
	private List<CommodityMarketDetail> commodities = new ArrayList<CommodityMarketDetail>();
	
	@Indexed(expireAfterSeconds=5184000)
	private Date created = new Date();
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
	
	public List<CommodityMarketDetail> getCommodities() {
		return commodities;
	}
	public void setCommodities(List<CommodityMarketDetail> commodities) {
		this.commodities = commodities;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}
