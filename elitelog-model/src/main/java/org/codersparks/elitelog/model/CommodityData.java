package org.codersparks.elitelog.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

@Document
public class CommodityData extends ResourceSupport { 
	
	@Id
	private String id;
	
	@Indexed
	private String station;
	
	@Indexed
	private String system;
	
	@Indexed
	private String name;
	
	private int supply;
	
	@Indexed
	private String supplyLevel;
	
	private int demand;
	
	@Indexed
	private String demandLevel;
	
	private int buy;
	
	private int sell;
	
	@Indexed(expireAfterSeconds=1296000)
	private Date created = new Date();
	
	
	
	
	public CommodityData(String station, String system, String name,
			int supply, String supplyLevel, int demand, String demandLevel,
			int buy, int sell, Date created) {
		super();
		this.station = station;
		this.system = system;
		this.name = name;
		this.supply = supply;
		this.supplyLevel = supplyLevel;
		this.demand = demand;
		this.demandLevel = demandLevel;
		this.buy = buy;
		this.sell = sell;
		this.created = created;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public int getSupply() {
		return supply;
	}
	public void setSupply(int supply) {
		this.supply = supply;
	}
	public String getSupplyLevel() {
		return supplyLevel;
	}
	public void setSupplyLevel(String supplyLevel) {
		this.supplyLevel = supplyLevel;
	}
	public int getDemand() {
		return demand;
	}
	public void setDemand(int demand) {
		this.demand = demand;
	}
	public String getDemandLevel() {
		return demandLevel;
	}
	public void setDemandLevel(String demandLevel) {
		this.demandLevel = demandLevel;
	}
	public int getBuy() {
		return buy;
	}
	public void setBuy(int buy) {
		this.buy = buy;
	}
	public int getSell() {
		return sell;
	}
	public void setSell(int sell) {
		this.sell = sell;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
