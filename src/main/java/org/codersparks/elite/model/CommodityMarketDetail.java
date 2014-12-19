package org.codersparks.elite.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

public class CommodityMarketDetail {
	
	@Indexed
	private String name;
	
	private int supply;
	
	private String supplyLevel;
	
	private int demand;
	
	private String demandLevel;
	
	private int buy;
	
	private int sell;
	
	
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
		this.supplyLevel = supplyLevel.toUpperCase();
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
		this.demandLevel = demandLevel.toUpperCase();
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
