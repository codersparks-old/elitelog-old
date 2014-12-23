package org.codersparks.elite.repository;

import java.util.Collection;

import org.codersparks.elite.model.CommodityData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel="commodities", path="commodities")
public interface CommodityDataRepository extends MongoRepository<CommodityData, String>{

		@RestResource(rel="findByStation", path="findByStation")
		Collection<CommodityData> findByStationOrderByCreatedDesc(@Param("station") String station);
		
		@RestResource(rel="findBySystem", path="findBySystem")
		Collection<CommodityData> findBySystemOrderByCreatedDesc(@Param("system") String system);
		
		@RestResource(rel="findByCommodity", path="findByCommodity")
		Collection<CommodityData> findByNameOrderByCreatedDesc(@Param("name") String name);
		
		
	
}
