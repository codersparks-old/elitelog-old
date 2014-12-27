package org.codersparks.elite.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.codersparks.elite.resource.DistinctCommodities;
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
@ExposesResourceFor(DistinctCommodities.class)
public class CommoditiesController implements ResourceProcessor<RepositoryLinksResource> {
	
	@Autowired
	MongoTemplate mongo;

	@RequestMapping("/api/commodities/distinct")
	@ResponseBody
	public HttpEntity<DistinctCommodities> distinctCommodities() {
		
		List<String> distinctList = mongo.getCollection("commodityData").distinct("name");
		
		DistinctCommodities resource = new DistinctCommodities(distinctList);
		resource.add(linkTo(methodOn(CommoditiesController.class).distinctCommodities()).withSelfRel());
		return new ResponseEntity<DistinctCommodities>(resource, HttpStatus.OK);
	}

	@Override
	public RepositoryLinksResource process(RepositoryLinksResource resource) {
		resource.add(linkTo(methodOn(CommoditiesController.class).distinctCommodities()).withRel("distinctCommoditiesList"));
		return resource;
	}
	
}
