package org.codersparks.elitelog.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.codersparks.elitelog.resource.DistinctSystems;
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
@ExposesResourceFor(DistinctSystems.class)
public class SystemsController implements ResourceProcessor<RepositoryLinksResource>{

	@Autowired
	MongoTemplate mongo;
	
	@RequestMapping("/api/systems/distinct")
	@ResponseBody
	public HttpEntity<DistinctSystems> distinctSystems() {
		
		@SuppressWarnings("unchecked")
		List<String> distinctList = mongo.getCollection("commodityData").distinct("system");
		
		DistinctSystems resource = new DistinctSystems(distinctList);
		resource.add(linkTo(methodOn(SystemsController.class).distinctSystems()).withSelfRel());
		return new ResponseEntity<DistinctSystems>(resource, HttpStatus.OK);
	}
	
	@Override
	public RepositoryLinksResource process(RepositoryLinksResource resource) {
		resource.add(linkTo(methodOn(SystemsController.class).distinctSystems()).withRel("systemsList"));
		return resource;
	}
	
}
