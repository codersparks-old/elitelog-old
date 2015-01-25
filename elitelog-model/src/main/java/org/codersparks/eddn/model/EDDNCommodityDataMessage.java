package org.codersparks.eddn.model;

import java.util.Date;

import org.codersparks.elitelog.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EDDNCommodityDataMessage {

	private static Logger logger = LoggerFactory.getLogger(EDDNCommodityDataMessage.class);
	
	
	private int buyPrice;
	private Date timestamp;
	private int stationStock;
	private String systemName;
	private String stationName;
	private int demand;
	private int sellPrice;
	private String itemName;
	private String demandLevel;
	private String supplyLevel;

	public int getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getStationStock() {
		return stationStock;
	}

	public void setStationStock(int stationStock) {
		this.stationStock = stationStock;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public int getDemand() {
		return demand;
	}

	public void setDemand(int demand) {
		this.demand = demand;
	}

	public int getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDemandLevel() {
		return demandLevel;
	}

	public void setDemandLevel(String demandLevel) {
		this.demandLevel = demandLevel;
	}

	public String getSupplyLevel() {
		return supplyLevel;
	}

	public void setSupplyLevel(String supplyLevel) {
		this.supplyLevel = supplyLevel;
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
