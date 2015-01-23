package org.codersparks.elitelog.model;

import java.util.List;

import org.codersparks.elitelog.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

@Document
public class CurrentSystem extends ResourceSupport {
	
	private static Logger logger = LoggerFactory.getLogger(CurrentSystem.class);

	@Id
	private String _id;
	
	@Indexed(unique=true)
	private String commanderName;
	
	private String systemName;
	
	private float x;
	
	private float y;
	
	private float z;
	
	@Indexed
	private List<String> closestSystems;

	public String getCommanderName() {
		return commanderName;
	}

	public void setCommanderName(String commanderName) {
		this.commanderName = commanderName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public List<String> getClosestSystems() {
		return closestSystems;
	}

	public void setClosestSystems(List<String> closestSystems) {
		this.closestSystems = closestSystems;
	}
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String toString() {
		try {
			return JSONUtils.convertToJson(this);
		} catch (Exception e) {
			logger.error("Cannot convert to JSON, dropping back to default behaviour", e);
			return super.toString();
		}
	}
	
	
}
