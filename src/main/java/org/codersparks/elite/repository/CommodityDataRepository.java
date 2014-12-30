package org.codersparks.elite.repository;

import java.util.Collection;

import org.codersparks.elite.model.CommodityData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "commodities", path = "commodities")
public interface CommodityDataRepository extends
		MongoRepository<CommodityData, String> {

	@RestResource(rel = "findByStation", path = "byStation")
	Page<CommodityData> findByStationIgnoreCase(
			@Param("station") String station, Pageable pageable);

	@RestResource(rel = "bySystem", path = "bySystem")
	Page<CommodityData> findBySystemIgnoreCase(
			@Param("system") String system, Pageable pageable);

	@RestResource(rel = "byName", path = "byName")
	Page<CommodityData> findByNameIgnoreCase(
			@Param("name") String name, Pageable pageable);

	@RestResource(rel = "byDemandLevel", path = "byDemandLevel")
	Page<CommodityData> findByDemandLevelIn(
			@Param("level") Collection<String> demandLevel, Pageable pageable);

	@RestResource(rel = "bySupplyLevel", path = "bySupplyLevel")
	Page<CommodityData> findBySupplyLevelIn(
			@Param("level") Collection<String> supplyLevel, Pageable pageable);

	@RestResource(rel = "byNameAndStation", path = "byNameAndStation")
	Page<CommodityData> findByNameIgnoreCaseAndStationIn(
			@Param("name") String name,
			@Param("station") Collection<String> station, Pageable pageable);
	
	@RestResource(rel = "byNameAndSystem", path = "byNameAndSystem")
	Page<CommodityData> findByNameIgnoreCaseAndSystemIn(
			@Param("name") String name,
			@Param("system") Collection<String> system, Pageable pageable);

}
