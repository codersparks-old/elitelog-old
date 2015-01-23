package org.codersparks.eddn.client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.codersparks.eddn.model.EDDNCommodityDataMessage;
import org.codersparks.elitelog.client.EliteLogClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class App {

	private static Logger logger = LoggerFactory.getLogger("EDDNClient-App");

	public static void main(String[] args) throws Exception {
		
		boolean test = false;
		
		Options options = new Options();
		options.addOption(new Option("h", "help", false,
				"Show this help message"));
		options.addOption(new Option(
				"t",
				"test",
				false,
				"Use test data"));
		try {
			CommandLineParser parser = new GnuParser();
			CommandLine cmdLine = parser.parse(options, args);

			if (cmdLine.hasOption("h")) {
				printUsage(options);
				System.exit(1);
			}
			if(cmdLine.hasOption("t")) {
				test=true;
			}
		
			logger.info("Using test data: " + test);
			EDDNClient client = new EDDNClient();
			RestTemplate template = new RestTemplate();
			EliteLogClient eliteLogClient = new EliteLogClient(System.getenv("OPENSHIFT_GEAR_DNS"), template);
			BlockingQueue<EDDNCommodityDataMessage> queue = new ArrayBlockingQueue<EDDNCommodityDataMessage>(10000);
			
			EDDNMessageConsumer consumer = new EDDNMessageConsumer(queue, eliteLogClient, 2);
			EDDNMessageProducer producer = new EDDNMessageProducer(queue, client, test);
			
			ExecutorService service = Executors.newCachedThreadPool();
			
			service.execute(consumer);
			service.execute(producer);
		} catch (ParseException e) {
			logger.error(
					"Error parsing command line options: "
							+ e.getLocalizedMessage(), e);
			System.err.println("Error parsing command line options: "
					+ e.getLocalizedMessage());
			printUsage(options);
			System.exit(1);
		}	
		
	}

	private static void printUsage(Options options) {
		HelpFormatter hf = new HelpFormatter();
		hf.printHelp("java -jar systemlogger.jar", options);
	}

}
