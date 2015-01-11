package org.codersparks.edscclient;

/**
 * For some obscure reason the expected POST data for EDSC expects the query
 * object to be wrapped in the following <code>{ data : queryObject } </code>
 * rather than just an anonymous object this provides that wrapper
 * 
 * @author codersparks
 *
 */
public class EDSCPostDataWrapper {

	private EDSCPostData data;
	
	public EDSCPostDataWrapper(EDSCPostData data) {
		super();
		this.data = data;
	}

	public EDSCPostData getData() {
		return data;
	}

	public void setData(EDSCPostData data) {
		this.data = data;
	}
	
	
}
