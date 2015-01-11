package org.codersparks.elitelog.resource;

import java.util.List;
import java.util.TreeSet;

import org.springframework.hateoas.ResourceSupport;

public class DistinctCommodities extends ResourceSupport {

	private TreeSet<String> commodities;

	public DistinctCommodities(TreeSet<String> commodities) {
		this.commodities = commodities;
	}
	
	public DistinctCommodities(List<String> commodities) {
		this.commodities = new TreeSet<String>(commodities);
	}

	public TreeSet<String> getCommodities() {
		return commodities;
	}

	public void setCommodities(TreeSet<String> commodities) {
		this.commodities = commodities;
	}
	
	
}
