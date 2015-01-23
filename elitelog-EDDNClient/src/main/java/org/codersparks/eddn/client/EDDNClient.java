package org.codersparks.eddn.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;

import org.codersparks.eddn.model.EDDNCommodityData;
import org.codersparks.eddn.model.EDDNCommodityDataMessage;
import org.codersparks.elitelog.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class EDDNClient {
	
	private static final String DEFAULT_SUBSCRIPTION_ADDRESS = "tcp://eddn-relay.elite-markets.net:9500";
	
	private static Logger logger = LoggerFactory.getLogger(EDDNClient.class);
	
	private final Inflater inflater;
	private final Context context;
	private final Socket subscriber;
	
	
	public EDDNClient(String eddnSubscriptionString) {
		this.inflater = new Inflater();
		this.context = ZMQ.context(1);
		this.subscriber = context.socket(ZMQ.SUB);
		this.subscriber.connect(eddnSubscriptionString);
		this.subscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);
		
	}
	
	public EDDNClient() {
		this(DEFAULT_SUBSCRIPTION_ADDRESS);
	}
	
	public EDDNCommodityData readCommodityData() throws Exception {
		
		inflater.reset();
		logger.debug("Waiting for EDDN Message");
		
		byte[] message = subscriber.recv();
		
		inflater.setInput(message);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(message.length);
		
		byte[] buffer = new byte[1024];
		
		while(!inflater.finished()) {
			int count = inflater.inflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		
		outputStream.close();
		
		byte[] inflatedData = outputStream.toByteArray();
		
		EDDNCommodityData data = JSONUtils.fromJson(inflatedData,
				EDDNCommodityData.class, true);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Commodity Data received: " + data.toString());
		}
		
		return data;
 	}

}
