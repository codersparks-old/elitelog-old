package org.codersparks.java_eddn_client_test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		
		System.out.println("Hello World!");

		Context context = ZMQ.context(1);
		Socket subscriber = context.socket(ZMQ.SUB);
		try {
			

			// First connect subscriber socket
			
			subscriber.connect("tcp://eddn-relay.elite-markets.net:9500");
			subscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);

			while (true) {
				
				
				Inflater inflater = new Inflater();
				
				System.out.println("Waiting for message...");
				byte[] message = subscriber.recv();
				
				inflater.setInput(message);
				
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(message.length);
				byte[] buffer = new byte[1024];
				while(!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				
				outputStream.close();
				
				String s = new String(outputStream.toByteArray());
				
				System.out.println(s);
				
			}

		} finally {
			System.out.println("Shutting down...");
			subscriber.close();
			context.term();
		}

	}
}
