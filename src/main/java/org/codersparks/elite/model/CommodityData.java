package org.codersparks.elite.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class CommodityData { 
	
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
	
	@Indexed(expireAfterSeconds=5184000)
	private Date created = new Date();
	
	
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
