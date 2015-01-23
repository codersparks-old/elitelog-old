package org.codersparks.edsc.model;

import java.util.Date;

import org.codersparks.elitelog.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EDSCSystem {
	
	private static Logger logger = LoggerFactory.getLogger(EDSCSystem.class);
	
	private long id;
	private String name;
	private float[] coord;
	private int cr;
	
	@JsonProperty("commandercreate")
	private String commanderCreate;
	
	@JsonProperty("createdate")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="UTC")
	private Date createDate;
	
	@JsonProperty("commanderupdate")
	private String commanderUpdate;
	
	@JsonProperty("updatedate")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="UTC")
	private Date updateDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float[] getCoord() {
		return coord;
	}

	public void setCoord(float[] coord) {
		this.coord = coord;
	}

	public int getCr() {
		return cr;
	}

	public void setCr(int cr) {
		this.cr = cr;
	}

	public String getCommanderCreate() {
		return commanderCreate;
	}

	public void setCommanderCreate(String commanderCreate) {
		this.commanderCreate = commanderCreate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCommanderUpdate() {
		return commanderUpdate;
	}

	public void setCommanderUpdate(String commanderUpdate) {
		this.commanderUpdate = commanderUpdate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Override
	public String toString() {
		try {
			return JSONUtils.convertToJson(this);
		} catch (Exception e) {
			logger.error("Cannot convert object to JSON string dropping back to default behaviour", e);
			return super.toString();
		}
	}

	
	
}
