package org.codersparks.edscclient;

public class Application {

	public static void main(String[] args) {
		
		EDSCClient client = new EDSCClient();
		
		EDSCFilter filter = new EDSCFilter.EDSCFilterBuilder().build();
		
		String response = client.getSystems(true, 2, filter);
		
		System.out.println("Response: \n" + response);

	}

}
