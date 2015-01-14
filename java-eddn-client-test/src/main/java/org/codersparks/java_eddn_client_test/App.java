package org.codersparks.java_eddn_client_test;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

import org.apache.commons.io.IOUtils;
import org.codersparks.eddn.model.EDDNCommodityData;
import org.codersparks.elitelog.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * Hello world!
 *
 */
public class App {

	private static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main2(String[] args) throws Exception {

		List<String> systemlist = Arrays.asList(IOUtils.toString(
				App.class.getClassLoader()
						.getResourceAsStream("systemtest.txt")).split(";"));

		for (String s : systemlist) {
			System.out.println(s);
		}

	}

	public static void main(String[] args) throws Exception {

		logger.debug("Hello World!");

		List<String> systemlist = Arrays.asList(IOUtils.toString(
				App.class.getClassLoader()
						.getResourceAsStream("systemtest.txt")).split(";"));

		logger.debug("System list length: " + systemlist.size());
		Context context = ZMQ.context(1);
		Socket subscriber = context.socket(ZMQ.SUB);
		
		int counter = 0;
		try {

			// First connect subscriber socket

			subscriber.connect("tcp://eddn-relay.elite-markets.net:9500");
			subscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);

			while (true) {

				Inflater inflater = new Inflater();

				System.out.println("Waiting for message...");
				byte[] message = subscriber.recv();

				inflater.setInput(message);

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
						message.length);
				byte[] buffer = new byte[1024];
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
				}

				outputStream.close();

				EDDNCommodityData data = JSONUtils.fromJson(
						outputStream.toByteArray(), EDDNCommodityData.class,
						true);

				logger.debug(data.toString());

				if (systemlist.contains(data.getMessage().getSystemName())) {
					System.out.println("******* Found in list *********");
					++counter;
					
					//System.out.println(data.getMessage().toString());
				}
				logger.debug("Number items with station in list: " + counter);
			}

		} finally {
			subscriber.close();
			context.term();
		}

	}
}
