package org.codersparks.elitelog.resource;

import java.util.List;
import java.util.TreeSet;

import org.springframework.hateoas.ResourceSupport;

public class DistinctStation extends ResourceSupport{

	private TreeSet<String> stations;

	public DistinctStation(TreeSet<String> stations) {
		this.stations = stations;
	}
	
	public DistinctStation(List<String> stations) {
		this.stations = new TreeSet<String>(stations);
	}

	public TreeSet<String> getStations() {
		return stations;
	}

	public void setStations(TreeSet<String> stations) {
		this.stations = stations;
	}
	
	
	
	
}
