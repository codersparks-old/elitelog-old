package org.codersparks.elite.systemlogger;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.input.Tailer;
import org.codersparks.edscclient.EDSCClient;
import org.codersparks.elitelog.client.EliteLogClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@ComponentScan
public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		Options options = new Options();
		options.addOption(new Option("h", "help", false,
				"Show this help message"));
		options.addOption(new Option(
				"d",
				"logDirectory",
				true,
				"The Elite Dangerous log directory that contains the netLog file to use as the source of current system"));
		options.addOption(new Option("n", "name", true,
				"The commander name for who the system should be logged"));
		options.addOption(new Option(
				"e",
				"elitelogUrl",
				true,
				"Base URL for the elitelog webapp"));
		options.addOption(new Option(
				"r",
				"sphereRadius",
				true,
				"Radius to use to find the systems of interest"
		));

		try {
			CommandLineParser parser = new GnuParser();
			CommandLine cmdLine = parser.parse(options, args);

			String logDirectoryName;
			String commanderName;
			String elitelogBaseUrl;

			if (cmdLine.hasOption("h")) {
				printUsage(options);
				System.exit(1);
			}

			if (cmdLine.hasOption("d")) {
				logDirectoryName = cmdLine.getOptionValue("d");
			} else {
				throw new ParseException("ERROR: No log directory supplied");
			}

			if (cmdLine.hasOption("n")) {
				commanderName = cmdLine.getOptionValue("n");
			} else {
				throw new ParseException("ERROR: No commander name supplied");
			}

			if (cmdLine.hasOption("e")) {
				elitelogBaseUrl = cmdLine.getOptionValue("e");

			} else {
				throw new ParseException(
							"ERROR: No elitelog base url supplied");
				
			}

			logger.debug("Log directory: " + logDirectoryName);
			logger.debug("Commander Name: " + commanderName);

			File logFile = findLogFile(logDirectoryName);
			logger.info("Using log file: " + logFile.getAbsolutePath());

			// Create the client for the EDSC web site
			EDSCClient edscClient = new EDSCClient();

			// Create the client for the elitelog rest api
			EliteLogClient eliteLogClient = new EliteLogClient(elitelogBaseUrl, new RestTemplate());

			SystemLogFileTailerListener listener;
			
			if(cmdLine.hasOption("r")) {
				listener = new SystemLogFileTailerListener(
					edscClient, eliteLogClient, commanderName, Integer.parseInt(cmdLine.getOptionValue("r")));
			} else {
				listener = new SystemLogFileTailerListener(edscClient, eliteLogClient, commanderName);
			}
			Tailer tailer = new Tailer(logFile, listener, 12000);
			tailer.run();

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

	private static File findLogFile(String logDirectoryName) {
		final PathMatcher matcher = FileSystems.getDefault().getPathMatcher(
				"glob:netLog*.log");
		File logDirectory = new File(logDirectoryName);
		File[] files = logDirectory.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {

				Path path = file.toPath().getFileName();

				if (path != null && matcher.matches(path)) {
					return true;
				}

				return false;
			}
		});

		long lastMod = Long.MIN_VALUE;

		File choice = null;

		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}

		return choice;
	}
}
