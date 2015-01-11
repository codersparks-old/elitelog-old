package org.codersparks.elite.resource.pricePerStation;

import java.util.Map;

import org.codersparks.elite.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

public class CommodityDetail extends ResourceSupport {
	private static Logger logger = LoggerFactory.getLogger(CommodityDetail.class);
	
	private String _id;
	private Map<String, StationDetail> value;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public Map<String, StationDetail> getValue() {
		return value;
	}
	public void setValue(Map<String, StationDetail> value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		try {
			return JSONUtils.convertToJson(this);
		} catch (Exception e) {
			logger.error("Cannot convert to JSON, using super toString function", e);
			return super.toString();
		}
	}

}
