package org.codersparks.eddn.client;

import java.util.concurrent.BlockingQueue;

import org.codersparks.eddn.model.EDDNCommodityData;
import org.codersparks.eddn.model.EDDNCommodityDataMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EDDNMessageProducer implements Runnable{
	
	private static Logger logger = LoggerFactory.getLogger(EDDNMessageProducer.class);
	private static final String EDDN_TEST_SCHEMA="http://schemas.elite-markets.net/eddn/commodity/1/test";
	private static final String EDDN_DATA_SCHEMA="http://schemas.elite-markets.net/eddn/commodity/1";
	
	private EDDNClient client;
	private BlockingQueue<EDDNCommodityDataMessage> queue;
	private boolean test;
	
	public EDDNMessageProducer(BlockingQueue<EDDNCommodityDataMessage> queue, EDDNClient client, boolean test) {
		this.client = client;
		this.queue = queue;
		this.test = test;
	}

	@Override
	public void run() {
		
		EDDNCommodityData commodityData;
		
		while(true) {
			
			try {
				commodityData = client.readCommodityData();
				logger.debug("Data received: " + commodityData.toString());
				boolean offered = false;
				while(offered == false) {
					if(
							(commodityData.get$schemaRef().equals(EDDN_TEST_SCHEMA) && this.test) ||
							(commodityData.get$schemaRef().equals(EDDN_DATA_SCHEMA)))
					{
						offered = queue.offer(commodityData.getMessage());
					} else {
						logger.debug("Breaking as data is not wanted...");
						break;
					}
				}
				logger.debug("Loaded data to queue");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
	}
	
	

}
