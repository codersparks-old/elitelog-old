package org.codersparks.elite.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.codersparks.elite.resource.DistinctStation;
import org.codersparks.elite.resource.DistinctSystems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ExposesResourceFor(DistinctStation.class)
public class StationsController implements ResourceProcessor<RepositoryLinksResource> {
	@Autowired
	MongoTemplate mongo;
	
	@RequestMapping("/api/stations/distinct")
	@ResponseBody
	public HttpEntity<DistinctStation> distinctStations() {
		
		List<String> distinctList = mongo.getCollection("commodityData").distinct("station");
		
		DistinctStation resource = new DistinctStation(distinctList);
		resource.add(linkTo(methodOn(StationsController.class).distinctStations()).withSelfRel());
		return new ResponseEntity<DistinctStation>(resource, HttpStatus.OK);
	}
	
	
	
	
	@Override
	public RepositoryLinksResource process(RepositoryLinksResource resource) {
		resource.add(linkTo(methodOn(StationsController.class).distinctStations()).withRel("stationsList"));
		return resource;
	}
}
