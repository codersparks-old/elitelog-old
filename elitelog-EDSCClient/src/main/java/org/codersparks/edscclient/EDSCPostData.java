package org.codersparks.edscclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)	
public class EDSCPostData {

	private int ver = 2;
	private boolean test = false;
	private int outputMode = 1;
	private EDSCFilter filter = null;
	public int getVer() {
		return ver;
	}
	public void setVer(int ver) {
		this.ver = ver;
	}
	public boolean isTest() {
		return test;
	}
	public void setTest(boolean test) {
		this.test = test;
	}
	public int getOutputMode() {
		return outputMode;
	}
	public void setOutputMode(int outputMode) {
		this.outputMode = outputMode;
	}
	public EDSCFilter getFilter() {
		return filter;
	}
	public void setFilter(EDSCFilter filter) {
		this.filter = filter;
	}
	
	
}
