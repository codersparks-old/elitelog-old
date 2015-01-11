package org.codersparks.edscclient;

import org.codersparks.edscclient.EDSCFilter.FilterException;

public class Application {

	public static void main(String[] args) throws FilterException {
		
		EDSCClient client = new EDSCClient();
		
		float[][] coords = new float[3][2];
		
		for(int i = 0; i < 3; i++) {
			
			coords[i][0] = -20;
			coords[i][1] = 20;
		}
		
		EDSCFilter filter = new EDSCFilter.EDSCFilterBuilder()
								.coordcube(coords)
								.date("2014-11-25 00:00:00")
								.build();
		
		EDSCSystemsResultWrapper response = client.getSystems(true, 2, filter);
		
		System.out.println("Response: \n" + response.getResult());

	}

}
