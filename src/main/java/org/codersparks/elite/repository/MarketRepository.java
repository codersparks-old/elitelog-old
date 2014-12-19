package org.codersparks.elite.repository;

import java.util.Collection;

import org.codersparks.elite.model.Market;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface MarketRepository extends MongoRepository<Market, String>{
	
	@RestResource(rel="byStation", path="byStation")
	Collection<Market> findByStationOrderByCreatedDesc(@Param("station") String station);
	
	@RestResource(rel="bySystem", path="bySystem")
	Collection<Market> findBySystemOrderByCreatedDesc(@Param("system") String system);
	
	@RestResource(rel="byCommodity", path="byCommodity")
	@Query(value="{ 'commodities.name' : ?0 }")
	Collection<Market> findByCommodityName(@Param("commodity") String commodity);
	
}
