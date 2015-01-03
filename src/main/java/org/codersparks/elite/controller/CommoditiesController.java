package org.codersparks.elite.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.codersparks.elite.resource.DistinctCommodities;
import org.codersparks.elite.resource.pricePerStation.CommodityDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
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

import com.mongodb.DB;

@Controller
@ExposesResourceFor(DistinctCommodities.class)
public class CommoditiesController implements
		ResourceProcessor<RepositoryLinksResource> {

	private static Logger logger = LoggerFactory
			.getLogger(CommoditiesController.class);

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	DB mongoDB;

	@RequestMapping("/api/commodities/distinct")
	@ResponseBody
	public HttpEntity<DistinctCommodities> distinctCommodities() {

		List<String> distinctList = mongoTemplate
				.getCollection("commodityData").distinct("name");

		DistinctCommodities resource = new DistinctCommodities(distinctList);
		resource.add(linkTo(
				methodOn(CommoditiesController.class).distinctCommodities())
				.withSelfRel());
		return new ResponseEntity<DistinctCommodities>(resource, HttpStatus.OK);
	}

/*	@RequestMapping("/api/commodities/stationPricePerCommodity")
	@ResponseBody
	public HttpEntity<Resources<CommodityPerStationResult>> stationPricesPerCommodity() {

		// DB db = mongoClient.getDB("elitelog");
		//
		// db.authenticate(username, password)

		DBCollection collection = mongoDB.getCollection("commodityData");

		List<DBObject> aggregationCommands = new ArrayList<DBObject>();

		aggregationCommands.add(AggregationHelper
				.sortByCreatedAggregationItem());
		aggregationCommands.add(AggregationHelper.groupByNameAndStation());
		aggregationCommands
				.add(AggregationHelper.groupByNameForStationPrices());
		aggregationCommands.add(AggregationHelper.sortByNameAggregationItem());

		AggregationOutput aggregationOutput = collection
				.aggregate(aggregationCommands);

		List<CommodityPerStationResult> results = new ArrayList<CommodityPerStationResult>();

		for (DBObject o : aggregationOutput.results()) {

			CommodityPerStationResult c = mongoTemplate.getConverter().read(
					CommodityPerStationResult.class, o);
			results.add(c);
			System.out.println(c.toString());
		}

		Resources<CommodityPerStationResult> resource = new Resources<CommodityPerStationResult>(
				results, linkTo(
						methodOn(CommoditiesController.class)
								.stationPricesPerCommodity()).withRel(
						"commodityPricePerStation"));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Resources<CommodityPerStationResult>> entity = new HttpEntity<Resources<CommodityPerStationResult>>(
				resource, headers);

		return entity;

	}*/

	
	@RequestMapping("/api/commodities/pagedPricePerStation")
	@ResponseBody
	public PagedResources<Resource<CommodityDetail>> commodityPricePerStation(@Param("page") Long page, @Param("size") Long size) throws Exception {
		
		logger.info("Page: " + page + " Size: " + size);
		
		if(page == null) {
			logger.debug("Page detected as null, setting to 1");
			page = 0l;
		}
		
		if(size == null) {
			logger.debug("Size detected as null, setting to 20");
			size = 20l;
		}
		String mapFunction = IOUtils.toString(this.getClass().getClassLoader()
				.getResourceAsStream("CommodityPerStationMapFunction.js"));
		logger.debug("Map Function: " + mapFunction);

		String reduceFunction = IOUtils.toString(this.getClass()
				.getClassLoader()
				.getResourceAsStream("CommodityPerStationReduceFunction.js"));
		logger.debug("Reduce Function: " + reduceFunction);

		String finalizeFunction = IOUtils.toString(this.getClass()
				.getClassLoader()
				.getResourceAsStream("CommodityPerStationFinalizeFunction.js"));
		logger.debug("Finalize Function: " + finalizeFunction);

		MapReduceOptions mapReduceOptions = MapReduceOptions.options()
				.outputTypeInline().finalizeFunction(finalizeFunction);

		MapReduceResults<CommodityDetail> mapReduceResults = mongoTemplate
				.mapReduce("commodityData", mapFunction, reduceFunction,
						mapReduceOptions, CommodityDetail.class);

		List<CommodityDetail> resultsList = new ArrayList<CommodityDetail>();
		for (CommodityDetail result : mapReduceResults) {
				logger.debug("Result object.toString(): " + result.toString());
				resultsList.add(result);
				
		}
		
		PageMetadata meta = new PageMetadata(size, page, resultsList.size() );
		
		PagedResources<Resource<CommodityDetail>> resources = PagedResources.wrap(resultsList, meta);
		
		
		
		return resources;
	}
	
	
	@RequestMapping("/api/commodities/pricePerStation")
	@ResponseBody
	public HttpEntity<Resources<CommodityDetail>> commodityPricePerStation() throws Exception {

		String mapFunction = IOUtils.toString(this.getClass().getClassLoader()
				.getResourceAsStream("CommodityPerStationMapFunction.js"));
		logger.debug("Map Function: " + mapFunction);

		String reduceFunction = IOUtils.toString(this.getClass()
				.getClassLoader()
				.getResourceAsStream("CommodityPerStationReduceFunction.js"));
		logger.debug("Reduce Function: " + reduceFunction);

		String finalizeFunction = IOUtils.toString(this.getClass()
				.getClassLoader()
				.getResourceAsStream("CommodityPerStationFinalizeFunction.js"));
		logger.debug("Finalize Function: " + finalizeFunction);

		MapReduceOptions mapReduceOptions = MapReduceOptions.options()
				.outputTypeInline().finalizeFunction(finalizeFunction);

		MapReduceResults<CommodityDetail> mapReduceResults = mongoTemplate
				.mapReduce("commodityData", mapFunction, reduceFunction,
						mapReduceOptions, CommodityDetail.class);

		List<CommodityDetail> resultsList = new ArrayList<CommodityDetail>();
		for (CommodityDetail result : mapReduceResults) {
				logger.debug("Result object.toString(): " + result.toString());
				resultsList.add(result);
				
		}
		
		Resources<CommodityDetail> resource = new Resources<CommodityDetail>(resultsList, 
				linkTo(methodOn(CommoditiesController.class).commodityPricePerStation()).withSelfRel());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Resources<CommodityDetail>> entity = new HttpEntity<Resources<CommodityDetail>>(resource, headers);
		return entity;
	}

	@Override
	public RepositoryLinksResource process(RepositoryLinksResource resource) {
		resource.add(linkTo(
				methodOn(CommoditiesController.class).distinctCommodities())
				.withRel("distinctCommoditiesList"));
		try {
			resource.add(linkTo(
					methodOn(CommoditiesController.class).commodityPricePerStation()).withRel(
					"pricePerCommodity"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resource;
	}

}
