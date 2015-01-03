package org.codersparks.elite.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.codersparks.elite.aggregation.AggregationHelper;
import org.codersparks.elite.resource.CommodityPerStationResult;
import org.codersparks.elite.resource.DistinctCommodities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.AggregationOutput;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Controller
@ExposesResourceFor(DistinctCommodities.class)
public class CommoditiesController implements
		ResourceProcessor<RepositoryLinksResource> {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	DB mongoDB;

	@RequestMapping("/api/commodities/distinct")
	@ResponseBody
	public HttpEntity<DistinctCommodities> distinctCommodities() {

		List<String> distinctList = mongoTemplate.getCollection("commodityData")
				.distinct("name");

		DistinctCommodities resource = new DistinctCommodities(distinctList);
		resource.add(linkTo(
				methodOn(CommoditiesController.class).distinctCommodities())
				.withSelfRel());
		return new ResponseEntity<DistinctCommodities>(resource, HttpStatus.OK);
	}

	@RequestMapping("/api/commodities/stationPricePerCommodity")
	@ResponseBody
	public HttpEntity<Resources<CommodityPerStationResult>> stationPricesPerCommodity() {

//		DB db = mongoClient.getDB("elitelog");
//		
//		db.authenticate(username, password)
		
		DBCollection collection = mongoDB.getCollection("commodityData");
		
		List<DBObject> aggregationCommands = new ArrayList<DBObject>();
		
		aggregationCommands.add(AggregationHelper.sortByCreatedAggregationItem());
		aggregationCommands.add(AggregationHelper.groupByNameAndStation());
		aggregationCommands.add(AggregationHelper.groupByNameForStationPrices());
		aggregationCommands.add(AggregationHelper.sortByNameAggregationItem());
		
		AggregationOutput aggregationOutput = collection.aggregate(aggregationCommands);
		
		List<CommodityPerStationResult> results = new ArrayList<CommodityPerStationResult>();
		
		for(DBObject o : aggregationOutput.results()) {
			
			CommodityPerStationResult c = mongoTemplate.getConverter().read(CommodityPerStationResult.class, o);
			results.add(c);
			System.out.println(c.toString());
		}
		
		Resources<CommodityPerStationResult> resource = new Resources<CommodityPerStationResult>(results, 
				linkTo(methodOn(CommoditiesController.class).stationPricesPerCommodity()).withRel("commodityPricePerStation"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Resources<CommodityPerStationResult>> entity = new HttpEntity<Resources<CommodityPerStationResult>>(
				resource, headers);

		return entity;

	}
	
	public HttpEntity<String> commodityPricePerStation() {
		
		
		
	}

	@Override
	public RepositoryLinksResource process(RepositoryLinksResource resource) {
		resource.add(linkTo(
				methodOn(CommoditiesController.class).distinctCommodities())
				.withRel("distinctCommoditiesList"));
		resource.add(linkTo(
				methodOn(CommoditiesController.class)
						.stationPricesPerCommodity()).withRel(
				"stationPricePerCommodity"));
		return resource;
	}

}
