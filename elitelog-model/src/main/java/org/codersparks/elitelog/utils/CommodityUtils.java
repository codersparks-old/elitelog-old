package org.codersparks.elitelog.utils;

import org.codersparks.eddn.model.EDDNCommodityDataMessage;
import org.codersparks.elitelog.model.CommodityData;

public class CommodityUtils {
	
	public static CommodityData convertEDDNCommodity(EDDNCommodityDataMessage message) {
		
		return new CommodityData(
				message.getStationName(), 
				message.getSystemName(), 
				message.getItemName(),
				message.getStationStock(),
				message.getSupplyLevel(),
				message.getDemand(),
				message.getDemandLevel(),
				message.getBuyPrice(), 
				message.getSellPrice(),
				message.getTimestamp());
	}

}
