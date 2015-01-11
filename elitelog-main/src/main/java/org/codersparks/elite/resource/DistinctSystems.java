package org.codersparks.elite.resource;

import java.util.List;
import java.util.TreeSet;

import org.springframework.hateoas.ResourceSupport;

public class DistinctSystems extends ResourceSupport {

	private TreeSet<String> systems;

	public DistinctSystems(TreeSet<String> systems) {
		this.systems = systems;
	}
	
	public DistinctSystems(List<String> systems) {
		this.systems = new TreeSet<String>(systems);
	}

	public TreeSet<String> getSystems() {
		return systems;
	}

	public void setSystems(TreeSet<String> systems) {
		this.systems = systems;
	}
	
	
}
