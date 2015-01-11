package org.codersparks.elitelog.aggregation;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class AggregationHelper {

	public static DBObject sortByCreatedAggregationItem() {

		DBObject sortObject = (DBObject) new BasicDBObject().append("$sort",
				(DBObject) new BasicDBObject("created", -1));

		return sortObject;
	}
	
	public static DBObject sortByNameAggregationItem() {

		DBObject sortObject = (DBObject) new BasicDBObject().append("$sort",
				(DBObject) new BasicDBObject("name", 1));

		return sortObject;
	}

	public static DBObject groupByNameAndStation() {

		DBObject idObject = (DBObject) new BasicDBObject().append("name",
				"$name").append("station", "$station");

		DBObject groupObject = (DBObject) new BasicDBObject()
				.append("_id", idObject)
				.append("name", (DBObject) new BasicDBObject("$first", "$name"))
				.append("system",
						(DBObject) new BasicDBObject("$first", "$system"))
				.append("station",
						(DBObject) new BasicDBObject("$first", "$station"))
				.append("buy", (DBObject) new BasicDBObject("$first", "$buy"))
				.append("supplyLevel",
						(DBObject) new BasicDBObject("$first", "$supplyLevel"))
				.append("sell", (DBObject) new BasicDBObject("$first", "$sell"))
				.append("demandLevel",
						(DBObject) new BasicDBObject("$first", "$demandLevel"))
				.append("created",
						(DBObject) new BasicDBObject("$first", "$created"));
		
		return new BasicDBObject("$group", groupObject);
	}
	
	public static DBObject groupByNameForStationPrices() {
		
		DBObject pushObject = (DBObject) new BasicDBObject()
			.append("system", "$system").append("station", "$station")
			.append("buy", "$buy").append("supplyLevel", "$supplyLevel")
			.append("sell", "$sell").append("demandLevel", "$demandLevel")
			.append("created", "$created");
		
		DBObject stationMarketDetailsObject = (DBObject) new BasicDBObject("$push", pushObject);
		
		DBObject idObject = (DBObject) new BasicDBObject(
				"_id", new BasicDBObject("name", "$name"));
		
		DBObject groupObject = (DBObject) new BasicDBObject()
			.append("_id",idObject)
			.append("name", new BasicDBObject("$first", "$name"))
			.append("stationMarketDetails",
				stationMarketDetailsObject);
		
		return new BasicDBObject("$group", groupObject);
	}
	
	

}
